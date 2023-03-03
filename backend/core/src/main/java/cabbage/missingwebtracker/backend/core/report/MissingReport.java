package cabbage.missingwebtracker.backend.core.report;

import java.util.UUID;

public interface MissingReport {

    String name();

    UUID uuid();


    ReportType reportType();

    ProtoMissingReport toProtoReport();

}
