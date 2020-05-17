package com.sba.covid_19tracker.District;

public class DistrictModelClass implements Comparable  {
    private String Districtname;
    private int Confirm_case, delta_c;

    public DistrictModelClass(String districtname, int confirm_case, int delta_c) {
        Districtname = districtname;
        Confirm_case = confirm_case;
        this.delta_c = delta_c;
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
