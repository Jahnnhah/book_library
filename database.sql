create database book_library;

create sequence auteur_seq start with 1 increment by 1;

create table auteur
(
    id  varchar primary key,
    nom varchar(50) not null
);

create sequence collection_seq start with 1 increment by 1;

create table collection
(
    id          varchar primary key,
    designation varchar(50) not null
);

create sequence theme_seq start with 1 increment by 1;

create table theme
(
    id          varchar primary key,
    designation varchar(50) not null
);

create sequence categorie_seq start with 1 increment by 1;

create table categorie
(
    id          varchar primary key,
    designation varchar(50) not null
);

create sequence editeur_seq start with 1 increment by 1;

create table editeur
(
    id          varchar primary key,
    designation varchar(50) not null
);

create table langue
(
    code        varchar(3) primary key,
    designation varchar(50) not null
);

create sequence tpmembre_seq start with 1 increment by 1;

create table type_membre
(
    id           varchar primary key,
    designation  varchar(50) not null,
    coeff_retard int         not null
);

create sequence membre_seq start with 1 increment by 1;

create table membre
(
    id             varchar primary key,
    nom            varchar(50) not null,
    prenom         varchar(50) not null,
    email          varchar(50),
    date_naissance date        not null,
    type_membre    varchar     not null,
    foreign key (type_membre) references type_membre (id),
    password       varchar
);

create sequence livre_seq start with 1 increment by 1;

create table livre
(
    id           varchar primary key,
    titre        varchar        not null,
    auteur       varchar        not null,
    foreign key (auteur) references auteur (id),
    isbn         varchar unique not null,
    numero_cote  varchar unique not null,
    editeur      varchar        not null,
    foreign key (editeur) references editeur (id),
    date_edition date           not null,
    tome         int,
    collection   varchar,
    foreign key (collection) references collection (id),
    langue       varchar        not null,
    foreign key (langue) references langue (code),
    nombre_pages int            not null,
    resume       text
);

create table theme_livre
(
    livre varchar not null,
    foreign key (livre) references livre (id),
    theme varchar not null,
    foreign key (theme) references theme (id),
    primary key (livre, theme)
);

create sequence exemplaire_seq start with 1 increment by 1;

create table exemplaire
(
    id         varchar primary key,
    livre      varchar              not null,
    foreign key (livre) references livre (id),
    disponible boolean default true not null
);

create sequence emprunt_seq start with 1 increment by 1;

create table emprunt
(
    id            varchar primary key,
    exemplaire    varchar not null,
    foreign key (exemplaire) references exemplaire (id),
    membre        varchar not null,
    foreign key (membre) references membre (id),
    date_emprunt  date    not null,
    date_echeance date    not null,
    date_rendu    date
);

create sequence rglemprunt_seq start with 1 increment by 1;

create table regle_emprunt
(
    id                  varchar primary key,
    livre               varchar not null,
    foreign key (livre) references livre (id),
    type_membre         varchar not null,
    foreign key (type_membre) references type_membre (id),
    peut_emprunter      boolean not null,
    peut_emmener_maison boolean not null,
    limite_age          int     not null,
    limite_retard       int     not null
);

create sequence admin_seq start with 1 increment by 1;

create table admin
(
    id       varchar primary key,
    email    varchar not null,
    password varchar not null
);

create table penalite
(
    id varchar primary key,
    membre varchar not null,
    foreign key (membre) references membre (id),
    date_debut date not null,
    date_fin date not null
);

create sequence penalite_seq start with 1 increment by 1;

-- insert ADMIN
INSERT INTO admin (id, email, password)
VALUES (CONCAT('ADM', LPAD(CAST(NEXTVAL('admin_seq') AS TEXT), 3, '0')), 'admin@gmail.com', 'admin');

