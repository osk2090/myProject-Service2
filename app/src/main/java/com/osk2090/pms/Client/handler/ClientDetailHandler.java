package com.osk2090.pms.Client.handler;

import com.osk2090.pms.Client.util.Prompt;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class ClientDetailHandler implements Command {

    @Override
    public void service() throws Exception {
        System.out.println("[응모자 상세보기]");

        int no = Prompt.promptInt("번호? ");

        try (Connection con = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/servicedb?user=osk&password=2090");
             PreparedStatement stmt = con.prepareStatement(
                     "select * from pms_client where no = ?")) {
            stmt.setInt(1, no);

            try (ResultSet rs = stmt.executeQuery()) {
                if (!rs.next()) {
                    System.out.println("해당 번호의 응모자가 없습니다.");
                    return;
                }

                //이름: %s 전화번호: %s 생년월일: %s 아이디: %s 사이즈:
                System.out.printf("이름: %s\n", rs.getString("name"));
                System.out.printf("전화번호: %s\n", rs.getString("phone_number"));
                System.out.printf("생년월일: %s\n", rs.getString("birth_number"));
                System.out.printf("NIKE 아이디: %s\n", rs.getString("id"));
                System.out.printf("사이즈: %s\n", rs.getInt("cSize"));
            }
        }
    }
}