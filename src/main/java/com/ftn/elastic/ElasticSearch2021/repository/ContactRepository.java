package com.ftn.elastic.ElasticSearch2021.repository;

import com.ftn.elastic.ElasticSearch2021.model.Contact;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ContactRepository extends JpaRepository<Contact, Integer> {

    Contact findOneById(Integer id);

    List<Contact> findAllByUser_id(Integer id);

    @Query("Select c from Contact c WHERE " +
            "(:firstName is null OR lower(c.firstName) like lower(CONCAT('%', :firstName, '%'))) AND " +
            "(:lastName is null OR lower(c.lastName) like lower(CONCAT('%', :lastName, '%'))) AND " +
            "(:email is null OR c.email like CONCAT('%', :email, '%')) AND " +
            "(:note is null OR lower(c.note) like lower(CONCAT('%', :note, '%')))")
    List<Contact> filterContacts(@Param("firstName") String firstName, @Param("lastName") String lastName,
                                 @Param("email") String email, @Param("note") String note);
}