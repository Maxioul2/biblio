package com.example.demo;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.*;

@Controller
@SpringBootApplication
public class Main {

    
    /** 
     * Header de chaque page du site
     * @param request
     * @return String
     */
    private String header(HttpServletRequest request) {
        String result = "<head><title>Biblio</title><meta charset=\"UTF-8\"><link rel=\"stylesheet\" href=\"style.css\"></head><body>";
        result += "<div class=\"header\"><h2>Biblio</h2>";
        result += "<button onclick=\"window.location.href='/browse'\">Parcourir la bibliothèque</button>";
        HttpSession session = request.getSession();
        if (session.getAttribute("id") == null) {
            result += "<button onclick=\"window.location.href='/login'\">Se connecter</button>";
            result += "<button onclick=\"window.location.href='/register'\">Créer un compte</button>";
        } else {
            result += "<button onclick=\"window.location.href='/prets'\">Mes prêts</button>";
            result += "<button onclick=\"window.location.href='/infos'\">Informations sur le compte</button>";
            result += "<button onclick=\"window.location.href='/logout'\">Se déconnecter</button>";
        }
        result += "</div>";
        return result;
    }

    
    /** 
     * Page d'accueil du site
     * @param request
     * @return String
     */
    @RequestMapping("/")
    @ResponseBody
    String home(HttpServletRequest request) {
        String result = header(request);
        result += "<div class=\"content\"><h3>Bienvenue sur \"Biblio\", le site de la bibliothèque de \"Pontault-Combault\"</h3>";
        result += "</body>";
        return result;
    }

    
    /** 
     * Page de recherche de livres
     * @param request
     * @param response
     * @return String
     * @throws ServletException
     * @throws IOException
     */
    @RequestMapping("/browse")
    @ResponseBody
    String browse(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String result = header(request);
        result += "<div class=\"searchFilters\">";
        result += "<h3>Recherche : </h3>";
        result += "<form action=\"/browse\" method=\"post\">";
        result += "<div class=\"searchFilter\">";
        result += "<label for=\"title\">Titre : </label>";
        result += "<input type=\"text\" name=\"title\" placeholder=\"Titre\"/>";
        result += "</div>";
        result += "<div class=\"searchFilter\">";
        result += "<label for=\"author\">Auteur : </label>";
        result += "<input type=\"text\" name=\"author\" placeholder=\"Auteur\"/>";
        result += "</div>";
        result += "<div class=\"searchFilter\">";
        result += "<label for=\"genre\">Genre : </label>";
        result += "<select name=\"genre\">";
        result += "<option value=\"\">Genre</option>";
        result += "<option value=\"Action\">Action</option>";
        result += "<option value=\"Aventure\">Aventure</option>";
        result += "<option value=\"Fantastique\">Fantastique</option>";
        result += "<option value=\"Horreur\">Horreur</option>";
        result += "<option value=\"Policier\">Policier</option>";
        result += "<option value=\"Romance\">Romance</option>";
        result += "<option value=\"Science-Fiction\">Science-Fiction</option>";
        result += "</select>";
        result += "</div>";
        result += "<div class=\"searchFilter\">";
        result += "<label for=\"language\">Langue : </label>";
        result += "<select name=\"language\">";
        result += "<option value=\"\">Langue</option>";
        result += "<option value=\"Français\">Français</option>";
        result += "<option value=\"Anglais\">Anglais</option>";
        result += "<option value=\"Espagnol\">Espagnol</option>";
        result += "<option value=\"Italien\">Italien</option>";
        result += "<option value=\"Japonais\">Japonais</option>";
        result += "</select>";
        result += "</div>";
        result += "<input type=\"submit\" name=\"submit\" value=\"Lancer la recherche\"/>";
        result += "</form>";
        result += "</div>";
        result += "<div class=\"content\">";
        result += "<h3>Résultats : </h3>";
        String title = request.getParameter("title");
        String author = request.getParameter("author");
        String genre = request.getParameter("genre");
        String language = request.getParameter("language");
        ArrayList<Livre> livres = DB.getLivres(title, author, genre, language);
        if (livres.size() == 0) {
            result += "<p>Aucun résultat</p>";
        } else {
            for (Livre livre : livres) {
                try {
                    result += "<div class=\"book\">";
                    result += "<hr>";
                    result += "<h4>" + livre.getTitre() + "</h4>";
                    result += "<p>ISBN : " + livre.getIsbn() + "<br>";
                    result += "Auteur : " + livre.getAuteur() + "<br>";
                    result += "Genre : " + livre.getGenre() + "<br>";
                    result += "Date de publication : " + livre.getDatePublication() + "<br>";
                    result += "Éditeur : " + livre.getEditeur() + "</p>";
                } catch (NullPointerException e) {
                    result += "<p>Aucun résultat</p>";
                }
            }
        }
        result += "</body>";
        return result;
    }

    
    /** 
     * Formulaire de connexion
     * @param request
     * @return String
     */
    @RequestMapping("/login")
    @ResponseBody
    String login(HttpServletRequest request) {
        String result = header(request);
        result += "<form action=\"/loginServlet\" method=\"post\">";
        result += "<div class=\"formItem\"><label for=\"email\">E-mail : </label><input type=\"text\" name=\"email\" required></div>";
        result += "<div class=\"formItem\"><label for=\"password\">Mot de passe : </label><input type=\"password\" name=\"password\" required></div>";
        result += "<input type=\"submit\" value=\"Connexion\">";
        result += "</body>";
        return result;
    }

    
    /** 
     * Formulaire de création de compte
     * @param request
     * @return String
     */
    @RequestMapping("/register")
    @ResponseBody
    String register(HttpServletRequest request) {
        String result = header(request);
        result += "<form action=\"/registerServlet\" method=\"post\">";
        result += "<div class=\"formItem\"><label for=\"lastName\">Nom : </label><input type=\"text\" name=\"lastName\" required></div>";
        result += "<div class=\"formItem\"><label for=\"firstName\">Prénom : </label><input type=\"text\" name=\"firstName\" required></div>";
        result += "<div class=\"formItem\"><label for=\"email\">E-mail : </label><input type=\"text\" name=\"email\" required></div>";
        result += "<div class=\"formItem\"><label for=\"password\">Mot de passe : </label><input type=\"password\" name=\"password\" required></div>";
        result += "<div class=\"formItem\"><label for=\"passwordConfirmation\">Confirmation du mot de passe : </label><input type=\"password\" name=\"passwordConfirmation\" required></div>";
        result += "<input type=\"submit\" value=\"Valider la création du compte\">";
        result += "</body>";
        //DB.addUser("email", "lastName", "firstName", "password", "test");
        return result;
    }

    
    /** 
     * Page répertoriant les prets en cours de l'utilisateur
     * @param request
     * @return String
     */
    @RequestMapping("/prets")
    @ResponseBody
    String prets(HttpServletRequest request) {
        String result = header(request);
        ArrayList<Pret> prets = DB.getPrets((int)request.getSession().getAttribute("id"));
        if (prets.size() == 0) {
            result += "<p>Aucun résultat</p>";
        } else {
            for (Pret pret : prets) {
                try {
                    result += "<div class=\"pret\">";
                    result += "<hr>";
                    result += "<h4>" + pret.getLivre().getTitre() + "</h4>";
                    result += "<p>ISBN : " + pret.getLivre().getIsbn() + "<br>";
                    result += "Auteur : " + pret.getLivre().getAuteur() + "<br>";
                    result += "Genre : " + pret.getLivre().getGenre() + "<br>";
                    result += "Date de publication : " + pret.getLivre().getDatePublication() + "<br>";
                    result += "Éditeur : " + pret.getLivre().getEditeur() + "<br>";
                    result += "<b>Début du prêt : " + pret.getDateDebut() + "</b><br>";
                    result += "<b>Fin du prêt : " + pret.getDateFin() + "</b><br>";
                    if (pret.isRenouvele()) {
                        result += "<b>Prêt renouvelé</b></p>";
                    } else {
                        result += "<b>Prêt non renouvelé</b></p>";
                        result += "<form action=\"/renouvelerPretServlet\" method=\"post\">";
                        result += "<input type=\"hidden\" name=\"userId\" value=\"" + pret.getUserId() + "\"/>";
                        result += "<input type=\"hidden\" name=\"isbn\" value=\"" + pret.getLivre().getIsbn() + "\"/>";
                        result += "<input type=\"submit\" value=\"Renouveler le prêt\">";
                        result += "</form>";
                    }
                } catch (NullPointerException e) {
                    result += "<p>Aucun résultat</p>";
                }
            }
        }
        result += "</body>";
        return result;
    }

    
    /** 
     * Page d'informations de l'utilisateur
     * @param request
     * @return String
     */
    @RequestMapping("/infos")
    @ResponseBody
    String infos(HttpServletRequest request) {
        String result = header(request);
        String[] infos = DB.getUserInfos((int)request.getSession().getAttribute("id"));
        result += "<div class=\"infos\">";
        result += "<div class=\"infosItem\"><label>Nom : </label><span>" + infos[0] + "</span></div>";
        result += "<div class=\"infosItem\"><label>Prénom : </label><span>" + infos[1] + "</span></div>";
        result += "<div class=\"infosItem\"><label>E-mail : </label><span>" + infos[2] + "</span></div>";
        result += "</div>";
        result += "<button onclick=\"window.location.href='/EditInfos'\">Modifier les informations</button>";
        result += "</body>";
        return result;
    }

    
    /** 
     * Formulaire de modification des informations de l'utilisateur
     * @param request
     * @return String
     */
    @RequestMapping("/EditInfos")
    @ResponseBody
    String editInfos(HttpServletRequest request) {
        String result = header(request);
        String[] infos = DB.getUserInfos((int)request.getSession().getAttribute("id"));
        result += "<form action=\"/EditInfosServlet\" method=\"post\">";
        result += "<div class=\"formItem\"><label for=\"lastName\">Nom : </label><input type=\"text\" name=\"lastName\" value=\"" + infos[0] + "\" required></div>";
        result += "<div class=\"formItem\"><label for=\"firstName\">Prénom : </label><input type=\"text\" name=\"firstName\" value=\"" + infos[1] + "\" required></div>";
        result += "<div class=\"formItem\"><label for=\"email\">E-mail : </label><input type=\"text\" name=\"email\" value=\"" + infos[2] + "\" required></div>";
        result += "<div class=\"formItem\"><label for=\"password\">Nouveau mot de passe : </label><input type=\"password\" name=\"password\" required></div>";
        result += "<div class=\"formItem\"><label for=\"passwordConfirmation\">Confirmation du mot de passe : </label><input type=\"password\" name=\"passwordConfirmation\" required></div>";
        result += "<input type=\"submit\" value=\"Valider les modifications\">";
        result += "</body>";
        return result;
    }

