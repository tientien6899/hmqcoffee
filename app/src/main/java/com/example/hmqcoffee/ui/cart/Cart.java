package com.example.hmqcoffee.ui.cart;

public class Cart {
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int id;
    public String link;
    public String ten;
    public String gia;
    public String sl;

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String total;


    public Cart() {
    }

    public Cart(int id, String link, String ten, String gia, String sl, String total) {
        this.id = id;
        this.link = link;
        this.ten = ten;
        this.gia = gia;
        this.sl = sl;
        this.total = total;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getTen() {
        return ten;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }

    public String getGia() {
        return gia;
    }

    public void setGia(String gia) {
        this.gia = gia;
    }

    public String getSl() {
        return sl;
    }

    public void setSl(String sl) {
        this.sl = sl;
    }
}
