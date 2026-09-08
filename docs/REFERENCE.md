# FastNet Reference

- `connect(host, port)` returns a future for a connected TCP channel.
- `sendUdp(host, port, payload)` sends the remaining bytes of a read-only buffer.
- Caller-owned buffers are never repositioned by the API.
- Network failures are represented by `FastNet.NetworkException`.
