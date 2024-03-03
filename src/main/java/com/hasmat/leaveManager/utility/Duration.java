package com.hasmat.leaveManager.utility;

import java.util.Calendar;

/**
 * @author Hasmat.Noorani
 * @since 28-12-23
 */
public class Duration {
    public enum Unit {
        DAYS(86400000.0), HOURS(3600000.0), MINUTES(60000.0), SECONDS(1000.0), MILLISECONDS(1.0);

        double dividend;

        Unit(Double dividend) {
            this.dividend = dividend;
        }

        public double getDividend() {
            return dividend;
        }
    }

    private long duration = 0;

    public Duration(String days, String hours, String minutes, String seconds, String millis) {
        int daysInt = 0;
        int hoursInt = 0;
        int minutesInt = 0;
        int secondsInt = 0;
        int millisInt = 0;
        try {
            // If value of days is not empty then remove formating character and parse string value to integer
            if (!days.isEmpty()){
                daysInt = Integer.parseInt(StringConverter.removeFormattingCharacters(days));
            }// If value of hours is not empty then remove formating character and parse string value to integer
            if (!hours.isEmpty()){
                hoursInt = Integer.parseInt(StringConverter.removeFormattingCharacters(hours));
            }// If value of minutes is not empty then remove formating character and parse string value to integer
            if (!minutes.isEmpty()){
                minutesInt = Integer.parseInt(StringConverter.removeFormattingCharacters(minutes));
            }// If value of seconds is not empty then remove formating character and parse string value to integer
            if (!seconds.isEmpty()){
                secondsInt = Integer.parseInt(StringConverter.removeFormattingCharacters(seconds));
            }// If value of milliseconds is not empty then remove formating character and parse string value to integer
            if (!millis.isEmpty()){
                millisInt = Integer.parseInt(StringConverter.removeFormattingCharacters(millis));
            }
            setDuration(daysInt, hoursInt, minutesInt, secondsInt, millisInt);
        } catch (NumberFormatException e) {
            // A String could not be parsed into integer
        }
    }

    public Duration(long days, long hours, long minutes, long seconds, long millis) {
        setDuration(days, hours, minutes, seconds, millis);
    }

    public Duration(Calendar calendarTime) {
        if (calendarTime != null) {
            Calendar calendarDate = (Calendar) calendarTime.clone();
            calendarDate.set(Calendar.YEAR, calendarTime.get(Calendar.YEAR));
            calendarDate.set(Calendar.MONTH, calendarTime.get(Calendar.MONTH));
            calendarDate.set(Calendar.DAY_OF_MONTH, calendarTime.get(Calendar.DAY_OF_MONTH));
            calendarDate.set(Calendar.HOUR_OF_DAY, 0);
            calendarDate.set(Calendar.MINUTE, 0);
            calendarDate.set(Calendar.SECOND, 0);
            calendarDate.set(Calendar.MILLISECOND, 0);
            duration = calendarTime.getTimeInMillis() - calendarDate.getTimeInMillis();
        }
    }

    private void setDuration(long days, long hours, long minutes, long seconds, long millis) {
        duration = millis + cvtToMillis(seconds, Unit.SECONDS) + cvtToMillis(minutes, Unit.MINUTES) + cvtToMillis(hours, Unit.HOURS) + cvtToMillis(days, Unit.DAYS);
    }

    public void add(Duration duration) {
        this.duration = this.duration + (long) duration.getTime(Unit.MILLISECONDS);
    }

    public void substract(Duration duration) {
        this.duration = this.duration - (long) duration.getTime(Unit.MILLISECONDS);
    }

    public boolean greater(Duration duration) {
        if (this.duration > duration.getTime(Unit.MILLISECONDS))
            return true;
        return false;
    }

    public boolean smaller(Duration duration) {
        if (this.duration < duration.getTime(Unit.MILLISECONDS))
            return true;
        return false;
    }

    public boolean equals(Duration duration) {
        if (this.duration == duration.getTime(Unit.MILLISECONDS))
            return true;
        return false;
    }

    private long cvtToMillis(long value, Unit unit) {
        return (long) (value * unit.getDividend());
    }

    public double getTime(Unit unit) {
        return (duration / unit.getDividend());
    }

    @Override
    public String toString() {
        StringBuffer result = new StringBuffer();

        result.append(this.duration);

        return result.toString();
    }

}
