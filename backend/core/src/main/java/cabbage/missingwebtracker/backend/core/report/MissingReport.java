package cabbage.missingwebtracker.backend.core.report;

import cabbage.missingwebtracker.backend.core.util.GeographicLocation;

import java.util.UUID;

public interface MissingReport {

    UUID uuid();

    long lastSeenEpochMilli();

    GeographicLocation lastKnownLocation();

    boolean resolved();

    ReportType reportType();

    ReportSourceType reportSourceType();

    ProtoMissingReport toProtoReport();

}
