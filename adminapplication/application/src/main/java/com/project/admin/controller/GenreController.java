package com.project.admin.controller;

import com.project.admin.entity.Genre;
import com.project.admin.service.GenreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin/genres")
public class GenreController {

    @Autowired
    private GenreService genreService;

    @GetMapping
    public String listGenres(Model model) {
        model.addAttribute("genres", genreService.findAll());
        model.addAttribute("genreBookCount", genreService.getGenreBookCount());
        return "genres";
    }

    @GetMapping("/add")
    public String addGenreForm(Model model) {
        model.addAttribute("genre", new Genre());
        return "addGenre";
    }

    @PostMapping("/add")
    public String addGenre(@ModelAttribute("genre") Genre genre) {
        genreService.save(genre);
        return "redirect:/admin/genres";
    }

    @GetMapping("/delete/{id}")
    public String deleteGenre(@PathVariable("id") Long id) {
        genreService.deleteById(id);
        return "redirect:/admin/genres";
    }
}
