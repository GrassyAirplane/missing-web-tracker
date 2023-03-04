package cabbage.missingwebtracker.backend.core;

import cabbage.missingwebtracker.backend.core.database.MemoryMissingReportDatabase;
import cabbage.missingwebtracker.backend.core.report.*;
import cabbage.missingwebtracker.backend.core.util.Gender;
import cabbage.missingwebtracker.backend.core.util.SerializationContext;
import org.spongepowered.configurate.ConfigurateException;
import org.spongepowered.configurate.ConfigurationNode;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

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

    private static String serializeReport(MissingReport report) throws ConfigurateException {
        ConfigurationNode node = SerializationContext.newNode();
        node.set(report);
        return SerializationContext.newBuilder().buildAndSaveString(node);
    }

    private static String serializeReports(List<MissingReport> reports) throws ConfigurateException {
        ConfigurationNode node = SerializationContext.newNode();
        node.setList(MissingReport.class, reports);
        return SerializationContext.newBuilder().buildAndSaveString(node);
    }

    private static Optional<MissingReport> deserializeReport(String json) throws ConfigurateException {
        ConfigurationNode node = SerializationContext.newBuilder().buildAndLoadString(json);
        final MissingReport report = node.get(MissingReport.class);
        return Optional.ofNullable(report);
    }

    @GetMapping("/reports")
    public String allReports() {
        final List<MissingReport> reportList = new ArrayList<>(database.allReports());
        try {
            return serializeReports(reportList);
        } catch (ConfigurateException ex) {
            throw new RuntimeException(ex);
        }
    }

    @GetMapping(value = "/reports")
    public String queryReportsByNameOrType(@RequestParam(value = "name", required = false) String name,
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

    @GetMapping(value = "/report/{id}")
    public String queryReportById(@PathVariable("id") String id) {
        final UUID uuid = UUID.fromString(id);
        final Optional<MissingReport> optionalReport = database.allReports().stream()
                .filter(report -> uuid.equals(report.uuid()))
                .findFirst();
        if (optionalReport.isEmpty()) {
            return "{}";
        }
        try {
            return serializeReport(optionalReport.get());
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
