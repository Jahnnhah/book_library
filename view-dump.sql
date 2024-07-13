CREATE VIEW public.v_emprunt_en_cours AS
 SELECT count(e.id) AS count
   FROM public.emprunt e
  WHERE (e.date_rendu IS NULL);


CREATE VIEW public.v_livre AS
 SELECT l.id,
    l.titre,
    l.auteur,
    a.nom AS auteur_nom,
    l.isbn,
    l.numero_cote,
    l.editeur,
    e.designation AS editeur_designation,
    l.date_edition,
    l.tome,
    l.collection,
    c.designation AS collection_designation,
    l.langue,
    l2.designation AS langue_designation,
    l.nombre_pages,
    l.resume
   FROM ((((public.livre l
     LEFT JOIN public.auteur a ON (((a.id)::text = (l.auteur)::text)))
     LEFT JOIN public.collection c ON (((c.id)::text = (l.collection)::text)))
     LEFT JOIN public.editeur e ON (((e.id)::text = (l.editeur)::text)))
     LEFT JOIN public.langue l2 ON (((l2.code)::text = (l.langue)::text)));

CREATE VIEW public.v_livre_plus_emprunte AS
 SELECT l.id,
    l.titre,
    l.auteur,
    l.isbn,
    l.numero_cote,
    l.editeur,
    l.date_edition,
    l.tome,
    l.collection,
    l.langue,
    l.nombre_pages,
    l.resume,
    count(ex.livre) AS count
   FROM ((public.emprunt em
     JOIN public.exemplaire ex ON (((em.exemplaire)::text = (ex.id)::text)))
     RIGHT JOIN public.livre l ON (((ex.livre)::text = (l.id)::text)))
  GROUP BY l.id
  ORDER BY (count(ex.livre)) DESC;

CREATE VIEW public.v_membre AS
 SELECT membre.id,
    membre.nom,
    membre.prenom,
    membre.email,
    membre.date_naissance,
    membre.type_membre,
    membre.password,
    type_membre.designation AS type_membre_designation
   FROM (public.membre
     JOIN public.type_membre ON (((membre.type_membre)::text = (type_membre.id)::text)));


CREATE VIEW public.v_membre_plus_actif AS
 SELECT m.id,
    m.nom,
    m.prenom,
    m.email,
    m.date_naissance,
    m.type_membre,
    m.password,
    count(m.id) AS count
   FROM (public.emprunt em
     JOIN public.membre m ON (((em.membre)::text = (m.id)::text)))
  GROUP BY m.id
  ORDER BY (count(m.id)) DESC
 LIMIT 1;


CREATE VIEW public.v_penalite AS
 SELECT DISTINCT ON (penalite.membre) penalite.id,
    penalite.membre,
    penalite.date_debut,
    penalite.date_fin,
    ((CURRENT_DATE >= penalite.date_debut) AND (CURRENT_DATE <= penalite.date_fin)) AS estpenalise
   FROM public.penalite
  ORDER BY penalite.membre, penalite.date_debut DESC;


CREATE VIEW public.v_regle_emprunt AS
 SELECT regle_emprunt.id,
    regle_emprunt.livre,
    regle_emprunt.type_membre,
    regle_emprunt.peut_emprunter,
    regle_emprunt.peut_emmener_maison,
    regle_emprunt.limite_age,
    regle_emprunt.limite_retard,
    type_membre.designation AS type_membre_designation,
    l.titre AS livre_titre
   FROM ((public.regle_emprunt
     JOIN public.type_membre ON (((regle_emprunt.type_membre)::text = (type_membre.id)::text)))
     JOIN public.livre l ON (((l.id)::text = (regle_emprunt.livre)::text)));


CREATE VIEW public.v_usage AS
 SELECT e.id,
    e.livre,
    e.disponible,
    count(em.id) AS usage
   FROM (public.exemplaire e
     LEFT JOIN public.emprunt em ON (((e.id)::text = (em.exemplaire)::text)))
  GROUP BY e.id;
