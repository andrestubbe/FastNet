# FastNet 0.1.0 [ALPHA-2026-09] — Ultra-Fast Native Network Engine for Java

[![Status](https://img.shields.io/badge/status-0.1.0-brightgreen.svg)](https://github.com/andrestubbe/FastNet)
[![Java](https://img.shields.io/badge/Java-17+-blue.svg)](https://www.java.com)
[![Platform](https://img.shields.io/badge/Platform-Windows%2010+-lightgrey.svg)]()
[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT)

---

**Ultra-fast asynchronous networking for the FastJava ecosystem.** FastNet defines a zero-copy-friendly transport API for telemetry, game servers, crawlers and real-time services; native WinSock2/IOCP is the planned Windows backend.

## Quick Start

```java
FastNet.sendUdp("127.0.0.1", 9999, ByteBuffer.wrap("telemetry".getBytes()))
        .thenAccept(System.out::println);
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

Standard blocking networking creates thread pressure and makes high-frequency services pay for unnecessary buffer copies and polling. FastNet separates asynchronous completion from application state and keeps the API ready for direct-buffer and IOCP backends.

---

## Features

- Asynchronous TCP connection and UDP submission.
- Direct `ByteBuffer` payload support without changing the caller's buffer position.
- Native WinSock2/IOCP integration boundary.
- Clean integration point for FastTLS and FastDNS.

---

## Real-World Scenarios

- **Live telemetry:** Send compact UDP metrics from a sensor or game loop.
- **Game servers:** Establish low-latency TCP control channels and UDP state updates.
- **Web crawling:** Resolve endpoints with FastDNS and submit concurrent HTTP work.
- **Secure services:** Place FastTLS directly above the transport boundary.

---

## Performance Benchmarks

The included benchmark measures repeated UDP submissions against a local endpoint; production IOCP figures must be measured on the native backend.

| Operation | Current Java fallback | Native target |
|---|---:|---:|
| UDP submission | Measured by `run-benchmark.bat` | IOCP / zero-copy |
| TCP connect | Measured by application | Async WinSock2 |

---

## API Quick Reference

| Method | Description |
|---|---|
| `connect(host, port)` | Opens an asynchronous TCP connection. |
| `sendUdp(host, port, payload)` | Sends a direct or heap `ByteBuffer` asynchronously. |

---

## Technical Examples & Hero Demos

| Case | Java Example | Launcher | Description |
|---|---|---|---|
| **Telemetry Packet** | [Demo.java](examples/Demo/src/main/java/fastnet/Demo.java) | `run-demo.bat` | Sends a real UDP telemetry packet to a local service. |
| **UDP Throughput** | [Benchmark.java](examples/Benchmark/src/main/java/fastnet/benchmark/Benchmark.java) | `run-benchmark.bat` | Measures repeated local UDP submissions. |

---

## Installation

### Option 1: Maven (Recommended)

```xml
<dependency>
    <groupId>com.github.andrestubbe</groupId>
    <artifactId>FastNet</artifactId>
    <version>0.1.0</version>
</dependency>
```

### Option 2: Gradle (via JitPack)

```groovy
implementation 'com.github.andrestubbe:FastNet:0.1.0'
```

### Option 3: Direct Download (No Build Tool)

Download the latest FastNet JAR from the [GitHub releases](https://github.com/andrestubbe/FastNet/releases) page.

---

## Documentation

- [COMPILE.md](docs/COMPILE.md): Build and launcher instructions.
- [REFERENCE.md](docs/REFERENCE.md): Transport API and buffer contract.
- [PHILOSOPHY.md](docs/PHILOSOPHY.md): Native-first networking principles.
- [ROADMAP.md](docs/ROADMAP.md): Planned IOCP and zero-copy milestones.
- [CHANGELOG.md](docs/CHANGELOG.md): Version history.

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

**Part of the FastJava Ecosystem** — Making the JVM faster. Small package. Maximum speed.
