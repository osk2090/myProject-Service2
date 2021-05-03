package com.osk2090.util;

import java.util.HashMap;
import java.util.Map;

public class Session {

    private Map<String, Object> map = new HashMap<>();

    public void setAttribute(String name, Object value) {
        map.put(name, value);
    }

    public Object getAttribute(String name) {
        return map.get(name);
    }

    public void invalidate() {
        map.clear();
    }
}
