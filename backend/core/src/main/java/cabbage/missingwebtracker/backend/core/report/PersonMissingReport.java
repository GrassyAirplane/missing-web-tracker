package cabbage.missingwebtracker.backend.core.report;

import cabbage.missingwebtracker.backend.core.util.Age;
import cabbage.missingwebtracker.backend.core.util.GeographicLocation;

import java.util.UUID;

public record PersonMissingReport(UUID uuid, String name, Age age, long lastSeenEpochMilli, String appearance,
                                  ReportSourceType reportSourceType, GeographicLocation lastKnownLocation,
                                  String additionalInformation, boolean resolved)
        implements MissingReport {


    @Override
    public ReportType reportType() {
        return ReportType.PERSON;
    }

    @Override
    public ProtoMissingReport toProtoReport() {
        return null;
    }
}
