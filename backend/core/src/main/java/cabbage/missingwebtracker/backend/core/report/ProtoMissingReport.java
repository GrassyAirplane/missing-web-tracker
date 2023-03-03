package cabbage.missingwebtracker.backend.core.report;

import cabbage.missingwebtracker.backend.core.util.ImageStore;
import cabbage.missingwebtracker.backend.core.util.PropertyStore;

public class ProtoMissingReport {

    private final ReportType reportType;
    private final PropertyStore propertyStore = new PropertyStore();

    private final ImageStore imageStore = new ImageStore();

    public ProtoMissingReport(ReportType reportType) {
        this.reportType = reportType;
    }

    public ReportType reportType() {
        return this.reportType;
    }

    public PropertyStore properties() {
        return this.propertyStore;
    }

    public ImageStore images() {
        return this.imageStore;
    }

}
