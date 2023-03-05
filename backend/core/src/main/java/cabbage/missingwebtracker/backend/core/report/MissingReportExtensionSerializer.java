package cabbage.missingwebtracker.backend.core.report;

import org.checkerframework.checker.nullness.qual.Nullable;
import org.spongepowered.configurate.ConfigurationNode;
import org.spongepowered.configurate.serialize.SerializationException;
import org.spongepowered.configurate.serialize.TypeSerializer;

import java.lang.reflect.Type;

public class MissingReportExtensionSerializer implements TypeSerializer<MissingReportExtension> {

    @Override
    public MissingReportExtension deserialize(Type type, ConfigurationNode node) throws SerializationException {
        ConfigurationNode parent = node.parent();
        if (parent == null) {
            throw new SerializationException("Cannot locate report type without parent");
        }
        ConfigurationNode nodeReportType = parent.node("report-type");
        if (nodeReportType == null || nodeReportType.isNull()) {
            return null;
        }
        ReportType reportType = nodeReportType.get(ReportType.class);
        if (reportType == ReportType.PET) {
            return node.get(PetExtension.class);
        } else if (reportType == ReportType.PERSON) {
            return node.get(PersonExtension.class);
        }
        return null;
    }

    @Override
    public void serialize(Type type, @Nullable MissingReportExtension obj, ConfigurationNode node) throws SerializationException {
        if (obj instanceof PetExtension petExtension) {
            node.set(PetExtension.class, petExtension);
        } else if (obj instanceof PersonExtension personExtension) {
            node.set(PersonExtension.class, personExtension);
        }
    }
}
