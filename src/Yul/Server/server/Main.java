package Yul.Server.server;

        import Yul.Server.collection.CollectionManagerImpl;
        import Yul.Server.commands.ServerCommandReaderImpl;
        import Yul.Server.connection.ServerConnectionManagerImpl;
        import Yul.Server.connection.request.RequestReaderImpl;
        import Yul.Server.connection.response.ResponseCreator;
        import Yul.Server.connection.response.ResponseCreatorImpl;
        import Yul.Server.connection.response.ResponseSenderImpl;
        import Yul.Server.fileWorker.CSVStudyGroupReader;
        import Yul.Server.fileWorker.CSVStudyGroupWriter;

public class Main {
    public static void main(String[] args) {
        if (args.length == 2) {
            try {
                ResponseCreator responseCreator = new ResponseCreatorImpl();
                CollectionManagerImpl collectionManager = new CollectionManagerImpl(responseCreator);
                CSVStudyGroupReader reader = new CSVStudyGroupReader(collectionManager);
                CSVStudyGroupWriter writer = new CSVStudyGroupWriter(collectionManager);
                ServerCommandReaderImpl commandReader = new ServerCommandReaderImpl();
                ServerConnectionManagerImpl connectionManager = new ServerConnectionManagerImpl();

                ServerApp server = new Server(reader,
                        writer,
                        collectionManager,
                        commandReader,
                        connectionManager,
                        responseCreator,
                        new RequestReaderImpl(),
                        new ResponseSenderImpl());

                server.start(Integer.parseInt(args[0]), args[1]);

            } catch (NumberFormatException e) {
                System.err.println(e.getMessage());
            }
        } else
            System.err.println("Input arguments: port fileName");
    }
}