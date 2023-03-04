package cabbage.missingwebtracker.backend.core.report;

import java.util.List;
import java.util.UUID;

public final class MissingReportBaseBuilder {
    private UUID uuid;
    private String name;

    private String appearance;
    private String additionalInformation;
    private int[] age;
    private long lastSeenEpochMilli;
    private double[] lastKnownLocation;
    private boolean resolved;
    private ReportSourceType reportSourceType;
    private ReportType reportType;
    private MissingReportExtension extension;

    private List<String> images;

    public MissingReportBaseBuilder randomUuid() {
        this.uuid = UUID.randomUUID();
        return this;
    }

    public MissingReportBaseBuilder uuid(UUID uuid) {
        this.uuid = uuid;
        return this;
    }

    public MissingReportBaseBuilder name(String name) {
        this.name = name;
        return this;
    }

    public MissingReportBaseBuilder appearance(String appearance) {
        this.appearance = appearance;
        return this;
    }

    public MissingReportBaseBuilder additionalInformation(String additionalInformation) {
        this.additionalInformation = additionalInformation;
        return this;
    }

    public MissingReportBaseBuilder age(int[] age) {
        this.age = age;
        return this;
    }

    public MissingReportBaseBuilder lastSeenEpochMilli(long lastSeenEpochMilli) {
        this.lastSeenEpochMilli = lastSeenEpochMilli;
        return this;
    }

    public MissingReportBaseBuilder lastKnownLocation(double[] lastKnownLocation) {
        this.lastKnownLocation = lastKnownLocation;
        return this;
    }

    public MissingReportBaseBuilder resolved(boolean resolved) {
        this.resolved = resolved;
        return this;
    }

    public MissingReportBaseBuilder reportSourceType(ReportSourceType reportSourceType) {
        this.reportSourceType = reportSourceType;
        return this;
    }

    public MissingReportBaseBuilder reportType(ReportType reportType) {
        this.reportType = reportType;
        return this;
    }

    public MissingReportBaseBuilder extension(MissingReportExtension extension) {
        this.extension = extension;
        return this;
    }

    public MissingReportBaseBuilder images(List<String> images) {
        this.images = images;
        return this;
    }

    public MissingReport build() {
        return new MissingReport(uuid, name, appearance, additionalInformation, age, lastSeenEpochMilli, lastKnownLocation, resolved, reportSourceType, reportType, extension, images);
    }
}
