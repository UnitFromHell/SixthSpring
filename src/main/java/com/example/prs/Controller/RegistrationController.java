package com.example.prs.Controller;

import com.example.prs.models.Person;
import com.example.prs.repositories.PersonRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class RegistrationController {

    @Autowired
    private PersonRepository personRepository;

    @GetMapping("/registration")
    public String regForm()
    {
        return "registration";
    }

    @PostMapping("/registration")
    public String registerPerson(@Valid Person person, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "registration";
        }
        personRepository.save(person);
        return "redirect:/login";
    }
}
