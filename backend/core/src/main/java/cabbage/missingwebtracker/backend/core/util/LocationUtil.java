package cabbage.missingwebtracker.backend.core.util;

/**
 * Taken from <a href="https://stackoverflow.com/a/12600225">stackoverflow</a>
 */
public class LocationUtil {
    public static final double AVERAGE_RADIUS_OF_EARTH_KM = 6371;

    public static double calculateDistanceInKilometer(double[] loc1, double[] loc2) {
        return calculateDistanceInKilometer(loc1[0], loc1[1], loc2[0], loc2[1]);
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
