package cabbage.missingwebtracker.backend.core.util;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public final class ImageStore {

    private File primaryImage;
    private final List<File> secondaryImages = new ArrayList<>();

    public void importImages(ImageStore other) {
        this.primaryImage = other.primaryImage;
        this.secondaryImages.addAll(other.secondaryImages);
    }

    public void primaryImage(File primaryImage) {
        this.primaryImage = primaryImage;
    }

    public void addSecondaryImage(File file) {
        this.secondaryImages.add(file);
    }

    public void removeSecondaryImage(File file) {
        this.secondaryImages.remove(file);
    }

    public void clearSecondaryImages() {
        this.secondaryImages.clear();
    }

    public Optional<File> primaryImage() {
        return Optional.ofNullable(this.primaryImage);
    }

    public List<File> secondaryImages() {
        return Collections.unmodifiableList(secondaryImages);
    }


}
