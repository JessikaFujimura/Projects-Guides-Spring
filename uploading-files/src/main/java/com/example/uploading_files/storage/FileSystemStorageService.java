package com.example.uploading_files.storage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.stream.Stream;

@Service
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
         Path destination = this.rootLocation.resolve(Paths.get(multipartFile.getOriginalFilename())).normalize().toAbsolutePath();
         if (!destination.getParent().equals(this.rootLocation.toAbsolutePath())) {
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
        try {
            return Files.walk(this.rootLocation, 1).filter(path -> !path.equals(this.rootLocation)).map(rootLocation::relativize);
        } catch (IOException e){
            throw new StorageException("Failed to read stored files", e);
        }
    }
    
    @Override
    public Path load(final String fileName) {
        return rootLocation.resolve(fileName);
    }
    
    @Override
    public Resource loadAsResource(final String fileName) {
        try{
            Path file = load(fileName);
            Resource resource = new UrlResource(file.toUri());
            if(resource.exists() || resource.isReadable()){
                return resource;
            } else {
                throw new StorageFileNotFoundException("Could not read file: "+ fileName);
            }
        } catch (MalformedURLException e) {
            throw new StorageFileNotFoundException("Could not read file: " +  fileName, e);
        }
    }
    
    @Override
    public void deleteAll() {
        FileSystemUtils.deleteRecursively(this.rootLocation.toFile());
    }
}
