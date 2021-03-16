package com.osk2090.pms.Server.table;

import com.osk2090.pms.Server.util.Request;
import com.osk2090.pms.Server.util.Response;

public interface DataTable {
    void service(Request request, Response response) throws Exception;
}
