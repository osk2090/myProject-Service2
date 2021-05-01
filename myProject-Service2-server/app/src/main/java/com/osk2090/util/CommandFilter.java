package com.osk2090.util;

import com.osk2090.pms.handler.Command;
import com.osk2090.util.concurrent.CommandRequest;
import com.osk2090.util.concurrent.CommandResponse;

public class CommandFilter implements Filter {
    private Command command;

    public CommandFilter(Command command) {
        this.command = command;
    }

    @Override
    public void doFilter(CommandRequest request, CommandResponse response, FilterChain nextChain) throws Exception {
        command.service(request, response);
    }
}
