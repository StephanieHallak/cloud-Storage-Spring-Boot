package com.udacity.jwdnd.course1.cloudstorage.entity;

import javax.persistence.*;

@Entity
@Table(name="Credential")
public class Credential {
    @Id
    @GeneratedValue
    private Long credentialId;
    private String url;
    private String username;
    @Column(name="key_steph")
    private String key;
    private String password;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    protected Credential() {
    }

    public Credential(Long credentialId, String url, String username, String key, String password, User user) {
        this.credentialId = credentialId;
        this.url = url;
        this.username = username;
        this.key = key;
        this.password = password;
        this.user = user;
    }

    public Long getCredentialId() {
        return credentialId;
    }

    public void setCredentialId(Long credentialId) {
        this.credentialId = credentialId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
