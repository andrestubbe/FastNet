# Building FastNet

Requires JDK 17+ and Maven 3.9+.

- `mvn clean test` compiles the library and unit tests.
- `run-demo.bat` sends a local UDP telemetry packet.
- `run-benchmark.bat` measures repeated UDP submissions.

The current backend is a portable Java fallback; native WinSock2/IOCP integration belongs behind the same API.
