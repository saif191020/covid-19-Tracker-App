package com.sba.covid_19tracker;

public class CountryModelClass implements Comparable{
    private String countryName;
    private String Conf, Des, Rec;

    public CountryModelClass(String countryName, String conf, String des, String rec) {
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

    public String getConf() {
        return Conf;
    }

    public void setConf(String conf) {
        Conf = conf;
    }

    public String getDes() {
        return Des;
    }

    public void setDes(String des) {
        Des = des;
    }

    public String getRec() {
        return Rec;
    }

    public void setRec(String rec) {
        Rec = rec;
    }

    @Override
    public int compareTo(Object o) {
        String comparecase=((CountryModelClass)o).getConf();
        String a =comparecase;
        String b =getConf();
        if(a.equals("N/A") || a.equals(""))
            a="0";
        if(b.equals("N/A") || b.equals(""))
            b="0";
        a=a.replace(",","");
        b=b.replace(",","");
        return Integer.valueOf(a)-Integer.valueOf(b);

    }
}
