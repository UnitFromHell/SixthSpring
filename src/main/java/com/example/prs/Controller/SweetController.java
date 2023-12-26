package com.example.prs.Controller;


import com.example.prs.models.Sweet;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/sweet")
@PreAuthorize("hasAuthority('USER')")
public class SweetController {
    private com.example.prs.repositories.SweetRepository sweetRepository;

    @Autowired
    public SweetController(com.example.prs.repositories.SweetRepository sweetRepository) {
        this.sweetRepository = sweetRepository;
    }

    @GetMapping()
    public String index(Model model) {
        model.addAttribute("sweets", sweetRepository.findAll());
        return "/sweet/index";
    }

    @GetMapping("/new")
    public String showAddForm(Sweet sweet) {
        return "sweet/new";
    }

    @GetMapping("/edit/{id}")
    public String showUpdateForm(@PathVariable("id") long id, Model model) {
        Sweet sweet = sweetRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid strawberry Id: " + id));
        model.addAttribute("sweet", sweet);
        return "sweet/edit";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id") long id, Model model) {
        try {
            Sweet sweet = sweetRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid strawberry Id: " + id));
            sweetRepository.delete(sweet);
        }
        catch (MethodArgumentTypeMismatchException e) {
            return "redirect:/sweet";

        }
        model.addAttribute("sweets", sweetRepository.findAll());
        return "redirect:/sweet";
    }

    @PostMapping("/addsweet")
    public String addPerson(@Valid Sweet sweet, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "sweet/new";
        }
        sweetRepository.save(sweet);
        model.addAttribute("sweets", sweetRepository.findAll());
        return "redirect:/sweet";
    }

    @PostMapping("/{id}")
    public String update(@PathVariable("id") long id, @Valid Sweet sweet, BindingResult result, Model model) {
        if (result.hasErrors()) {
            sweet.setId(id);
            return "sweet/edit";
        }
        sweetRepository.save(sweet);
        model.addAttribute("sweets", sweetRepository.findAll());
        return "sweet/index";
    }

    @GetMapping("/search")
    public String search(@RequestParam("name") String name, Model model) {
        List<Sweet> sweets = sweetRepository.findAll();
        List<Sweet> sortSweets = new ArrayList<>();
        for (Sweet sweet:
                sweets) {
            if(sweet.getName().toLowerCase().contains(name.toLowerCase()))
            {
                sortSweets.add(sweet);
            }
        }
        model.addAttribute("sweets", sortSweets);
        return "sweet/index";
    }
}
