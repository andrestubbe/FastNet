package fastnet;

import fastnet.FastNet;
import java.nio.ByteBuffer;

public final class Demo {
    public static void main(String[] args) {
        FastNet.sendUdp("127.0.0.1", 9999, ByteBuffer.wrap("telemetry".getBytes()))
                .thenAccept(bytes -> System.out.println("Sent telemetry bytes: " + bytes))
                .join();
    }
}
