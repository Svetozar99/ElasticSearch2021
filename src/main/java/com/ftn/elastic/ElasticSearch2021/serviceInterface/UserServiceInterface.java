package com.ftn.elastic.ElasticSearch2021.serviceInterface;
import com.ftn.elastic.ElasticSearch2021.dto.UserDTO;
import com.ftn.elastic.ElasticSearch2021.model.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;
import java.util.Optional;

public interface UserServiceInterface {

    public List<UserDTO> getAll();

    public UserDTO getOne(Integer id);

    public UserDTO save(UserDTO userDTO);

    public UserDTO update(Integer id, UserDTO userDTO);

    public void delete(Integer id);

    public UserDTO getByUsername(String username);

    List<UserDTO> filterUsers(UserDTO userDTO);

    UserDetails loadUserByUsername(String username);

    public Optional<User> getUserByUsername(String username);
}
