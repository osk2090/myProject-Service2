package com.osk2090.pms.Client.handler;

import com.osk2090.pms.Client.util.Prompt;

import java.io.DataInputStream;
import java.io.DataOutputStream;

public class ClientSearchHandler implements Command {
    @Override
    public void service(DataInputStream in, DataOutputStream out) throws Exception {
        String keword = Prompt.promptString("검색어? ");


    }
}