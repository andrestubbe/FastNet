# FastNet Philosophy

FastNet separates transport completion from application state, favors direct buffers and keeps native IOCP as an interchangeable backend rather than leaking platform details into user code.
