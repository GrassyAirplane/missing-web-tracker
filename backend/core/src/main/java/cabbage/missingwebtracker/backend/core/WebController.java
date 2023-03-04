package cabbage.missingwebtracker.backend.core;

import cabbage.missingwebtracker.backend.core.database.MemoryMissingReportDatabase;
import cabbage.missingwebtracker.backend.core.report.MissingReport;
import cabbage.missingwebtracker.backend.core.report.PersonMissingReportBuilder;
import cabbage.missingwebtracker.backend.core.report.PetMissingReportBuilder;
import cabbage.missingwebtracker.backend.core.report.ReportSourceType;
import cabbage.missingwebtracker.backend.core.report.ReportType;
import cabbage.missingwebtracker.backend.core.util.Age;
import cabbage.missingwebtracker.backend.core.util.GeographicLocation;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.io.ByteArrayOutputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Stream;

@RestController
public class WebController {

    private static final GeographicLocation MONASH = new GeographicLocation(-37.911450, 145.146440);
    private final MemoryMissingReportDatabase database = new MemoryMissingReportDatabase();

    public WebController() {
        registerDummyReports();
    }

    private static String serializeReport(MissingReport report) {
        ObjectMapper mapper = new JsonMapper();
        final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        try {
            mapper.writeValue(outputStream, report);
        } catch (Exception ex) {
            throw new IllegalArgumentException(ex);
        }
        return outputStream.toString(StandardCharsets.UTF_8);
    }

    private static String serializeReports(List<MissingReport> reports) {
        ObjectMapper mapper = new JsonMapper();
        final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        try {
            mapper.writeValue(outputStream, reports);
        } catch (Exception ex) {
            throw new IllegalArgumentException(ex);
        }
        return outputStream.toString(StandardCharsets.UTF_8);
    }

    @RequestMapping("/reports")
    public String allReports() {
        final List<MissingReport> reportList = new ArrayList<>(database.allReports());
        return serializeReports(reportList);
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
        return serializeReports(stream.toList());
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
        return serializeReport(optionalReport.get());
    }

    @ExceptionHandler({IllegalArgumentException.class})
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public String handleException(IllegalArgumentException exception) {
        return "{ \"errorMsg\": " + "\"" + exception.getMessage() + "\"" + "}";
    }

    private void registerDummyReports() {
        MissingReport samplePerson = new PersonMissingReportBuilder()
                .randomUuid()
                .name("Andrew W")
                .reportSourceType(ReportSourceType.USER)
                .age(new Age(16, 0, 0))
                .appearance("161cm, asian")
                .lastKnownLocation(MONASH)
                .additionalInformation("n/a")
                .lastSeen(System.currentTimeMillis())
                .build();
        MissingReport samplePet = new PetMissingReportBuilder()
                .randomUuid()
                .name("Sally")
                .reportSourceType(ReportSourceType.USER)
                .animalType("Dog")
                .age(new Age(1, 2, 0))
                .appearance("small doggo")
                .breed("corgi")
                .lastKnownLocation(MONASH)
                .lastSeen(System.currentTimeMillis())
                .additionalInformation("sally likes cuddling up to random people")
                .resolved(true)
                .build();
        this.database.submitReport(samplePerson);
        this.database.submitReport(samplePet);
    }

}
