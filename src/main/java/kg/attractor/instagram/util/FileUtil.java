package kg.attractor.instagram.util;

import lombok.SneakyThrows;

import lombok.experimental.UtilityClass;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@UtilityClass
public class FileUtil {

    private static final String IMAGE_DIR = "data/";
    public static final String DEFAULT_AVATAR = "default_avatar.jpg";
    public static final String IMAGES_SUBDIR = "images/";

    @SneakyThrows
    public String saveUploadFile(MultipartFile file, String subDir) {
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

    public ResponseEntity<?> getOutputFile(String filename, MediaType mediaType) {
        try {
            Path filePath = filename.equals(DEFAULT_AVATAR)
                    ? Paths.get(IMAGE_DIR + filename)
                    : Paths.get(IMAGE_DIR + IMAGES_SUBDIR + filename);

            byte[] fileContent = Files.readAllBytes(filePath);
            String originalFileName = filename.substring(filename.indexOf('_') + 1);
            String encodedFileName = URLEncoder.encode(originalFileName, StandardCharsets.UTF_8)
                    .replace("+", "%20");

            Resource resource = new ByteArrayResource(fileContent);

            return ResponseEntity.ok()
                    .contentType(mediaType)
                    .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename*=UTF-8''" + encodedFileName)
                    .body(resource);
        } catch (IOException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
