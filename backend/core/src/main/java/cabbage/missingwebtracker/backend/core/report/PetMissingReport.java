package cabbage.missingwebtracker.backend.core.report;

import cabbage.missingwebtracker.backend.core.util.Age;
import cabbage.missingwebtracker.backend.core.util.GeographicLocation;

import java.util.UUID;

public record PetMissingReport(UUID uuid, String name, Age age, long lastSeenEpochMilli,
                               String animalType, String breed, String appearance, ReportSourceType reportSourceType,
                               GeographicLocation lastKnownLocation, String additionalInformation, boolean resolved)
        implements MissingReport {

    public ReportType reportType() {
        return ReportType.PET;
    }

    @Override
    public ProtoMissingReport toProtoReport() {
        return null;
    }
}