-- insert AUTEUR
INSERT INTO auteur (id, nom)
VALUES (CONCAT('AUT', LPAD(CAST(NEXTVAL('auteur_seq') AS TEXT), 3, '0')), 'J.K. Rowling');
INSERT INTO auteur (id, nom)
VALUES (CONCAT('AUT', LPAD(CAST(NEXTVAL('auteur_seq') AS TEXT), 3, '0')), 'George Orwell');
INSERT INTO auteur (id, nom)
VALUES (CONCAT('AUT', LPAD(CAST(NEXTVAL('auteur_seq') AS TEXT), 3, '0')), 'Jane Austen');
INSERT INTO auteur (id, nom)
VALUES (CONCAT('AUT', LPAD(CAST(NEXTVAL('auteur_seq') AS TEXT), 3, '0')), 'Mark Twain');
INSERT INTO auteur (id, nom)
VALUES (CONCAT('AUT', LPAD(CAST(NEXTVAL('auteur_seq') AS TEXT), 3, '0')), 'Leo Tolstoy');
INSERT INTO auteur (id, nom)
VALUES (CONCAT('AUT', LPAD(CAST(NEXTVAL('auteur_seq') AS TEXT), 3, '0')), 'Charles Dickens');
INSERT INTO auteur (id, nom)
VALUES (CONCAT('AUT', LPAD(CAST(NEXTVAL('auteur_seq') AS TEXT), 3, '0')), 'F. Scott Fitzgerald');
INSERT INTO auteur (id, nom)
VALUES (CONCAT('AUT', LPAD(CAST(NEXTVAL('auteur_seq') AS TEXT), 3, '0')), 'Ernest Hemingway');
INSERT INTO auteur (id, nom)
VALUES (CONCAT('AUT', LPAD(CAST(NEXTVAL('auteur_seq') AS TEXT), 3, '0')), 'Harper Lee');
INSERT INTO auteur (id, nom)
VALUES (CONCAT('AUT', LPAD(CAST(NEXTVAL('auteur_seq') AS TEXT), 3, '0')), 'Herman Melville');

-- insert EDITEUR
insert into editeur (id, designation)
values (CONCAT('EDI', LPAD(CAST(NEXTVAL('editeur_seq') AS TEXT), 3, '0')), 'Bloomsbury');
insert into editeur (id, designation)
values (CONCAT('EDI', LPAD(CAST(NEXTVAL('editeur_seq') AS TEXT), 3, '0')), 'Bantam Books');
insert into editeur (id, designation)
values (CONCAT('EDI', LPAD(CAST(NEXTVAL('editeur_seq') AS TEXT), 3, '0')), 'Reynal & Hitchcock');
insert into editeur (id, designation)
values (CONCAT('EDI', LPAD(CAST(NEXTVAL('editeur_seq') AS TEXT), 3, '0')), 'Gallimard');


-- insert COLLECTION
insert into collection (id, designation)
values (CONCAT('COL', LPAD(CAST(NEXTVAL('collection_seq') AS TEXT), 3, '0')), 'Harry Potter');
insert into collection (id, designation)
values (CONCAT('COL', LPAD(CAST(NEXTVAL('collection_seq') AS TEXT), 3, '0')), 'A Song of Ice and Fire');

-- insert LANGUE
insert into langue (code, designation)
values ('EN', 'English');
insert into langue (code, designation)
values ('FR', 'Français');

-- insert LIVRE
insert into livre (id, titre, auteur, isbn, numero_cote, editeur, date_edition, tome, collection, langue, nombre_pages,
                   resume)
values (CONCAT('LIV', LPAD(CAST(NEXTVAL('livre_seq') AS TEXT), 3, '0')), 'Harry Potter and the Philosopher\''s Stone',
        'AUT001', '9780747532699', 'C1-001', 'EDI001', '1997-06-26', 1, 'COL001', 'EN', 223,
        'The first book in the Harry Potter series.');

insert into livre (id, titre, auteur, isbn, numero_cote, editeur, date_edition, tome, collection, langue, nombre_pages,
                   resume)
values (CONCAT('LIV', LPAD(CAST(NEXTVAL('livre_seq') AS TEXT), 3, '0')), 'A Game of Thrones', 'AUT002', '9780553103540',
        'C2-001', 'EDI002', '1996-08-06', 1, 'COL002', 'EN', 694,
        'The first book in the A Song of Ice and Fire series.');

insert into livre (id, titre, auteur, isbn, numero_cote, editeur, date_edition, tome, collection, langue, nombre_pages,
                   resume)
