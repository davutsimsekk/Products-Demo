package kha.productsdemo.dto.converter;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Date;

@Component
@AllArgsConstructor
@NoArgsConstructor
public class ImageConverter {
    //eski resim dosyasinin silinip silinmedigini kontrol et
    private String imagesDir = "public/productImages/";
    private String storageImageName;
    private Date date = new Date();
    public String SaveImage(MultipartFile image){
        storageImageName = date.getTime() + "_" + image.getOriginalFilename();
        try {

            Path uploadPath = Paths.get(imagesDir);
            if (!Files.exists(uploadPath)){
                Files.createDirectories(uploadPath);
            }
            try(InputStream inputStream = image.getInputStream()) {
                Files.copy(inputStream,
                        Paths.get(imagesDir + storageImageName),
                        StandardCopyOption.REPLACE_EXISTING);
            }
        }catch (IOException exception){
            System.out.println("Exception " + exception.getMessage());
        }
        return storageImageName;
    }
    protected void deleteImage(String imageName){
        Path deletePath = Paths.get(imagesDir + imageName);
        try {
            Files.delete(deletePath);
        }
        catch (IOException e) {
            System.out.println("an error occurred while deleting image: " + e.getMessage());
        }
    }



}
