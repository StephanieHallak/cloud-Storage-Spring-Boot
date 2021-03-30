package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.entity.Credential;
import com.udacity.jwdnd.course1.cloudstorage.entity.User;
import com.udacity.jwdnd.course1.cloudstorage.repository.CredentialRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class CredentialService {
    @Autowired
    CredentialRepository credentialRepository;

    public void addCredential(Credential credential){
        credentialRepository.save(credential);
    }
    public void updateCredential(Credential credential){
        credentialRepository.updateCredential(credential.getUrl(), credential.getUsername(), credential.getPassword(),credential.getKey(), credential.getCredentialId());
    }
    public void deleteCredential(Credential credential){
        credentialRepository.delete(credential);
    }
    public Credential getOneCredential(Long credentialId){
        return credentialRepository.findById(credentialId).get();
    }
    public List<Credential> getAllCredentials(User user){
        return credentialRepository.findAllByUser(user);
    }
}
