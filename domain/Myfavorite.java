package cn.itcast.travel.domain;

import java.util.Date;

public class Myfavorite {
    private int rid;
    private Date date;
    private int uid;

    public Myfavorite() {
    }

    public int getRid() {
        return rid;
    }

    public void setRid(int rid) {
        this.rid = rid;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public Myfavorite(int rid, Date date, int uid) {
        this.rid = rid;
        this.date = date;
        this.uid = uid;
    }
}
