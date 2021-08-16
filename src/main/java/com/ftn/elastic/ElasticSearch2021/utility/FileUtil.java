package com.ftn.elastic.ElasticSearch2021.utility;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileUtil {

    @Value("${upload.path}")
    private static String uploadPath;

    @PostConstruct
    public static void init() {
        try {
            Files.createDirectories(Paths.get(uploadPath));
        } catch (IOException e) {
            throw new RuntimeException("Could not create upload folder!");
        }
    }

    public static boolean save(MultipartFile file) {
        try {
            Path root = Paths.get(uploadPath);
            if (!Files.exists(root)) {
                init();
            }
            Files.copy(file.getInputStream(), root.resolve(file.getOriginalFilename()));
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static byte[] readBytes(String path) {
        byte[] data = null;
        try {
            data = Files.readAllBytes(Paths.get(path));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return data;
    }

    public static boolean saveBytes(InputStream is, File f) {
        try{
            FileOutputStream fos = new FileOutputStream(f);
            byte[] buf = new byte[4096];
            int bytesRead;
            while((bytesRead = is.read(buf))!=-1) {
                fos.write(buf, 0, bytesRead);
            }
            fos.close();
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return false;
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
