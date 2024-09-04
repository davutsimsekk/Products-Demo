package kha.productsdemo.service;

import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.*;

@Service
public class ImageService {
    private String imagesDir = "public/productImages/";

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
