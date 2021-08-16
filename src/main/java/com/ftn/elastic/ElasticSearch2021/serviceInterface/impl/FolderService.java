package com.ftn.elastic.ElasticSearch2021.serviceInterface.impl;

import com.ftn.elastic.ElasticSearch2021.dto.ContactDTO;
import com.ftn.elastic.ElasticSearch2021.dto.FolderDTO;
import com.ftn.elastic.ElasticSearch2021.model.Account;
import com.ftn.elastic.ElasticSearch2021.model.Contact;
import com.ftn.elastic.ElasticSearch2021.model.Folder;
import com.ftn.elastic.ElasticSearch2021.repository.AccountRepository;
import com.ftn.elastic.ElasticSearch2021.repository.FolderRepository;
import com.ftn.elastic.ElasticSearch2021.serviceInterface.FolderServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class FolderService implements FolderServiceInterface {

    @Autowired
    FolderRepository folderRepository;

    @Autowired
    AccountRepository accountRepository;

    @Override
    public List<FolderDTO> getAll() {
        List<Folder> folders = folderRepository.findAll();

        List<FolderDTO> dtos = new ArrayList<>();
        for(Folder f: folders) {
            dtos.add(new FolderDTO(f));
        }
        return dtos;
    }

    @Override
    public FolderDTO getOne(Integer id) {
        Folder f = folderRepository.findOneById(id);

        return new FolderDTO(f);
    }

    @Override
    public FolderDTO save(FolderDTO folderDTO) {
        Account a = accountRepository.findOneById(folderDTO.getAccount().getId());
//        Folder folder = folderRepository.findOneById(folderDTO.getParentFolder().getId());
        Folder f = new Folder();
        f.setName(folderDTO.getName());
//        f.setParentFolder(folder);
        f.setAccount(a);

        f = folderRepository.save(f);

        return new FolderDTO(f);
    }

    @Override
    public FolderDTO update(Integer id, FolderDTO folderDTO) {
        Folder f = folderRepository.findOneById(id);

//        Folder folder = f.getParentFolder();
        Account a = f.getAccount();
        f.setName(folderDTO.getName());
        f = folderRepository.save(f);

        return new FolderDTO(f);
    }

    @Override
    public void delete(Integer id) {
        folderRepository.deleteById(id);
    }
}
