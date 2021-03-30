package com.udacity.jwdnd.course1.cloudstorage.repository;

import com.udacity.jwdnd.course1.cloudstorage.entity.Note;
import com.udacity.jwdnd.course1.cloudstorage.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NoteRepository extends JpaRepository<Note, Long> {
    List<Note> findAllByUser(User user);

    @Modifying(flushAutomatically = true, clearAutomatically = true)
    @Query("update Note n set n.noteTitle = :noteTitle, n.noteDescription = :noteDescription WHERE n.noteId = :noteId")
    void updateNote(@Param("noteTitle") String noteTitle, @Param("noteDescription") String noteDescription, @Param("noteId") Long noteId);

}
