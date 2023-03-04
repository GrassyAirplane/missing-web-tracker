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

@Controller
public class ExampleController {

    private static final double[] MONASH = new double[]{-37.911450, 145.146440};


    @Autowired
    public ExampleController(MemoryMissingReportDatabase reportDatabase) {
        registerExamples(reportDatabase);
    }

    public void registerExamples(MemoryMissingReportDatabase reportDatabase) {
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
                .images(Arrays.asList("face.jpg", "full.jpg"))
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
                .images(Arrays.asList("dog1.jpg", "dog2.jpg"))
                .build();
        reportDatabase.submitReport(samplePerson);
        reportDatabase.submitReport(samplePet);
    }

}
