package Yul.Server.connection;

        import java.io.IOException;
        import java.net.InetSocketAddress;
        import java.nio.channels.SelectionKey;
        import java.nio.channels.Selector;
        import java.nio.channels.ServerSocketChannel;
        import java.nio.channels.SocketChannel;
        import java.util.Iterator;
        import java.util.Set;

public class ServerConnectionManagerImpl implements ServerConnectionManager {
    private ServerSocketChannel serverChannel;
    private Selector selector;

    @Override
    public void openConnection(int port) throws IOException {
        selector = Selector.open();
        serverChannel = ServerSocketChannel.open();
        serverChannel.bind(new InetSocketAddress("localhost", port));
        serverChannel.configureBlocking(false);
        serverChannel.register(selector, SelectionKey.OP_ACCEPT);
    }

    @Override
    public void closeConnection() throws IOException {
        selector.close();
        serverChannel.socket().close();
        serverChannel.close();
    }

    @Override
    public Selector select() throws IOException {
        selector.select();
        Set<SelectionKey> keys = selector.selectedKeys();
        Iterator<SelectionKey> iterator = keys.iterator();
        while (iterator.hasNext()) {
            SelectionKey key = iterator.next();
            iterator.remove();
            if (key.isValid() && key.isAcceptable()) {
                SocketChannel clientChannel = serverChannel.accept();
                clientChannel.configureBlocking(false);
                clientChannel.register(selector, SelectionKey.OP_READ);
            }
        }
        return selector;
    }
}