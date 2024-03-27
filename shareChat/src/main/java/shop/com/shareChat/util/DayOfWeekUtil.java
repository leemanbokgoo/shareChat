package shop.com.shareChat.util;


import java.time.DayOfWeek;

public class DayOfWeekUtil {
    public static DayOfWeek parseDayOfWeek(int dayOfWeekNumber) {
        switch (dayOfWeekNumber) {
            case 0:
                return DayOfWeek.MONDAY;
            case 1:
                return DayOfWeek.TUESDAY;
            case 2:
                return DayOfWeek.WEDNESDAY;
            case 3:
                return DayOfWeek.THURSDAY;
            case 4:
                return DayOfWeek.FRIDAY;
            case 5:
                return DayOfWeek.SATURDAY;
            case 6:
                return DayOfWeek.SUNDAY;
            default:
                throw new IllegalArgumentException("Invalid day of week number: " + dayOfWeekNumber);
        }
    }
}
