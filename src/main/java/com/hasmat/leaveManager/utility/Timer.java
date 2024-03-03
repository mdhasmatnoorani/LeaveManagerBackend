package com.hasmat.leaveManager.utility;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Calendar;

/**
 * @author Hasmat.Noorani
 * @since 28-12-23
 */
public class Timer {
    Logger logger = LoggerFactory.getLogger(Timer.class);

    long startTime;
    Duration duration = null;

    public Timer() {
        startTime = Calendar.getInstance().getTimeInMillis();
    }

    public void resetTimer() {
        startTime = Calendar.getInstance().getTimeInMillis();
    }

    public void printTime() {
        logger.info("Benchmark. Timer: " + (Calendar.getInstance().getTimeInMillis() - startTime) + " milliseconds.");
    }

    public double getTime(Duration.Unit unit) {
        duration = new Duration(0, 0, 0, 0, (long) (Calendar.getInstance().getTimeInMillis() - startTime));
        return duration.getTime(unit);
    }
}
