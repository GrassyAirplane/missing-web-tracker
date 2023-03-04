package cabbage.missingwebtracker.backend.core;

import cabbage.missingwebtracker.backend.core.database.MemoryMissingReportDatabase;
import cabbage.missingwebtracker.backend.core.report.*;
import cabbage.missingwebtracker.backend.core.util.Gender;
import org.spongepowered.configurate.ConfigurateException;
import org.spongepowered.configurate.ConfigurationNode;
import org.spongepowered.configurate.jackson.JacksonConfigurationLoader;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Stream;

@RestController
public class WebController {

    private static final double[] MONASH = new double[]{-37.911450, 145.146440};
    private final MemoryMissingReportDatabase database = new MemoryMissingReportDatabase();

    public WebController() {
        registerDummyReports();
    }

    private static String serializeReport(MissingReportBase report) throws ConfigurateException {
        final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        final BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(outputStream));
        JacksonConfigurationLoader configurationLoader = JacksonConfigurationLoader.builder()
                .defaultOptions(options -> options.serializers(serializer -> serializer.registerExact(MissingReportExtension.class, new MissingReportExtensionSerializer())))
                .sink(() -> writer)
                .build();
        ConfigurationNode node = configurationLoader.createNode();
        node.set(MissingReportBase.class, report);
        configurationLoader.save(node);
        return outputStream.toString(StandardCharsets.UTF_8);
    }

    private static String serializeReports(List<MissingReport> reports) throws ConfigurateException {

        final List<MissingReportBase> target = reports.stream().map(MissingReportBase.class::cast).toList();
        final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        final BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(outputStream));
        JacksonConfigurationLoader configurationLoader = JacksonConfigurationLoader.builder()
                .defaultOptions(options -> options.serializers(serializer -> serializer.registerExact(MissingReportExtension.class, new MissingReportExtensionSerializer())))
                .sink(() -> writer)
                .build();
        ConfigurationNode node = configurationLoader.createNode();
        node.setList(MissingReportBase.class, target);
        configurationLoader.save(node);
        return outputStream.toString(StandardCharsets.UTF_8);
    }

    @RequestMapping("/reports")
    public String allReports() {
        final List<MissingReport> reportList = new ArrayList<>(database.allReports());
        try {
            return serializeReports(reportList);
        } catch (ConfigurateException ex) {
            throw new RuntimeException(ex);
        }
    }

    @RequestMapping(value = "/reports", method = RequestMethod.GET)
    public String queryReportsByCriteria(@RequestParam(value = "name", required = false) String name,
                                         @RequestParam(value = "reportType", required = false) ReportType reportType) {
        Stream<MissingReport> stream = database.allReports().stream();
        if (name != null) {
            stream = stream.filter(report -> report.name().startsWith(name));
        }
        if (reportType != null) {
            stream = stream.filter(report -> report.reportType() == reportType);
        }
        try {
            return serializeReports(stream.toList());
        } catch (ConfigurateException ex) {
            throw new RuntimeException(ex);
        }
    }

    @RequestMapping(value = "/report/{id}", method = RequestMethod.GET)
    public String queryReportById(@PathVariable("id") String id) {
        final UUID uuid = UUID.fromString(id);
        final Optional<MissingReport> optionalReport = database.allReports().stream()
                .filter(report -> uuid.equals(report.uuid()))
                .findFirst();
        if (optionalReport.isEmpty()) {
            return "{}";
        }
        try {
            return serializeReport((MissingReportBase) optionalReport.get());
        } catch (ConfigurateException ex) {
            throw new RuntimeException(ex);
        }
    }

    @ExceptionHandler({RuntimeException.class})
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public String handleException(RuntimeException exception) {
        exception.printStackTrace();
        return "{ \"errorMsg\": " + "\"" + exception.getMessage() + "\"" + "}";
    }

    private void registerDummyReports() {
        MissingReport samplePerson = new MissingReportBaseBuilder()
                .randomUuid()
                .name("Andrew W")
                .reportType(ReportType.PERSON)
                .reportSourceType(ReportSourceType.USER)
                .age(new int[]{16, 0})
                .appearance("161cm, asian")
                .lastKnownLocation(MONASH)
                .additionalInformation("n/a")
                .lastSeenEpochMilli(System.currentTimeMillis())
                .extension(new PersonExtension(Gender.MALE))
                .build();

        MissingReport samplePet = new MissingReportBaseBuilder()
                .randomUuid()
                .name("Sally")
                .reportType(ReportType.PET)
                .reportSourceType(ReportSourceType.USER)
                .age(new int[]{1, 2})
                .lastKnownLocation(MONASH)
                .lastSeenEpochMilli(System.currentTimeMillis())
                .additionalInformation("sally likes cuddling up to random people")
                .resolved(true)
                .extension(new PetExtension("Dog", "Corgi"))
                .build();
        this.database.submitReport(samplePerson);
        this.database.submitReport(samplePet);
    }

}
