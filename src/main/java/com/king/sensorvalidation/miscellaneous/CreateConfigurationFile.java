package com.king.sensorvalidation.miscellaneous;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.io.File;

@Component
public class CreateConfigurationFile{
    private final Logger logger = LoggerFactory.getLogger(CreateConfigurationFile.class);

    @Bean
    CommandLineRunner run(){
        return args -> {
            final String filePath = "valid_configuration.txt";
            File config = new File(filePath);
            config.createNewFile();
            logger.info("Config file created " + config.getPath());
        };
    }
}
