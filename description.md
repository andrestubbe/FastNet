# FastNet

Zero-Copy Netzwerk-Sockets (WinSock2/IOCP) für ultra-hohen Durchsatz und minimale Latenz.

---

## ⚡ Overview

**FastNet** ist eine hardwarenahe Netzwerk-Engine auf Basis von WinSock2 und I/O Completion Ports (`IOCP`) für Windows.

Standard-Java NIO leidet unter Selektor-Overhead, Buffer-Kopien zwischen JVM-Heap und OS-Socketpuffern sowie GC-Pausen. FastNet nutzt Direct Memory Ringpuffer und Ring-Buffer-DMA für Millionen Pakete pro Sekunde mit Sub-Mikrosekunden-Jitter.

---

## 🔑 Kernmerkmale

- **Windows IOCP (I/O Completion Ports)**: Echte asynchrone Kernel-I/O ohne Polling-Threads.
- **Zero-Copy DMA Socket Streaming**: Direktes Senden und Empfangen von `DirectByteBuffer` und `FastSharedMemory`.
- **TCP, UDP & Raw Sockets**: Volle Kontrolle über Socket-Optionen (`TCP_NODELAY`, `SO_REUSEADDR`, Kernel-Puffergrößen).
- **Sub-Mikrosekunden-Latenz**: Entwickelt für Hochfrequenzhandel, Game-Server und Echtzeit-Telemetry.

---

## 🏗️ Ecosystem Integration

- **FastCrypto / FastTLS**: Hardwarebeschleunigte End-to-End-Verschlüsselung im Übertragungspfad.
- **FastArchitecture**: Klare Trennung von Network-Transport (`Control`) und Anwendungszustand (`Model`).