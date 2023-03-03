package cabbage.missingwebtracker.backend.core.util;

/**
 * Taken from <a href="https://stackoverflow.com/a/12600225">stackoverflow</a>
 */
public class LocationUtil {
    public static final double AVERAGE_RADIUS_OF_EARTH_KM = 6371;

    public static double calculateDistanceInKilometer(GeographicLocation loc1, GeographicLocation loc2) {
        return calculateDistanceInKilometer(loc1.latitude(), loc1.longitude(), loc2.latitude(), loc2.longitude());
    }

    public static double calculateDistanceInKilometer(double userLat, double userLng,
                                            double venueLat, double venueLng) {

        double latDistance = Math.toRadians(userLat - venueLat);
        double lngDistance = Math.toRadians(userLng - venueLng);

        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(userLat)) * Math.cos(Math.toRadians(venueLat))
                * Math.sin(lngDistance / 2) * Math.sin(lngDistance / 2);

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        return AVERAGE_RADIUS_OF_EARTH_KM * c;
    }

}
