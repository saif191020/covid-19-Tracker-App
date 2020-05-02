package com.sba.covid_19tracker;

public class ZoneModelClass implements Comparable {
    String District_name,Zone_colour, Last_update;

    public ZoneModelClass(String district_name, String zone_colour, String last_update) {
        District_name = district_name;
        Zone_colour = zone_colour;
        Last_update = last_update;
    }

    public String getDistrict_name() {
        return District_name;
    }

    public void setDistrict_name(String district_name) {
        District_name = district_name;
    }

    public String getZone_colour() {
        return Zone_colour;
    }

    public void setZone_colour(String zone_colour) {
        Zone_colour = zone_colour;
    }

    public String getLast_update() {
        return Last_update;
    }

    public void setLast_update(String last_update) {
        Last_update = last_update;
    }

    @Override
    public int compareTo(Object o) {
        String z1color=((ZoneModelClass)o).getZone_colour();
        /* For Ascending order*/
        return Zone_colour.compareTo(z1color);
    }
}
