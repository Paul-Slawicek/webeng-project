package at.technikum.springrestbackend.ServiceTest;

import at.technikum.springrestbackend.service.FileService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.util.FileSystemUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

class FileServiceTest {

    private FileService fileService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        fileService = new FileService();
    }

    @Test
    void testGetFile() {
        Path tempDir = Paths.get(System.getProperty("user.dir"), "src/main/resources/public/uploads");
        String fileName = "test-file.txt";

        // Temporäre Datei erstellen
        Path filePath = tempDir.resolve(fileName);
        try {
            Files.createDirectories(tempDir);
            Files.createFile(filePath);
        } catch (IOException e) {
            fail("Failed to set up test file.");
        }

        File file = fileService.getFile(fileName);
        assertEquals(filePath.toAbsolutePath().toString(), file.getAbsolutePath());

        // Aufräumen
        try {
            Files.deleteIfExists(filePath);
        } catch (IOException e) {
            fail("Failed to clean up test file.");
        }
    }

    @Test
    void testGetFileExtensionReturnsCorrectExtension() {
        // Arrange
        String fileName = "example.txt";

        // Act
        String extension = fileService.getFileExtension(fileName);

        // Assert
        assertEquals("txt", extension, "The file extension should be 'txt'");
    }

    @Test
    void testGetFileExtensionReturnsEmptyForNoExtension() {
        // Arrange
        String fileName = "example";

        // Act
        String extension = fileService.getFileExtension(fileName);

        // Assert
        assertEquals("", extension, "The file extension should be an empty string");
    }

    @Test
    void testGetFileExtensionHandlesNull() {
        // Act
        String extension = fileService.getFileExtension(null);

        // Assert
        assertEquals("", extension, "The file extension should be an empty string for null input");
    }
}
