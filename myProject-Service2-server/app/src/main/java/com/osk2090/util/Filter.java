package com.osk2090.util;

import com.osk2090.util.concurrent.CommandRequest;
import com.osk2090.util.concurrent.CommandResponse;


public interface Filter {
    void doFilter(CommandRequest request, CommandResponse response, FilterChain nextChain) throws Exception;
}
