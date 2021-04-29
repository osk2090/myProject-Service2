package com.osk2090.util.concurrent;

import com.osk2090.util.Prompt;

public class CommandRequest {

    private String commandPath;
    private String remoteAddr;
    private int remotePort;
    private Prompt prompt;

    public CommandRequest(String commandPath, String remoteAddr, int remotePort, Prompt prompt) {
        this.prompt = prompt;
        this.commandPath = commandPath;
        this.remoteAddr = remoteAddr;
        this.remotePort = remotePort;
    }

    public String getCommandPath() {
        return commandPath;
    }

    public String getRemoteAddr() {
        return remoteAddr;
    }

    public int getRemotePort() {
        return remotePort;
    }

    public Prompt getPrompt() {
        return prompt;
    }
}
