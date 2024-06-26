package com.example;

import com.example.uploading_files.FileUploadController;
import com.example.uploading_files.storage.StorageFileNotFoundException;
import com.example.uploading_files.storage.StorageService;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;
import java.util.stream.Stream;

import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest(classes = FileUploadController.class)
@AutoConfigureMockMvc
public class FileUploadTests {
    
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private StorageService storageService;
    
    @Test
    public void shouldSaveUploadedFile() throws Exception {
        MockMultipartFile multipartFile = new MockMultipartFile("file", "test.txt", "text/plain", "Spring framework".getBytes(StandardCharsets.UTF_8));
        this.mockMvc.perform(MockMvcRequestBuilders.multipart("/").file(multipartFile)).andExpect(status().isFound()).andExpect(header().string(
                "Location", "/"
        ));
        
        then(this.storageService).should().store(multipartFile);
    }
    
    @Test
    public void shouldListAllFiles() throws Exception {
        given(storageService.loadAll()).willReturn(Stream.of(Paths.get("first.txt"), Paths.get("second.txt")));
        
        this.mockMvc.perform(MockMvcRequestBuilders.get("/")).andExpect(status().isOk()).andExpect(model().attribute("files", Matchers.contains(
                "http://localhost/files/first.txt",
                "http://localhost/files/second.txt")));
    }
    
    @SuppressWarnings("unchecked")
    @Test
    public void should404WhenMissingFile() throws Exception {
        given(storageService.loadAsResource("test.txt")).willThrow(StorageFileNotFoundException.class);
        
        this.mockMvc.perform(MockMvcRequestBuilders.get("/files/test.txt")).andExpect(status().isNotFound());
    }
}
