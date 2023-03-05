package cabbage.missingwebtracker.backend.core.scraping;

import cabbage.missingwebtracker.backend.core.util.Gender;

public final class ScrapedReportBuilder {
    private String name;
    private int age;

    private long lastSeenEpochMilli;
    private String lastKnownLocation;

    private Gender gender;

    public ScrapedReportBuilder name(String name) {
        this.name = name;
        return this;
    }

    public ScrapedReportBuilder age(int age) {
        this.age = age;
        return this;
    }

    public ScrapedReportBuilder lastKnownLocation(String lastKnownLocation) {
        this.lastKnownLocation = lastKnownLocation;
        return this;
    }

    public ScrapedReportBuilder lastKnownEpochMilli(long time) {
        this.lastSeenEpochMilli = time;
        return this;
    }

    public ScrapedReportBuilder gender(Gender gender) {
        this.gender = gender;
        return this;
    }

    public ScrapedReport build() {
        if (name == null || age == 0 || lastSeenEpochMilli == 0 || lastKnownLocation == null) {
            return null;
        }
        return new ScrapedReport(name, age, lastSeenEpochMilli, lastKnownLocation, gender);
    }
}
