package com.ftn.elastic.ElasticSearch2021.serviceInterface;
import com.ftn.elastic.ElasticSearch2021.dto.UserDTO;

import java.util.List;

public interface UserServiceInterface {

    public List<UserDTO> getAll();

    public UserDTO getOne(Integer id);

    public UserDTO save(UserDTO userDTO);

    public UserDTO update(Integer id, UserDTO userDTO);

    public void delete(Integer id);

    public UserDTO getByUsername(String username);

    List<UserDTO> filterUsers(UserDTO userDTO);
}
