package com.example.prs.Controller;
import com.example.prs.models.*;
import com.example.prs.repositories.TeamRepository;
import com.example.prs.repositories.TournamentRepository;
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
@RequestMapping("/tournament")
public class TournamentController {
    @Autowired
private TournamentRepository tournamentRepository;
    @Autowired
    private TeamRepository teamRepository;

    @Autowired
    public TournamentController(TournamentRepository tournamentRepository) {
        this.tournamentRepository = tournamentRepository;
    }
    @GetMapping()
    public String index(Model model) {
        model.addAttribute("tournaments", tournamentRepository.findAll());
        return "/tournament/index";
    }
    @GetMapping("/new")
    public String showAddForm(Tournament tournament) {
        return "tournament/new";
    }

    @GetMapping("/edit/{id}")
    public String showUpdateForm(@PathVariable("id") long id, Model model) {
        Tournament  tournament = tournamentRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid strawberry Id: " + id));
        model.addAttribute("tournament", tournament);
        return "tournament/edit";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id") long id, Model model) {
        try {
            Tournament tournament = tournamentRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid strawberry Id: " + id));
            tournamentRepository.delete(tournament);
        }
        catch (MethodArgumentTypeMismatchException e) {
            return "redirect:/tournament";

        }
        model.addAttribute("tournaments", tournamentRepository.findAll());
        return "redirect:/tournament";
    }

    @PostMapping("/addtournament")
    public String addPerson(@Valid Tournament tournament, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "tournament/new";
        }
        tournamentRepository.save(tournament);
        model.addAttribute("tournaments", tournamentRepository.findAll());
        return "redirect:/tournament";
    }

    @PostMapping("/{id}")
    public String update(@PathVariable("id") long id, @Valid Tournament tournament, BindingResult result, Model model) {
        if (result.hasErrors()) {
            tournament.setId(id);
            return "tournament/edit";
        }
        tournamentRepository.save(tournament);
        model.addAttribute("tournaments", tournamentRepository.findAll());
        return "tournament/index";
    }

    @GetMapping("/search")
    public String search(@RequestParam("name") String name, Model model) {
        List<Tournament> tournaments = tournamentRepository.findAll();
        List<Tournament> sortTournaments = new ArrayList<>();
        for (Tournament tournament:
                tournaments) {
            if(tournament.getName().toLowerCase().contains(name.toLowerCase()))
            {
                sortTournaments.add(tournament);
            }
        }
        model.addAttribute("tournaments", sortTournaments);
        return "tournament/index";
    }
}
