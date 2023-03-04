package cabbage.missingwebtracker.backend.core.util;

import cabbage.missingwebtracker.backend.core.report.MissingReportExtension;
import cabbage.missingwebtracker.backend.core.report.MissingReportExtensionSerializer;
import org.spongepowered.configurate.ConfigurationNode;
import org.spongepowered.configurate.jackson.FieldValueSeparatorStyle;
import org.spongepowered.configurate.jackson.JacksonConfigurationLoader;
import org.spongepowered.configurate.loader.ConfigurationLoader;

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

}
