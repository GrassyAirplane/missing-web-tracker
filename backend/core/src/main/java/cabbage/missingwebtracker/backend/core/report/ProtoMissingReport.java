package cabbage.missingwebtracker.backend.core.report;

import cabbage.missingwebtracker.backend.core.util.ImageStore;

public class ProtoMissingReport {

    private final ReportType reportType;

    private final ImageStore imageStore = new ImageStore();

    private final MissingReport report;

    public ProtoMissingReport(ReportType reportType, MissingReport report) {
        this.reportType = reportType;
        this.report = report;
    }

    public ReportType reportType() {
        return this.reportType;
    }

    public ImageStore images() {
        return this.imageStore;
    }

    public MissingReport report() {
        return this.report;
    }

}
