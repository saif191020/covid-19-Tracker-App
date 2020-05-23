package com.sba.covid_19tracker.District;

public class DistrictModelClass implements Comparable  {
    private String Districtname;
    private int Confirm_case, delta_c, Recovered_case, delta_r, Dead_case, delta_d;
    private boolean expanded;


    public DistrictModelClass(String districtname, int confirm_case, int delta_c, int recovered_case, int delta_r, int dead_case, int delta_d) {
        Districtname = districtname;
        Confirm_case = confirm_case;
        this.delta_c = delta_c;
        Recovered_case = recovered_case;
        this.delta_r = delta_r;
        Dead_case = dead_case;
        this.delta_d = delta_d;
        this.expanded = false;
    }

    public boolean isExpanded() {
        return expanded;
    }

    public void setExpanded(boolean expanded) {
        this.expanded = expanded;
    }

    public int getRecovered_case() {
        return Recovered_case;
    }

    public void setRecovered_case(int recovered_case) {
        Recovered_case = recovered_case;
    }

    public int getDelta_r() {
        return delta_r;
    }

    public void setDelta_r(int delta_r) {
        this.delta_r = delta_r;
    }

    public int getDead_case() {
        return Dead_case;
    }

    public void setDead_case(int dead_case) {
        Dead_case = dead_case;
    }

    public int getDelta_d() {
        return delta_d;
    }

    public void setDelta_d(int delta_d) {
        this.delta_d = delta_d;
    }

    public String getDistrictname() {
        return Districtname;
    }

    public void setDistrictname(String districtname) {
        Districtname = districtname;
    }

    public int getConfirm_case() {
        return Confirm_case;
    }

    public void setConfirm_case(int confirm_case) {
        Confirm_case = confirm_case;
    }

    public int getDelta_c() {
        return delta_c;
    }

    public void setDelta_c(int delta_c) {
        this.delta_c = delta_c;
    }



    @Override
    public int compareTo(Object o) {
        int comparecase=((DistrictModelClass)o).getConfirm_case();
        /* For Ascending order*/
        return comparecase-this.Confirm_case;

    }
}
