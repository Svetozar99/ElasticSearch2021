package com.ftn.elastic.ElasticSearch2021.serviceInterface;


import com.ftn.elastic.ElasticSearch2021.dto.TagDTO;

import java.util.List;

public interface TagServiceInterface {

    public List<TagDTO> getAll();

    public TagDTO getOne(Integer id);

    public TagDTO save(TagDTO tagDTO);

    public TagDTO update(Integer id, TagDTO tagDTO);

    public void delete(Integer id);
}