values (CONCAT('LIV', LPAD(CAST(NEXTVAL('livre_seq') AS TEXT), 3, '0')), 'Harry Potter and the Chamber of Secrets',
        'AUT001', '9780747538493', 'C1-002', 'EDI001', '1998-07-02', 2, 'COL001',
        'EN', 251, 'The second book in the Harry Potter series.');

insert into livre (id, titre, auteur, isbn, numero_cote, editeur, date_edition, tome, collection, langue, nombre_pages,
                   resume)
values (CONCAT('LIV', LPAD(CAST(NEXTVAL('livre_seq') AS TEXT), 3, '0')), 'Harry Potter and the Prisoner of Azkaban',
        'AUT001', '9780747542155', 'C1-003', 'EDI001', '1999-07-08', 3, 'COL001',
        'EN', 317, 'The third book in the Harry Potter series.');

insert into livre (id, titre, auteur, isbn, numero_cote, editeur, date_edition, tome, collection, langue, nombre_pages,
                   resume)
values (CONCAT('LIV', LPAD(CAST(NEXTVAL('livre_seq') AS TEXT), 3, '0')), 'A Clash of Kings', 'AUT002', '9780553108033',
        'C2-002', 'EDI002', '1999-02-02', 2, 'COL002', 'EN', 768,
        'The second book in the A Song of Ice and Fire series.');

insert into livre (id, titre, auteur, isbn, numero_cote, editeur, date_edition, tome, collection, langue, nombre_pages,
                   resume)
values (CONCAT('LIV', LPAD(CAST(NEXTVAL('livre_seq') AS TEXT), 3, '0')), 'A Storm of Swords', 'AUT002', '9780553106633',
        'C2-003', 'EDI002', '2000-10-31', 3, 'COL002', 'EN', 973,
        'The third book in the A Song of Ice and Fire series.');

insert into livre (id, titre, auteur, isbn, numero_cote, editeur, date_edition, tome, collection, langue, nombre_pages,
                   resume)
values (CONCAT('LIV', LPAD(CAST(NEXTVAL('livre_seq') AS TEXT), 3, '0')), 'Le Petit Prince', 'AUT003', '9780156012195',
        'C3-001', 'EDI003', '1943-04-06', null, null, 'FR', 96,
        'A children''s book with philosophical themes.');

insert into livre (id, titre, auteur, isbn, numero_cote, editeur, date_edition, tome, collection, langue, nombre_pages,
                   resume)
values (CONCAT('LIV', LPAD(CAST(NEXTVAL('livre_seq') AS TEXT), 3, '0')), 'L''Étranger', 'AUT004', '9782070360024',
        'C4-001',
        'EDI004', '1942-05-19', null, null, 'FR', 159,
        'A novel by Albert Camus.');

-- insert TYPE MEMBRE
insert into type_membre
values ('TME001', 'professionnel', 1);
insert into type_membre
values ('TME002', 'professeur', 2);
insert into type_membre
values ('TME003', 'étudiant', 3);
insert into type_membre
values ('TME004', 'simple', 4);

-- insert MEMBRE
INSERT INTO membre (id, nom, prenom, email, date_naissance, type_membre, password)
VALUES (CONCAT('MBR', LPAD(CAST(NEXTVAL('membre_seq') AS TEXT), 3, '0')), 'Dupont', 'Jean', 'jean.dupont@example.com', '1980-01-15', 'TME002', '1234'),
       (CONCAT('MBR', LPAD(CAST(NEXTVAL('membre_seq') AS TEXT), 3, '0')), 'Martin', 'Sophie', 'sophie.martin@example.com', '1992-05-23', 'TME003', '1234'),
       (CONCAT('MBR', LPAD(CAST(NEXTVAL('membre_seq') AS TEXT), 3, '0')), 'Bernard', 'Luc', 'luc.bernard@example.com', '1985-03-30', 'TME004', '1234'),
       (CONCAT('MBR', LPAD(CAST(NEXTVAL('membre_seq') AS TEXT), 3, '0')), 'Petit', 'Marie', 'marie.petit@example.com', '1990-07-19', 'TME001', '1234'),
       (CONCAT('MBR', LPAD(CAST(NEXTVAL('membre_seq') AS TEXT), 3, '0')), 'Leroy', 'Paul', 'paul.leroy@example.com', '1983-11-22', 'TME003', '1234'),
       (CONCAT('MBR', LPAD(CAST(NEXTVAL('membre_seq') AS TEXT), 3, '0')), 'Moreau', 'Claire', 'claire.moreau@example.com', '1987-02-13', 'TME004', '1234'),
       (CONCAT('MBR', LPAD(CAST(NEXTVAL('membre_seq') AS TEXT), 3, '0')), 'Fournier', 'Julien', 'julien.fournier@example.com', '1995-08-08', 'TME002', '1234'),
       (CONCAT('MBR', LPAD(CAST(NEXTVAL('membre_seq') AS TEXT), 3, '0')), 'Girard', 'Emma', 'emma.girard@example.com', '1993-06-17', 'TME001', '1234'),
       (CONCAT('MBR', LPAD(CAST(NEXTVAL('membre_seq') AS TEXT), 3, '0')), 'Durand', 'Nicolas', 'nicolas.durand@example.com', '1989-12-05', 'TME004', '1234'),
       (CONCAT('MBR', LPAD(CAST(NEXTVAL('membre_seq') AS TEXT), 3, '0')), 'Lefevre', 'Alice', 'alice.lefevre@example.com', '1991-09-27', 'TME002', '1234');

