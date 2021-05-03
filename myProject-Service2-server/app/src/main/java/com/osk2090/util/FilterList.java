package com.osk2090.util;

public class FilterList {

    FilterChain header;

    public void add(Filter filter) {
        header = new DefaultFilterChain(header, filter);
    }

    public FilterChain getHeaderChain() {
        return header;
    }
}
