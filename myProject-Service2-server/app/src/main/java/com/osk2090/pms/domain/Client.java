package com.osk2090.pms.domain;

public class Client {

    private static final long serialVersionUID = 1L;

    private String id;//응모자 나이키 아이디
    private String password;
    private String name;//응모자 이름
    private String tel;//응모자 전화번호
    private String birth;//응모자 생년월일

    @Override
    public String toString() {
        return "Client{" +
                "id='" + id + '\'' +
                ", password='" + password + '\'' +
                ", name='" + name + '\'' +
                ", tel='" + tel + '\'' +
                ", birth='" + birth + '\'' +
                '}';
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getBirth() {
        return birth;
    }

    public void setBirth(String birth) {
        this.birth = birth;
    }
}