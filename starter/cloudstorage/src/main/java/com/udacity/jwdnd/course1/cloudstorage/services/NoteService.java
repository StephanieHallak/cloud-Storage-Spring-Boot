package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.entity.Note;
import com.udacity.jwdnd.course1.cloudstorage.entity.NoteDTO;
import com.udacity.jwdnd.course1.cloudstorage.entity.User;
import com.udacity.jwdnd.course1.cloudstorage.repository.NoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;


@Service
@Transactional
public class NoteService {
    @Autowired
    private NoteRepository noteRepository;

    public void addNote(Note note){
        noteRepository.save(note);
    }

    public void updateNote(Note note){
        noteRepository.updateNote(note.getNoteTitle(), note.getNoteDescription(), note.getNoteId());
    }

    public List<Note> getAllNotes(User user){
        return noteRepository.findAllByUser(user);
    }
    public Note getOneNote(Long noteId){
        return noteRepository.findById(noteId).get();
    }

    public void deleteNote(Note note){
        noteRepository.delete(note);
    }
}
