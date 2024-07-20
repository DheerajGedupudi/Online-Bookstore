package com.project.user.service;

import com.project.user.dao.GenreRepository;
import com.project.user.entity.Genre;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GenreService {

    @Autowired
    private GenreRepository genreRepository;

    public List<Genre> findAll() {
        return genreRepository.findAll();
    }
}
