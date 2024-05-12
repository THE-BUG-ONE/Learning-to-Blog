package com.framework.utils;

import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.UUID;

@Component
public class FileUtils {

    public String subStringPath(String filePath, String suffix) throws FileNotFoundException {
        return ResourceUtils.getURL("classpath:").getPath() +
                filePath + UUID.randomUUID() + "." +
                suffix.substring(suffix.lastIndexOf("/")+1);
    }
}
