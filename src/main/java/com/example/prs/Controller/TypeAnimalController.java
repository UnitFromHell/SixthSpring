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
@RequestMapping("/typeanimal")
public class TypeAnimalController {
    @Autowired
    private com.example.prs.repositories.TypeAnimalRepository typeanimalRepository;
    @Autowired
    private com.example.prs.repositories.AnimalRepository animalRepository;



    @GetMapping()
    public String index(Model model) {
        model.addAttribute("typeanimals", typeanimalRepository.findAll());
        return "/typeanimal/index";
    }

    @GetMapping("/new")
    public String showAddForm(TypeAnimal typeAnimal) {
        return "typeanimal/new";
    }

    @GetMapping("/edit/{id}")
    public String showUpdateForm(@PathVariable("id") long id, Model model) {
        TypeAnimal  typeanimal = typeanimalRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid strawberry Id: " + id));
        model.addAttribute("typeanimal", typeanimal);
        return "typeanimal/edit";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id") long id, Model model) {
        try {
            TypeAnimal typeAnimal = typeanimalRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid strawberry Id: " + id));
            if (typeAnimal.getAnimalItems().isEmpty()) {
                typeanimalRepository.delete(typeAnimal);
            } else {
                List<Animal> animalDelList = typeAnimal.getAnimalItems();
                for (Animal animal:
                        animalDelList) {
                    animalRepository.delete(animal);
                }
                typeanimalRepository.delete(typeAnimal);
            }

        }
        catch (MethodArgumentTypeMismatchException e) {
            return "redirect:/typeanimal";
        }
        model.addAttribute("typeanimals", typeanimalRepository.findAll());
        return "redirect:/typeanimal";
    }
    @PostMapping("/addtypeanimal")
    public String addPerson(@Valid TypeAnimal typeanimal, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "typeanimal/new";
        }
        typeanimalRepository.save(typeanimal);
        model.addAttribute("typeanimals", typeanimalRepository.findAll());
        return "redirect:/typeanimal";
    }

    @PostMapping("/{id}")
    public String update(@PathVariable("id") long id, @Valid TypeAnimal typeanimal, BindingResult result, Model model) {
        if (result.hasErrors()) {
            typeanimal.setId(id);
            return "typeanimal/edit";
        }
        typeanimalRepository.save(typeanimal);
        model.addAttribute("typeanimals", typeanimalRepository.findAll());
        return "typeanimal/index";
    }
}
//    @GetMapping("/search")
//    public String search(@RequestParam("name") String name, Model model) {
//        if (name.isEmpty()) {
//            model.addAttribute("animals", animalRepository.findAll());
//        } else {
//            List<Animal> animals = animalRepository.findAll();
//            List<Animal> sortAnimals = new ArrayList<>();
//            for (Animal animal : animals) {
//                if (animal.getName().toLowerCase().contains(name.toLowerCase())) {
//                    sortAnimals.add(animal);
//                }
//            }
//            model.addAttribute("animals", sortAnimals);
//        }
//        return "/people/animal/index";
//    }

