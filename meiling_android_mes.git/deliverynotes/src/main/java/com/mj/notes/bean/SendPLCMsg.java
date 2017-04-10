package com.mj.notes.bean;

public class SendPLCMsg {

    private IceBox ib;
    //plc
    private Boolean hd = false;

    //是否打印了
    private Boolean st = false;

    public Boolean getSt() {
        return st;
    }

    public void setSt(Boolean st) {
        this.st = st;
    }

    public SendPLCMsg(IceBox ib) {
        this.ib = ib;
    }

    public Boolean getHd() {
        return hd;
    }

    public void setHd(Boolean hd) {
        this.hd = hd;
    }

    public IceBox getIb() {
        return ib;
    }

    public void setIb(IceBox ib) {
        this.ib = ib;
    }

    @Override
    public String toString() {
        return "SendPLCMsg{" +
                "hd=" + hd +
                ", ib=" + ib +
                ", st=" + st +
                '}';
    }
}
