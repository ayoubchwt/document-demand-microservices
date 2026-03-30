package tn.citypulse.documentdemand.service.Implmentation;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import tn.citypulse.documentdemand.service.ICloudinaryService;
import java.io.IOException;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class CloudinaryService implements ICloudinaryService {
    private final Cloudinary cloudinary;
    @Override
    public String uploadFile(MultipartFile file , String folder) {
        try {
            Map uploadResult = cloudinary.uploader().upload(
                    file.getBytes(),
                    ObjectUtils.asMap(
                            "folder", folder
                    )
            );
            return uploadResult.get("url").toString();
        } catch(IOException e) {
            throw new RuntimeException("Failed to upload file to Cloudinary");
        }
    }
}
