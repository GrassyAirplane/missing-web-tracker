package cabbage.missingwebtracker.backend.core.report;

import cabbage.missingwebtracker.backend.core.util.Age;
import cabbage.missingwebtracker.backend.core.util.GeographicLocation;

import java.util.Calendar;
import java.util.UUID;

public final class PetMissingReportBuilder {
    private UUID uuid;
    private String name;
    private Age age;
    private long lastSeenEpochMilli;
    private String animalType;
    private String breed;
    private String appearance;
    private ReportSourceType reportSourceType;
    private GeographicLocation lastKnownLocation;
    private String additionalInformation;
    private boolean resolved;

    public PetMissingReportBuilder randomUuid() {
        this.uuid = UUID.randomUUID();
        return this;
    }

    public PetMissingReportBuilder uuid(UUID uuid) {
        this.uuid = uuid;
        return this;
    }

    public PetMissingReportBuilder name(String name) {
        this.name = name;
        return this;
    }

    public PetMissingReportBuilder age(Age age) {
        this.age = age;
        return this;
    }

    public PetMissingReportBuilder lastSeen(Calendar calendar) {
        return lastSeen(calendar.getTimeInMillis());
    }

    public PetMissingReportBuilder lastSeen(long lastSeenEpochMilli) {
        this.lastSeenEpochMilli = lastSeenEpochMilli;
        return this;
    }

    public PetMissingReportBuilder animalType(String animalType) {
        this.animalType = animalType;
        return this;
    }

    public PetMissingReportBuilder breed(String breed) {
        this.breed = breed;
        return this;
    }

    public PetMissingReportBuilder appearance(String appearance) {
        this.appearance = appearance;
        return this;
    }

    public PetMissingReportBuilder reportSourceType(ReportSourceType reportSourceType) {
        this.reportSourceType = reportSourceType;
        return this;
    }

    public PetMissingReportBuilder lastKnownLocation(GeographicLocation lastKnownLocation) {
        this.lastKnownLocation = lastKnownLocation;
        return this;
    }

    public PetMissingReportBuilder additionalInformation(String additionalInformation) {
        this.additionalInformation = additionalInformation;
        return this;
    }

    public PetMissingReportBuilder resolved(boolean resolved) {
        this.resolved = resolved;
        return this;
    }

    public PetMissingReport build() {
        return new PetMissingReport(uuid, name, age, lastSeenEpochMilli, animalType, breed, appearance, reportSourceType, lastKnownLocation, additionalInformation, resolved);
    }
}