-- insert EXEMPLAIRE
INSERT INTO exemplaire (id, livre, disponible)
VALUES (CONCAT('EXP', LPAD(CAST(NEXTVAL('exemplaire_seq') AS TEXT), 3, '0')), 'LIV001', true),
       (CONCAT('EXP', LPAD(CAST(NEXTVAL('exemplaire_seq') AS TEXT), 3, '0')), 'LIV001', true),
       (CONCAT('EXP', LPAD(CAST(NEXTVAL('exemplaire_seq') AS TEXT), 3, '0')), 'LIV001', true),
       (CONCAT('EXP', LPAD(CAST(NEXTVAL('exemplaire_seq') AS TEXT), 3, '0')), 'LIV002', true),
       (CONCAT('EXP', LPAD(CAST(NEXTVAL('exemplaire_seq') AS TEXT), 3, '0')), 'LIV002', true),
       (CONCAT('EXP', LPAD(CAST(NEXTVAL('exemplaire_seq') AS TEXT), 3, '0')), 'LIV002', true),
       (CONCAT('EXP', LPAD(CAST(NEXTVAL('exemplaire_seq') AS TEXT), 3, '0')), 'LIV003', true),
       (CONCAT('EXP', LPAD(CAST(NEXTVAL('exemplaire_seq') AS TEXT), 3, '0')), 'LIV003', true),
       (CONCAT('EXP', LPAD(CAST(NEXTVAL('exemplaire_seq') AS TEXT), 3, '0')), 'LIV003', true),
       (CONCAT('EXP', LPAD(CAST(NEXTVAL('exemplaire_seq') AS TEXT), 3, '0')), 'LIV004', true),
       (CONCAT('EXP', LPAD(CAST(NEXTVAL('exemplaire_seq') AS TEXT), 3, '0')), 'LIV004', true),
       (CONCAT('EXP', LPAD(CAST(NEXTVAL('exemplaire_seq') AS TEXT), 3, '0')), 'LIV004', true),
       (CONCAT('EXP', LPAD(CAST(NEXTVAL('exemplaire_seq') AS TEXT), 3, '0')), 'LIV005', true),
       (CONCAT('EXP', LPAD(CAST(NEXTVAL('exemplaire_seq') AS TEXT), 3, '0')), 'LIV005', true),
       (CONCAT('EXP', LPAD(CAST(NEXTVAL('exemplaire_seq') AS TEXT), 3, '0')), 'LIV005', true),
       (CONCAT('EXP', LPAD(CAST(NEXTVAL('exemplaire_seq') AS TEXT), 3, '0')), 'LIV006', true),
       (CONCAT('EXP', LPAD(CAST(NEXTVAL('exemplaire_seq') AS TEXT), 3, '0')), 'LIV006', true),
       (CONCAT('EXP', LPAD(CAST(NEXTVAL('exemplaire_seq') AS TEXT), 3, '0')), 'LIV006', true),
       (CONCAT('EXP', LPAD(CAST(NEXTVAL('exemplaire_seq') AS TEXT), 3, '0')), 'LIV007', true),
       (CONCAT('EXP', LPAD(CAST(NEXTVAL('exemplaire_seq') AS TEXT), 3, '0')), 'LIV007', true),
       (CONCAT('EXP', LPAD(CAST(NEXTVAL('exemplaire_seq') AS TEXT), 3, '0')), 'LIV007', true);

