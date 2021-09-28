package Yul.Server.server;

        import Yul.General.exceptions.CommandIsNotExistException;
        import Yul.General.exceptions.UnsupportedFileException;
        import Yul.General.general.*;
        import Yul.General.validation.StudyGroupBuilder;
        import Yul.General.validation.StudyGroupBuilderImpl;
        import Yul.General.validation.StudyGroupValidatorImpl;
        import Yul.Server.collection.CollectionManager;
        import Yul.Server.commands.*;
        import Yul.Server.connection.ServerConnectionManager;
        import Yul.Server.connection.request.RequestReader;
        import Yul.Server.connection.response.ResponseCreator;
        import Yul.Server.connection.response.ResponseSender;
        import Yul.Server.fileWorker.StudyGroupReader;
        import Yul.Server.fileWorker.StudyGroupWriter;

        import java.io.IOException;
        import java.io.StreamCorruptedException;
        import java.nio.channels.ClosedSelectorException;
        import java.nio.channels.Selector;
        import java.util.NoSuchElementException;

public class Server implements ServerApp, IOimpl {
    private final StudyGroupReader reader;
    private final StudyGroupWriter writer;
    private final CollectionManager collectionManager;
    private final ServerCommandReader commandReader;
    private final ServerConnectionManager connectionManager;
    private final ResponseCreator responseCreator;
    private final RequestReader requestReader;
    private final ResponseSender responseSender;
    private boolean isRunning = true;


    public Server(StudyGroupReader reader,
                  StudyGroupWriter writer,
                  CollectionManager collectionManager,
                  ServerCommandReader commandReader,
                  ServerConnectionManager connectionManager,
                  ResponseCreator responseCreator,
                  RequestReader requestReader,
                  ResponseSender responseSender) {
        this.reader = reader;
        this.writer = writer;
        this.collectionManager = collectionManager;
        this.commandReader = commandReader;
        this.connectionManager = connectionManager;
        this.responseCreator = responseCreator;
        this.requestReader = requestReader;
        this.responseSender = responseSender;
    }

    /**
     * Начинает работу программы Сервера
     * Обеспечивает слушание одного из клиентов в порядке очереди
     *
     * @param port     порт по которому идет слушание
     * @param fileName имя файла, из которого идет чтение и в который идет запись
     */
    @Override
    public void start(int port, String fileName) {
        prepareToStart(port, fileName);
        startConsoleDaemon(commandReader);
        accept(port);
    }

    private void accept(int port) {
        Selector selector;
        while (isRunning) {
            try {
                connectionManager.openConnection(port);
                try {
                    selector = connectionManager.select();
                } catch (ClosedSelectorException e) {
                    return;
                }
                Request request;
                try {
                    request = requestReader.readRequest(selector);
                } catch (ClosedSelectorException e) {
                    errPrint("Клиент пока не отправил сообщение\nВведите еще раз exit чтобы выйти");
                    continue;
                } catch (StreamCorruptedException e) {
                    connectionManager.closeConnection();
                    continue;
                }
                try {
                    Response response;
                    if (request.getRequestType().equals(RequestType.EXECUTE_COMMAND)) {
                        String addingString = request.getCommandName().trim();
                        if (request.getArg() != null)
                            addingString = addingString + " " + request.getArg().trim();
                        commandReader.executeCommand(addingString, collectionManager.getServerStudyGroup(request.getStudyGroup()));
                        response = responseCreator.getResponse();
                    } else if (request.getRequestType().equals(RequestType.ERROR_TYPE_REQUEST)) {
                        Response responseError = new Response(ResponseType.ERROR_RESPONSE, "Your message is too big");
                        responseSender.sendResponse(selector, responseError);
                        connectionManager.closeConnection();
                        continue;
                    } else {
                        Command command = commandReader.getCommandByName(request.getCommandName().trim().toLowerCase());
                        if (request.getCommandName().trim().equalsIgnoreCase("execute_script")) {
                            responseSender.sendResponse(selector, new Response(ResponseType.BASIC_RESPONSE, ""));
                            continue;
                        }
                        if (command == null) {
                            response = responseCreator.getResponse();
                            response.setMessage(response.getMessage() + "Данной команды не существует\n");
                        } else if (command.isStudyGroupCommand()) {
                            response = responseCreator.getResponse("", ResponseType.STUDY_GROUP_RESPONSE);
                        } else {
                            String addingString = request.getCommandName();
                            if (request.getArg() != null)
                                addingString += " " + request.getArg();
                            commandReader.executeCommand(addingString, null);
                            response = responseCreator.getResponse();
                        }
                    }
                    responseSender.sendResponse(selector, response);
                    connectionManager.closeConnection();
                } catch (CommandIsNotExistException e) {
                    Response response = new Response(ResponseType.ERROR_RESPONSE, e.getMessage());
                    response.setMessage(response.getMessage());
                    responseSender.sendResponse(selector, response);
                    connectionManager.closeConnection();
                }
            } catch (IOException | ClassNotFoundException ioe) {
                ioe.printStackTrace();
                try {
                    connectionManager.closeConnection();
                } catch (IOException e) {
                    isRunning = false;
                }
            }
        }
    }

