package Yul.Client.client;

        import Yul.Client.commands.ClientCommandReaderImpl;
        import Yul.Client.connection.ClientConnectionManagerImpl;
        import Yul.Client.connection.request.ClientRequestSender;
        import Yul.Client.connection.response.ResponseReaderImpl;

public class Main {
    public static void main(String[] args) {
        if (args.length == 1) {
            try {
                ClientApp client = new Client(new ClientCommandReaderImpl(),
                        new ClientConnectionManagerImpl(),
                        new ClientRequestSender(),
                        new ResponseReaderImpl(),
                        Integer.parseInt(args[0])
                );
                client.start(Integer.parseInt(args[0]));
            } catch (NumberFormatException e) {
                System.err.println(e.getMessage());
            }
        } else
            System.err.println("Input arguments: port");
    }
}