package com.osk2090.pms.handler;

import com.osk2090.util.Prompt;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class ClientSearchHandler implements Command {
    @Override
    public void service() throws Exception {
        String keword = Prompt.promptString("검색어? ");

        if (keword.length() == 0) {
            System.out.println("검색어를 입력하세요.");
            return;
        }

        try (Connection con = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/servicedb?user=osk&password=2090");
             PreparedStatement stmt = con.prepareStatement(
                     "select no,name,id"
                             + " from pms_client"
                             + " or name like concat('%','?','%')"
                             + " or id like concat('%','?','%')"
                             + " order by no desc")) {

            stmt.setString(1, keword);
            stmt.setString(2, keword);

            try (ResultSet rs = stmt.executeQuery()) {
                if (!rs.next()) {
                    System.out.println("검색어에 해당하는 회원이 없습니다.");
                    return;

                }
                do {
                    System.out.printf("%d, %s, %s\n",
                            rs.getInt("no"),
                            rs.getString("name"),
                            rs.getString("id"));
                } while (rs.next());
            }
        }
    }
}