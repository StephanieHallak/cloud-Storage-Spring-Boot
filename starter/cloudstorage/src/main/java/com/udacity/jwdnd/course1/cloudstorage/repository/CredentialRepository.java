package com.udacity.jwdnd.course1.cloudstorage.repository;

import com.udacity.jwdnd.course1.cloudstorage.entity.Credential;
import com.udacity.jwdnd.course1.cloudstorage.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface CredentialRepository extends JpaRepository<Credential, Long> {
    @Modifying(flushAutomatically = true, clearAutomatically = true)
    @Query("update Credential c set c.url = :url, c.username = :username, c.password = :password, c.key = :key" +
            " WHERE c.credentialId = :credentialId")
    void updateCredential(@Param("url") String url, @Param("username") String username, @Param("password") String password,@Param("key") String key, @Param("credentialId")Long credentialId);

    List<Credential> findAllByUser(User user);
}
