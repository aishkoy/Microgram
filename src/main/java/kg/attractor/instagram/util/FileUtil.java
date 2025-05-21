package kg.attractor.instagram.util;

import lombok.SneakyThrows;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Component
public class FileUtil {

    private static final String IMAGE_DIR = "data/";
    public static final String DEFAULT_AVATAR = "default_avatar.jpg";
    public static final String IMAGES_SUBDIR = "images/";

    @SneakyThrows
    public static String saveUploadFile(MultipartFile file, String subDir) {
        String uuidFile = UUID.randomUUID().toString();
        String resultFileName = uuidFile + "_" + file.getOriginalFilename();

        Path pathDir = Paths.get(IMAGE_DIR + subDir);
        Files.createDirectories(pathDir);

        Path filePath = pathDir.resolve(resultFileName);
        try (OutputStream os = Files.newOutputStream(filePath)) {
            os.write(file.getBytes());
        }
        return resultFileName;
    }

    public static ResponseEntity<?> getOutputFile(String filename, MediaType mediaType) {
        try {
            Path filePath = filename.equals(DEFAULT_AVATAR)
                    ? Paths.get(IMAGE_DIR + filename)
                    : Paths.get(IMAGE_DIR + IMAGES_SUBDIR + filename);

            byte[] fileContent = Files.readAllBytes(filePath);
            ByteArrayResource resource = new ByteArrayResource(fileContent);

            return ResponseEntity.ok()
                    .contentType(mediaType)
                    .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + filePath.getFileName() + "\"")
                    .body(resource);
        } catch (IOException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
