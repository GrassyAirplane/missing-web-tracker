package cabbage.missingwebtracker.backend.core.report;

import cabbage.missingwebtracker.backend.core.util.Age;
import cabbage.missingwebtracker.backend.core.util.GeographicLocation;

import java.util.Calendar;
import java.util.UUID;

public final class PersonMissingReportBuilder {
    private UUID uuid;
    private String name;
    private Age age;
    private long lastSeenEpochMilli;
    private String appearance;

    private String additionalInformation;
    private ReportSourceType reportSourceType;
    private GeographicLocation lastKnownLocation;
    private boolean resolved;

    public PersonMissingReportBuilder randomUuid() {
        this.uuid = UUID.randomUUID();
        return this;
    }

    public PersonMissingReportBuilder uuid(UUID uuid) {
        this.uuid = uuid;
        return this;
    }

    public PersonMissingReportBuilder name(String name) {
        this.name = name;
        return this;
    }

    public PersonMissingReportBuilder age(Age age) {
        this.age = age;
        return this;
    }

    public PersonMissingReportBuilder lastSeen(Calendar calendar) {
        return lastSeen(calendar.getTimeInMillis());
    }

    public PersonMissingReportBuilder lastSeen(long lastSeenEpochMilli) {
        this.lastSeenEpochMilli = lastSeenEpochMilli;
        return this;
    }

    public PersonMissingReportBuilder appearance(String appearance) {
        this.appearance = appearance;
        return this;
    }

    public PersonMissingReportBuilder reportSourceType(ReportSourceType reportSourceType) {
        this.reportSourceType = reportSourceType;
        return this;
    }

    public PersonMissingReportBuilder lastKnownLocation(GeographicLocation lastKnownLocation) {
        this.lastKnownLocation = lastKnownLocation;
        return this;
    }

    public PersonMissingReportBuilder additionalInformation(String additionalInformation) {
        this.additionalInformation = additionalInformation;
        return this;
    }

    public PersonMissingReportBuilder resolved(boolean resolved) {
        this.resolved = resolved;
        return this;
    }

    public PersonMissingReport build() {
        return new PersonMissingReport(uuid, name, age, lastSeenEpochMilli, appearance, reportSourceType, lastKnownLocation, additionalInformation, resolved);
    }
}
