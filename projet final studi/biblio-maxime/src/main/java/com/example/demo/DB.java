package com.example.demo;

import java.sql.*;
import java.util.ArrayList;

public class DB {
    private static Connection connect() {
        Connection conn = null;
        try {
            Class.forName("org.postgresql.Driver");

            // connect way #1
            String url = "jdbc:postgresql://ec2-18-214-134-226.compute-1.amazonaws.com:5432/dchrg4f7v8smgf";
            String user = "ziqbimpzukmmdc";
            String password = "80e30057d9a5426da6550c47c342176b46a202d47f8244244772886aaa859254";

            conn = DriverManager.getConnection(url, user, password);
        } catch (SQLException ex) {
            System.out.println("SQLException: " + ex.getMessage());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return conn;
    }

    public static void addUser(String email, String lastName, String firstName, String password, String sel) {
        System.out.println("addUser");
        Connection conn = connect();
        if (conn != null) {
            String sql = "INSERT INTO utilisateur (email, nom, prenom, mdp, sel) VALUES (?, ?, ?, ?, ?)";
            try {
                PreparedStatement pstmt = conn.prepareStatement(sql);
                pstmt.setString(1, email);
                pstmt.setString(2, lastName);
                pstmt.setString(3, firstName);
                pstmt.setString(4, password);
                pstmt.setString(5, sel);
                pstmt.executeUpdate();
            } catch (SQLException ex) {
                System.out.println("SQLException: " + ex.getMessage());
            } finally {
                try {
                    conn.close();
                } catch (SQLException ex) {
                    System.out.println("SQLException: " + ex.getMessage());
                }
            }
        }
    }

    public static void editUser(String email, String lastName, String firstName) {
        System.out.println("editUser");
        Connection conn = connect();
        if (conn != null) {
            String sql = "UPDATE utilisateur SET nom = ?, prenom = ? WHERE email = ?";
            try {
                PreparedStatement pstmt = conn.prepareStatement(sql);
                pstmt.setString(1, lastName);
                pstmt.setString(2, firstName);
                pstmt.setString(3, email);
                pstmt.executeUpdate();
            } catch (SQLException ex) {
                System.out.println("SQLException: " + ex.getMessage());
            } finally {
                try {
                    conn.close();
                } catch (SQLException ex) {
                    System.out.println("SQLException: " + ex.getMessage());
                }
            }
        }
    }

    public static int checkUser(String email, String password) {
        Connection conn = connect();
        if (conn != null) {
            String sql = "SELECT * FROM utilisateur WHERE email = ? AND mdp = ?";
            try {
                PreparedStatement pstmt = conn.prepareStatement(sql);
                pstmt.setString(1, email);
                pstmt.setString(2, password);
                ResultSet rs = pstmt.executeQuery();
                if (rs.next()) {
                    return rs.getInt("id");
                }
            } catch (SQLException ex) {
                System.out.println("SQLException: " + ex.getMessage());
            } finally {
                try {
                    conn.close();
                } catch (SQLException ex) {
                    System.out.println("SQLException: " + ex.getMessage());
                }
            }
        }
        return -1;
    }

    public static String[] getUserInfos(int id) {
        String[] infos = new String[3];
        Connection conn = connect();
        if (conn != null) {
            String sql = "SELECT * FROM utilisateur WHERE id = ?";
            try {
                PreparedStatement pstmt = conn.prepareStatement(sql);
                pstmt.setInt(1, id);
                ResultSet rs = pstmt.executeQuery();
                if (rs.next()) {
                    infos[0] = rs.getString("nom");
                    infos[1] = rs.getString("prenom");
                    infos[2] = rs.getString("email");
                }
            } catch (SQLException ex) {
                System.out.println("SQLException: " + ex.getMessage());
            } finally {
                try {
                    conn.close();
                } catch (SQLException ex) {
                    System.out.println("SQLException: " + ex.getMessage());
                }
            }
        }
        return infos;
    }

    public static ArrayList<Livre> getLivres(String title, String author, String genre, String language) {
        ArrayList<Livre> livres = new ArrayList<>();
        Connection conn = connect();
        if (conn != null) {
            String sql = "SELECT livre.isbn, livre.titre, auteur.nom AS auteur, editeur.nom AS editeur, livre.date_publication, livre.langue, genre.nom AS genre FROM livre "
            + "LEFT JOIN editeur ON livre.editeur_id = editeur.id " 
            + "LEFT JOIN livre_genre ON livre.isbn = livre_genre.isbn "
            + "LEFT JOIN genre ON livre_genre.nom = genre.nom "
            + "LEFT JOIN livre_auteur ON livre_auteur.isbn = livre.isbn "
            + "LEFT JOIN auteur ON livre_auteur.auteur_id = auteur.id "
            + "WHERE 1 = 1 ";
            if (title != null && !title.isEmpty()) {
                sql += " AND titre LIKE '%" + title + "%' ";
            }
            if (author != null && !author.isEmpty()) {
                sql += " AND auteur.nom LIKE '%" + author + "%' ";
            }
            if (genre != null && !genre.isEmpty()) {
                sql += " AND genre.nom LIKE '%" + genre + "%' ";
            }
            if (language != null && !language.isEmpty()) {
                sql += " AND langue LIKE '%" + language + "%' ";
            }
            try {
                PreparedStatement pstmt = conn.prepareStatement(sql);
                ResultSet rs = pstmt.executeQuery();
                while (rs.next()) {
                    livres.add(new Livre(rs.getString("isbn"), rs.getString("titre"), rs.getString("auteur"), rs.getString("editeur"), rs.getString("date_publication"), rs.getString("langue"), rs.getString("genre")));
                }
            } catch (SQLException ex) {
                System.out.println("SQLException: " + ex.getMessage() + "\nquery: " + sql);
            } finally {
                try {
                    conn.close();
                } catch (SQLException ex) {
                    System.out.println("SQLException: " + ex.getMessage());
                }
            }
        }
        return livres;
    }

    public static Livre getLivre(String isbn) {
        Livre livre = null;
        Connection conn = connect();
        if (conn != null) {
            String sql = "SELECT livre.isbn, livre.titre, auteur.nom AS auteur, editeur.nom AS editeur, livre.date_publication, livre.langue, genre.nom AS genre FROM livre "
            + "LEFT JOIN editeur ON livre.editeur_id = editeur.id "
            + "LEFT JOIN livre_genre ON livre.isbn = livre_genre.isbn "
            + "LEFT JOIN genre ON livre_genre.nom = genre.nom "
            + "LEFT JOIN livre_auteur ON livre_auteur.isbn = livre.isbn "
            + "LEFT JOIN auteur ON livre_auteur.auteur_id = auteur.id "
            + "WHERE livre.isbn = ?";
            try {
                PreparedStatement pstmt = conn.prepareStatement(sql);
                pstmt.setString(1, isbn);
                ResultSet rs = pstmt.executeQuery();
                if (rs.next()) {
                    livre = new Livre(rs.getString("isbn"), rs.getString("titre"), rs.getString("auteur"), rs.getString("editeur"), rs.getString("date_publication"), rs.getString("langue"), rs.getString("genre"));
                }
            } catch (SQLException ex) {
                System.out.println("SQLException: " + ex.getMessage());
            } finally {
                try {
                    conn.close();
                } catch (SQLException ex) {
                    System.out.println("SQLException: " + ex.getMessage());
                }
            }
        }
        return livre;
    }

    public static ArrayList<Pret> getPrets(int user_id) {
        ArrayList<Pret> prets = new ArrayList<>();
        Connection conn = connect();
        if (conn != null) {
            String sql = "SELECT pret.utilisateur_id AS user_id, exemplaire.isbn AS isbn, pret.date_debut, pret.date_fin, pret.renouvele FROM pret "
            + "LEFT JOIN exemplaire ON pret.exemplaire_id = exemplaire.id "
            + "WHERE pret.utilisateur_id = ?";
            try {
                PreparedStatement pstmt = conn.prepareStatement(sql);
                pstmt.setInt(1, user_id);
                ResultSet rs = pstmt.executeQuery();
                while (rs.next()) {
                    Livre livre = getLivre(rs.getString("isbn"));
                    prets.add(new Pret(rs.getInt("user_id"), livre, rs.getString("date_debut"), rs.getString("date_fin"), rs.getBoolean("renouvele")));
                }
            } catch (SQLException ex) {
                System.out.println("SQLException: " + ex.getMessage() + "\nquery: " + sql);
            } finally {
                try {
                    conn.close();
                } catch (SQLException ex) {
                    System.out.println("SQLException: " + ex.getMessage());
                }
            }
        }
        return prets;
    }
}
