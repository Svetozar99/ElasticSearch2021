package com.ftn.elastic.ElasticSearch2021.serviceInterface.impl;

import com.ftn.elastic.ElasticSearch2021.dto.AccountDTO;
import com.ftn.elastic.ElasticSearch2021.dto.UserDTO;
import com.ftn.elastic.ElasticSearch2021.model.Account;
import com.ftn.elastic.ElasticSearch2021.model.User;
import com.ftn.elastic.ElasticSearch2021.repository.UserRepository;
import com.ftn.elastic.ElasticSearch2021.security.CustomPrincipal;
import com.ftn.elastic.ElasticSearch2021.serviceInterface.UserServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

@Service
public class UserService implements UserServiceInterface {

    @Autowired
    UserRepository userRepository;

    @Override
    public List<UserDTO> getAll() {
        List<User> users = userRepository.findAll();

        List<UserDTO> dtos = new ArrayList<>();
        for(User u: users) {
            dtos.add(new UserDTO(u));
        }
        return dtos;
    }

    @Override
    public UserDTO getOne(Integer id) {
        User user = userRepository.findOneById(id);

        return new UserDTO(user);
    }

    @Override
    public UserDTO save(UserDTO userDTO) {

        User u = new User();
        u.setFirstName(userDTO.getFirstName());
        u.setLastName(userDTO.getLastName());
        u.setUsername(userDTO.getUsername());
        u.setPassword(userDTO.getPassword());

        u = userRepository.save(u);

        return new UserDTO(u);
    }

    @Override
    public UserDTO update(Integer id, UserDTO userDTO) {
        User u = userRepository.findOneById(id);

        u.setFirstName(userDTO.getFirstName());
        u.setLastName(userDTO.getLastName());
        u.setUsername(userDTO.getUsername());
        u.setPassword(userDTO.getPassword());

        u = userRepository.save(u);

        return new UserDTO(u);
    }

    @Override
    public void delete(Integer id) {
        userRepository.deleteById(id);
    }

    @Override
    public UserDTO getByUsername(String username) {
        User user = userRepository.findOneByUsername(username);
        if(user != null)
            return new UserDTO(user);
        else throw new EntityNotFoundException();
    }

    @Override
    public List<UserDTO> filterUsers(UserDTO userDTO) {
        List<User> users = userRepository.filterUsers(userDTO.getUsername(), userDTO.getFirstName(), userDTO.getLastName());

        List<UserDTO> dtos = new ArrayList<>();
        for(User u: users) {
            dtos.add(new UserDTO(u));
        }
        return dtos;
    }

    @Override
    public UserDetails loadUserByUsernameAndPassword(String username,String password) {
        Optional<User> user = userRepository.findByUsernameAndPassword(username, password);
        if(user.isEmpty()) throw new UsernameNotFoundException(String.format("User with username: %s not found", username));

        User u = user.get();

        return new CustomPrincipal(u.getId(), u.getUsername(), u.getFirstName(), u.getLastName(), u.getPassword(), new HashSet<>());
    }

    @Override
    public Optional<User> getUserByUsername(String username) {
        return userRepository.findUserByUsername(username);
    }

    @Override
    public User getByFirstName(String firstName) {
        return userRepository.findOneByFirstName(firstName);
    }

    public User getMeByUsername(String username) {
        return userRepository.findOneByUsername(username);
    }
}