    @RequestMapping("/mailBatch")
    @ResponseBody
    String mailBatch(HttpServletRequest request) {
        String result = header(request);
        DB.mailBatch();
        result += "<p>Mails envoyés</p>";
        return result;
    }

    
    /** 
     * Permet de se déconnecter
     * @param request
     * @param response
     * @throws IOException
     */
    @RequestMapping("/logout")
    @ResponseBody
    void logout(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession();
        session.invalidate();
        response.sendRedirect("/");
    }

    
    /** 
     * Permet de récupérer les informations du formulaire d'inscription
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    @RequestMapping("/registerServlet")
    void registerServlet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("registerServlet");

        String email = request.getParameter("email");
        String lastName = request.getParameter("lastName");
        String firstName = request.getParameter("firstName");
        String password = request.getParameter("password");
        String sel = "test";

        DB.addUser(email, lastName, firstName, password, sel);
        response.sendRedirect("/login");
    }

    
    /** 
     * Permet de se connecter via l'email et le mot de passe
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    @RequestMapping("/loginServlet")
    protected void loginServlet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        int id = DB.checkUser(email, password);
        if (id != -1) {
            HttpSession session = request.getSession();
            session.setAttribute("id", id);
            response.sendRedirect("/");
        } else {
            response.sendRedirect("/login");
        }
    }

    
    /** 
     * Permet d'éditer les informations d'un utilisateur
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    @RequestMapping("/EditInfosServlet")
    void editInfosServlet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String email = request.getParameter("email");
        String lastName = request.getParameter("lastName");
        String firstName = request.getParameter("firstName");
        String password = request.getParameter("password");
        int id = (int)request.getSession().getAttribute("id");

        DB.editUser(email, lastName, firstName, password, id);
        response.sendRedirect("/infos");
    }

    @RequestMapping("/renouvelerPretServlet")
    void renouvelerPretServlet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int userId = (int)request.getSession().getAttribute("id");
        String isbn = request.getParameter("isbn");
        DB.renouvelerPret(userId, isbn);
        response.sendRedirect("/prets");
    }

    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }
}