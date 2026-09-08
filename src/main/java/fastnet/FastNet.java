package fastnet;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.nio.channels.SocketChannel;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;

/** Asynchronous network facade with a portable Java fallback. */
public final class FastNet {
    private static final DatagramChannel UDP_CHANNEL = openDatagramChannel();

    private FastNet() {
    }

    public static CompletableFuture<SocketChannel> connect(String host, int port) {
        Objects.requireNonNull(host, "host");
        return CompletableFuture.supplyAsync(() -> {
            try {
                SocketChannel channel = SocketChannel.open();
                channel.connect(new InetSocketAddress(host, port));
                return channel;
            } catch (IOException exception) {
                throw new NetworkException("TCP connection failed", exception);
            }
        });
    }

    public static CompletableFuture<Integer> sendUdp(String host, int port, ByteBuffer payload) {
        Objects.requireNonNull(host, "host");
        Objects.requireNonNull(payload, "payload");
        ByteBuffer copy = payload.asReadOnlyBuffer();
        return CompletableFuture.supplyAsync(() -> {
            try {
                synchronized (UDP_CHANNEL) {
                    return UDP_CHANNEL.send(copy, new InetSocketAddress(host, port));
                }
            } catch (IOException exception) {
                throw new NetworkException("UDP send failed", exception);
            }
        });
    }

    private static DatagramChannel openDatagramChannel() {
        try {
            return DatagramChannel.open();
        } catch (IOException exception) {
            throw new ExceptionInInitializerError(exception);
        }
    }

    public static final class NetworkException extends RuntimeException {
        public NetworkException(String message, Throwable cause) {
            super(message, cause);
        }
    }
}
