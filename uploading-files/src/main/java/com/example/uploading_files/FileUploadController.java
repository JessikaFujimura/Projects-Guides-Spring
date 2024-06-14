package com.example.uploading_files;

import com.example.uploading_files.storage.StorageFileNotFoundException;
import com.example.uploading_files.storage.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Objects;
import java.util.stream.Collectors;

@Controller
public class FileUploadController {
    
    private final StorageService storageService;
    
    @Autowired
    private FileUploadController(StorageService storageService) {
        this.storageService = storageService;
    }
    
    @GetMapping("/")
    public String listUploadFiles(Model model) {
        model.addAttribute("files", storageService.loadAll().map(path -> MvcUriComponentsBuilder.fromMethodName(FileUploadController.class,
                "serverFile", path.getFileName().toString()).build().toUri().toString()).collect(Collectors.toList()));
        return "uploadForm";
    }
    
    @GetMapping("/files/{fileName:.+}")
    @ResponseBody
    public ResponseEntity<Resource> serveFile(@PathVariable String fileName){
            Resource file = storageService.loadAsResource(fileName);
            if(Objects.isNull(file))
                return ResponseEntity.notFound().build();
            
            return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, "attachment; fileName=\"", file.getFilename() + "\"").body(file);
    }
    
    @PostMapping("/")
    public String handleFileUpload(@RequestParam("file") MultipartFile file, RedirectAttributes redirectAttributes){
        storageService.store(file);
        redirectAttributes.addFlashAttribute("message", "You successfully uploaded " + file.getOriginalFilename() + "!");
        return "redirect:/";
    }
    
    
    public ResponseEntity<?> handleStorageFileNotFound(StorageFileNotFoundException ex){
        return ResponseEntity.notFound().build();
    }
    
}
