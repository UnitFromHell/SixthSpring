package com.example.prs.Controller;


import com.example.prs.models.Team;
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
@RequestMapping("/people/team")
public class TeamController {
    private com.example.prs.repositories.TeamRepository teamRepository;

    @Autowired
    public TeamController(com.example.prs.repositories.TeamRepository teamRepository) {
        this.teamRepository = teamRepository;
    }

    @GetMapping()
    public String index(Model model) {
        model.addAttribute("teams", teamRepository.findAll());
        return "/people/team/index";
    }

    @GetMapping("/new")
    public String showAddForm(Team team) {
        return "people/team/new";
    }

    @GetMapping("/edit/{id}")
    public String showUpdateForm(@PathVariable("id") long id, Model model) {
        Team team = teamRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid strawberry Id: " + id));
        model.addAttribute("team", team);
        return "people/team/edit";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id") long id, Model model) {
        try {
            Team team = teamRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid strawberry Id: " + id));
            teamRepository.delete(team);
        }
        catch (MethodArgumentTypeMismatchException e) {
            return "redirect:/people/team";

        }
        model.addAttribute("teams", teamRepository.findAll());
        return "redirect:/people/team";
    }

    @PostMapping("/addteam")
    public String addPerson(@Valid Team team, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "people/team/new";
        }
        teamRepository.save(team);
        model.addAttribute("teams", teamRepository.findAll());
        return "redirect:/people/team";
    }

    @PostMapping("/{id}")
    public String update(@PathVariable("id") long id, @Valid Team team, BindingResult result, Model model) {
        if (result.hasErrors()) {
            team.setId(id);
            return "people/team/edit";
        }
        teamRepository.save(team);
        model.addAttribute("teams", teamRepository.findAll());
        return "people/team/index";
    }

    @GetMapping("/search")
    public String search(@RequestParam("name") String name, Model model) {
        List<Team> teams = teamRepository.findAll();
        List<Team> sortTeam = new ArrayList<>();
        for (Team team:
                teams) {
            if(team.getName().toLowerCase().contains(name.toLowerCase()))
            {
                sortTeam.add(team);
            }
        }
        model.addAttribute("teams", sortTeam);
        return "people/team/index";
    }
}
