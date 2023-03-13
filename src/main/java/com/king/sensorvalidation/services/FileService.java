package com.king.sensorvalidation.services;


import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;

public class FileService {

    private static final String PATH_NAME = "./src/main/java/com/king/sensorvalidation/miscellaneous/config-";

    public static File sensorConfigFile(String sensorId, String configuration) throws IOException {
        File file = new File(PATH_NAME.concat(sensorId));
        if (file.createNewFile()){
           writeToFile(configuration, file);
        }
        return file;
    }

    public static File getFile(String path){
        return new File(path);
    }


    public static String readContents(String path) throws IOException {
        return Arrays.toString(Files.readAllBytes(Paths.get(path)));
    }

    public static void writeToFile(String content, File file){
        FileWriter pen = null;
        try{
            pen = new FileWriter(file);
            pen.write(content);
        }catch (IOException exception){
            exception.printStackTrace();;
        }finally {
            try {
                assert pen != null;
                pen.close();
            }catch (IOException exception){
                exception.printStackTrace();
            }
        }
    }

}