-- insert REGLE EMPRUNT
INSERT INTO regle_emprunt (id, livre, type_membre, peut_emprunter, peut_emmener_maison, limite_age, limite_retard)
VALUES (CONCAT('REM', LPAD(CAST(NEXTVAL('rglemprunt_seq') AS TEXT), 3, '0')), 'LIV001', 'TME001', true, true, 0, 10),
       (CONCAT('REM', LPAD(CAST(NEXTVAL('rglemprunt_seq') AS TEXT), 3, '0')), 'LIV001', 'TME002', true, true, 0, 7),
       (CONCAT('REM', LPAD(CAST(NEXTVAL('rglemprunt_seq') AS TEXT), 3, '0')), 'LIV001', 'TME003', true, false, 12, 5),
       (CONCAT('REM', LPAD(CAST(NEXTVAL('rglemprunt_seq') AS TEXT), 3, '0')), 'LIV001', 'TME004', true, false, 12, 3);
INSERT INTO regle_emprunt (id, livre, type_membre, peut_emprunter, peut_emmener_maison, limite_age, limite_retard)
VALUES
    (CONCAT('REM', LPAD(CAST(NEXTVAL('rglemprunt_seq') AS TEXT), 3, '0')), 'LIV002', 'TME001', true, true, 5, 8),
    (CONCAT('REM', LPAD(CAST(NEXTVAL('rglemprunt_seq') AS TEXT), 3, '0')), 'LIV002', 'TME002', false, true, 10, 5),
    (CONCAT('REM', LPAD(CAST(NEXTVAL('rglemprunt_seq') AS TEXT), 3, '0')), 'LIV002', 'TME003', true, false, 8, 2),
    (CONCAT('REM', LPAD(CAST(NEXTVAL('rglemprunt_seq') AS TEXT), 3, '0')), 'LIV002', 'TME004', false, false, 15, 7),

    (CONCAT('REM', LPAD(CAST(NEXTVAL('rglemprunt_seq') AS TEXT), 3, '0')), 'LIV003', 'TME001', true, true, 3, 6),
    (CONCAT('REM', LPAD(CAST(NEXTVAL('rglemprunt_seq') AS TEXT), 3, '0')), 'LIV003', 'TME002', false, false, 12, 4),
    (CONCAT('REM', LPAD(CAST(NEXTVAL('rglemprunt_seq') AS TEXT), 3, '0')), 'LIV003', 'TME003', true, false, 6, 10),
    (CONCAT('REM', LPAD(CAST(NEXTVAL('rglemprunt_seq') AS TEXT), 3, '0')), 'LIV003', 'TME004', false, true, 14, 9),

    (CONCAT('REM', LPAD(CAST(NEXTVAL('rglemprunt_seq') AS TEXT), 3, '0')), 'LIV004', 'TME001', true, false, 7, 1),
    (CONCAT('REM', LPAD(CAST(NEXTVAL('rglemprunt_seq') AS TEXT), 3, '0')), 'LIV004', 'TME002', true, true, 11, 8),
    (CONCAT('REM', LPAD(CAST(NEXTVAL('rglemprunt_seq') AS TEXT), 3, '0')), 'LIV004', 'TME003', false, true, 9, 3),
    (CONCAT('REM', LPAD(CAST(NEXTVAL('rglemprunt_seq') AS TEXT), 3, '0')), 'LIV004', 'TME004', true, false, 13, 4),

    (CONCAT('REM', LPAD(CAST(NEXTVAL('rglemprunt_seq') AS TEXT), 3, '0')), 'LIV005', 'TME001', false, true, 4, 5),
    (CONCAT('REM', LPAD(CAST(NEXTVAL('rglemprunt_seq') AS TEXT), 3, '0')), 'LIV005', 'TME002', true, false, 10, 2),
    (CONCAT('REM', LPAD(CAST(NEXTVAL('rglemprunt_seq') AS TEXT), 3, '0')), 'LIV005', 'TME003', false, false, 8, 9),
    (CONCAT('REM', LPAD(CAST(NEXTVAL('rglemprunt_seq') AS TEXT), 3, '0')), 'LIV005', 'TME004', true, true, 6, 7),

    (CONCAT('REM', LPAD(CAST(NEXTVAL('rglemprunt_seq') AS TEXT), 3, '0')), 'LIV006', 'TME001', true, true, 2, 10),
    (CONCAT('REM', LPAD(CAST(NEXTVAL('rglemprunt_seq') AS TEXT), 3, '0')), 'LIV006', 'TME002', false, false, 14, 6),
    (CONCAT('REM', LPAD(CAST(NEXTVAL('rglemprunt_seq') AS TEXT), 3, '0')), 'LIV006', 'TME003', true, false, 5, 3),
    (CONCAT('REM', LPAD(CAST(NEXTVAL('rglemprunt_seq') AS TEXT), 3, '0')), 'LIV006', 'TME004', false, true, 9, 8),

    (CONCAT('REM', LPAD(CAST(NEXTVAL('rglemprunt_seq') AS TEXT), 3, '0')), 'LIV007', 'TME001', true, false, 6, 4),
    (CONCAT('REM', LPAD(CAST(NEXTVAL('rglemprunt_seq') AS TEXT), 3, '0')), 'LIV007', 'TME002', true, true, 13, 1),
    (CONCAT('REM', LPAD(CAST(NEXTVAL('rglemprunt_seq') AS TEXT), 3, '0')), 'LIV007', 'TME003', false, true, 10, 7),
    (CONCAT('REM', LPAD(CAST(NEXTVAL('rglemprunt_seq') AS TEXT), 3, '0')), 'LIV007', 'TME004', false, false, 12, 2),

    (CONCAT('REM', LPAD(CAST(NEXTVAL('rglemprunt_seq') AS TEXT), 3, '0')), 'LIV008', 'TME001', true, true, 7, 5),
    (CONCAT('REM', LPAD(CAST(NEXTVAL('rglemprunt_seq') AS TEXT), 3, '0')), 'LIV008', 'TME002', false, true, 4, 9),
    (CONCAT('REM', LPAD(CAST(NEXTVAL('rglemprunt_seq') AS TEXT), 3, '0')), 'LIV008', 'TME003', true, false, 11, 6),
    (CONCAT('REM', LPAD(CAST(NEXTVAL('rglemprunt_seq') AS TEXT), 3, '0')), 'LIV008', 'TME004', false, false, 8, 3);


