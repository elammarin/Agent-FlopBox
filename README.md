# assima_elammari_projet2_sr2

application cliente pour la plate-forme FlopBox qui permet de synchroniser les données stockées à distance dans un ensemble de serveurs FTP avec le système de fichiers local d'une machine sur laquelle l'application cliente s'exécute.

## compilation 

```console
mvn package assembly:single
```

## execution 

recuperer le depot de la plateforme du projet 1 et lancer la. Faites un git pull si necessaire car elle a été legerement modifié depuis.
lien : https://gitlab.univ-lille.fr/nordine.elammari.etu/flopbox-sr2.git

puis lancer :

```console
java -jar target/agentFlopbox-agentFlopBox-jar-with-dependencies.jar
```

## commentaires

L'agent fonctionne bien il récupère tout les contenus des differents serveurs enregistrés sur la plateforme FlopBox même pour les serveurs free et ubuntu mais cela met du temps voir est interminable à cause du nombre de fichiers et certains de ces fichiers étant très volumineux.
Etant donné que dans le projet 1 aucun de nous deux n'a réussi à faire l'upload de fichier local vers le serveur distant nous avons géré pour l'agent la mis à jour des fichiers locaux lorsqu'un fichier est modifié à distance et la création d'un repertoire .deleted à distance contenant le ou les fichiers supprimés localement.




