package Yul.Server.server;

public interface ServerApp {
    void start(int port, String fileName);

    void exit();
}