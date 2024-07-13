package com.springmvc.booklibrary.controller;

import com.springmvc.booklibrary.dao.JdbcService;
import com.springmvc.booklibrary.models.*;
import com.springmvc.booklibrary.modelsAffichage.ExemplaireUsage;
import com.springmvc.booklibrary.modelsAffichage.LivreAffichage;
import com.springmvc.booklibrary.modelsAffichage.RegleEmpruntAffichage;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

@Controller
@RequestMapping("/book-library/livre")
public class LivreController {

    @GetMapping
    public String livreView(@ModelAttribute LivreSearch search_livre, Model model, HttpSession session) throws SQLException, IllegalAccessException {
        String idAdmin = (String) session.getAttribute("id");
        if (idAdmin == null) {
            return "redirect:/";
        }

        Connection con = JdbcService.getConnection();

        if (search_livre.isEmpty()) {
            System.out.println("not searching");
            LivreAffichage livre = new LivreAffichage();
            List<Object> livres = livre.findAll(con);
            model.addAttribute("livres", livres);
        } else {
            System.out.println("searching");
            LivreAffichage[] livres = search_livre.search(con);
            model.addAttribute("livres", livres);
        }

        Auteur auteur = new Auteur();
        List<Object> auteurs = auteur.findAll(con);
        model.addAttribute("auteurs", auteurs);

        Editeur editeur = new Editeur();
        List<Object> editeurs = editeur.findAll(con);
        model.addAttribute("editeurs", editeurs);

        Collection collection = new Collection();
        List<Object> collections = collection.findAll(con);
        model.addAttribute("collections", collections);

        Langue langue = new Langue();
        List<Object> langues = langue.findAll(con);
        model.addAttribute("langues", langues);

        con.close();

        return "pages/livre";
    }

    @PostMapping("/save")
    public String livreCreate(Livre input_livre) throws SQLException {
        Connection con = JdbcService.getConnection();

        input_livre.save(con);

        con.close();

        return "redirect:/book-library/livre";
    }

    @GetMapping("/{id}")
    public String livreDetail(@PathVariable("id") String id, Model model, HttpSession session) throws SQLException {
        String idAdmin = (String) session.getAttribute("id");
        if (idAdmin == null) {
            return "redirect:/";
        }

        Connection con = JdbcService.getConnection();

        ExemplaireUsage exemplaire = new ExemplaireUsage();
        exemplaire.setLivre(id);
        List exemplaires = exemplaire.find(con);
        model.addAttribute("exemplaires", exemplaires);

        RegleEmpruntAffichage regleEmpruntAffichage = new RegleEmpruntAffichage();
        regleEmpruntAffichage.setLivre(id);
        List<Object> regleEmprunts = regleEmpruntAffichage.find(con);
        model.addAttribute("regleEmprunts", regleEmprunts);

        Livre livre = new Livre();
        livre.setId(id);
        livre = (Livre) livre.get(con);
        model.addAttribute("livre", livre);

        con.close();

        return "pages/livre-detail";
    }

    @GetMapping("/{id}/delete")
    public String livreDeleteView(@PathVariable("id") String id, Model model, HttpSession session) throws SQLException {
        String idAdmin = (String) session.getAttribute("id");
        if (idAdmin == null) {
            return "redirect:/";
        }

        Connection con = JdbcService.getConnection();

        Livre livre = new Livre();
        livre.setId(id);
        livre = (Livre) livre.get(con);

        model.addAttribute("livre", livre);

        con.close();

        return "pages/livre-delete";
    }
    @PostMapping("/{id}/delete")
    public String livreDeleteView(@PathVariable("id") String id) throws SQLException {
        Connection con = JdbcService.getConnection();

        Livre livre = new Livre();
        livre.setId(id);
        livre.delete(con);

        con.close();

        return "redirect:/book-library/livre";
    }

    @GetMapping("/{id}/update")
    public String livreUpdateView(@PathVariable("id") String id, Model model, HttpSession session) throws SQLException {
        String idAdmin = (String) session.getAttribute("id");
        if (idAdmin == null) {
            return "redirect:/";
        }

        Connection con = JdbcService.getConnection();

        Livre livre = new Livre();
        livre.setId(id);
        livre = (Livre) livre.get(con);
        model.addAttribute("livre", livre);

        Auteur auteur = new Auteur();
        List<Object> auteurs = auteur.findAll(con);
        model.addAttribute("auteurs", auteurs);

        Editeur editeur = new Editeur();
        List<Object> editeurs = editeur.findAll(con);
        model.addAttribute("editeurs", editeurs);

        Collection collection = new Collection();
        List<Object> collections = collection.findAll(con);
        model.addAttribute("collections", collections);

        Langue langue = new Langue();
        List<Object> langues = langue.findAll(con);
        model.addAttribute("langues", langues);

        con.close();

        return "pages/livre-update";
    }

    @GetMapping("/search")
    @ResponseBody
    public Livre[] livreSearch(@RequestParam("titre") String titre, @RequestParam("auteur") String auteur) throws SQLException {
        Connection con = JdbcService.getConnection();

        LivreSearch livreSearch = new LivreSearch();
        livreSearch.setTitre(titre);
        livreSearch.setAuteur(auteur);

        Livre[] livres = livreSearch.search(con);
        con.close();
        return livres;
    }

}
