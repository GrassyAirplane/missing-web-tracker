package cabbage.missingwebtracker.backend.core;

import cabbage.missingwebtracker.backend.core.database.MemoryMissingReportDatabase;
import cabbage.missingwebtracker.backend.core.report.MissingReport;
import cabbage.missingwebtracker.backend.core.report.MissingReportBaseBuilder;
import cabbage.missingwebtracker.backend.core.report.PersonExtension;
import cabbage.missingwebtracker.backend.core.report.PetExtension;
import cabbage.missingwebtracker.backend.core.report.ReportSourceType;
import cabbage.missingwebtracker.backend.core.report.ReportType;
import cabbage.missingwebtracker.backend.core.util.Gender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.Arrays;
import java.util.Random;

@Controller
public class ExampleController {
    private static final double[] MAX = new double[]{-37.564340, 145.429444};
    private static final double[] MIN = new double[]{-38.126187, 144.397366};

    @Autowired
    public ExampleController(MemoryMissingReportDatabase reportDatabase) {
        registerExamples(reportDatabase);
    }

    public double[] generateRandomCoordinates(Random random) {
        final double lat = random.nextDouble(MIN[0], MAX[0]);
        final double lng = random.nextDouble(MIN[1], MAX[1]);
        return new double[]{lat, lng};
    }

    public void registerExamples(MemoryMissingReportDatabase reportDatabase) {
        Random random = new Random();
        for (int i = 0; i < 10; i++) {
            MissingReport samplePerson = new MissingReportBaseBuilder()
                    .randomUuid()
                    .name("Andrew")
                    .reportType(ReportType.PERSON)
                    .reportSourceType(ReportSourceType.USER)
                    .age(new int[]{16, 0})
                    .appearance("161cm, asian")
                    .lastKnownLocation(generateRandomCoordinates(random))
                    .additionalInformation("n/a")
                    .lastSeenEpochMilli(System.currentTimeMillis())
                    .images(Arrays.asList("face.jpg", "full.jpg"))
                    .extension(new PersonExtension(Gender.MALE))
                    .build();

            MissingReport samplePet = new MissingReportBaseBuilder()
                    .randomUuid()
                    .name("Sally")
                    .reportType(ReportType.PET)
                    .reportSourceType(ReportSourceType.USER)
                    .age(new int[]{1, 2})
                    .lastKnownLocation(generateRandomCoordinates(random))
                    .lastSeenEpochMilli(System.currentTimeMillis())
                    .additionalInformation("sally likes cuddling up to random people")
                    .resolved(true)
                    .extension(new PetExtension("Dog", "Corgi"))
                    .images(Arrays.asList("dog1.jpg", "dog2.jpg"))
                    .build();
            reportDatabase.submitReport(samplePerson);
            reportDatabase.submitReport(samplePet);
        }
    }

}
