package at.technikum.springrestbackend.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class FileService {

    private final String uploadDir = System.getProperty("user.dir") + "/src/main/resources/public/uploads";

    public String upload(MultipartFile file) throws IOException {
        Path uploadPath = Paths.get(uploadDir);
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        String fileExtension = getFileExtension(file.getOriginalFilename());
        String uniqueFileName = UUID.randomUUID() + (fileExtension.isEmpty() ? "" : "." + fileExtension);

        Path filePath = uploadPath.resolve(uniqueFileName);
        file.transferTo(filePath.toFile());

        return uniqueFileName;
    }
    private String getFileExtension(String originalFileName) {
        if (originalFileName == null || originalFileName.isEmpty()) {
            return "";
        }
        int lastIndexOfDot = originalFileName.lastIndexOf('.');
        return (lastIndexOfDot == -1) ? "" : originalFileName.substring(lastIndexOfDot + 1);
    }
    public Path resolveFilePath(String fileName) {
        return Paths.get(uploadDir).resolve(fileName).toAbsolutePath();
    }

    public File getFile(String fileName) {
        return resolveFilePath(fileName).toFile();
    }
}
