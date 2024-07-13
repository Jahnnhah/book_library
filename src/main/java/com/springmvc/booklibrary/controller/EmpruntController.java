package com.springmvc.booklibrary.controller;

import com.springmvc.booklibrary.dao.JdbcService;
import com.springmvc.booklibrary.exceptions.EmpruntException;
import com.springmvc.booklibrary.models.*;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

@Controller
@RequestMapping("/book-library/emprunt")
public class EmpruntController {

    @GetMapping
    public String empruntView(Model model, HttpSession session) {
        String idAdmin = (String) session.getAttribute("id");
        if (idAdmin == null) {
            return "redirect:/";
        }

        Connection con = JdbcService.getConnection();

        Livre livre = new Livre();
        List<Object> livres = livre.findAll(con);
        model.addAttribute("livres", livres);

        Membre membre = new Membre();
        List<Object> membres = membre.findAll(con);
        model.addAttribute("membres", membres);

        Emprunt emprunt = new Emprunt();
        List<Object> emprunts = emprunt.findAll(con);
        model.addAttribute("emprunts", emprunts);

        return "pages/emprunt";
    }

    @PostMapping("/save")
    public String empruntSave(Emprunt emprunt, @RequestParam(name = "livre", required = false) String idLivre, @RequestParam(name = "emmenerMaison", required = false) String emmenerMaison) throws SQLException {
        Connection con = JdbcService.getConnection();

        Livre livre = new Livre();
        livre.setId(idLivre);

        Membre membre = new Membre();
        membre.setId(emprunt.getMembre());
        membre = (Membre) membre.get(con);

        // mi enregistre date rendu de livre
        if (emprunt.getId() != null) {
            try {
                membre.rendre(con, emprunt);
                return "redirect:/book-library/emprunt?message2=rendu enregistre";
            } catch (EmpruntException e) {
                return "redirect:/book-library/emprunt?message2=" + e.getMessage();
            } finally {
                con.close();
            }
        }

        try {
            String message = membre.emprunter(con, livre, emprunt, (emmenerMaison != null));
            return "redirect:/book-library/emprunt?message1=" + message;
        } catch (EmpruntException e) {
            return "redirect:/book-library/emprunt?message1=" + e.getMessage();
        } finally {
            con.close();
        }

    }

}

// TODO : update exemplaire to not disponible (the function is already there)
