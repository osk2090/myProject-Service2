package com.osk2090.pms.Client.dao;

import com.osk2090.pms.Client.domain.Client;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class ClientDao {

    Connection con;

    public ClientDao() throws Exception {
        this.con = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/servicedb?user=osk&password=2090"
        );
    }

    public int insert(Client client)throws Exception {
        try (PreparedStatement stmt = con.prepareStatement(
                "insert into pms_client(name, phone_number, birth_number, id, cSize) " +
                        "values (?,?,?,?,?)")) {
            stmt.setString(1, client.getName());
            stmt.setString(2, client.getPhone_number());
            stmt.setString(3, client.getBirth_number());
            stmt.setString(4, client.getId());
            stmt.setInt(5, client.getcSize());

            return stmt.executeUpdate();
        }
    }

    public Client findByNo(int no) throws Exception {
        try (PreparedStatement stmt = con.prepareStatement(
                "select * from pms_client where no = ?")) {
            stmt.setInt(1, no);

            try (ResultSet rs = stmt.executeQuery()) {
                if (!rs.next()) {
                    return null;
                }

                Client c = new Client();
                //이름: %s 전화번호: %s 생년월일: %s 아이디: %s 사이즈:
                c.setNo(no);
                c.setName(rs.getString("name"));
                c.setPhone_number(rs.getString("phone_number"));
                c.setBirth_number(rs.getString("birth_number"));
                c.setId(rs.getString("id"));
                c.setcSize(rs.getInt("cSize"));

                return c;
            }
        }
    }

    public List<Client> findAll() throws Exception {
        ArrayList<Client> list = new ArrayList<>();

        try (PreparedStatement stmt = con.prepareStatement(
                "select no,name,id from pms_client order by id asc");
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Client c = new Client();
                c.setNo(rs.getInt("no"));
                c.setName(rs.getString("name"));
                c.setId(rs.getString("id"));
                list.add(c);
            }
        }
        return list;
    }

    public int delete(int no)throws Exception {
        try (PreparedStatement stmt = con.prepareStatement(
                "delete from pms_client where no=?")) {

            stmt.setInt(1, no);
            return stmt.executeUpdate();
        }
    }
}