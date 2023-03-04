package cabbage.missingwebtracker.backend.core.util;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.regex.Pattern;

public class Utils {

    public static NumberFormat LOCATION_FORMAT = new DecimalFormat("#.###############");

    public static final Pattern NUMERIC = Pattern.compile("\\d+");

    public static boolean isInt(String s) {
        return NUMERIC.matcher(s).matches();
    }

    public static String formatLocation(double location) {
        return LOCATION_FORMAT.format(location);
    }

}
