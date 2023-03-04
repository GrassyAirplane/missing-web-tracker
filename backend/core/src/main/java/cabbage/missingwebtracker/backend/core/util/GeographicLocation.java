package cabbage.missingwebtracker.backend.core.util;

import java.util.Optional;

public record GeographicLocation(double longitude, double latitude) {

    public static Optional<GeographicLocation> fromString(String s) {
        String[] raw = s.split(",");
        if (raw.length != 2) {
            return Optional.empty();
        }
        double longitude;
        double latitude;
        try {
            longitude = Double.parseDouble(raw[0]);
            latitude = Double.parseDouble(raw[1]);
        } catch (NumberFormatException ex) {
            return Optional.empty();
        }
        return Optional.of(new GeographicLocation(longitude, latitude));
    }

    @Override
    public String toString() {
        return Utils.formatLocation(this.longitude) + "," + Utils.formatLocation(this.latitude);
    }

}
