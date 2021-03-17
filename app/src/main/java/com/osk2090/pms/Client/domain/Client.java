package com.osk2090.pms.Client.domain;

import java.util.Objects;

public class Client {

    private static final long serialVersionUID = 1L;

    private String name;//응모자 이름
    private String pN;//응모자 전화번호
    private String bN;//응모자 생년월일
    private String id;//응모자 나이키 아이디
    private int cSize;//응모자 사이즈
    private int no;//인덱스 번호

    public Client() {

    }

    //인덱스 .이름: %s 전화번호: %s 생년월일: %s 아이디: %s 사이즈:
    public Client(String csv) {
        String[] fields = csv.split(",");
        this.setNo(Integer.parseInt(fields[0]));
        this.setName(fields[1]);
        this.setpN(fields[2]);
        this.setbN(fields[3]);
        this.setId(fields[4]);
        this.setcSize(Integer.parseInt(fields[5]));
    }

    public String tocsvString() {
        return String.format("%d,%s,%s,%s,%s,%d\n",
                this.getNo(),
                this.getName(),
                this.getpN(),
                this.getbN(),
                this.getId(),
                this.getcSize());
    }

    public static Client valueOfCsv(String csv) {
        String[] fields = csv.split(",");
        Client c = new Client();
        c.setNo(Integer.parseInt(fields[0]));
        c.setName(fields[1]);
        c.setpN(fields[2]);
        c.setbN(fields[3]);
        c.setId(fields[4]);
        c.setcSize(Integer.parseInt(fields[5]));
        return c;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getpN() {
        return pN;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Client client = (Client) o;
        return Objects.equals(name, client.name) && Objects.equals(pN, client.pN) && Objects.equals(id, client.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, pN, id);//이름 전화번호 아이디
    }

    public void setpN(String pN) {
        this.pN = pN;
    }

    public String getbN() {
        return bN;
    }

    public void setbN(String bN) {
        this.bN = bN;
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