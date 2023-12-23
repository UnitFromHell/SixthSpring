package com.example.prs.Controller;


import com.example.prs.models.Animal;
import com.example.prs.models.Fish;
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
@RequestMapping("/people/fish")
public class FishController {
    private com.example.prs.repositories.FishRepository fishRepository;

    @Autowired
    public FishController(com.example.prs.repositories.FishRepository fishRepository) {
        this.fishRepository = fishRepository;
    }

    @GetMapping()
    public String index(Model model) {
        model.addAttribute("fishs", fishRepository.findAll());
        return "/people/fish/index";
    }

    @GetMapping("/new")
    public String showAddForm(Fish fish) {
        return "people/fish/new";
    }

    @GetMapping("/edit/{id}")
    public String showUpdateForm(@PathVariable("id") long id, Model model) {
        Fish fish = fishRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid strawberry Id: " + id));
        model.addAttribute("fish", fish);
        return "people/fish/edit";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id") long id, Model model) {
        try {
            Fish fish = fishRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid strawberry Id: " + id));
            fishRepository.delete(fish);
        }
        catch (MethodArgumentTypeMismatchException e) {
            return "redirect:/people/fish";

        }

        model.addAttribute("fishs", fishRepository.findAll());
        return "redirect:/people/fish";
    }

    @PostMapping("/addfish")
    public String addPerson(@Valid Fish fish, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "people/fish/new";
        }
        fishRepository.save(fish);
        model.addAttribute("fishs", fishRepository.findAll());
        return "redirect:/people/fish";
    }

    @PostMapping("/{id}")
    public String update(@PathVariable("id") long id, @Valid Fish fish, BindingResult result, Model model) {
        if (result.hasErrors()) {
            fish.setId(id);
            return "people/fish/edit";
        }
        fishRepository.save(fish);
        model.addAttribute("fishs", fishRepository.findAll());
        return "people/fish/index";
    }

    @GetMapping("/search")
    public String search(@RequestParam("name") String name, Model model) {
        if (name.isEmpty()) {
            model.addAttribute("fishs", fishRepository.findAll());
        } else {
            List<Fish> fishs = fishRepository.findAll();
            List<Fish> sortFishs = new ArrayList<>();
            for (Fish fish : fishs) {
                if (fish.getName().toLowerCase().contains(name.toLowerCase())) {
                    sortFishs.add(fish);
                }
            }
            model.addAttribute("animals", sortFishs);
        }
        return "/people/animal/index";
    }
}
