package util;

import java.text.NumberFormat;
import java.util.Locale;

public class Helper {

    public static String formatDecimal(double number) {
        NumberFormat n = NumberFormat.getCurrencyInstance(Locale.getDefault());
        return n.format(number);
    }

}
