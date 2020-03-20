package com.mio.miocma2020.transaction;

public class Transaction {
    String transid,cno,fno,prodid,weight,rdate,flot;

    public Transaction(String transid, String cno, String fno, String prodid, String weight, String rdate, String flot) {
        this.transid = transid;
        this.cno = cno;
        this.fno = fno;
        this.prodid = prodid;
        this.weight = weight;
        this.rdate = rdate;
        this.flot = flot;
    }

    public String getTransid() {
        return transid;
    }

    public void setTransid(String transid) {
        this.transid = transid;
    }

    public String getCno() {
        return cno;
    }

    public void setCno(String cno) {
        this.cno = cno;
    }

    public String getFno() {
        return fno;
    }

    public void setFno(String fno) {
        this.fno = fno;
    }

    public String getProdid() {
        return prodid;
    }

    public void setProdid(String prodid) {
        this.prodid = prodid;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getRdate() {
        return rdate;
    }

    public void setRdate(String rdate) {
        this.rdate = rdate;
    }

    public String getFlot() {
        return flot;
    }

    public void setFlot(String flot) {
        this.flot = flot;
    }
}
