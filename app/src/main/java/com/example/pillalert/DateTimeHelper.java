package com.example.pillalert;

import android.content.Context;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class DateTimeHelper {

    private static final SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault());
    private Date date;

    public DateTimeHelper(String dateTime) throws ParseException {
        date = formatter.parse(dateTime);
    }

    public static int getDifferentMinutes (String targetDate, String actualDate) {

        try {
            // Parse target and consume date-time
            Date target = formatter.parse(targetDate);
            Date actual = formatter.parse(actualDate);

            // Calculate difference in milliseconds
            assert target != null;
            assert actual != null;
            long diffMillis = target.getTime() - actual.getTime();

            // Convert to minutes
            long difference = TimeUnit.MILLISECONDS.toMinutes(diffMillis);

            return (int) difference;
        } catch (ParseException e) {
            return 0;
        }
    }

    public String getDateTime() {
        return formatter.format(date);
    }

    public void addHour(int hour) {
        // Use Calendar to add hour
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.HOUR, hour);

        // Set new date
        date = calendar.getTime();
    }

    public List<String> getIntervalDateTime(int amountDataToGet, int hourInterval) {
        List<String> result = new ArrayList<>();
        result.add(getDateTime()); // current DateTime

        for (int i = 0; i < amountDataToGet - 1; i++) {
            addHour(hourInterval);
            result.add(getDateTime());
        }

        return result;
    }
}