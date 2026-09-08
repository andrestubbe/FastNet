package fastnet;

import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public final class TcpLoopbackDemo {
    private TcpLoopbackDemo() {
    }

    public static void main(String[] args) throws Exception {
        try (ServerSocket server = new ServerSocket(0)) {
            Thread worker = new Thread(() -> {
                try (Socket socket = server.accept()) {
                    socket.getOutputStream().write("telemetry-ok".getBytes(StandardCharsets.UTF_8));
                } catch (Exception exception) {
                    throw new RuntimeException(exception);
                }
            });
            worker.start();
            try (Socket socket = FastNet.connect("127.0.0.1", server.getLocalPort()).join()) {
                System.out.println(new String(socket.getInputStream().readAllBytes(), StandardCharsets.UTF_8));
            }
            worker.join();
        }
    }
}
