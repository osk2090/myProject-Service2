package com.osk2090.pms.domain;

import java.sql.Date;

public class Board {
    private int no;
    private String title;
    private String content;
    private int price;
    private String model;
    private Date startDate;
    private Date endDate;
    private Date selStart;
    private Date selEnd;
    private int photo1;
    private int photo2;
    private int photo3;
    private int photo4;
    private int photo5;
    private int photo6;
    private int photo7;
    private int photo8;
    private int photo9;
    private int photo10;

    @Override
    public String toString() {
        return "Board{" +
                "no=" + no +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", price=" + price +
                ", model='" + model + '\'' +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", selStart=" + selStart +
                ", selEnd=" + selEnd +
                ", photo1=" + photo1 +
                ", photo2=" + photo2 +
                ", photo3=" + photo3 +
                ", photo4=" + photo4 +
                ", photo5=" + photo5 +
                ", photo6=" + photo6 +
                ", photo7=" + photo7 +
                ", photo8=" + photo8 +
                ", photo9=" + photo9 +
                ", photo10=" + photo10 +
                '}';
    }

    public int getNo() {
        return no;
    }

    public void setNo(int no) {
        this.no = no;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Date getSelStart() {
        return selStart;
    }

    public void setSelStart(Date selStart) {
        this.selStart = selStart;
    }

    public Date getSelEnd() {
        return selEnd;
    }

    public void setSelEnd(Date selEnd) {
        this.selEnd = selEnd;
    }

    public int getPhoto1() {
        return photo1;
    }

    public void setPhoto1(int photo1) {
        this.photo1 = photo1;
    }

    public int getPhoto2() {
        return photo2;
    }

    public void setPhoto2(int photo2) {
        this.photo2 = photo2;
    }

    public int getPhoto3() {
        return photo3;
    }

    public void setPhoto3(int photo3) {
        this.photo3 = photo3;
    }

    public int getPhoto4() {
        return photo4;
    }

    public void setPhoto4(int photo4) {
        this.photo4 = photo4;
    }

    public int getPhoto5() {
        return photo5;
    }

    public void setPhoto5(int photo5) {
        this.photo5 = photo5;
    }

    public int getPhoto6() {
        return photo6;
    }

    public void setPhoto6(int photo6) {
        this.photo6 = photo6;
    }

    public int getPhoto7() {
        return photo7;
    }

    public void setPhoto7(int photo7) {
        this.photo7 = photo7;
    }

    public int getPhoto8() {
        return photo8;
    }

    public void setPhoto8(int photo8) {
        this.photo8 = photo8;
    }

    public int getPhoto9() {
        return photo9;
    }

    public void setPhoto9(int photo9) {
        this.photo9 = photo9;
    }

    public int getPhoto10() {
        return photo10;
    }

    public void setPhoto10(int photo10) {
        this.photo10 = photo10;
    }
}