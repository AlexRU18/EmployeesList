package ru.alexsuvorov.employee_service.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
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
}