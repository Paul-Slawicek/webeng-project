package at.technikum.springrestbackend.service;

import org.springframework.beans.factory.annotation.Value;
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

    private final String uploadDir="../upload";

    public String upload(MultipartFile file) throws IOException {
        Path uploadPath = Paths.get(uploadDir);
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        // Generate a unique file name
        String fileExtension = getFileExtension(file.getOriginalFilename());
        String uniqueFileName = UUID.randomUUID().toString() + (fileExtension.isEmpty() ? "" : "." + fileExtension);

        // Save the file
        Path filePath = uploadPath.resolve(uniqueFileName);
        file.transferTo(filePath.toFile());

        // Return the file reference (e.g., file name or full path)
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
