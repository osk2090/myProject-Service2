package com.osk2090.pms.handler;

import com.osk2090.util.concurrent.CommandRequest;
import com.osk2090.util.concurrent.CommandResponse;

public interface Command {
    void service(CommandRequest request, CommandResponse response) throws Exception;
}
