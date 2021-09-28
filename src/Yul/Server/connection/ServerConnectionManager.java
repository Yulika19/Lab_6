package Yul.Server.connection;

        import java.io.IOException;
        import java.nio.channels.Selector;

public interface ServerConnectionManager {
    void openConnection(int port) throws IOException;

    void closeConnection() throws IOException;

    Selector select() throws IOException;
}