package cabbage.missingwebtracker.backend.core.report;

import org.spongepowered.configurate.objectmapping.ConfigSerializable;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@ConfigSerializable
public record MissingReport(UUID uuid,
                            String name,
                            String appearance,
                            String additionalInformation,
                            int[] age,
                            long lastSeenEpochMilli,
                            double[] lastKnownLocation,
                            boolean resolved,
                            ReportSourceType reportSourceType,
                            ReportType reportType,
                            MissingReportExtension extension,
                            List<String> images) {

}
