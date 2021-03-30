package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.entity.Note;
import com.udacity.jwdnd.course1.cloudstorage.entity.NoteDTO;
import com.udacity.jwdnd.course1.cloudstorage.entity.User;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class NoteController {
    @Autowired
    private NoteService noteService;
    @Autowired
    private UserService userService;

    @PostMapping("/uploadNote")
    public String manageNote(Authentication authentication, Model model, @ModelAttribute("noteDTO")NoteDTO noteDTO){
        User user = userService.getUser(authentication.getName());
        //noteDTO.setUserId(user.getUserId());
        Note note = new Note(noteDTO.getNoteId(), noteDTO.getNoteTitle(), noteDTO.getNoteDescription(), user);
        if (noteDTO.getNoteId() == null){
            //THE NOTE IS NEW TO UPLOAD
            noteService.addNote(note);
        }
        else{
            noteService.updateNote(note);
        }
        model.addAttribute("result", "success");
        return "result";
    }

    @GetMapping("/deleteNote/{noteId}")
    public String deleteNote(@PathVariable("noteId")Long noteId, Authentication authentication, Model model){
        Note note = noteService.getOneNote(noteId);
        noteService.deleteNote(note);
        model.addAttribute("result", "success");
        return "result";
    }
}
