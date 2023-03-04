package cabbage.missingwebtracker.backend.core.database;


import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Path;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ForkJoinPool;

public class ImageDatabase {

    private final Path dataStore = new File(System.getenv("LOCALAPPDATA")).toPath();

    public void submitImage(String uuid, String fileName, MultipartFile file) {
        File filePath = dataStore.resolve(uuid).resolve(fileName).toFile();
        if (filePath.exists()) {
            filePath.delete();
        }
        filePath.mkdirs();
        ForkJoinPool.commonPool().submit(() -> {
            try (InputStream inputStream = file.getInputStream();
                 OutputStream outputStream = new FileOutputStream(filePath)) {
                inputStream.transferTo(outputStream);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
    }

    public CompletableFuture<byte[]> getImage(String uuid, String file) throws IOException {
        File filePath = dataStore.resolve(uuid).resolve(file).toFile();
        if (!filePath.isFile()) {
            throw new FileNotFoundException(String.format("reports/%s/%s", uuid, file));
        }
        CompletableFuture<byte[]> future = new CompletableFuture<>();
        ForkJoinPool.commonPool().submit(() -> {
            try (InputStream inputStream = new FileInputStream(filePath)) {
                future.complete(inputStream.readAllBytes());
            } catch (IOException ex) {
                future.completeExceptionally(ex);
            }
        });
        return future;
    }

    public void deleteImage(String uuid, String fileName) {
        File filePath = dataStore.resolve(uuid).resolve(fileName).toFile();
        filePath.delete();
    }

    public void deleteImages(String uuid) {
        File dir = dataStore.resolve(uuid).toFile();
        if (dir.isDirectory()) {
            dir.delete();
        }
    }

}
