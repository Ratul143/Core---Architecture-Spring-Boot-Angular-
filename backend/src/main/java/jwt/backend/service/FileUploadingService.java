package jwt.backend.service;

import jwt.backend.exception.InvalidTypeException;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface FileUploadingService {
    String saveImage(String name, String uniqueId, MultipartFile imageFile, String absolutePath) throws IOException, InvalidTypeException;

    String saveDocument(String name, String uniqueId, MultipartFile fileType, String folder) throws IOException, InvalidTypeException;
}