-- CREATE OR REPLACE VIEW v_penalite AS
-- WITH latest_penalties AS (
--     SELECT DISTINCT ON (membre)
--         id,
--         membre,
--         date_debut,
--         date_fin,
--         CURRENT_DATE BETWEEN date_debut AND date_fin AS estPenalise
--     FROM penalite
--     ORDER BY membre, date_debut DESC
-- )
-- SELECT * FROM latest_penalties;
-- ;

CREATE OR REPLACE VIEW v_penalite AS
SELECT DISTINCT ON (membre)
    id,
    membre,
    date_debut,
    date_fin,
    CURRENT_DATE BETWEEN date_debut AND date_fin AS estPenalise
FROM penalite
ORDER BY membre, date_debut DESC;

SELECT COUNT(id) FROM emprunt GROUP BY exemplaire;

CREATE or replace VIEW v_usage AS
SELECT e.id,
       e.livre,
       e.disponible,
       COUNT(em.id) AS usage
FROM exemplaire e
         LEFT JOIN
     emprunt em
     ON
         e.id = em.exemplaire
GROUP BY e.id;

-- le livre le plus emprunté
create view v_livre_plus_emprunte as
select l.*,
       count(l.id) as count
from emprunt em
         join exemplaire ex on em.exemplaire = ex.id
         join livre l on ex.livre = l.id
group by l.id
order by count desc
limit 1;

-- membre le plus actif
create view v_membre_plus_actif as
select m.*, count(m.id) as count from emprunt em
join membre m on em.membre = m.id
group by m.id
order by count desc
limit 1;

-- emprunt en cours
create view v_emprunt_en_cours as
select count(e.id) from emprunt e
where e.date_rendu is null;
