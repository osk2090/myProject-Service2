package com.osk2090.util.concurrent;

import com.osk2090.util.Prompt;
import com.osk2090.util.Session;

public class CommandRequest {

    private String commandPath;
    private String remoteAddr;
    private int remotePort;
    private Prompt prompt;
    private Session session;

    public CommandRequest(String commandPath, String remoteAddr, int remotePort, Prompt prompt, Session session) {
        this.session = session;
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

    public Session getSession() {
        return session;
    }
}
