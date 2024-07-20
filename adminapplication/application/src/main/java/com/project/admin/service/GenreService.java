package com.project.admin.service;

import com.project.admin.dao.GenreRepository;
import com.project.admin.entity.Genre;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class GenreService {

    @Autowired
    private GenreRepository genreRepository;

    @Autowired
    private BookService bookService;

    public List<Genre> findAll() {
        return genreRepository.findAll();
    }

    public void save(Genre genre) {
        genreRepository.save(genre);
    }

    public void deleteById(Long id) {
        genreRepository.deleteById(id);
    }

    public Map<Long, Long> getGenreBookCount() {
        Map<Long, Long> genreBookCount = new HashMap<>();
        List<Genre> genres = findAll();
        for (Genre genre : genres) {
            Long count = bookService.countByGenreId(genre.getGenreId());
            genreBookCount.put(genre.getGenreId(), count);
        }
        return genreBookCount;
    }
}
