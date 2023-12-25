package com.example.prs.Controller;


import com.example.prs.models.Animal;
import com.example.prs.models.TypeAnimal;
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
@RequestMapping("/animal")
public class AnimalController {
    @Autowired
    private com.example.prs.repositories.AnimalRepository animalRepository;
    @Autowired
    private com.example.prs.repositories.TypeAnimalRepository typeAnimalRepository;



    @GetMapping()
    public String index(Model model) {
        model.addAttribute("animals", animalRepository.findAll());
        model.addAttribute("typeanimals", typeAnimalRepository.findAll());
        return "animal/index";
    }

    @GetMapping("/new")
    public String showAddForm(Animal animal, Model model)
    {
        List<TypeAnimal> typeAnimals = typeAnimalRepository.findAll();
        model.addAttribute("typeanimals", typeAnimals);
        return "animal/new";
    }

    @GetMapping("/edit/{id}")
    public String showUpdateForm(@PathVariable("id") long id, Model model) {
        Animal animal = animalRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid strawberry Id: " + id));
        model.addAttribute("animal", animal);
        List<TypeAnimal> typeAnimals = typeAnimalRepository.findAll();
        model.addAttribute("typeanimals", typeAnimals);
        return "animal/edit";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id") long id, Model model) {
        try {
            Animal animal = animalRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid strawberry Id: " + id));
            animalRepository.delete(animal);
        }
        catch (MethodArgumentTypeMismatchException e) {
            return "redirect:/animal";
        }
        model.addAttribute("animals", animalRepository.findAll());
        return "redirect:/animal";
    }
    @PostMapping("/addanimal")
    public String addPerson(@Valid Animal animal, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "animal/new";
        }
        animalRepository.save(animal);
        model.addAttribute("animals", animalRepository.findAll());
        return "redirect:/animal";
    }

    @PostMapping("/{id}")
    public String update(@PathVariable("id") long id, @Valid Animal animal, BindingResult result, Model model) {
        if (result.hasErrors()) {
            animal.setId(id);
            return "animal/edit";
        }
        animalRepository.save(animal);
        List<TypeAnimal> typeAnimals = typeAnimalRepository.findAll();
        model.addAttribute("typeanimals", typeAnimals);
        model.addAttribute("animals", animalRepository.findAll());
        return "animal/index";
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
        return "/animal/index";
    }
}
