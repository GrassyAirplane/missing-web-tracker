package cabbage.missingwebtracker.backend.core;

import cabbage.missingwebtracker.backend.core.database.MemoryMissingReportDatabase;
import cabbage.missingwebtracker.backend.core.report.MissingReport;
import cabbage.missingwebtracker.backend.core.report.MissingReportBaseBuilder;
import cabbage.missingwebtracker.backend.core.report.PersonExtension;
import cabbage.missingwebtracker.backend.core.report.PetExtension;
import cabbage.missingwebtracker.backend.core.report.ReportSourceType;
import cabbage.missingwebtracker.backend.core.report.ReportType;
import cabbage.missingwebtracker.backend.core.util.Gender;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

@SpringBootApplication
public class BackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(BackendApplication.class, args);
    }


}
