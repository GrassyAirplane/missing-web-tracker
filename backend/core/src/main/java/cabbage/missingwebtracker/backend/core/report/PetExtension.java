package cabbage.missingwebtracker.backend.core.report;

import org.spongepowered.configurate.objectmapping.ConfigSerializable;
import org.spongepowered.configurate.objectmapping.meta.Required;

@ConfigSerializable
public record PetExtension(@Required String animalType, @Required String breed) implements MissingReportExtension {

}
