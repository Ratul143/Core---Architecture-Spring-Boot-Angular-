package jwt.backend.controller;

import jwt.backend.constant.ApiUrl;
import jwt.backend.exception.*;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import static jwt.backend.constant.AppConstant.*;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.springframework.http.MediaType.*;

@RestController
@AllArgsConstructor
@RequestMapping(ApiUrl.BASE_API + ApiUrl.FILE)
public class FileUploadingController extends ExceptionHandling {


//    @PostMapping("/update-profile-image")
//    public ResponseEntity<User> updateProfileImage(@RequestParam("username") String username,
//                                                   @RequestParam(value = "profileImage") MultipartFile profileImage) throws UserNotFoundException, EmailExistException, UsernameExistsException, IOException, InvalidTypeException {
//        User user = userService.updateProfileImage(username, profileImage);
//        return new ResponseEntity<>(user, OK);
//    }


    @GetMapping(path = ApiUrl.GET_USER_IMAGE, produces = IMAGE_JPEG_VALUE)
    public byte[] getUserImage(@PathVariable("fileName") String fileName) throws IOException {
        return Files.readAllBytes(Paths.get(USER_FOLDER + fileName));
    }

    // Robohash image processing
    @GetMapping(path = ApiUrl.TEMP_PROFILE_IMAGE, produces = IMAGE_JPEG_VALUE)
    public byte[] getTempProfileImage(@PathVariable("fileName") String fileName) throws IOException {
        URL url = new URL(TEMP_PROFILE_IMAGE_BASE_URL + fileName);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try (InputStream inputStream = url.openStream()) {
            int bytesRead;
            byte[] chunk = new byte[1024];
            while ((bytesRead = inputStream.read(chunk)) > 0) {
                byteArrayOutputStream.write(chunk, 0, bytesRead);
            }
        }
        return byteArrayOutputStream.toByteArray();
    }
}