    /**
     * Завершает последующие итерации слушания клиента
     * Другими словами, работу программы
     */
    @Override
    public void exit() {
        try {
            connectionManager.closeConnection();
            isRunning = false;
            commandReader.executeServerCommand("save");
        } catch (IOException | CommandIsNotExistException ioException) {
            ioException.printStackTrace();
        }
    }

    /**
     * Начинает работу второго потока - демона, отвечающего за ввод команд на сервере
     * Обеспечивает ввод команд на сервере.
     *
     * @param commandReader
     */
    private void startConsoleDaemon(ServerCommandReader commandReader) {
        Thread consoleThread = new Thread(() -> {
            IOimpl userIO = new IOimpl() {
            };
            while (!Thread.interrupted()) {
                try {
                    String str = userIO.readLine();
                    if (str == null)
                        throw new NoSuchElementException();
                    commandReader.executeServerCommand(str);
                } catch (CommandIsNotExistException ioe) {
                    println(ioe.getMessage());
                } catch (NoSuchElementException | IOException e) {
                    errPrint("You can't input this\nThe work of Server will be stopped");
                    exit();
                    return;
                }
            }
        });
        consoleThread.setDaemon(true);
        consoleThread.start();
    }

    /**
     * Добавляет все команды для работы с коллекцией
     */
    private void addCommands() {
        StudyGroupBuilder builder = new StudyGroupBuilderImpl(getReader(), false, new StudyGroupValidatorImpl());
        commandReader.addCommand("add", new AddCommand(collectionManager));
        commandReader.addCommand("clear", new ClearCommand(collectionManager));
        commandReader.addCommand("count_less_than_semester", new CountLessThanSemesterCommand(collectionManager, builder, responseCreator));
        commandReader.addCommand("history", new HistoryCommand(commandReader, responseCreator));
        commandReader.addCommand("info", new InfoCommand(collectionManager));
        commandReader.addCommand("print_ascending", new PrintAscendingCommand(collectionManager, responseCreator));
        commandReader.addCommand("remove_by_id", new RemoveByIdCommand(collectionManager, builder, responseCreator));
        commandReader.addCommand("remove_first", new RemoveFirstCommand(collectionManager));
        commandReader.addCommand("remove_greater", new RemoveGreaterCommand(collectionManager));
        commandReader.addCommand("show", new ShowCommand(collectionManager, responseCreator));
        commandReader.addCommand("sum_of_students_count", new SumOfStudentsCountCommand(collectionManager));
        commandReader.addCommand("update", new UpdateCommand(collectionManager, responseCreator));
        commandReader.addCommand("help", new HelpCommand(commandReader.getCommandMap(), responseCreator));

    }

    /**
     * Добавляет все серверные команды
     */
    private void addServerCommands() {
        commandReader.addServerCommand("save", new SaveCommand(writer));
        commandReader.addServerCommand("exit", new ExitCommand(writer, connectionManager, this));
    }

    /**
     * Подготовка к работе программы
     * Чтение коллекции из файла
     * Инициализация полей
     * Добавление команд
     *
     * @param port
     * @param filePath
     */
    private void prepareToStart(int port, String filePath) {
        try {
            reader.setFilePath(filePath);
            writer.setFilePath(filePath);
            reader.loadInput();
            addServerCommands();
            addCommands();
        } catch (UnsupportedFileException e) {
            errPrint(e.getMessage());
            reader.inputFilePath();
            prepareToStart(port, reader.getFilePath());
        }
    }
}