#Selenium-Flash

Cet ersatz d'application existe afin de montrer par un exemple simple le fonctionnement de l'application Selenium-Flash.

Elle permet également à montrer comment j'ai contourné le problème de l'inaccessibilité des sites frontaux en vérifiant la présence de contenus éditoriaux dans Superdup.
Elle n'a pas vocation à fonctionner.

Malgré tout le soin apporté à cette reconstitution partielle et tronquée, certaines erreurs (inconsistences, illogismes...) peuvent subsister dans le code.

Merci à J. Q. et G. N. qui se reconnaîtront, ainsi qu'à A.d.M. pour l'idée de la branche live-coding.

**Il existe une branche "live-coding"** qui permet, en naviguant du commit le plus récent au plus ancien de voir les différentes étapes d'une de mes contributions au projet.

Pour se déplacer sur cette branche:

`git checkout live-coding`

La commande suivante donne la liste des étapes.

`git log`

Les différentes étapes sont :
716b5423 - 1) préparatifs et mise en place d'un test de connexion à Superdup (début)
a5b8b440 - 2) recherche de l'archive dans Superdup et téléchargement
b9db3f00 - 3) dézippage et parsage du XML
88ae74f5 - 4) intégration des tests Superdup à un test de publication d'un contenu éditorial

Par exemple, pour se déplacer vers l'étape 4) il faut faire:

git checkout 88ae74f5
