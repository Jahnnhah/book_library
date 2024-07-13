package com.springmvc.booklibrary.controller;

import com.springmvc.booklibrary.dao.JdbcService;
import com.springmvc.booklibrary.modelsAffichage.LivrePlusEmprunte;
import com.springmvc.booklibrary.modelsAffichage.MembrePlusActif;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;


@Controller
@RequestMapping("/book-library")
public class BookLibraryController {

    @GetMapping
    public String accueilView(HttpSession session, Model model) throws SQLException {
        String idAdmin = (String) session.getAttribute("id");
        if (idAdmin == null) {
            return "redirect:/";
        }

        Connection con = JdbcService.getConnection();

        LivrePlusEmprunte livre = new LivrePlusEmprunte();
        List livres = livre.findAll(con);
        if (livres.size() > 0) {
            livre = (LivrePlusEmprunte) livres.get(0);
        }
        model.addAttribute("livre", livre);
        model.addAttribute("livres", livres);

        MembrePlusActif membre = new MembrePlusActif();
        List membres = membre.findAll(con);
        if (membres.size() > 0) {
            membre = (MembrePlusActif) membres.get(0);
        }
        model.addAttribute("membre", membre);

        Long empruntEnCour = (Long) JdbcService.query(con, "select * from v_emprunt_en_cours");
        model.addAttribute("empruntEnCour", empruntEnCour);

        model.addAttribute("id", idAdmin);

        con.close();
        return "pages/accueil";
    }



}

// TODO: ajouter emprunt -> exemplaire devient non disponible -> ajouter emprunt dans table emprunt
