package cabbage.missingwebtracker.backend.core.database;

import cabbage.missingwebtracker.backend.core.report.MissingReport;
import cabbage.missingwebtracker.backend.core.report.ReportType;
import cabbage.missingwebtracker.backend.core.util.LocationUtil;

import java.util.Collection;
import java.util.Collections;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.UUID;
import java.util.function.Predicate;
import java.util.stream.Stream;

public class MemoryMissingReportDatabase {

    private final Map<UUID, MissingReport> reportMap = new HashMap<>();
    private final Map<ReportType, Collection<MissingReport>> reportTypeLookup = new EnumMap<>(ReportType.class);

    public MemoryMissingReportDatabase() {
        for (ReportType reportType : ReportType.values()) {
            this.reportTypeLookup.put(reportType, new LinkedList<>());
        }
    }

    public Stream<MissingReport> reportsNear(double[] location, double radiusKm) {
        Predicate<MissingReport> distanceFilter =
                report -> LocationUtil.calculateDistanceInKilometer(location, report.lastKnownLocation()) <= radiusKm;
        return this.reportMap.values().stream()
                .filter(distanceFilter);
    }

    public void submitReport(MissingReport report) {
        removeReport(report.uuid());
        this.reportMap.put(report.uuid(), report);
        this.reportTypeLookup.get(report.reportType()).add(report);
    }

    public boolean removeReport(UUID reportId) {
        MissingReport removed = this.reportMap.remove(reportId);
        if (removed != null) {
            removeReportFromIndex(removed);
            return true;
        }
        return false;
    }

    private void removeReportFromIndex(MissingReport report) {
        this.reportTypeLookup.get(report.reportType()).remove(report);
    }

    public void purgeResolved() {
        Iterator<MissingReport> iterator = this.reportMap.values().iterator();
        while (iterator.hasNext()) {
            MissingReport report = iterator.next();
            if (report.resolved()) {
                iterator.remove();
                removeReportFromIndex(report);
            }
        }
    }

    public Collection<MissingReport> allReports() {
        return Collections.unmodifiableCollection(this.reportMap.values());
    }

    public Collection<MissingReport> reportOfType(ReportType reportType) {
        return Collections.unmodifiableCollection(this.reportTypeLookup.get(reportType));
    }

    public void clear() {
        this.reportMap.clear();
        this.reportTypeLookup.values().forEach(Collection::clear);
    }

}
