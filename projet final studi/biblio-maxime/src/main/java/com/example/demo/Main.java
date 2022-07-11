package com.example.demo;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.*;
import org.springframework.ui.Model;

@Controller
@SpringBootApplication
public class Main {

    public static void main(String[] args) throws Exception {
        SpringApplication.run(Main.class, args);
    }
    
    /** 
     * Booléen session active ou non
     * @param request
     * @return String
     */
    private boolean isConnected(HttpServletRequest request) {
        HttpSession session = request.getSession();
        if (session.getAttribute("id") == null) {
            return false;
        }
        return true;
    }

    
    /** 
     * Page d'accueil du site
     * @param request
     * @return String
     */
    @RequestMapping("/")
    String home(HttpServletRequest request, Model model) {
        model.addAttribute("connected", isConnected(request));
        return "index";
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
    String browse(HttpServletRequest request, HttpServletResponse response, Model model) throws ServletException, IOException {
        model.addAttribute("connected", isConnected(request));
        String title = request.getParameter("title");
        String author = request.getParameter("author");
        String genre = request.getParameter("genre");
        String language = request.getParameter("language");
        ArrayList<Livre> livres = DB.getLivres(title, author, genre, language);
        model.addAttribute("books", livres);
        return "browse";
    }

    
    /** 
     * Formulaire de connexion
     * @param request
     * @return String
     */
    @RequestMapping("/login")
    String login(HttpServletRequest request, Model model) {
        model.addAttribute("connected", isConnected(request));
        return "login";
    }

    
    /** 
     * Formulaire de création de compte
     * @param request
     * @return String
     */
    @RequestMapping("/register")
    String register(HttpServletRequest request, Model model) {
        model.addAttribute("connected", isConnected(request));
        return "register";
    }

    
    /** 
     * Page répertoriant les prets en cours de l'utilisateur
     * @param request
     * @return String
     */
    @RequestMapping("/prets")
    String prets(HttpServletRequest request, Model model) {
        model.addAttribute("connected", isConnected(request));
        ArrayList<Pret> prets = DB.getPrets((int)request.getSession().getAttribute("id"));
        model.addAttribute("prets", prets);
        return "prets";
    }

    
    /** 
     * Page d'informations de l'utilisateur
     * @param request
     * @return String
     */
    @RequestMapping("/infos")
    String infos(HttpServletRequest request, Model model) {
        model.addAttribute("connected", isConnected(request));
        String[] infos = DB.getUserInfos((int)request.getSession().getAttribute("id"));
        model.addAttribute("name", infos[0]);
        model.addAttribute("firstname", infos[1]);
        model.addAttribute("mail", infos[2]);
        return "infos";
    }

    
    /** 
     * Formulaire de modification des informations de l'utilisateur
     * @param request
     * @return String
     */
    @RequestMapping("/EditInfos")
    String editInfos(HttpServletRequest request, Model model) {
        model.addAttribute("connected", isConnected(request));
        String[] infos = DB.getUserInfos((int)request.getSession().getAttribute("id"));
        model.addAttribute("name", infos[0]);
        model.addAttribute("firstname", infos[1]);
        model.addAttribute("mail", infos[2]);
        return "editInfos";
    }

    @RequestMapping("/stats")
    String stats(HttpServletRequest request, Model model) {
        model.addAttribute("connected", isConnected(request));
        model.addAttribute("nb_users", DB.getNbUsers());
        model.addAttribute("nb_prets", DB.getNbPrets());
        return "stats";
    }

    @RequestMapping("/mailBatch")
    String mailBatch(HttpServletRequest request, Model model) {
        model.addAttribute("connected", isConnected(request));
        DB.mailBatch();
        return "mailBatch";
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
}