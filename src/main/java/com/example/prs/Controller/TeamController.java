package com.example.prs.Controller;


import com.example.prs.models.Team;
import com.example.prs.models.Tournament;
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
@RequestMapping("/team")
public class TeamController {
    @Autowired
    private com.example.prs.repositories.TeamRepository teamRepository;
    @Autowired
    private com.example.prs.repositories.TournamentRepository tournamentRepository;

    @Autowired
    public TeamController(com.example.prs.repositories.TeamRepository teamRepository) {
        this.teamRepository = teamRepository;
    }

    @GetMapping()
    public String index(Model model) {
        model.addAttribute("teams", teamRepository.findAll());
        model.addAttribute("tournaments", tournamentRepository.findAll());
        return "/team/index";
    }

    @GetMapping("/new")
    public String showAddForm(Team team) {
        return "team/new";
    }

    @GetMapping("/edit/{id}")
    public String showUpdateForm(@PathVariable("id") long id, Model model) {
        Team team = teamRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid strawberry Id: " + id));
        model.addAttribute("team", team);
        return "team/edit";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id") long id, Model model) {
        try {
            Team team = teamRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid strawberry Id: " + id));
            teamRepository.delete(team);
        }
        catch (MethodArgumentTypeMismatchException e) {
            return "redirect:/team";

        }
        model.addAttribute("teams", teamRepository.findAll());
        return "redirect:/team";
    }

    @PostMapping("/addteam")
    public String addPerson(@Valid Team team, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "team/new";
        }
        teamRepository.save(team);
        model.addAttribute("teams", teamRepository.findAll());
        return "redirect:/team";
    }

    @PostMapping("/{id}")
    public String update(@PathVariable("id") long id, @Valid Team team, BindingResult result, Model model) {
        if (result.hasErrors()) {
            team.setId(id);
            return "team/edit";
        }

        Team existingTeam = teamRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Недопустимый идентификатор команды: " + id));

        existingTeam.setName(team.getName());

        teamRepository.save(existingTeam);

        model.addAttribute("teams", teamRepository.findAll());
        return "team/index";
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
        return "team/index";
    }


    @GetMapping("/new2")
    public String showAddForm2(Model model) {
        model.addAttribute("team", new Team());
        model.addAttribute("tournament", new Tournament());
        model.addAttribute("teams", teamRepository.findAll());
        model.addAttribute("tournaments", tournamentRepository.findAll());
        return "team/newTeamTournament";
    }
        @PostMapping("/addteamTournament")
    public String addteamTournament(@RequestParam Long team, @RequestParam Long tournament,  Model model) {

            Team foundTeam = teamRepository.findById(team).orElseThrow();
            Tournament foundTournament = tournamentRepository.findById(tournament).orElseThrow();
            foundTeam.getTournaments().add(foundTournament);
            teamRepository.save(foundTeam);

        return "redirect:/team";
    }

}
