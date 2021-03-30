package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.entity.CredentialDTO;
import com.udacity.jwdnd.course1.cloudstorage.entity.FileDTO;
import com.udacity.jwdnd.course1.cloudstorage.entity.NoteDTO;
import com.udacity.jwdnd.course1.cloudstorage.entity.User;
import com.udacity.jwdnd.course1.cloudstorage.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/home")
public class HomeController {
    @Autowired
    private UserService userService;
    @Autowired
    private EncryptionService encryptionService;
    @Autowired
    private FileService fileService;
    @Autowired
    private NoteService noteService;
    @Autowired
    private CredentialService credentialService;

    @GetMapping
    public String getHomePage(Authentication authentication, Model model, @ModelAttribute("fileDTO")FileDTO fileDTO,
                              @ModelAttribute("noteDTO")NoteDTO noteDTO, @ModelAttribute("credentialDTO")CredentialDTO credentialDTO){
        User user = userService.getUser(authentication.getName());
        model.addAttribute("files", fileService.getAllFilesByUser(user));
        model.addAttribute("notes", noteService.getAllNotes(user));
        model.addAttribute("credentials", credentialService.getAllCredentials(user));
        model.addAttribute("encryptionService", encryptionService);
        return "home";
    }
}
