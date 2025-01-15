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
import static org.mockito.Mockito.*;

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

        Path filePath = tempDir.resolve(fileName);
        try {
            Files.createDirectories(tempDir);
            Files.createFile(filePath);
        } catch (IOException e) {
            fail("Failed to set up test file.");
        }

        File file = fileService.getFile(fileName);
        assertEquals(filePath.toAbsolutePath().toString(), file.getAbsolutePath());

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
        String extension = fileService.getFileExtension(null);

        assertEquals("", extension, "The file extension should be an empty string for null input");
    }

    @Test
    void testUploadFileWithExistingDirectory() throws IOException {
        MockMultipartFile mockFile = new MockMultipartFile(
                "file",
                "test.txt",
                "text/plain",
                "This is a test".getBytes()
        );

        Path tempDir = Paths.get(System.getProperty("user.dir"), "src/main/resources/public/uploads");
        Files.createDirectories(tempDir);

        String uploadedFileName = fileService.upload(mockFile);

        assertNotNull(uploadedFileName, "Uploaded file name should not be null");
        assertTrue(Files.exists(tempDir.resolve(uploadedFileName)), "Uploaded file should exist");

        Files.deleteIfExists(tempDir.resolve(uploadedFileName));
    }

    @Test
    void testUploadFileWithNonExistingDirectory() throws IOException {
        MockMultipartFile mockFile = new MockMultipartFile(
                "file",
                "test.txt",
                "text/plain",
                "This is a test".getBytes()
        );

        Path tempDir = Paths.get(System.getProperty("user.dir"), "src/main/resources/public/uploads");
        FileSystemUtils.deleteRecursively(tempDir);

        String uploadedFileName = fileService.upload(mockFile);

        assertNotNull(uploadedFileName, "Uploaded file name should not be null");
        assertTrue(Files.exists(tempDir.resolve(uploadedFileName)), "Uploaded file should exist");

        Files.deleteIfExists(tempDir.resolve(uploadedFileName));
        FileSystemUtils.deleteRecursively(tempDir);
    }

    @Test
    void testUploadFileWithoutExtension() throws IOException {
        MockMultipartFile mockFile = new MockMultipartFile(
                "file",
                "testfile",
                "text/plain",
                "This is a test".getBytes()
        );

        String uploadedFileName = fileService.upload(mockFile);

        assertNotNull(uploadedFileName, "Uploaded file name should not be null");
        assertFalse(uploadedFileName.contains("."), "Uploaded file name should not contain an extension");

        Path tempDir = Paths.get(System.getProperty("user.dir"), "src/main/resources/public/uploads");
        Files.deleteIfExists(tempDir.resolve(uploadedFileName));
    }

    @Test
    void testUploadThrowsIOException() {
        MockMultipartFile mockFile = mock(MockMultipartFile.class);
        when(mockFile.getOriginalFilename()).thenReturn("test.txt");

        try {
            doThrow(new IOException("Test exception")).when(mockFile).transferTo(any(File.class));
        } catch (IOException e) {
            fail("Mock setup failed");
        }

        assertThrows(IOException.class, () -> fileService.upload(mockFile));
    }

    @Test
    void testResolveFilePath() {
        String fileName = "test.txt";
        Path resolvedPath = fileService.resolveFilePath(fileName);

        assertNotNull(resolvedPath, "Resolved path should not be null");
        assertEquals(Paths.get(System.getProperty("user.dir"), "src/main/resources/public/uploads", fileName).toAbsolutePath(), resolvedPath);
    }

    @Test
    void testGetFileForNonExistingFile() {
        String fileName = "non-existent-file.txt";

        File file = fileService.getFile(fileName);

        assertNotNull(file, "File object should not be null");
        assertFalse(file.exists(), "File should not exist");
    }

}
