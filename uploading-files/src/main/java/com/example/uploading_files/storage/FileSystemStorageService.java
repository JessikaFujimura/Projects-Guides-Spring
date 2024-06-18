package com.example.uploading_files.storage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.stream.Stream;

public class FileSystemStorageService implements StorageService{
    private final Path rootLocation;
    
    @Autowired
    FileSystemStorageService(StorageProperties storageProperties){
        if(storageProperties.getLocation().trim().length() == 0){
            throw new StorageException("File upload location can not be Empty.");
        }
        this.rootLocation = Path.of(storageProperties.getLocation());
    }
    
    @Override
    public void init() {
        try{
            Files.createDirectories(rootLocation);
        } catch (IOException e) {
            throw new StorageException("Could not initialize storage", e);
        }
    }
    
    @Override
    public void store(final MultipartFile multipartFile) {
     try {
         if (multipartFile.isEmpty()) {
             throw new StorageException("Failed to store empty file");
         }
         Path destination = this.rootLocation.resolve(multipartFile.getOriginalFilename()).normalize().toAbsolutePath();
         if (destination.getParent().equals(this.rootLocation.toAbsolutePath())) {
             throw new StorageException("Can not store file outside current directory.");
         }
         try (InputStream inputStream = multipartFile.getInputStream()) {
            Files.copy(inputStream, destination, StandardCopyOption.REPLACE_EXISTING);
         }
     } catch (IOException e) {
         throw new StorageException("Failed to store file. ", e);
     }
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
        FileSystemUtils.deleteRecursively(this.rootLocation.toFile());
    }
}
