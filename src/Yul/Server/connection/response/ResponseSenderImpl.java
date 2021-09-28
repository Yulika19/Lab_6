package Yul.Server.connection.response;

        import Yul.General.general.Response;

        import java.io.ByteArrayOutputStream;
        import java.io.IOException;
        import java.io.ObjectOutputStream;
        import java.nio.ByteBuffer;
        import java.nio.channels.SelectionKey;
        import java.nio.channels.Selector;
        import java.nio.channels.SocketChannel;
        import java.util.Iterator;
        import java.util.Set;

public class ResponseSenderImpl implements ResponseSender {
    private Selector selector;

    public void sendResponse(Selector selector, Response response) throws IOException {
        this.selector = selector;
        sendBytes(serializeResponse(response));
    }

    private void sendBytes(byte[] bytes) throws IOException {
        ByteBuffer buf = ByteBuffer.wrap(bytes);
        SocketChannel channel = null;
        while (channel == null) {
            selector.select();
            Set<SelectionKey> keys = selector.selectedKeys();
            for (Iterator<SelectionKey> iter = keys.iterator(); iter.hasNext(); ) {
                SelectionKey key = iter.next();
                if (key.isWritable()) {
                    channel = (SocketChannel) key.channel();
                    channel.write(buf);
                }
                iter.remove();
            }
        }
    }

    private byte[] serializeResponse(Response response) throws IOException {
        ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
        ObjectOutputStream stream = new ObjectOutputStream(byteStream);
        stream.writeObject(response);
        return byteStream.toByteArray();
    }
}