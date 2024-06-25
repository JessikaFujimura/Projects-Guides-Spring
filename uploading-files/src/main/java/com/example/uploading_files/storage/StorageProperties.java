package com.example.uploading_files.storage;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("storage")
public class StorageProperties {
    
    private String location = "/Users/jessika/Programming/Projects-Guides-Spring/uploading-files/direct-file";
    
    public String getLocation() {
        return location;
    }
    
    public void setLocation(final String location) {
        this.location = location;
    }
}
