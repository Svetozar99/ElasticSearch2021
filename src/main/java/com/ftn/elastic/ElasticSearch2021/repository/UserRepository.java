package com.ftn.elastic.ElasticSearch2021.repository;

import com.ftn.elastic.ElasticSearch2021.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {

    User findOneById(Integer id);

    Optional<User> findByUsername(String username);

    User findOneByUsername(String username);

    @Query("Select u from User u where u.username = :username")
    Optional<User> findUserByUsername(@Param("username") String username);


    @Query("SELECT u from User u WHERE "
            + "(:username is null OR u.username LIKE CONCAT('%', :username, '%')) AND "
            + "(:firstName is null OR lower(u.firstName) LIKE lower(CONCAT('%', :firstName, '%'))) AND "
            + "(:lastName is null OR lower(u.lastName) LIKE lower(CONCAT('%', :lastName, '%')))")
    List<User> filterUsers(@Param("username") String username, @Param("firstName") String firstName,
                           @Param("lastName") String lastName);
}