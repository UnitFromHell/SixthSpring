package com.example.prs.Controller;
import com.example.prs.models.Passport;
import com.example.prs.models.Person;
import com.example.prs.repositories.PassportRepository;
import com.example.prs.repositories.PersonRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
@Controller
@RequestMapping("/passport")
public class PassportController {
    @Autowired
    PassportRepository passportRepository;

    @Autowired
    PersonRepository personRepository;

    @GetMapping()
    public String index(Model model) {
        model.addAttribute("passports", passportRepository.findAll());
        return "/passport/index";
    }

    @GetMapping("/new")
    public String showAddForm(Passport passport) {
        return "passport/new";
    }

    @GetMapping("/edit/{id}")
    public String showUpdateForm(@PathVariable("id") long id, Model model) {
        Passport passport = passportRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid strawberry Id: " + id));
        model.addAttribute("passport", passport);
        return "passport/edit";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id") long id, Model model) {
        try {
            Passport passport = passportRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid strawberry Id: " + id));
            if (passport.getOwner() == null) {
                passportRepository.delete(passport);
            } else {
                Person person = passport.getOwner();
                personRepository.delete(person);
                passportRepository.delete(passport);
            }
        }
        catch (MethodArgumentTypeMismatchException e) {
            return "redirect:/passport";
        }
        model.addAttribute("passports", passportRepository.findAll());
        return "redirect:/passport";
    }

    @PostMapping("/addpassport")
    public String addP(@Valid Passport passport, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "passport/new";
        }
        passportRepository.save(passport);
        model.addAttribute("passports", passportRepository.findAll());
        return "redirect:/passport";
    }

    @PostMapping("/{id}")
    public String update(@PathVariable("id") long id, @Valid Passport passport, BindingResult result, Model model) {
        if (result.hasErrors()) {
            passport.setId(id);
            return "passport/edit";
        }
        passportRepository.save(passport);
        model.addAttribute("passports", passportRepository.findAll());
        return "passport/index";
    }
}
