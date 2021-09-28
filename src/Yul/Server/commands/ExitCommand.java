package Yul.Server.commands;

        import Yul.Server.connection.ServerConnectionManager;
        import Yul.Server.fileWorker.StudyGroupWriter;
        import Yul.Server.server.Server;
        import Yul.Server.server.ServerApp;

        import java.io.IOException;

public class ExitCommand implements ServerCommand {
    private final ServerConnectionManager connectionManager;
    private final StudyGroupWriter writer;
    private final ServerApp server;

    public ExitCommand(StudyGroupWriter writer, ServerConnectionManager connectionManager, Server server) {
        this.writer = writer;
        this.connectionManager = connectionManager;
        this.server = server;
    }

    @Override
    public void execute() {
        writer.write();
        try {
            connectionManager.closeConnection();
            server.exit();
        } catch (IOException ignore) {
        }
    }
}