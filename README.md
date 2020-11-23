#Selenium-Flash

Cet ersatz d'application existe afin de montrer par un exemple simple le fonctionnement de l'application Selenium-Flash.

Elle permet également à montrer comment j'ai contourné le problème de l'inaccessibilité des sites frontaux en vérifiant la présence de contenus éditoriaux dans Superdup.
Elle n'a pas vocation à fonctionner.

Malgré tout le soin apporté à cette reconstitution partielle et tronquée, certaines erreurs (inconsistences, illogismes...) peuvent subsister dans le code.

Merci à J. Q. et G. N. qui se reconnaîtront, ainsi qu'à A.d.M. pour l'idée de la branche live-coding.

**Il existe une branche "live"** qui permet, en naviguant du commit le plus récent au plus ancien de voir les différentes étapes d'une de mes contributions au projet.

Pour se déplacer sur cette branche:

`git checkout live`

La commande suivante donne la liste des étapes.

`git log`

Les différentes étapes sont :
53cee1dd - 1) préparatifs et mise en place d'un test de connexion à Superdup (début)
40a3f68f - 2) recherche de l'archive dans Superdup et téléchargement
3593e1a5 - 3) dézippage et parsage du XML
48b23595 - 4) intégration des tests Superdup à un test de publication d'un contenu éditorial

Par exemple, pour se déplacer vers l'étape 4) il faut faire:

git checkout 48b23595
