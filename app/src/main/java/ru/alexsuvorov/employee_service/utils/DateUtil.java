package ru.alexsuvorov.employee_service.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class DateUtil {

    public static String validDateString(String strDate) {
        final List<String> dateFormats = Arrays.asList("yyyy-MM-dd", "dd-MM-yyyy", "MM-dd-yyyy", "MM/dd/yyyy", "dd/MM/yyyy");
        SimpleDateFormat sdf;
        final String RU_FORMAT = "dd.MM.yyyy";
        String output = "-";

        for (String format : dateFormats) {
            sdf = new SimpleDateFormat(format, new Locale("ru"));
            sdf.setLenient(false);
            try {
                if (sdf.parse(strDate) != null) {
                    Date date = sdf.parse(strDate);
                    sdf.applyPattern(RU_FORMAT);
                    return sdf.format(date);
                }
                break;
            } catch (ParseException e) {

            }
        }
        return output;
    }

    public static int calculateAge(String bithday) {
        int age = 0;
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy", new Locale("ru"));
        Date date = null;
        try {
            date = sdf.parse(bithday);
        } catch (ParseException e) {
            //e.printStackTrace();
        }
        if (date == null) return 0;

        Calendar bithdayDate = Calendar.getInstance();
        Calendar today = Calendar.getInstance();

        bithdayDate.setTime(date);

        int year = bithdayDate.get(Calendar.YEAR);
        int month = bithdayDate.get(Calendar.MONTH);
        int day = bithdayDate.get(Calendar.DAY_OF_MONTH);

        bithdayDate.set(year, month + 1, day);

        age = today.get(Calendar.YEAR) - bithdayDate.get(Calendar.YEAR);

        if (today.get(Calendar.DAY_OF_YEAR) < bithdayDate.get(Calendar.DAY_OF_YEAR)) {
            age--;
        }

        return age;
    }
}