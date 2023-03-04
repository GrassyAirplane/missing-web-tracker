package cabbage.missingwebtracker.backend.core;

import cabbage.missingwebtracker.backend.core.report.MissingReport;
import cabbage.missingwebtracker.backend.core.report.PersonMissingReportBuilder;
import cabbage.missingwebtracker.backend.core.report.PetMissingReportBuilder;
import cabbage.missingwebtracker.backend.core.report.ReportSourceType;
import cabbage.missingwebtracker.backend.core.util.Age;
import cabbage.missingwebtracker.backend.core.util.GeographicLocation;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.ByteArrayOutputStream;
import java.nio.charset.StandardCharsets;

@SpringBootApplication
public class BackendApplication {

    private static final GeographicLocation MONASH = new GeographicLocation(-37.911450, 145.146440);

    public static void main(String[] args) {
        SpringApplication.run(BackendApplication.class, args);
    }

    private static void test() {
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


}
