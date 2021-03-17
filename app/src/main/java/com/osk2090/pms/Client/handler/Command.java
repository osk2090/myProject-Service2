package com.osk2090.pms.Client.handler;

import java.io.DataInputStream;
import java.io.DataOutputStream;

public interface Command {
    void service(DataInputStream in, DataOutputStream out) throws Exception;
}
