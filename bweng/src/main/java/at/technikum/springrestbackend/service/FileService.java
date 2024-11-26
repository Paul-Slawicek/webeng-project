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

    @Value("${file.upload-dir:uploads}") // Base directory for uploads (default: "uploads")
    private String uploadDir;

    /**
     * Handles the upload of a file.
     *
     * @param file The file to upload.
     * @return A reference to the uploaded file (e.g., UUID or file path).
     * @throws IOException If an error occurs during file upload.
     */
    public String upload(MultipartFile file) throws IOException {
        // Ensure the upload directory exists
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

    /**
     * Retrieves the file extension from the original file name.
     *
     * @param originalFileName The original file name.
     * @return The file extension, or an empty string if none exists.
     */
    private String getFileExtension(String originalFileName) {
        if (originalFileName == null || originalFileName.isEmpty()) {
            return "";
        }
        int lastIndexOfDot = originalFileName.lastIndexOf('.');
        return (lastIndexOfDot == -1) ? "" : originalFileName.substring(lastIndexOfDot + 1);
    }

    /**
     * Resolves the path to an uploaded file.
     *
     * @param fileName The unique file name.
     * @return The file as a `Path`.
     */
    public Path resolveFilePath(String fileName) {
        return Paths.get(uploadDir).resolve(fileName).toAbsolutePath();
    }

    /**
     * Retrieves a file as a `File` object.
     *
     * @param fileName The unique file name.
     * @return The `File` object for the requested file.
     */
    public File getFile(String fileName) {
        return resolveFilePath(fileName).toFile();
    }
}
