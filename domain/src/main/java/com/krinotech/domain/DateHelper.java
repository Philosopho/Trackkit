package com.krinotech.domain;

import java.util.Calendar;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class DateHelper {
    private Calendar calendar;

    @Inject
    public DateHelper(Calendar calendar) {
        this.calendar = calendar;
    }

    public long getCurrentDate() {
        return calendar.getTimeInMillis();
    }

    public boolean hoursPassed(long previousDate, int hours) {
        double hoursPassed = (getCurrentDate() - previousDate) / 1000.0 / 60 / 60;
        return hoursPassed >= hours;
    }

    public boolean timesUp(long previousTime) {
        double timePassed = (getCurrentDate() - previousTime) / 1000.0 / 60 / 60;
        return timePassed >= 0;
    }
}
