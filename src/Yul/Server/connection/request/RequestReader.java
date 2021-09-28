package Yul.Server.connection.request;

        import Yul.General.general.Request;

        import java.io.IOException;
        import java.nio.channels.Selector;

public interface RequestReader {
    Request readRequest(Selector selector) throws IOException, ClassNotFoundException;
}