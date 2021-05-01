package com.osk2090.pms.filter;

import com.osk2090.util.Filter;
import com.osk2090.util.FilterChain;
import com.osk2090.util.concurrent.CommandRequest;
import com.osk2090.util.concurrent.CommandResponse;

public class RequestLogFilter implements Filter {
    @Override
    public void doFilter(CommandRequest request, CommandResponse response, FilterChain nextChain) throws Exception {
        System.out.printf("[%s:%d] %s\n", request.getCommandPath(), request.getRemotePort(), request.getCommandPath());
        nextChain.doFilter(request, response);
    }
}
