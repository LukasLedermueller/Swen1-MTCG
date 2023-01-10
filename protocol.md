# Monster Trading Card Game - SWEN1 Semesterprojekt
## Lukas Ledermüller - if21b143

### Design
Das Monster Trading Card Game läuft als http Server mit REST-Schnittstelle.
Für jede Route gibt es ein entsprechendes Service, das wiederum eine Funktion des Controllers aufruft. Welche Funktionen aufgerufen werden, hängt von dem Request und seiner Methode ab.
Der Controller führt die Business Logik durch und ruft bei Bedarf eine Funktion des entsprechenden Repositories auf. Für jeden Table in der Datenbank gibt es ein Repository, das die Queries durchführt.
Danach returniert der Controller eine http Response, je nachdem, ob der Request erfolgreich war oder etwas dazwischen gekommen ist. 
In diesem Fall wird entweder im Repo oder im Controller selber eine Exception geworfen und dementsprechend die Response aufgebaut. Falls keine Exception geworfen wird, war der Request erfolgreich.

Ich habe mich dafür entschieden, dass der Controller den ganzen Request übergeben bekommt, da man meistens den Token auslesen muss und öfters ein Requestbody mitgeschickt wird.
Außerdem macht das Service - Controller - Repo Design hier Sinn, da im Vorhinein die einzelnen Routen schon definiert waren und man mittels Repository die Geschäftslogik von der Datenschicht trennt.
Die einzige Klasse, die diesem Schema nicht folgt, ist der Battlehandler. Ich habe mir gedacht, dass es mehr Sinn macht, das Battle in einer eigenen Klasse laufen zu lassen, die vom Controller gestartet wird und den Log zurückschickt, um ihn dem User zu senden.

Die größte Schwierigkeit war es, sich für ein Design Pattern zu entscheiden. Damit meine ich nicht Service - Controller - Repo, sondern wie die Ordnerstruktur ist und hauptsächlich, wie der Controller für jede Funktion aufgebaut sein soll.
Alle Ausnahmen werden in eigene Exceptions gepackt, damit ein erfolgreicher Responsecode nur dann geschickt wird, wenn nichts dazwischen kommt.
Eine weitere Schwierigkeit war es, die UnitOfWork so einzubauen, dass sie reibungslos funktioniert und im Falle eines Fehlers richtig geschlossen wird und immer ein Rollback durchgeführt wird. 
Ich habe mich dazu entschlossen, dass am Anfang jedes Controllers die UOW aufgebaut wird und gleich ein 500er Error geschickt wird, wenn sie nicht aufgebaut werden kann. Falls das aber funktioniert, wird sie mittels AutoCloseable geschlossen.


### Testing
Das Hauptaugenmerk beim Testen liegt an den Controllern. Es werden alle möglichen Fehler bewusst erzeugt, um zu schauen, ob die richtige Respone zurückkommt. 
Deshalb existieren für die Controller am meisten Tests, da sie die wichtigste Logik beinhalten. Außerdem kann man so auch gleichzeitig die Repos mittesten, wenn man sie nicht gemockt hat.

### Zeiterfassung



### Github Repo
https://github.com/LukasLedermueller/Swen1-MTCG