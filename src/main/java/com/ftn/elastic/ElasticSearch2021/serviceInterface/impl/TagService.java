package com.ftn.elastic.ElasticSearch2021.serviceInterface.impl;

import com.ftn.elastic.ElasticSearch2021.dto.TagDTO;
import com.ftn.elastic.ElasticSearch2021.dto.UserDTO;
import com.ftn.elastic.ElasticSearch2021.model.Tag;
import com.ftn.elastic.ElasticSearch2021.model.User;
import com.ftn.elastic.ElasticSearch2021.repository.TagRepository;
import com.ftn.elastic.ElasticSearch2021.repository.UserRepository;
import com.ftn.elastic.ElasticSearch2021.serviceInterface.TagServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TagService implements TagServiceInterface {

    @Autowired
    TagRepository tagRepository;

    @Autowired
    UserRepository userRepository;

    @Override
    public List<TagDTO> getAll() {
        List<Tag> tags = tagRepository.findAll();

        List<TagDTO> dtos = new ArrayList<>();
        for(Tag t: tags) {
            dtos.add(new TagDTO(t));
        }
        return dtos;
    }

    @Override
    public TagDTO getOne(Integer id) {
        Tag tag = tagRepository.findOneById(id);

        return new TagDTO(tag);
    }

    @Override
    public TagDTO save(TagDTO tagDTO) {

        User u = userRepository.findOneById(tagDTO.getUser().getId());
        Tag t = new Tag();
        t.setName(tagDTO.getName());
        t.setUser(u);

        t= tagRepository.save(t);
        return  new TagDTO(t);
    }

    @Override
    public TagDTO update(Integer id, TagDTO tagDTO) {
        Tag t = tagRepository.findOneById(id);
        User u = t.getUser();
        t.setName(tagDTO.getName());
        t.setUser(u);

        t= tagRepository.save(t);
        return  new TagDTO(t);
    }

    @Override
    public void delete(Integer id) {
        tagRepository.deleteById(id);
    }
}
