package cabbage.missingwebtracker.backend.core.util;

import cabbage.missingwebtracker.backend.core.report.MissingReport;
import cabbage.missingwebtracker.backend.core.report.MissingReportExtension;
import cabbage.missingwebtracker.backend.core.report.MissingReportExtensionSerializer;
import org.spongepowered.configurate.ConfigurateException;
import org.spongepowered.configurate.ConfigurationNode;
import org.spongepowered.configurate.jackson.FieldValueSeparatorStyle;
import org.spongepowered.configurate.jackson.JacksonConfigurationLoader;
import org.spongepowered.configurate.loader.ConfigurationLoader;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class SerializationContext {

    public static final ConfigurationLoader<?> GLOBAL_LOADER = newBuilder().build();

    public static ConfigurationNode newNode() {
        synchronized (GLOBAL_LOADER) {
            return GLOBAL_LOADER.createNode();
        }
    }

    public static JacksonConfigurationLoader.Builder newBuilder() {
        return JacksonConfigurationLoader.builder()
                .defaultOptions(options -> options.serializers(builder -> builder.registerExact(MissingReportExtension.class, new MissingReportExtensionSerializer())))
                .fieldValueSeparatorStyle(FieldValueSeparatorStyle.SPACE_AFTER);
    }

    public static String serializeReport(MissingReport report) throws ConfigurateException {
        ConfigurationNode node = SerializationContext.newNode();
        node.set(report);
        return SerializationContext.newBuilder().buildAndSaveString(node);
    }

    public static String serializeReports(List<MissingReport> reports) throws ConfigurateException {
        ConfigurationNode node = SerializationContext.newNode();
        node.setList(MissingReport.class, reports);
        return SerializationContext.newBuilder().buildAndSaveString(node);
    }

    public static Optional<MissingReport> createNewReport(String json) throws ConfigurateException {
        ConfigurationNode node = SerializationContext.newBuilder().buildAndLoadString(json);
        node.node("uuid").set(UUID.randomUUID());
        final MissingReport report = node.get(MissingReport.class);
        return Optional.ofNullable(report);
    }

}
