package cabbage.missingwebtracker.backend.core.report;

import org.spongepowered.configurate.objectmapping.ConfigSerializable;

@ConfigSerializable
public record PetExtension(String animalType, String breed) implements MissingReportExtension {

}
