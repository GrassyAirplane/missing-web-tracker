package cabbage.missingwebtracker.backend.core.scraping;

public record ScrapedReport(String name, int age, long lastSeenEpochMilli, String lastKnownLocation) {
}
