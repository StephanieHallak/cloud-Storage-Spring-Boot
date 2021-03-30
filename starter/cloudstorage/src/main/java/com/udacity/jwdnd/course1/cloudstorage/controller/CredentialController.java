package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.entity.Credential;
import com.udacity.jwdnd.course1.cloudstorage.entity.CredentialDTO;
import com.udacity.jwdnd.course1.cloudstorage.entity.User;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialService;
import com.udacity.jwdnd.course1.cloudstorage.services.EncryptionService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class CredentialController {
    @Autowired
    CredentialService credentialService;
    @Autowired
    UserService userService;
    @Autowired
    EncryptionService encryptionService;

    @PostMapping("/manageCredential")
    public String manageCredential(@ModelAttribute("credentialDTO")CredentialDTO credentialDTO, Model model, Authentication authentication){
        User user = userService.getUser(authentication.getName());
        String key = encryptionService.generateKey();

        String encryptedPassword = encryptionService.encryptValue(credentialDTO.getPassword(), key);
        Credential credential = new Credential(credentialDTO.getCredentialId(), credentialDTO.getUrl(), credentialDTO.getUsername(),
                key, encryptedPassword, user);

        if (credentialDTO.getCredentialId() == null){
            //THE CREDENTIAL IS NEW TO ADD
            credentialService.addCredential(credential);
        }
        else {
            //THE CREDENTIAL IS TO BE UPDATED
            credentialService.updateCredential(credential);
        }
        model.addAttribute("result", "success");
        return "result";
    }

    @GetMapping("/deleteCredential/{credentialId}")
    public String deleteCredential(@PathVariable("credentialId")Long credentialId, Model model){
        Credential credential = credentialService.getOneCredential(credentialId);
        credentialService.deleteCredential(credential);
        model.addAttribute("result", "success");
        return "result";
    }

}
