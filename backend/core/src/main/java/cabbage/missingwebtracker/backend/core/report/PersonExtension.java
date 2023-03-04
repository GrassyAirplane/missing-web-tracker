package cabbage.missingwebtracker.backend.core.report;

import cabbage.missingwebtracker.backend.core.util.Gender;
import org.spongepowered.configurate.objectmapping.ConfigSerializable;

@ConfigSerializable
public record PersonExtension(Gender gender) implements MissingReportExtension {
}
