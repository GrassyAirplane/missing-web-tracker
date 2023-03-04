package cabbage.missingwebtracker.backend.core.util;

import cabbage.missingwebtracker.backend.core.report.MissingReport;
import cabbage.missingwebtracker.backend.core.report.MissingReportExtension;
import cabbage.missingwebtracker.backend.core.report.MissingReportExtensionSerializer;
import org.spongepowered.configurate.ConfigurateException;
import org.spongepowered.configurate.ConfigurationNode;
import org.spongepowered.configurate.jackson.FieldValueSeparatorStyle;
import org.spongepowered.configurate.jackson.JacksonConfigurationLoader;
import org.spongepowered.configurate.loader.ConfigurationLoader;

import java.io.File;
import java.nio.file.Path;
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

    /**
     * Get the working directory based on OS
     * @return Return the path to the working directory
     * Taken from https://stackoverflow.com/a/16660314
     */
    public static String workingDirectory() {
        String workingDirectory;
        //here, we assign the name of the OS, according to Java, to a variable...
        String OS = (System.getProperty("os.name")).toUpperCase();
        //to determine what the workingDirectory is.
        //if it is some version of Windows
        if (OS.contains("WIN")) {
            //it is simply the location of the "AppData" folder
            workingDirectory = System.getenv("AppData");
        }
        //Otherwise, we assume Linux or Mac
        else {
            //in either case, we would start in the user's home directory
            workingDirectory = System.getProperty("user.home");
            //if we are on a Mac, we are not done, we look for "Application Support"
            workingDirectory += "/Library/Application Support";
        }
        return workingDirectory;
    }

    public static Path dataStore() {
        return Path.of(workingDirectory()).resolve("missing-web-tracker");
    }

}
