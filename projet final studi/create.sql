CREATE TABLE IF NOT EXISTS genre (
    nom VARCHAR(20) NOT NULL PRIMARY KEY
);

CREATE TABLE IF NOT EXISTS auteur (
    id SERIAL PRIMARY KEY,
    nom VARCHAR(100) NOT NULL,
    prenom VARCHAR(100)
);

CREATE TABLE IF NOT EXISTS editeur (
    id SERIAL PRIMARY KEY,
    nom VARCHAR(100) NOT NULL
);

CREATE TABLE IF NOT EXISTS langue (
    nom VARCHAR(50) NOT NULL PRIMARY KEY
);

CREATE TABLE IF NOT EXISTS utilisateur (
    id SERIAL PRIMARY KEY,
    email VARCHAR(100) NOT NULL UNIQUE,
    nom VARCHAR(100) NOT NULL,
    prenom VARCHAR(100),
    mdp VARCHAR(100) NOT NULL,
    sel VARCHAR(20) NOT NULL
);

CREATE TABLE IF NOT EXISTS bibliotheque (
    nom VARCHAR(50) NOT NULL PRIMARY KEY
);

CREATE TABLE IF NOT EXISTS description (
    id SERIAL PRIMARY KEY,
    titre VARCHAR(100)
);

CREATE TABLE IF NOT EXISTS paragraphe (
    description_id INTEGER NOT NULL,
    ordre INTEGER NOT NULL,
    texte VARCHAR NOT NULL,
    FOREIGN KEY(description_id) REFERENCES description(id),
    primary key(description_id, ordre)
);

CREATE TABLE IF NOT EXISTS livre (
    isbn VARCHAR(20) NOT NULL PRIMARY KEY,
    titre VARCHAR(100) NOT NULL,
    editeur_id INTEGER NOT NULL,
    date_publication DATE NOT NULL,
    description_id INTEGER NOT NULL,
    langue VARCHAR(50) NOT NULL,
    FOREIGN KEY(editeur_id) REFERENCES editeur(id),
    FOREIGN KEY(description_id) REFERENCES description(id),
    FOREIGN KEY(langue) REFERENCES langue(nom)
);

CREATE TABLE IF NOT EXISTS exemplaire (
    id SERIAL PRIMARY KEY,
    isbn VARCHAR(20) NOT NULL,
    bibliotheque VARCHAR(50) NOT NULL,
    FOREIGN KEY(isbn) REFERENCES livre(isbn),
    FOREIGN KEY(bibliotheque) REFERENCES bibliotheque(nom)
);

CREATE TABLE IF NOT EXISTS pret (
    id SERIAL PRIMARY KEY,
    date_debut DATE NOT NULL,
    date_fin DATE NOT NULL,
    renouvele BOOLEAN NOT NULL,
    utilisateur_id INTEGER NOT NULL,
    exemplaire_id INTEGER NOT NULL,
    FOREIGN KEY(utilisateur_id) REFERENCES utilisateur(id),
    FOREIGN KEY(exemplaire_id) REFERENCES exemplaire(id)
);

CREATE TABLE IF NOT EXISTS livre_genre (
    isbn VARCHAR(20) NOT NULL,
    nom VARCHAR(20) NOT NULL,
    FOREIGN KEY(nom) REFERENCES genre(nom),
    FOREIGN KEY(isbn) REFERENCES livre(isbn)
);

CREATE TABLE IF NOT EXISTS livre_auteur (
    isbn VARCHAR(20) NOT NULL,
    auteur_id INTEGER NOT NULL,
    FOREIGN KEY(auteur_id) REFERENCES auteur(id),
    FOREIGN KEY(isbn) REFERENCES livre(isbn)
);