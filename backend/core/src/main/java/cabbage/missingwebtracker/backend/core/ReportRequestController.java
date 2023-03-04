package cabbage.missingwebtracker.backend.core;

import cabbage.missingwebtracker.backend.core.database.ImageDatabase;
import cabbage.missingwebtracker.backend.core.database.MemoryMissingReportDatabase;
import cabbage.missingwebtracker.backend.core.report.MissingReport;
import cabbage.missingwebtracker.backend.core.report.ReportType;
import cabbage.missingwebtracker.backend.core.util.LocationUtil;
import cabbage.missingwebtracker.backend.core.util.SerializationContext;
import cabbage.missingwebtracker.backend.core.util.Utils;
import org.spongepowered.configurate.ConfigurateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;
import java.util.UUID;
import java.util.stream.Stream;

@RestController
public class ReportRequestController {

    @Autowired
    private MemoryMissingReportDatabase reportDatabase;

    @Autowired
    private ImageDatabase imageDatabase;

    @GetMapping(value = "/reports")
    public String queryReportsByNameOrType(@RequestParam(value = "name", required = false) String name,
                                           @RequestParam(value = "reportType", required = false) ReportType reportType) {
        Stream<MissingReport> stream = reportDatabase.allReports().stream();
        if (name != null) {
            stream = stream.filter(report -> report.name().startsWith(name));
        }
        if (reportType != null) {
            stream = stream.filter(report -> report.reportType() == reportType);
        }
        try {
            return SerializationContext.serializeReports(stream.toList());
        } catch (ConfigurateException ex) {
            throw new RuntimeException(ex);
        }
    }

    @GetMapping(value = "/report/{id}")
    public String queryReportById(@PathVariable("id") String id) {
        final UUID uuid = UUID.fromString(id);
        final Optional<MissingReport> optionalReport = reportDatabase.allReports().stream()
                .filter(report -> uuid.equals(report.uuid()))
                .findFirst();
        if (optionalReport.isEmpty()) {
            return "{}";
        }
        try {
            return SerializationContext.serializeReport(optionalReport.get());
        } catch (ConfigurateException ex) {
            throw new RuntimeException(ex);
        }
    }

    @GetMapping(value = "/reports/filter/distance")
    public String queryByLocation(@RequestParam(value = "name", required = false) String name,
                                  @RequestParam(value = "reportType", required = false) ReportType reportType,
                                  @RequestParam(value = "longitude") double longitude,
                                  @RequestParam("latitude") double latitude,
                                  @RequestParam("distanceKm") double distanceKm) {
        double[] location = new double[]{latitude, longitude};
        Stream<MissingReport> stream = reportDatabase.allReports().stream();
        if (name != null) {
            stream = stream.filter(report -> report.name().startsWith(name));
        }
        if (reportType != null) {
            stream = stream.filter(report -> report.reportType() == reportType);
        }
        stream = stream.filter(report -> LocationUtil.calculateDistanceInKilometer(report.lastKnownLocation(), location) <= distanceKm);
        try {
            return SerializationContext.serializeReports(stream.toList());
        } catch (ConfigurateException ex) {
            throw new RuntimeException(ex);
        }
    }

    @PutMapping(value = "/reports")
    public boolean submitReport(String json) {
        Optional<MissingReport> optionalMissingReport;
        try {
            optionalMissingReport = SerializationContext.createNewReport(json);
        } catch (ConfigurateException ex) {
            return false;
        }
        if (optionalMissingReport.isEmpty()) {
            return false;
        }
        MissingReport report = optionalMissingReport.get();
        // We do not allow the user to define images at this stage
        report.images().clear();
        this.reportDatabase.submitReport(report);
        return true;
    }

    @DeleteMapping("/report/{id}")
    public void deleteReport(@PathVariable("id") String uuid) {
        final Optional<UUID> optionalUuid = Utils.parseUuid(uuid);
        if (optionalUuid.isEmpty()) {
            return;
        }
        this.imageDatabase.deleteImages(uuid);
        this.reportDatabase.removeReport(optionalUuid.get());
    }

    @ExceptionHandler({RuntimeException.class})
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public String handleException(RuntimeException exception) {
        exception.printStackTrace();
        return "{ \"errorMsg\": " + "\"" + exception.getMessage() + "\"" + "}";
    }

}
