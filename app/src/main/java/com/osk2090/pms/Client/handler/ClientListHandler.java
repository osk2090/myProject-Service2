package com.osk2090.pms.Client.handler;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class ClientListHandler implements Command {

    @Override
    public void service() throws Exception {

        System.out.println("응모자 목록");

        try (Connection con = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/servicedb?user=osk&password=2090");
             PreparedStatement stmt = con.prepareStatement(
                     "select no,name,id from pms_client order by no desc ");
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                System.out.printf("%d, %s, %s\n",
                        rs.getInt("no"),
                        rs.getString("name"),
                        rs.getString("id"));

            }
        }
    }
}