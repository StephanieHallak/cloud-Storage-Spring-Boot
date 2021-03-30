package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.entity.File;
import com.udacity.jwdnd.course1.cloudstorage.entity.FileDTO;
import com.udacity.jwdnd.course1.cloudstorage.entity.User;
import com.udacity.jwdnd.course1.cloudstorage.repository.FileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class FileService {
    @Autowired
    private FileRepository fileRepository;
    @Autowired
    private UserService userService;

    public void addFile(Authentication authentication, FileDTO fileDTO) throws IOException {
        String fileName = fileDTO.getFile().getOriginalFilename();
        String contentType = fileDTO.getFile().getContentType();
        String fileSize = String.valueOf(fileDTO.getFile().getSize());
        User user = userService.getUser(authentication.getName());
        byte[] fileData = fileDTO.getFile().getBytes();
        File file = new File(null, fileName, contentType, fileSize, user, fileData);
        fileRepository.save(file);
    }

    public List<File> getAllFilesByUser(User user){
        return fileRepository.findAllByUser(user);
    }

    public void deleteFile(File file){
        fileRepository.delete(file);
    }

    public File getOneFile(Long fileId){
        Optional<File> fileOptional = fileRepository.findById(fileId);
        return fileOptional.get();
    }
}
