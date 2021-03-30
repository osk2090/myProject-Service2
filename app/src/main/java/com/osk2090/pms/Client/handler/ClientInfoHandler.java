package com.osk2090.pms.Client.handler;

import com.osk2090.pms.Client.domain.Client;
import com.osk2090.pms.Client.util.Prompt;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

public class ClientInfoHandler {

    public int showCountClients() throws Exception {//카운팅

        try (Connection con = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/servicedb?user=osk&password=2090");
             PreparedStatement stmt = con.prepareStatement(
                     "select count(*) as from pms_client");
             ResultSet rs = stmt.executeQuery()) {
            int cnt = 0;
            while (rs.next()) {
                cnt += 1;
            }
            return cnt;
        }
    }

    public Client getInfo(int clientNo) throws Exception {
        try (Connection con = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/servicedb?user=osk&password=2090");
             PreparedStatement stmt = con.prepareStatement(
                     "select * from pms_client where no = ?")) {
            stmt.setInt(1, clientNo);

            try (ResultSet rs = stmt.executeQuery()) {
                if (!rs.next()) {
                    System.out.println("해당 번호의 응모자가 없습니다.");
                    return rs.getInt()
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

    public void removeClient(int clientNo) throws Exception {
        String input = Prompt.promptString("정말 삭제하시겠습니까?(y/N)? ");
        if (!input.equalsIgnoreCase("y")) {
            System.out.println("응모자 삭제를 취소하였습니다.");
            return;
        }

        try (Connection con = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/servicedb?user=osk&password=2090");
             PreparedStatement stmt = con.prepareStatement(
                     "delete from pms_client where no=?")) {

            stmt.setInt(1, clientNo);
            if (stmt.executeUpdate() == 0) {
                System.out.println("해당 번호의 응모자가 없습니다.");
            } else {
                System.out.println("응모자를 삭제하였습니다.");
            }
        }
    }
}