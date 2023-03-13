package cabbage.missingwebtracker.backend.core.scraping;

import cabbage.missingwebtracker.backend.core.util.Gender;

public record ScrapedReport(String name, int age, long lastSeenEpochMilli, String lastKnownLocation, Gender gender) {
}
