package com.osk2090.util;

import com.osk2090.util.concurrent.CommandRequest;
import com.osk2090.util.concurrent.CommandResponse;

public interface FilterChain {

    void doFilter(CommandRequest request, CommandResponse response) throws Exception;
}
