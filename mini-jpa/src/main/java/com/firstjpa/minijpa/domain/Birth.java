package com.firstjpa.minijpa.domain;

import lombok.Getter;

import javax.persistence.Embeddable;
import javax.persistence.Embedded;

@Embeddable
@Getter
public class Birth {
    private String year;
    private String month;
    private String date;

    protected Birth() {
    }

    public Birth(String year, String month, String date) {
        this.year = year;
        this.month = month;
        this.date = date;
    }
}
