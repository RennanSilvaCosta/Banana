package util;

import model.enums.LaunchType;

import java.text.NumberFormat;
import java.util.Locale;

public class Helper {

    public static String formatDecimal(double number) {
        NumberFormat n = NumberFormat.getCurrencyInstance(Locale.getDefault());
        return n.format(number);
    }

    public static String teste (LaunchType teste) {
        return teste.toString();
    }

}
