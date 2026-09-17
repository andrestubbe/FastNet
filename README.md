# FastNet 0.1.0 [ALPHA-2026-09] — Ultra-Fast Native Network Engine for Java

[![Status](https://img.shields.io/badge/status-0.1.0-brightgreen.svg)](https://github.com/andrestubbe/FastNet/releases/tag/0.1.0)
[![Java](https://img.shields.io/badge/Java-17+-blue.svg)](https://www.java.com)
[![Platform](https://img.shields.io/badge/Platform-Windows%2010+-lightgrey.svg)]()
[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT)
[![JitPack](https://img.shields.io/badge/JitPack-ready-green.svg)](https://jitpack.io/#andrestubbe/FastNet)

---

**⚡ Ultra-fast asynchronous networking for the FastJava ecosystem.**

**FastNet** is a low-latency transport engine for telemetry, game servers, crawlers and real-time services. It exposes a compact asynchronous API today and provides a clear native boundary for WinSock2, IOCP and direct-memory ring buffers on Windows.

[**Run the TCP Loopback Demo**](examples/Demo/src/main/java/fastnet/TcpLoopbackDemo.java) | [**Run the UDP Benchmark**](examples/Benchmark/src/main/java/fastnet/UdpThroughputBenchmark.java)

---

## Quick Start

```java
import fastnet.FastNet;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

public class Example {
    public static void main(String[] args) {
        FastNet.sendUdp("127.0.0.1", 9999,
                        ByteBuffer.wrap("telemetry".getBytes(StandardCharsets.UTF_8)))
                .thenAccept(bytes -> System.out.println("Sent bytes: " + bytes));
    }
}
```

## Table of Contents

- [Why FastNet?](#why-fastnet)
- [Quick Start](#quick-start)
- [Features](#features)
- [Real-World Scenarios](#real-world-scenarios)
- [Performance Benchmarks](#performance-benchmarks)
- [API Quick Reference](#api-quick-reference)
- [Technical Examples & Hero Demos](#technical-examples--hero-demos)
- [Installation](#installation)
- [Documentation](#documentation)
- [Platform Support](#platform-support)
- [License](#license)
- [Related Projects](#related-projects)

---

## Why FastNet?

Standard blocking networking creates thread pressure and makes high-frequency services pay for unnecessary copies and polling:

- **Thread exhaustion**: One blocking connection per worker does not scale cleanly for burst traffic.
- **Buffer copies**: Heap-to-native transitions add latency to telemetry and packet pipelines.
- **Polling overhead**: Application loops waste CPU while waiting for kernel I/O completion.

**FastNet** addresses this with a transport-first design:

- **Asynchronous completion**: TCP and UDP operations return futures instead of blocking application threads.
- **Direct-buffer boundary**: Payloads can be supplied as `ByteBuffer` values without changing caller state.
- **Native-ready transport**: WinSock2 and IOCP can replace the portable fallback behind the same API.

| Feature | java.net (Socket / Datagram) | Netty (NIO Transport) | FastNet |
|:---|:---|:---|:---|
| **I/O Model** | Blocking I/O (Thread-per-connection) | Heavy EventLoop channel graph | **Lightweight asynchronous completion** |
| **Native Transport** | POSIX / Win32 libc wrapper | JNI Epoll / KQueue (Linux/macOS) | **Windows WinSock2 + IOCP native ready**|
| **Buffer Handling** | Intermediate heap byte arrays | ByteBuf retain/release counts | **Direct ByteBuffer boundary (zero-copy)**|
| **GC / Allocation Footprint**| High stream wrapper churn | Moderate object allocations | **Zero GC payload submission** |

---

## Features

- **⚡ Asynchronous TCP and UDP**: Connect and send without a polling loop in application code.
- **📦 Direct buffer support**: Submit heap or direct `ByteBuffer` payloads without mutating them.
- **🪟 Windows native path**: Designed for WinSock2, IOCP and kernel completion events.
- **🔗 Ecosystem ready**: Integrates naturally with FastDNS resolution and FastTLS security.

---

## Real-World Scenarios

- **Live telemetry:** Send compact UDP metrics from a sensor or game loop.
- **Game servers:** Establish low-latency TCP control channels and UDP state updates.
- **Web crawling:** Resolve endpoints with FastDNS and submit concurrent HTTP work.
- **Secure services:** Place FastTLS directly above the transport boundary.

---

## Performance Benchmarks

FastNet includes a local UDP benchmark to expose transport overhead before the native IOCP backend is enabled.

| Metric / Transport Type | Current Java Fallback | Native Target |
|-------------------------|----------------------|---------------|
| **UDP submission** | Measured by benchmark | IOCP / zero-copy |
| **TCP connection** | Asynchronous NIO | Native WinSock2 |
| **Telemetry payload** | Direct-buffer ready | Ring-buffer DMA |

*The benchmark measures the portable Java path on the local machine. Native latency figures are reported only after IOCP integration.*

---

## Measured Benchmark Run

The optimized local telemetry benchmark completed **100 UDP packets in 28,968 ms** on this Windows workstation; the previous per-call channel setup measured **77,006 ms**, so channel reuse reduced this run by about **62%**.

```text
run-benchmark.bat -> fastnet.UdpThroughputBenchmark
```

Native IOCP and zero-copy measurements must be collected separately after the Windows backend is enabled.

---

## API Quick Reference

| Method | Description |
|---|---|
| Method | Description |
|--------|-------------|
| `connect(host, port)` | Opens an asynchronous TCP connection. |
| `sendUdp(host, port, payload)` | Sends a direct or heap `ByteBuffer` asynchronously. |

---

## Technical Examples & Hero Demos

| Case | Java Example | Launcher | Description |
|---|---|---|---|
| **TCP Telemetry Channel** | [TcpLoopbackDemo.java](examples/Demo/src/main/java/fastnet/TcpLoopbackDemo.java) | `run-demo.bat` | Establishes a local control channel and receives a telemetry response. |
| **UDP Throughput** | [UdpThroughputBenchmark.java](examples/Benchmark/src/main/java/fastnet/UdpThroughputBenchmark.java) | `run-benchmark.bat` | Measures repeated UDP submissions for a packet pipeline. |

---

## Installation

### Option 1: Maven (Recommended)

```xml
<repositories>
    <repository>
        <id>jitpack.io</id>
        <url>https://jitpack.io</url>
    </repository>
</repositories>
<dependencies>
    <dependency>
    <groupId>com.github.andrestubbe</groupId>
    <artifactId>FastNet</artifactId>
    <version>0.1.0</version>
</dependency>
</dependencies>
```

### Option 2: Gradle (via JitPack)

```groovy
repositories {
    maven { url 'https://jitpack.io' }
}

dependencies {
    implementation 'com.github.andrestubbe:FastNet:0.1.0'
}
```

### Option 3: Direct Download (No Build Tool)

Download the latest FastNet JAR from the [GitHub releases](https://github.com/andrestubbe/FastNet/releases) page.

---

## Documentation

* **[COMPILE.md](docs/COMPILE.md)**: Full compilation guide and launcher instructions.
* **[REFERENCE.md](docs/REFERENCE.md)**: Transport API and buffer contract.
* **[PHILOSOPHY.md](docs/PHILOSOPHY.md)**: Native-first networking principles.
* **[ROADMAP.md](docs/ROADMAP.md)**: Planned IOCP and zero-copy milestones.
* **[CHANGELOG.md](docs/CHANGELOG.md)**: Version history.

---

## Platform Support

| Platform | Status |
|---|---|
| Windows 10/11 x64 | Native backend planned |
| Linux | Java fallback |
| macOS | Java fallback |

---

## License

MIT License — See [LICENSE](LICENSE) for details.

---

## Related Projects

- [FastTLS](https://github.com/andrestubbe/FastTLS) — TLS transport security
- [FastDNS](https://github.com/andrestubbe/FastDNS) — Asynchronous hostname resolution
- [FastSharedMemory](https://github.com/andrestubbe/FastSharedMemory) — Off-heap data exchange

---

**Part of the FastJava Ecosystem** — *Making the JVM faster. Small package. Maximum speed. Zero bloat. 🚀📋*
