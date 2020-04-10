package com.sba.covid_19tracker;

public class StateModelClass {
    private String stateName;
    private int Con,dCon,Dec,dDec,Rec,dRec;

    public String getStateName() {
        return stateName;
    }

    public void setStateName(String stateName) {
        this.stateName = stateName;
    }

    public int getCon() {
        return Con;
    }

    public void setCon(int con) {
        Con = con;
    }

    public int getdCon() {
        return dCon;
    }

    public void setdCon(int dCon) {
        this.dCon = dCon;
    }

    public int getDec() {
        return Dec;
    }

    public void setDec(int dec) {
        Dec = dec;
    }

    public int getdDec() {
        return dDec;
    }

    public void setdDec(int dDec) {
        this.dDec = dDec;
    }

    public int getRec() {
        return Rec;
    }

    public void setRec(int rec) {
        Rec = rec;
    }

    public int getdRec() {
        return dRec;
    }

    public void setdRec(int dRec) {
        this.dRec = dRec;
    }

    public StateModelClass(String stateName, int con, int dCon, int dec, int dDec, int rec, int dRec) {
        this.stateName = stateName;
        Con = con;
        this.dCon = dCon;
        Dec = dec;
        this.dDec = dDec;
        Rec = rec;
        this.dRec = dRec;
    }
}
