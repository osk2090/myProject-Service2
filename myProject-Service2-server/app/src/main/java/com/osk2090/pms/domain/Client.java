package com.osk2090.pms.domain;

import java.util.Objects;

public class Client {

    private static final long serialVersionUID = 1L;

    private String name;//응모자 이름
    private String phone_number;//응모자 전화번호
    private String birth_number;//응모자 생년월일
    private String id;//응모자 나이키 아이디
    private int cSize;//응모자 사이즈
    private int no;//인덱스 번호

    public Client() {}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone_number() {
        return phone_number;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Client client = (Client) o;
        return Objects.equals(name, client.name) && Objects.equals(phone_number, client.phone_number) && Objects.equals(id, client.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, phone_number, id);//이름 전화번호 아이디
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public String getBirth_number() {
        return birth_number;
    }

    public void setBirth_number(String birth_number) {
        this.birth_number = birth_number;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getcSize() {
        return cSize;
    }

    public void setcSize(int cSize) {
        this.cSize = cSize;
    }

    public int getNo() {
        return no;
    }

    public void setNo(int no) {
        this.no = no;
    }
}