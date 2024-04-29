package com.example.rentalchalets.utils;

import android.util.Log;
import android.widget.RadioButton;

import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class FeeCalculator {


    public double calculateRoomTypeFee(boolean isWeekend, Date startDate,Date endDate, String roomType) {
        double rate = 0;

        boolean isHigherRatePeriod = isDateWithinHigherRatePeriod(startDate);

        if (roomType.equals("Simple Room")) {
            if (isWeekend && isHigherRatePeriod) {
                rate = 92;
            } else if (isWeekend) {
                rate = 80;
            } else if (isHigherRatePeriod) {
                rate = 85;
            } else {
                rate = 78;
            }
        } else if (roomType.equals("Luxurious Princess Room")) {
            if (isWeekend && isHigherRatePeriod) {
                rate = 185;
            } else if (isWeekend) {
                rate = 165;
            } else if (isHigherRatePeriod) {
                rate = 170;
            } else {
                rate = 150;
            }
        } else if (roomType.equals("Royal Luxurious Room")) {
            if (isWeekend && isHigherRatePeriod) {
                rate = 223;
            } else if (isWeekend) {
                rate = 195;
            } else if (isHigherRatePeriod) {
                rate = 205;
            } else {
                rate = 180;
            }
        }

//        long days =getNumberOfDays(startDate,endDate);
//        Log.w("Days number of days",String.valueOf(days));
        return rate;
    }
    private long getNumberOfDays(Date startDate, Date endDate) {
        long diffInMillis = endDate.getTime() - startDate.getTime();
        return TimeUnit.DAYS.convert(diffInMillis, TimeUnit.MILLISECONDS);
    }

    public double calculateBuffetOfKingsFee(boolean isWeekend,Date startDate,Date endDate, String activityBuffet) {
        double rate = 0;

        if (isWeekend) {
            rate = 39;
        } else {
            rate = 32;
        }
        Log.w("buffet",activityBuffet);
        // Multiply by rate based on activityBuffet
        if (activityBuffet.equals("Everyday")) {
            rate *= 7;
        } else if (activityBuffet.equals("Day of Week")) {
            rate *= 1; // assuming 5 weekdays
        } else if (activityBuffet.equals("Weekends")) {
            rate *= 2;
        }

        return rate;
    }

    public double calculateMassageFee(boolean isWeekend,Date startDate,Date endDate) {
        double rate = 0;

        if (isWeekend) {
            rate = 52;
        } else {
            rate = 40;
        }

        return rate;
    }

    public double calculateSaunaFee(boolean isWeekend, Date startDate,Date endDate) {
        double rate = 0;

        boolean isHigherRatePeriod = isDateWithinHigherRatePeriod(startDate);

        if (isWeekend && isHigherRatePeriod) {
            rate = 53;
        } else if (isWeekend) {
            rate = 35;
        } else if (isHigherRatePeriod) {
            rate = 45;
        } else {
            rate = 25;
        }

        return rate;
    }


    public double calculateBeautyTreatmentFee(boolean isWeekend,Date startDate,Date endDate, String beautyTreatment) {
        double rate = 0;

        if (beautyTreatment.equals("Manicure and Pedicure")) {
            if (isWeekend) {
                rate = 30;
            } else {
                rate = 22;
            }
        } else if (beautyTreatment.equals("Facial Care")) {
            if (isWeekend) {
                rate = 35;
            } else {
                rate = 25;
            }
        } else if (beautyTreatment.equals("Hairdressing")) {
            if (isWeekend) {
                rate = 40;
            } else {
                rate = 32;
            }
        }

        return rate;
    }

    private static boolean isDateWithinHigherRatePeriod(Date startDate) {
        // Create a calendar object from the date
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(startDate);

        // Check if the date falls within the higher rate period
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        return (month == Calendar.JUNE && day >= 21) ||
                (month == Calendar.JULY) ||
                (month == Calendar.AUGUST && day <= 20) ||
                (month == Calendar.DECEMBER && day >= 18) ||
                (month == Calendar.JANUARY && day <= 7);
    }

    public boolean isWeekend(Date startDate) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(startDate);

        int day = calendar.get(Calendar.DAY_OF_WEEK);
        return day == Calendar.SATURDAY || day == Calendar.SUNDAY;
    }


}