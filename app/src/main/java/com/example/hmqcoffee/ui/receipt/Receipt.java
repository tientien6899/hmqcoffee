package com.example.hmqcoffee.ui.receipt;

public class Receipt {
    long stt;
    String trangthai, ngaygio, tongdon;

    public Receipt() {
    }

    public Receipt(long stt, String trangthai, String ngaygio, String tongdon) {
        this.stt = stt;
        this.trangthai = trangthai;
        this.ngaygio = ngaygio;
        this.tongdon = tongdon;
    }

    public long getStt() {
        return stt;
    }

    public void setStt(long stt) {
        this.stt = stt;
    }

    public String getTrangthai() {
        return trangthai;
    }

    public void setTrangthai(String trangthai) {
        this.trangthai = trangthai;
    }

    public String getNgaygio() {
        return ngaygio;
    }

    public void setNgaygio(String ngaygio) {
        this.ngaygio = ngaygio;
    }

    public String getTongdon() {
        return tongdon;
    }

    public void setTongdon(String tongdon) {
        this.tongdon = tongdon;
    }
}

