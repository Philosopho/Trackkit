package com.krinotech.domain;

import org.junit.Before;
import org.junit.Test;

import java.util.Calendar;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class DateHelperTest {
    private Calendar calendarMock;
    private DateHelper testSubject;

    @Before
    public void setUp() {
        calendarMock = mock(Calendar.class);
        testSubject = new DateHelper(calendarMock);
    }

    @Test
    public void getCurrentDate_shouldCallCalenderGetTimeInMillis() {
        long expectedDate = Calendar.getInstance().getTimeInMillis();
        when(calendarMock.getTimeInMillis()).thenReturn(expectedDate);

        long result = testSubject.getCurrentDate();

        assertEquals(expectedDate, result);
    }

    @Test
    public void hoursPassed_2_3HoursPassed_shouldReturnTrue() {
        long currentDatePassed3Hours = setUpTimePassed(3, 0);

        assertTrue(testSubject.hoursPassed(currentDatePassed3Hours, 2));
    }

    @Test
    public void hoursPassed_2_1HourPassed_shouldReturnFalse() {
        long currentDatePassed3Hours = setUpTimePassed(1, 0);

        assertFalse(testSubject.hoursPassed(currentDatePassed3Hours, 2));
    }

    @Test
    public void hoursPassed_2_1Hour59Minutes_shouldReturnFalse() {
        long currentDatePassed3Hours = setUpTimePassed(1, 59);

        assertFalse(testSubject.hoursPassed(currentDatePassed3Hours, 2));
    }

    @Test
    public void hoursPassed_2_2HoursPassed_shouldReturnTrue() {
        long currentDatePassed3Hours = setUpTimePassed(2, 0);

        assertTrue(testSubject.hoursPassed(currentDatePassed3Hours, 2));
    }

    @Test
    public void hoursPassed_2_2Hours1MinutePassed_shouldReturnTrue() {
        long currentDatePassed3Hours = setUpTimePassed(2, 1);

        assertTrue(testSubject.hoursPassed(currentDatePassed3Hours, 2));
    }

    @Test
    public void hoursPassed_1_2Hours1MinutePassed_shouldReturnTrue() {
        long currentDatePassed3Hours = setUpTimePassed(2, 1);

        assertTrue(testSubject.hoursPassed(currentDatePassed3Hours, 1));
    }


    private long setUpTimePassed(int timeHours, int timeMinutes) {
        Calendar date = Calendar.getInstance();
        long currentDate = date.getTimeInMillis();

        when(calendarMock.getTimeInMillis()).thenReturn(currentDate);

        date.setTimeInMillis(currentDate);
        date.add(Calendar.HOUR, timeHours);
        date.add(Calendar.MINUTE, timeMinutes);

        return date.getTimeInMillis();
    }
}