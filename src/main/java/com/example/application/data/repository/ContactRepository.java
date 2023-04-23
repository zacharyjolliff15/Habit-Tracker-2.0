package com.example.application.data.repository;

import com.example.application.data.entity.Contact;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ContactRepository extends JpaRepository<Contact, Long> {

    @Query("select c from Contact c " +
            "where lower(c.habitName) like lower(concat('%', :searchTerm, '%')) " +
            "or lower(c.habitDescription) like lower(concat('%', :searchTerm, '%'))")
    List<Contact> search(@Param("searchTerm") String searchTerm);
}
