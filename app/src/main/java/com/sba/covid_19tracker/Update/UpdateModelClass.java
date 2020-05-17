package com.sba.covid_19tracker.Update;

public class UpdateModelClass implements Comparable {
    private String update;
    private Long timestamp;

    public UpdateModelClass(String update, Long timestamp) {
        this.update = update;
        this.timestamp = timestamp;
    }

    public String getUpdate() {
        return update;
    }

    public void setUpdate(String update) {
        this.update = update;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    public int compareTo(Object o) {
        if (timestamp == ((UpdateModelClass) o).getTimestamp())
            return 0;
        else if (timestamp > ((UpdateModelClass) o).getTimestamp())
            return -1;
        else
            return 1;

    }
}
