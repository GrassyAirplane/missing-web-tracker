package cabbage.missingwebtracker.backend.core.util;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Optional;
import java.util.UUID;
import java.util.regex.Pattern;

public class Utils {

    public static final Pattern NUMERIC = Pattern.compile("\\d+");
    public static NumberFormat LOCATION_FORMAT = new DecimalFormat("#.###############");

    public static boolean isInt(String s) {
        return NUMERIC.matcher(s).matches();
    }

    public static String formatLocation(double location) {
        return LOCATION_FORMAT.format(location);
    }

    public static Optional<UUID> parseUuid(String raw) {
        try {
            return Optional.of(UUID.fromString(raw));
        } catch (IllegalArgumentException ex) {
            return Optional.empty();
        }
    }

}
