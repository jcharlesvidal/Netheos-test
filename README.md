**** DESCRIPTION ***

Etant donné l'aspect ouvert des spécifications, certaines décisions ont été prises.

* Pour l'API REST, j'ai supposé qu'on employait du JSON, c'est ce qu'il y a de plus courant pour ce genre d'application.
* Pour différencier le user de l'admin dans les User Stories, j'ai supposé que les requêtes HTTP contenaient un header d'identification Basic (donc le login et le password d'un utilisateur), ce qui est non-sécurisé mais acceptable en HTTPS. J'ai donc créé une table user et une table role en BDD et un système d'authentification/authorization idoine à partir du header. Seuls les users admin:admin et user:user existent. Les roles possibles sont user et admin. Les pwd sont stockés en clair dans la base, ce n'est qu'une démo.
* Pour la User Story 3, j'ai pris la décision suivante : une série de mots clé sont envoyé dans l'URL séparés par des '+' (à la google). Chaque mot clé est recherché dans chaque question ou reponse (en fait un LIKE %keyword%). Dans un premier temps, on recherche les entrées comportant tous les mots clés (recherche AND). Si le résultat de la recherche est vide, on retourne alors une recherche sur au moins un des mots clés (recherche OR).
* Dans tous les User Story 2 et 3, on retourne toujours une liste de question/reponse/tags.Pour la User Story 3, ça ne nuit pas d'avoir la question associée à la réponse (et les tags aussi).
* Pour les erreurs d'authentification, en plus du status HTTP (401), un objet JSON est retourné (code erreur et texte erreur). Pour les erreurs d'execution, un code 500 est retourné. Toutes les erreurs dues à des url malformées ou à du JSON incorrect sont retournées par le framework (voir plus bas). 

*** IMPLEMENTATION **

* Pour gérer le REST/JSON, j'ai employé Jersey qui est simple et robuste. Un module Jackson a été ajouté pour le JSON. En pratique, c'est la version Jersey pour Spring qui a été utilisée.
* Spring a été utilisé. Vu la taille du projet, ce n'était pas nécessaire, mais ça permet une scalabilité.
* Classiquement, Hibernate a été utilisé pour gérer la BDD. Vu la taille de la BDD et du projet, j'ai simplifié en utilisant la Lazyness en EAGER, ce qui m'a dispensé de passer par des DTO pour gérer les objets passant sur le JSON. De plus la DataSource est celle de Spring par défaut, ce qui est bien suffisant. 
* La BDD est MySQL 5.x, c'est plus que suffisant, même si on envisage une utilisation "réelle". Dans le répertoire sql du projet on trouve 2 fichiers pour créer/remplir et détruitre la base.
* Pour les tests unitaires, Spring-test et Mockito s'imposaient
* Pour les tests d'intégration, j'ai utilisé cargo pour déployer le war et Jersey Client pour tester.

*** TESTS ****
* Sur ma BDD, je me connecte avec un user root:root. Il faudra peut-être changer ce login dans src/main/resources/application.properties
* Les tests unitaires se lancent classiquement via Maven
* Les tests d'intégration se lance via Maven et un profile integration : mvn clean install -Pintegration. A noter que les tests unitaires se lancent aussi par la même occasion. IMPORTANT : pour que les tests d'intégration fonctionnent, il faut avoir une BDD dans l'état initial (utiliser les scripts SQL pour ce faire). ATTENTION : le Jetty embarqué par cargo tourne sur le port 9999.
* Différents exemples pour tester avec curl se trouvent dans le répertoire curl.
