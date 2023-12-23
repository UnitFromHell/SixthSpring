package com.example.prs.Controller;


import com.example.prs.models.Person;
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
@RequestMapping("/people")
public class PersonController {
    private com.example.prs.repositories.PersonRepository personRepository;

    @Autowired
    public PersonController(com.example.prs.repositories.PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    @GetMapping()
    public String index(Model model) {
        model.addAttribute("people", personRepository.findAll());
        return "/people/index";
    }

    @GetMapping("/new")
    public String showAddForm(Person person) {
        return "people/new";
    }

    @GetMapping("/edit/{id}")
    public String showUpdateForm(@PathVariable("id") long id, Model model) {
        Person person = personRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid strawberry Id: " + id));
        model.addAttribute("person", person);
        return "people/edit";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id") long id, Model model) {
        try {
            Person person = personRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid strawberry Id: " + id));
            personRepository.delete(person);
        } catch (MethodArgumentTypeMismatchException e) {
            // Обработка ошибки преобразования типа
            // Можно просто проигнорировать ошибку и перенаправить на страницу с людьми
            return "redirect:/people";
        }
        model.addAttribute("people", personRepository.findAll());
        return "redirect:/people";
    }

    @PostMapping("/addperson")
    public String addPerson(@Valid Person person, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "people/new";
        }
        personRepository.save(person);
        model.addAttribute("people", personRepository.findAll());
        return "people/index";
    }

    @PostMapping("/{id}")
    public String update(@PathVariable("id") long id, @Valid Person person, BindingResult result, Model model) {
        if (result.hasErrors()) {
            person.setId(id);
            return "people/edit";
        }
        personRepository.save(person);
        model.addAttribute("persons", personRepository.findAll());
        return "redirect:/people";
    }

    @GetMapping("/search")
    public String search(@RequestParam("name") String name, Model model) {
        List<Person> persons = personRepository.findAll();
        List<Person> sortPerson = new ArrayList<>();
        for (Person person:
                persons) {
            if(person.getName().toLowerCase().contains(name.toLowerCase()))
            {
                sortPerson.add(person);
            }
        }
        model.addAttribute("people", sortPerson);
        return "/people/index";
    }
}
