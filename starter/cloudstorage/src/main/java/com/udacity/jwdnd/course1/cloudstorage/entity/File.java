package com.udacity.jwdnd.course1.cloudstorage.entity;

import javax.persistence.*;

@Entity
public class File {
    @Id
    @GeneratedValue
    private Long fileId;

    private String fileName;
    private String contentType;
    private String fileSize;

    @ManyToOne
    @JoinColumn(name = "user_id")//A COLUMN CALLED 'user_id' WILL BE CREATED IN THE 'FILE' TABLE
    private User user;

    @Lob
    private byte[] fileData;
    //DEFAULT CONSTRUCTOR FOR JPA USE. IT IS NOR USED DIRECTLY --> SO IT IS 'PROTECTED'
    protected File() {
    }

    public File(Long fileId, String fileName, String contentType, String fileSize, User user, byte[] fileData) {
        this.fileId = fileId;
        this.fileName = fileName;
        this.contentType = contentType;
        this.fileSize = fileSize;
        this.user = user;
        this.fileData = fileData;
    }

    public Long getFileId() {
        return fileId;
    }

    public void setFileId(Long fileId) {
        this.fileId = fileId;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public String getFileSize() {
        return fileSize;
    }

    public void setFileSize(String fileSize) {
        this.fileSize = fileSize;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public byte[] getFileData() {
        return fileData;
    }

    public void setFileData(byte[] fileData) {
        this.fileData = fileData;
    }
}
