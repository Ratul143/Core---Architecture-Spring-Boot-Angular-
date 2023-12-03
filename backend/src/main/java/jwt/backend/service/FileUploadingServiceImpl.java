package jwt.backend.service;

import jwt.backend.exception.InvalidTypeException;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.FilenameUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import jakarta.transaction.Transactional;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;
import static jwt.backend.constant.AppConstant.*;

/**
 * @author Jaber
 * @date 7/24/2022
 * @time 2:08 PM
 */
@Service
@Transactional
@RequiredArgsConstructor
public class FileUploadingServiceImpl implements FileUploadingService {
    public static final String INVALID_IMAGE_TYPE = " is a Invalid Image Type!";
    public static final String INVALID_FILE_TYPE = " is a Invalid File Type!";

    @Override
    public String saveImage(String name, String uniqueId, MultipartFile fileType, String folder) throws IOException, InvalidTypeException {
        if (!Arrays.asList(ALLOWED_IMAGE_TYPE).contains(fileType.getContentType())) {
            throw new InvalidTypeException(fileType.getOriginalFilename() + INVALID_IMAGE_TYPE);
        }
        Path userFolder = Paths.get(folder).toAbsolutePath().normalize();
        if (!Files.exists(userFolder)) {
            Files.createDirectories(userFolder);
        }
        Files.deleteIfExists(Paths.get(userFolder + name + "_" + uniqueId + DOT + JPG_EXTENSION));
        Files.copy(fileType.getInputStream(), userFolder.resolve(name + "_" + uniqueId + DOT + JPG_EXTENSION), REPLACE_EXISTING);
        return setImageUrl(name, uniqueId);
    }

    private String setImageUrl(String name, String uniqueId) {
        return ServletUriComponentsBuilder.fromCurrentContextPath().path(GLOBAL_IMAGE_PATH + name + FORWARD_SLASH + name + "_" + uniqueId + DOT + JPG_EXTENSION).toUriString();
    }

    @Override
    public String saveDocument(String name, String uniqueId, MultipartFile fileType, String folder) throws IOException, InvalidTypeException {
        if (!Arrays.asList(ALLOWED_DOCUMENT_TYPE).contains(fileType.getContentType())) {
            throw new InvalidTypeException(fileType.getOriginalFilename() + INVALID_FILE_TYPE);
        }
        String extension = FilenameUtils.getExtension(fileType.getOriginalFilename());
        Path userFolder = Paths.get(folder).toAbsolutePath().normalize();
        if (!Files.exists(userFolder)) {
            Files.createDirectories(userFolder);
        }
        Files.deleteIfExists(Paths.get(userFolder + name + "_" + uniqueId + DOT + extension));
        Files.copy(fileType.getInputStream(), userFolder.resolve(name + "_" + uniqueId + DOT + extension), REPLACE_EXISTING);
        return setDocumentUrl(name, uniqueId, extension);
    }

    private String setDocumentUrl(String name, String uniqueId, String extension) {
        return ServletUriComponentsBuilder.fromCurrentContextPath().path(GLOBAL_IMAGE_PATH + name + FORWARD_SLASH + name + "_" + uniqueId + DOT + extension).toUriString();
    }
}