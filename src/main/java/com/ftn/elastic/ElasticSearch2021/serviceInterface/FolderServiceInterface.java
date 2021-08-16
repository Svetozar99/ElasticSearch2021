package com.ftn.elastic.ElasticSearch2021.serviceInterface;

import com.ftn.elastic.ElasticSearch2021.dto.FolderDTO;
import com.ftn.elastic.ElasticSearch2021.model.Folder;

import java.util.List;

public interface FolderServiceInterface {

    public List<FolderDTO> getAll();

    public FolderDTO getOne(Integer id);

    public FolderDTO save(FolderDTO folderDTO);

    public FolderDTO update(Integer id, FolderDTO folderDTO);

    public void delete(Integer id);

    public Folder getByAccountId(Integer id, String name);
}
