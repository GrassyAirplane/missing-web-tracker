package cabbage.missingwebtracker.backend.core;


import cabbage.missingwebtracker.backend.core.database.ImageDatabase;
import cabbage.missingwebtracker.backend.core.database.MemoryMissingReportDatabase;
import cabbage.missingwebtracker.backend.core.report.MissingReport;
import cabbage.missingwebtracker.backend.core.util.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@RestController
@CrossOrigin(origins = {"http://localhost:8080", "http://localhost:5173"})
public class ImageRequestController {
    @Autowired
    private MemoryMissingReportDatabase reportDatabase;

    @Autowired
    private ImageDatabase imageDatabase;

    public ImageRequestController() {

    }
    @GetMapping("/report/{id}/images/{fileName}")
    @Async
    public CompletableFuture<byte[]> getImage(@PathVariable("id") String uuid, @PathVariable("fileName") String fileName) throws IOException {
        return this.imageDatabase.getImage(uuid, fileName);
    }

    @PutMapping("/report/{id}/images/{fileName}")
    public void submitImage(@PathVariable("id") String uuid, @PathVariable("fileName") String fileName, MultipartFile file) {
        this.imageDatabase.submitImage(uuid, fileName, file);
        Utils.parseUuid(uuid).flatMap(this.reportDatabase::findReport).ifPresent(report -> report.images().add(fileName));
    }

    @DeleteMapping("/report/{id}/images/{fileName}")
    public void deleteImage(@PathVariable("id") String uuid, @PathVariable("fileName") String fileName) {
        this.imageDatabase.deleteImage(uuid, fileName);
        Optional<UUID> optionalUuid = Utils.parseUuid(uuid);
        optionalUuid.flatMap(this.reportDatabase::findReport).ifPresent(report -> report.images().remove(fileName));
    }

    @DeleteMapping("/report/{id}/images")
    public void deleteImages(@PathVariable("id") String uuid) {
        this.imageDatabase.deleteImages(uuid);
        Optional<UUID> optionalUuid = Utils.parseUuid(uuid);
        optionalUuid.flatMap(this.reportDatabase::findReport).map(MissingReport::images).ifPresent(List::clear);
    }

}
