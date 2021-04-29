package com.osk2090.util.concurrent;

import java.io.PrintWriter;

public class CommandResponse {
    private PrintWriter writer;

    public CommandResponse(PrintWriter writer) {
        this.writer = writer;
    }

    public PrintWriter getWriter() {
        return writer;
    }
}
