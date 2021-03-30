package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.entity.File;
import com.udacity.jwdnd.course1.cloudstorage.entity.FileDTO;
import com.udacity.jwdnd.course1.cloudstorage.entity.User;
import com.udacity.jwdnd.course1.cloudstorage.services.FileService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.awt.*;
import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/home")
public class FileController {
    @Autowired
    private FileService fileService;
    @Autowired
    private UserService userService;

    @PostMapping
    public String uploadFile(Authentication authentication, @ModelAttribute("fileDTO")FileDTO fileDTO, Model model) throws IOException {
        User user = userService.getUser(authentication.getName());
        if (fileDTO.getFile().isEmpty()){
            //IN THE 'result.html' FILE REPLACE THE ATTRIBUTE 'result' BY 'error'
            model.addAttribute("result", "error");
            //IN THE 'result.html' FILE REPLACE THE ATTRIBUTE 'message' BY THE MESSAGE WE WANT TO SHOW
            model.addAttribute("message", "File is empty");
        }
        else if (fileDTO.getFile().getSize() >= 1000000){
            model.addAttribute("result", "error");
            model.addAttribute("message", "File size should be less than 10MB");
        }
        else if (!isDuplicated(fileDTO.getFile(), fileService.getAllFilesByUser(user))){
            fileService.addFile(authentication, fileDTO);
            model.addAttribute("result", "success");
        }
        else {
            model.addAttribute("result", "error");
            model.addAttribute("message", "File already exists");
        }

        return "result";
    }

    private Boolean isDuplicated(MultipartFile file, List<File> fileList){
        for (File f : fileList){
            if (f.getFileName().equals(file.getOriginalFilename())){
                return true;
            }
        }
        return false;
    }

    @GetMapping("/delete/{fileId}")
    public String deleteFile(Authentication authentication, @PathVariable Long fileId, @ModelAttribute("fileDTO") FileDTO fileDTO, Model model){
        User user = userService.getUser(authentication.getName());
        File file = fileService.getOneFile(fileId);
        fileService.deleteFile(file);
        model.addAttribute("result", "success");
        return "result";
    }

    @GetMapping(value = "/view/{fileId}", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    @ResponseBody
    public HttpEntity viewFile(@PathVariable Long fileId){
        File file = fileService.getOneFile(fileId);

        String contentType = file.getContentType();
        byte[] fileData = file.getFileData();

        final HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.valueOf(contentType));

        return new HttpEntity(fileData, headers);
    }
}
