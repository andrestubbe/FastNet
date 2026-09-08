package fastnet;

import java.nio.ByteBuffer;

public final class UdpThroughputBenchmark {
    private UdpThroughputBenchmark() {
    }

    public static void main(String[] args) {
        ByteBuffer payload = ByteBuffer.allocate(256);
        long start = System.nanoTime();
        for (int index = 0; index < 1000; index++) {
            FastNet.sendUdp("127.0.0.1", 9, payload).join();
        }
        long elapsed = System.nanoTime() - start;
        System.out.printf("datagrams=1,000, elapsedMs=%.2f%n", elapsed / 1_000_000.0);
    }
}
