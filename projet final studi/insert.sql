INSERT INTO genre ( nom ) values ('Action'), ('Aventure'), ('Fantastique'), ('Horreur'), ('Policier'), ('Romance'), ('Science-fiction');
INSERT INTO auteur values (DEFAULT, 'VERNE', 'Jules'), (DEFAULT, 'JK Rowling', NULL), (DEFAULT, 'JRR Tolkien', NULL), (DEFAULT, 'CHRISTIE', 'Agatha');
INSERT INTO editeur values (DEFAULT, 'Pierre-Jules Hetzel'), (DEFAULT, 'Bloomsbury'), (DEFAULT, 'Allen & Unwin'), (DEFAULT, 'Collins Crime Club');
INSERT INTO langue values ('Français'), ('Anglais'), ('Italien'), ('Espagnol'), ('Japonais');
INSERT INTO bibliotheque values ('Pontault-Combault');
INSERT INTO description values (DEFAULT, NULL), (DEFAULT, NULL), (DEFAULT, NULL), (DEFAULT, NULL);
INSERT INTO paragraphe values 
    (1, 1, 'A la fin de la guerre fédérale des états-Unis, les fanatiques artilleurs du Gun-Club (Club-Canon) de Baltimore sont bien désoeuvrés. Un beau jour, le président, Impey Barbicane, leur fait une proposition qui, le premier moment de stupeur passé, est accueillie avec un enthousiasme délirant. Il s''agit de se mettre en communication avec la Lune en lui envoyant un boulet, un énorme projectile qui serait lancé par un gigantesque canon ! Tandis que ce projet inouï est en voie d''exécution, un Parisien, Michel Ardan, un de ces originaux que le Créateur invente dans un moment de fantaisie, et dont il brise aussitôt le moule, télégraphie à Barbicane : « Remplacez obus sphérique par projectile cylindroconique. Partirai dedans »... Avec ses personnages parfaitement campés, son humour toujours présent, De la Terre à la Lune est une des grandes oeuvres de Jules Verne, une de ses plus audacieuses anticipations.'),
    (2, 1, 'Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur.'),
    (3, 1, 'Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur.'),
    (4, 1, 'Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur.');
INSERT INTO livre values 
    ('9782253006312', 'De la Terre à la Lune', 1, TO_DATE('14-10-1865', 'DD-MM-YYYY'), 1, 'Français'),
    ('6875411257780', 'Harry Potter et la Chambre des Secrets', 2, TO_DATE('06-07-1997', 'DD-MM-YYYY'), 2, 'Anglais'),
    ('2400156987752', 'Le Seigneur des Anneaux', 3, TO_DATE('29-07-1954', 'DD-MM-YYYY'), 3, 'Anglais'),
    ('7885023469875', 'Le Crime de l''Orient Express', 4, TO_DATE('01-01-1934', 'DD-MM-YYYY'), 4, 'Anglais');
INSERT INTO exemplaire values 
    (DEFAULT, '9782253006312', 'Pontault-Combault'),
    (DEFAULT, '6875411257780', 'Pontault-Combault'),
    (DEFAULT, '2400156987752', 'Pontault-Combault'),
    (DEFAULT, '7885023469875', 'Pontault-Combault');
INSERT INTO livre_genre values
    ('9782253006312', 'Science-fiction'),
    ('6875411257780', 'Fantastique'),
    ('2400156987752', 'Fantastique'),
    ('7885023469875', 'Policier');
INSERT INTO livre_auteur values
    ('9782253006312', 1),
    ('6875411257780', 2),
    ('2400156987752', 3),
    ('7885023469875', 4);
INSERT INTO pret values
    (DEFAULT, TO_DATE('25-04-2022', 'DD-MM-YYYY'), TO_DATE('13-05-2022', 'DD-MM-YYYY'), false, 6, 4),
    (DEFAULT, TO_DATE('25-04-2022', 'DD-MM-YYYY'), TO_DATE('13-05-2022', 'DD-MM-YYYY'), false, 6, 5);