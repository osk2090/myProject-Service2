package com.osk2090.pms.Client.handler;

import com.osk2090.pms.Client.util.Prompt;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class ClientDeleteHandler implements Command {

    @Override
    public void service() throws Exception {
        System.out.println("[응모자 삭제]");

        int no = Prompt.promptInt("번호? ");

        String input = Prompt.promptString("정말 삭제하시겠습니까?(y/N)? ");
        if (!input.equalsIgnoreCase("y")) {
            System.out.println("응모자 삭제를 취소하였습니다.");
            return;
        }

        try (Connection con = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/servicedb?user=osk&password=2090");
             PreparedStatement stmt = con.prepareStatement(
                     "delete from pms_client where no=?")) {

            stmt.setInt(1, no);
            if (stmt.executeUpdate() == 0) {
                System.out.println("해당 번호의 응모자가 없습니다.");
                return;
            } else {
                System.out.println("회원을 삭제하였습니다.");
            }
        }
    }
}