package Yul.Client.connection.response;

        import Yul.General.general.Response;

        import java.io.IOException;
        import java.nio.channels.SocketChannel;

public interface ResponseReader {
    Response readResponse(SocketChannel socketChannel) throws IOException, ClassNotFoundException;
}