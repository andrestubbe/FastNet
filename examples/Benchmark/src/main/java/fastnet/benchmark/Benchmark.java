package fastnet.benchmark;

import fastnet.FastNet;
import java.nio.ByteBuffer;

public final class Benchmark {
    public static void main(String[] args) {
        long start = System.nanoTime();
        for (int index = 0; index < 100; index++) {
            FastNet.sendUdp("127.0.0.1", 9999, ByteBuffer.wrap(new byte[256])).join();
        }
        System.out.printf("100 UDP telemetry packets in %.3f ms%n", (System.nanoTime() - start) / 1_000_000.0);
    }
}
