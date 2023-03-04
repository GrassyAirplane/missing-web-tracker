package cabbage.missingwebtracker.backend.core.report;

import org.spongepowered.configurate.objectmapping.ConfigSerializable;
import org.spongepowered.configurate.objectmapping.meta.Required;

import java.util.List;
import java.util.UUID;

@ConfigSerializable
public record MissingReport(UUID uuid,
                            @Required
                            String name,
                            String appearance,
                            String additionalInformation,
                            int[] age,
                            @Required
                            long lastSeenEpochMilli,
                            double[] lastKnownLocation,
                            boolean resolved,
                            @Required
                            ReportSourceType reportSourceType,
                            @Required
                            ReportType reportType,
                            @Required
                            MissingReportExtension extension,
                            List<String> images) {

}
