********************************************************************************
*   Änderungen, Anmerkungen & Co.                                              *
*   Luto, Lukas Völler, Tobias Gels, 2017                                      *
'*******************************************************************************

bean-discovery-mode in der beans.xml vol "all" auf "annotated"

Textänderungen überall price nicht prize. Das eine ist der Preis den man
bezahlt, der andere der Preis den man gewinnt.

********************************************************************************
*		      Datenbank füllen mit folgendem Code		       *
'*******************************************************************************

INSERT INTO APP.COOKIE(ID, COUNT, NAME, PRICE)
VALUES (1,64,'Butterkeks',1.99);

INSERT INTO APP.COOKIE(ID, COUNT, NAME, PRICE)
VALUES (2,64,'Cookie',2.99);

INSERT INTO APP.COOKIE(ID, COUNT, NAME, PRICE)
VALUES (3,64,'Schokokeks',1.49);

INSERT INTO APP.COOKIE(ID, COUNT, NAME, PRICE)
VALUES (4,64,'Marzipankeks',3.99);

INSERT INTO APP.COOKIE(ID, COUNT, NAME, PRICE)
VALUES (5,64,'Lebkuchenkeks',2.49);