package util;

import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;
import java.util.Locale;

public class Helper {

    public static String formatDecimal(double number) {
        NumberFormat n = NumberFormat.getCurrencyInstance(Locale.getDefault());
        return n.format(number);
    }

    public static LocalDate convertStringToLocalDate(String date){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        TemporalAccessor temporalAccessor = formatter.parse(date);
        return LocalDate.from(temporalAccessor);
    }

}
