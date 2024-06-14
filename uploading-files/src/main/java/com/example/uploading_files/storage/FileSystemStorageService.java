package com.example.uploading_files.storage;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;
import java.util.stream.Stream;

public class FileSystemStorageService implements StorageService{
    @Override
    public void init() {
    
    }
    
    @Override
    public void store(final MultipartFile multipartFile) {
    
    }
    
    @Override
    public Stream<Path> loadAll() {
        return null;
    }
    
    @Override
    public Path load(final String fileName) {
        return null;
    }
    
    @Override
    public Resource loadAsResource(final String fileName) {
        return null;
    }
    
    @Override
    public void deleteAll() {
    
    }
}
