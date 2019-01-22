package com.pitech.services.impl;

import com.pitech.models.Author;
import com.pitech.repositories.AuthorRepository;
import com.pitech.services.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthorServiceImpl implements AuthorService{
    private AuthorRepository authorRepository;

    @Autowired
    public void SetBookRepository(AuthorRepository authorRepository){
        this.authorRepository=authorRepository;
    }

    @Override
    public Iterable<Author> listAllAuthors(){
        return authorRepository.findAll();
    }

    @Override
    public Optional<Author> getAuthorById(Integer authorId) {

        return authorRepository.findById(authorId);
    }
}
