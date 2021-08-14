package com.ftn.elastic.ElasticSearch2021.serviceInterface.impl;

import com.ftn.elastic.ElasticSearch2021.dto.AccountDTO;
import com.ftn.elastic.ElasticSearch2021.dto.UserDTO;
import com.ftn.elastic.ElasticSearch2021.model.Account;
import com.ftn.elastic.ElasticSearch2021.model.User;
import com.ftn.elastic.ElasticSearch2021.repository.UserRepository;
import com.ftn.elastic.ElasticSearch2021.serviceInterface.UserServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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
}
