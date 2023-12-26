package com.example.prs.Controller;


import com.example.prs.models.Passport;
import com.example.prs.models.Person;
import com.example.prs.models.Role;
import com.example.prs.repositories.PassportRepository;
import com.example.prs.repositories.PersonRepository;
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
import java.util.Set;

@Controller
@RequestMapping("/people")
@PreAuthorize("hasAuthority('ADMIN')")
public class PersonController {
    @Autowired
    private PersonRepository personRepository;
    @Autowired
    private PassportRepository passportRepository;

    @GetMapping()
    public String index(Model model) {
        model.addAttribute("people", personRepository.findAll());
        return "/people/index";
    }

    @GetMapping("/new")
    public String showAddForm(Person person, Model model) {
        List<Passport> passports = passportRepository.findAll();
        model.addAttribute("passports", passports);
        return "people/new";
    }

    @GetMapping("/edit/{id}")
    public String showUpdateForm(@PathVariable("id") long id, Model model) {
        Person person = personRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid strawberry Id: " + id));
        model.addAttribute("person", person);
        List<Passport> passports = passportRepository.findAll();
        model.addAttribute("passports", passports);
        return "people/edit";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id") long id, Model model) {
        try {
            Person person = personRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid strawberry Id: " + id));
            personRepository.delete(person);
        } catch (MethodArgumentTypeMismatchException e) {

            return "redirect:/people";
        }
        model.addAttribute("people", personRepository.findAll());
        return "redirect:/people";
    }

    @PostMapping("/addperson")
    public String addPerson(@Valid Person person, BindingResult result, Model model, @RequestParam("roles") Set<Role> roles) {
        List<Passport> passports2 = passportRepository.findAll();
        model.addAttribute("passports", passports2);
        if (result.hasErrors()) {
            List<Passport> passports = passportRepository.findAll();
            model.addAttribute("passports", passports);
            return "people/new";
        }

        try {
            Passport selectedPassport = person.getPassport();
            if (selectedPassport != null && personRepository.existsByPassport(selectedPassport)) {
                result.rejectValue("passport", "error.person", "Данный паспорт уже присвоен другому человеку");
                return "people/new";
            }

            if (personRepository.existsByLogin(person.getLogin())) {
                result.rejectValue("login", "error.person", "Данный логин уже существует");
                return "people/new";
            }

            if (personRepository.existsByEmail(person.getEmail())) {
                result.rejectValue("email", "error.person", "Данный email уже существует");
                return "people/new";
            }

            person.setRoles(roles); // Установка выбранных ролей для пользователя
            personRepository.save(person);
            model.addAttribute("people", personRepository.findAll());
            return "people/index";
        } catch (Exception e) {
            result.reject("error.person", "Произошла ошибка при добавлении человека");
            return "people/new";
        }
    }

    @PostMapping("/{id}")
    public String update(@PathVariable("id") long id, @ModelAttribute("user") @Valid Person person, BindingResult result, Model model, @RequestParam("roles") Set<Role> roles) {
        List<Passport> passports = passportRepository.findAll();
        model.addAttribute("passports", passports);

        if (result.hasErrors()) {
            person.setId(id);
            person.setRoles(roles);
            List<Passport> passports2 = passportRepository.findAll();
            model.addAttribute("passports", passports2);
            return "people/edit";
        }

        Person existingPerson = personRepository.findById(id).orElse(null);
        Passport selectedPassport = person.getPassport();

        if (existingPerson != null && selectedPassport != null && existingPerson.getPassport() != null && existingPerson.getPassport().equals(selectedPassport)) {
            person.setPassport(existingPerson.getPassport());
        } else {
            if (selectedPassport != null && personRepository.existsByPassport(selectedPassport)) {
                result.rejectValue("passport", "error.person", "Данный паспорт уже присвоен другому человеку");
                return "people/edit";
            }
        }

        if (existingPerson != null && existingPerson.getLogin().equals(person.getLogin())) {
            person.setLogin(existingPerson.getLogin());
        } else {
            if (personRepository.existsByLogin(person.getLogin())) {
                result.rejectValue("login", "error.person", "Данный логин уже существует");
                return "people/edit";
            }
        }

        if (existingPerson != null && existingPerson.getEmail().equals(person.getEmail())) {
            person.setEmail(existingPerson.getEmail());
        } else {
            if (personRepository.existsByEmail(person.getEmail())) {
                result.rejectValue("email", "error.person", "Данный email уже существует");
                return "people/edit";
            }
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
