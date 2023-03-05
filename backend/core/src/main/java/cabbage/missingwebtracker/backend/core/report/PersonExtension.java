package cabbage.missingwebtracker.backend.core.report;

import cabbage.missingwebtracker.backend.core.util.Gender;
import org.spongepowered.configurate.objectmapping.ConfigSerializable;
import org.spongepowered.configurate.objectmapping.meta.Required;

@ConfigSerializable
public record PersonExtension(@Required Gender gender) implements MissingReportExtension {
}
