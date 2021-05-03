package com.osk2090.pms.handler;


import com.osk2090.pms.web.ClientAddHandler;
import com.osk2090.util.Agreement;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ClientPrintOneHandler extends HttpServlet {

    public ClientPrintOneHandler(ClientAddHandler clientAddHandler) {
        this.clientAddHandler = clientAddHandler;
    }

    ClientAddHandler clientAddHandler;

    @Override
    public void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (Agreement.Agree()) {
            this.clientAddHandler.service(request, response);
        }
    }
}