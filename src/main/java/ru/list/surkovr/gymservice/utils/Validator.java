package ru.list.surkovr.gymservice.utils;

import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.List;

public class Validator {

    public static boolean isFileExtensionValid(String fullFileName, List<String> allowedExtensions) {
        if (StringUtils.hasText(fullFileName) && !CollectionUtils.isEmpty(allowedExtensions)) {
            String filenameExtension = StringUtils.getFilenameExtension(fullFileName);
            return allowedExtensions.stream()
                    .anyMatch(allowedExtension -> allowedExtension.equalsIgnoreCase(filenameExtension));
        } else {
            return false;
        }
    }
}
