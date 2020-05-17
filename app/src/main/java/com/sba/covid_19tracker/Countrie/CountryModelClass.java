package com.sba.covid_19tracker.Countrie;

public class CountryModelClass implements Comparable {
    private String countryName;
    private int Conf, Des, Rec;

    public CountryModelClass(String countryName, int conf, int des, int rec) {
        this.countryName = countryName;
        Conf = conf;
        Des = des;
        Rec = rec;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public int getConf() {
        return Conf;
    }

    public void setConf(int conf) {
        Conf = conf;
    }

    public int getDes() {
        return Des;
    }

    public void setDes(int des) {
        Des = des;
    }

    public int getRec() {
        return Rec;
    }

    public void setRec(int rec) {
        Rec = rec;
    }

    @Override
    public int compareTo(Object o) {
        if (Conf == ((CountryModelClass) o).getConf())
            return 0;
        else if (Conf > ((CountryModelClass) o).getConf())
            return -1;
        else
            return 1;

    }
}
