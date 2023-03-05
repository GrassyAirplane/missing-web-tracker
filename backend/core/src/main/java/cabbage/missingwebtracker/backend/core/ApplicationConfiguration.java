package cabbage.missingwebtracker.backend.core;

import cabbage.missingwebtracker.backend.core.database.ImageDatabase;
import cabbage.missingwebtracker.backend.core.database.MemoryMissingReportDatabase;
import cabbage.missingwebtracker.backend.core.scraping.MissingPersonGovAuScraper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationConfiguration {

    @Bean
    public MemoryMissingReportDatabase reportDatabase() {
        return new MemoryMissingReportDatabase();
    }

    @Bean
    public ImageDatabase imageDatabase() {
        return new ImageDatabase();
    }

    @Bean
    public MissingPersonGovAuScraper scraper() {
        return new MissingPersonGovAuScraper();
    }

}
