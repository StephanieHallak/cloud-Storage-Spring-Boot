package com.udacity.jwdnd.course1.cloudstorage.entity;

import org.hibernate.annotations.Fetch;

import javax.persistence.*;

@Entity
public class Note {
    @Id
    @GeneratedValue
    private Long noteId;

    private String noteTitle;
    private String noteDescription;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    protected Note() {
    }

    public Note(Long noteId, String noteTitle, String noteDescription, User user) {
        this.noteId = noteId;
        this.noteTitle = noteTitle;
        this.noteDescription = noteDescription;
        this.user = user;
    }

    public Long getNoteId() {
        return noteId;
    }

    public void setNoteId(Long noteId) {
        this.noteId = noteId;
    }

    public String getNoteTitle() {
        return noteTitle;
    }

    public void setNoteTitle(String noteTitle) {
        this.noteTitle = noteTitle;
    }

    public String getNoteDescription() {
        return noteDescription;
    }

    public void setNoteDescription(String noteDescription) {
        this.noteDescription = noteDescription;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
