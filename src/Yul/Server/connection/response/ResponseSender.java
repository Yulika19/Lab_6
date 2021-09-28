package Yul.Server.connection.response;

        import Yul.General.general.Response;

        import java.io.IOException;
        import java.nio.channels.Selector;

public interface ResponseSender {
    void sendResponse(Selector selector, Response response) throws IOException;
}