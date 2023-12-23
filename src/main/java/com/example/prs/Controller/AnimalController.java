package com.example.prs.Controller;


import com.example.prs.models.Animal;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/people/animal")
public class AnimalController {
    private com.example.prs.repositories.AnimalRepository animalRepository;

    @Autowired
    public AnimalController(com.example.prs.repositories.AnimalRepository animalRepository) {
        this.animalRepository = animalRepository;
    }

    @GetMapping()
    public String index(Model model) {
        model.addAttribute("animals", animalRepository.findAll());
        return "/people/animal/index";
    }

    @GetMapping("/new")
    public String showAddForm(Animal animal) {
        return "people/animal/new";
    }

    @GetMapping("/edit/{id}")
    public String showUpdateForm(@PathVariable("id") long id, Model model) {
        Animal animal = animalRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid strawberry Id: " + id));
        model.addAttribute("animal", animal);
        return "people/animal/edit";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id") long id, Model model) {
        try {
            Animal animal = animalRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid strawberry Id: " + id));
            animalRepository.delete(animal);
        }
        catch (MethodArgumentTypeMismatchException e) {
            return "redirect:/people/animal";
        }
        model.addAttribute("animals", animalRepository.findAll());
        return "redirect:/people/animal";
    }
    @PostMapping("/addanimal")
    public String addPerson(@Valid Animal animal, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "people/animal/new";
        }
        animalRepository.save(animal);
        model.addAttribute("animals", animalRepository.findAll());
        return "redirect:/people/animal";
    }

    @PostMapping("/{id}")
    public String update(@PathVariable("id") long id, @Valid Animal animal, BindingResult result, Model model) {
        if (result.hasErrors()) {
            animal.setId(id);
            return "people/animal/edit";
        }
        animalRepository.save(animal);
        model.addAttribute("animals", animalRepository.findAll());
        return "people/animal/index";
    }

    @GetMapping("/search")
    public String search(@RequestParam("name") String name, Model model) {
        if (name.isEmpty()) {
            model.addAttribute("animals", animalRepository.findAll());
        } else {
            List<Animal> animals = animalRepository.findAll();
            List<Animal> sortAnimals = new ArrayList<>();
            for (Animal animal : animals) {
                if (animal.getName().toLowerCase().contains(name.toLowerCase())) {
                    sortAnimals.add(animal);
                }
            }
            model.addAttribute("animals", sortAnimals);
        }
        return "/people/animal/index";
    }
}
