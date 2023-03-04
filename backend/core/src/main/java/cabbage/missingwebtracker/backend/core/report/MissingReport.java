package cabbage.missingwebtracker.backend.core.report;

import java.util.List;
import java.util.UUID;

public interface MissingReport {

    UUID uuid();

    long lastSeenEpochMilli();

    double[] lastKnownLocation();

    boolean resolved();

    ReportSourceType reportSourceType();

    String name();

    String appearance();

    String additionalInformation();

    int[] age();

    ReportType reportType();

    MissingReportExtension extension();

    List<String> images();


}
