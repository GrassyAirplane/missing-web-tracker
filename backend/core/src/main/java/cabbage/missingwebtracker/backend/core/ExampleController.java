package cabbage.missingwebtracker.backend.core;

import cabbage.missingwebtracker.backend.core.database.MemoryMissingReportDatabase;
import cabbage.missingwebtracker.backend.core.report.MissingReport;
import cabbage.missingwebtracker.backend.core.report.MissingReportBaseBuilder;
import cabbage.missingwebtracker.backend.core.report.PersonExtension;
import cabbage.missingwebtracker.backend.core.report.PetExtension;
import cabbage.missingwebtracker.backend.core.report.ReportSourceType;
import cabbage.missingwebtracker.backend.core.report.ReportType;
import cabbage.missingwebtracker.backend.core.scraping.MissingPersonGovAuScraper;
import cabbage.missingwebtracker.backend.core.util.Gender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.Arrays;
import java.util.Random;

@Controller
public class ExampleController {

    private static final double[] GLOBAL = new double[] { -180, 180 };

    private static final double[] MAX_MELB = new double[] { -37.564340, 145.429444 };
    private static final double[] MIN_MELB = new double[] { -38.126187, 144.397366 };


    @Autowired
    public ExampleController(MemoryMissingReportDatabase reportDatabase, MissingPersonGovAuScraper scraper) {
        registerExamples(reportDatabase);
    }

    public double[] generateRandomCoordinates(Random random) {
        final double lat = random.nextDouble(-85, 85);
        final double lng = random.nextDouble(GLOBAL[0], GLOBAL[1]);
        return new double[] { lat, lng };
    }

    public void registerScraping(MissingPersonGovAuScraper scraper) {
        scraper.updateDatabase();
    }

    public void registerExamples(MemoryMissingReportDatabase reportDatabase) {
        Random random = new Random();
        for (int i = 0; i < 100; i++) {
            boolean male = random.nextBoolean();
            MissingReport samplePerson = new MissingReportBaseBuilder()
                    .randomUuid()
                    .name("Alex")
                    .reportType(ReportType.PERSON)
                    .reportSourceType(ReportSourceType.USER)
                    .age(new int[] { 16, 0 })
                    .appearance("161cm, asian")
                    .lastKnownLocation(generateRandomCoordinates(random))
                    .additionalInformation("n/a")
                    .lastSeenEpochMilli(System.currentTimeMillis())
                    .images(Arrays.asList("face.jpg", "full.jpg"))
                    .extension(new PersonExtension(male ? Gender.MALE : Gender.FEMALE))
                    .build();

            MissingReport samplePet = new MissingReportBaseBuilder()
                    .randomUuid()
                    .name("Sally")
                    .reportType(ReportType.PET)
                    .reportSourceType(ReportSourceType.USER)
                    .age(new int[] { 1, 2 })
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
