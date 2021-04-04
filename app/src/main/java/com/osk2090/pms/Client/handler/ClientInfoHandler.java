package com.osk2090.pms.Client.handler;

import com.osk2090.pms.Client.domain.Client;
import com.osk2090.pms.Client.util.Prompt;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class ClientInfoHandler {

    public static int showCountClients() throws Exception {
        try (Connection con = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/servicedb?user=osk&password=2090");
             PreparedStatement stmt = con.prepareStatement(String.valueOf(ResultSet.TYPE_SCROLL_INSENSITIVE),
                     ResultSet.CONCUR_UPDATABLE)) {
            ResultSet rs = stmt.executeQuery("select no from pms_client");
            int cnt = 0;
            while (rs.next()) {
                int no = rs.getInt("no");
                cnt++;
            }
            return cnt;
        }
    }

    public List<Client> getInfo(int clientNo) throws Exception {
        try (Connection con = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/servicedb?user=osk&password=2090");
             PreparedStatement stmt = con.prepareStatement(
                     "select * from pms_client where no = ?")) {
            stmt.setInt(1, clientNo);

            ArrayList<Client> list = new ArrayList<>();

            try (ResultSet rs = stmt.executeQuery()) {
                if (!rs.next()) {
                    System.out.println("해당 번호의 응모자가 없습니다.");
                    return null;
                }
                Client c = new Client();
                c.setName(rs.getString("name"));
                c.setPhone_number(rs.getString("phone_number"));
                c.setBirth_number(rs.getString("birth_number"));
                c.setId(rs.getString("id"));
                c.setcSize(rs.getInt("cSize"));
                list.add(c);
//                System.out.printf("이름: %s\n", rs.getString("name"));
//                System.out.printf("전화번호: %s\n", rs.getString("phone_number"));
//                System.out.printf("생년월일: %s\n", rs.getString("birth_number"));
//                System.out.printf("NIKE 아이디: %s\n", rs.getString("id"));
//                System.out.printf("사이즈: %s\n", rs.getInt("cSize"));
            }
            return list;
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