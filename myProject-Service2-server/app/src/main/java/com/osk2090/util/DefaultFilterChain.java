package com.osk2090.util;

import com.osk2090.util.concurrent.CommandRequest;
import com.osk2090.util.concurrent.CommandResponse;

public class DefaultFilterChain implements FilterChain {

    private FilterChain nextChain;
    private Filter filter;

    public DefaultFilterChain(FilterChain nextChain, Filter filter) {
        this.nextChain = nextChain;
        this.filter = filter;
    }

    @Override
    public void doFilter(CommandRequest request, CommandResponse response) throws Exception {
        filter.doFilter(request, response, nextChain);
    }

    public FilterChain getNextChain() {
        return nextChain;
    }

    public void setNextChain(FilterChain nextChain) {
        this.nextChain = nextChain;
    }

    public Filter getFilter() {
        return filter;
    }

    public void setFilter(Filter filter) {
        this.filter = filter;
    }
}
