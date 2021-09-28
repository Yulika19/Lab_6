package Yul.Client.client;

        import Yul.Client.commands.ClientCommandReaderImpl;
        import Yul.Client.commands.ClientExitCommand;
        import Yul.Client.commands.ExecuteScriptCommand;
        import Yul.Client.connection.ClientConnectionManager;
        import Yul.Client.connection.request.RequestSender;
        import Yul.Client.connection.response.ResponseReader;
        import Yul.General.exceptions.CommandIsNotExistException;
        import Yul.General.general.IOimpl;
        import Yul.General.general.Request;
        import Yul.General.general.Response;
        import Yul.General.general.ResponseType;
        import Yul.General.validation.StudyGroupBuilder;
        import Yul.General.validation.StudyGroupBuilderImpl;
        import Yul.General.validation.StudyGroupValidatorImpl;

        import java.io.IOException;
        import java.io.StreamCorruptedException;
        import java.net.ConnectException;
        import java.nio.BufferOverflowException;
        import java.nio.channels.SocketChannel;
        import java.util.NoSuchElementException;


public class Client implements ClientApp, IOimpl {
    private final ClientCommandReaderImpl commandReader;
    private final ClientConnectionManager connectionManager;
    private final RequestSender requestSender;
    private final ResponseReader responseReader;
    private final int port;
    private StudyGroupBuilder studyGroupBuilder;
    private boolean isRunning;

    public Client(ClientCommandReaderImpl commandReader,
                  ClientConnectionManager connectionManager,
                  RequestSender requestSender,
                  ResponseReader responseReader,
                  int port) {
        this.commandReader = commandReader;
        this.connectionManager = connectionManager;
        this.requestSender = requestSender;
        this.responseReader = responseReader;
        this.port = port;
        studyGroupBuilder = new StudyGroupBuilderImpl(getReader(), false, new StudyGroupValidatorImpl());
        addCommands();
        isRunning = true;
    }

    /**
     * Метод начинает работу программы клиента
     * Обеспечивает ввод пользователя и контролирует общение с сервером
     *
     * @param port
     */
    @Override
    public void start(int port) {
        println(">The work is started:");
        while (isRunning) {
            String userInput = "";
            try {
                userInput = readLine().trim();
                commandReader.executeCommand(userInput, null);
            } catch (NoSuchElementException | NullPointerException e) {
                errPrint("You can't input this\nThe work of Client will be stopped");
                return;
            } catch (IOException e) {
                errPrint(e.getMessage());
            } catch (CommandIsNotExistException e) {
                try {
                    Response response = communicateWithServer(userInput);
                    if (response == null) {
                        println("Ответ не получен, подождите пока сервер будет доступен или исправьте ошибку с буфером");
                        return;
                    }
                    println(response.getMessage());
                } catch (IOException | ClassNotFoundException ioException) {
                    ioException.printStackTrace();
                }
            }
        }
    }

    /**
     * Останавливает последующие итерации работы программы
     */
    @Override
    public void exit() {
        isRunning = false;
    }

    /**
     * Отправляет запрос серверу
     *
     * @param userString ввод пользователя
     * @return Response - ответ сервера
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public Response communicateWithServer(String userString) throws IOException, ClassNotFoundException {
        String[] fullCommand = userString.trim().split("\\s", 2);
        SocketChannel socketChannel;
        try {
            socketChannel = connectionManager.openConnection(port);
        } catch (ConnectException e) {
            println("Сервер недоступен");
            exit();
            return null;
        }
        Request request = requestSender.createBasicRequest(fullCommand[0]);
        if (fullCommand.length > 1)
            request.setArg(fullCommand[1]);
        requestSender.sendRequest(socketChannel, request);
        Response response = null;
        try {
            response = responseReader.readResponse(socketChannel);
        } catch (BufferOverflowException e) {
            System.out.println("Данные не поместились в буфер");
        }
        connectionManager.closeConnection();
        if (response == null) {
            exit();
            println("Соединение разорзавно");
            return null;
        } else if (response.getResponseType().equals(ResponseType.STUDY_GROUP_RESPONSE)) {
            return reCommunicateWithServer(request);
        }
        return response;
    }

    /**
     * Отправляет запрос серверу, вместе с элементом коллекции
     *
     * @param request
     * @return Response - ответ сервера
     * @throws IOException
     * @throws ClassNotFoundException
     */
    private Response reCommunicateWithServer(Request request) throws IOException, ClassNotFoundException {
        String arg = request.getArg();
        SocketChannel socketChannel = connectionManager.openConnection(port);
        request = requestSender.createExecuteRequest(request.getCommandName(), studyGroupBuilder.askStudyGroup());
        request.setArg(arg);
        requestSender.sendRequest(socketChannel, request);
        Response response = null;
        try {
            response = responseReader.readResponse(socketChannel);
        } catch (StreamCorruptedException e) {
            return response;
        } catch (BufferOverflowException e) {
            response = new Response();
            response.setMessage("Данные не поместились в буфер");
        }
        connectionManager.closeConnection();
        return response;
    }

    /**
     * Добавляет все клиентские команды
     */
    private void addCommands() {
        commandReader.addCommand("exit", new ClientExitCommand(this));
        commandReader.addCommand("execute_script", new ExecuteScriptCommand(this, commandReader));
    }
}