# Monster Trading Card Game - SWEN1 Semesterprojekt
## Lukas Ledermüller - if21b143

### Design
Das Monster Trading Card Game läuft als http Server mit REST-Schnittstelle.
Für jede Route gibt es ein entsprechendes Service, das wiederum eine Funktion des Controllers aufruft. Welche Funktionen aufgerufen werden, hängt von dem Request und seiner Methode ab.
Der Controller führt die Businesslogik durch und ruft bei Bedarf eine Funktion des entsprechenden Repositories auf. Für jeden Table in der Datenbank gibt es ein Repository, das die Queries durchführt.
Danach returniert der Controller eine http Response, je nachdem, ob der Request erfolgreich war oder etwas dazwischen gekommen ist. 
In diesem Fall wird entweder im Repo oder im Controller selber eine Exception geworfen und dementsprechend die Response aufgebaut. Falls keine Exception geworfen wird, war der Request erfolgreich.

Ich habe mich dafür entschieden, dass der Controller den ganzen Request übergeben bekommt, da man meistens den Token auslesen muss und öfters ein Requestbody mitgeschickt wird.
Außerdem macht das Service - Controller - Repo Design hier Sinn, da im Vorhinein die einzelnen Routen schon definiert waren und man mittels Repository die Geschäftslogik von der Datenschicht trennt.
Die einzige Klasse, die diesem Schema nicht folgt, ist der Battlehandler. Ich habe mir gedacht, dass es mehr Sinn macht, das Battle in einer eigenen Klasse laufen zu lassen, die vom Controller gestartet wird und den Log zurückschickt, um ihn dem User zu senden.

Die größte Schwierigkeit war es, sich für ein Design Pattern zu entscheiden. Damit meine ich nicht Service - Controller - Repo, sondern wie die Ordnerstruktur ist und hauptsächlich, wie der Controller für jede Funktion aufgebaut sein soll.
Alle Ausnahmen werden in eigene Exceptions gepackt, damit eine erfolgreiche Response nur dann geschickt wird, wenn nichts dazwischen kommt.
Eine weitere Schwierigkeit war es, die UnitOfWork so einzubauen, dass sie reibungslos funktioniert und im Falle eines Fehlers richtig geschlossen wird und immer ein Rollback durchgeführt wird. 
Ich habe mich dazu entschlossen, dass am Anfang jedes Controllers die UOW aufgebaut wird und gleich ein 500er Error geschickt wird, wenn sie nicht aufgebaut werden kann. Falls das aber funktioniert, wird sie mittels AutoCloseable geschlossen.
Dann wird alles ausgeführt. Falls eine Exception geworfen wird, wird rollback ausgeführt, sonst commit. Am Ende wird finishwork ausgeführt.


### Testing
Das Hauptaugenmerk beim Testen liegt an den Controllern. Es werden alle möglichen Fehler bewusst erzeugt, um zu schauen, ob die richtige Response zurückkommt. 
Deshalb existieren für die Controller am meisten Tests, da sie die wichtigste Logik beinhalten. Außerdem kann man so auch gleichzeitig die Repos mittesten. 
Es hat für mich keinen Sinn gemacht, die Repos einzeln zu testen, da man das gut mit den Controllern kombinieren kann. 
Das sind dann vermutlich keine "echten" Unit Tests mehr, aber für mich hat es so am meisten Sinn gemacht, da für den User ja die Responses am wichtigsten sind.

### Unique Feature
Zum einen habe ich hinzugefügt, dass man sich wieder ausloggen kann. Dabei wird der Token aus dem token Table gelöscht.
Zum anderen habe ich hinzugefügt, dass der admin mehr Rechte hat. Er kann z.B. UserData von anderen ändern, alle User auflisten und wie schon bekannt, Packages erstellen.
Ein für mich wichtiges Feature war, dass, wenn man beim Erstellen eines Packages bei einer oder mehreren Karten keine ID hinzufügt, also leer lässt, dass dann eine Random UUID erstellt wird. 
Das Schema passt zu denen, die im Skript enthalten sind. So kann man schnell Packages erstellen.

### Lessons Learned
Ich weiß jetzt, wie ein http server mit Rest Schnittstelle in Java aufgebaut ist und funktioniert. Das ist bestimmt hilfreich zu wissen, wenn man mit Frameworks wie Spring arbeitet.
Da kommt das alles "Out of the Box", aber jetzt weiß ich zumindest, wie es in etwa funktioniert. Außerdem habe ich mich gut mit der Programmiersprache Java anfreunden können.
Der Syntax ist etwas ähnlich wie C++, aber um einiges benutzerfreundlicher. Was definitiv spannend war, war mit Intellij Ultimate zu arbeiten. 
Es nimmt einem extrem viel ab, aber ist doch teilweise sehr überwältigend, wenn man bedenkt, wie viele Funktionalitäten es gibt.
Was für meine weitere Laufbahn vermutlich eines der wichtigsten Dinge ist, das ich gelernt habe, ist das Designschema, das ich hier angewandt habe. 
Die Trennung von Business und Data ist hier durch Repos und Controller übersichtlich getrennt und ergibt meiner Meinung nach auch Sinn.

### Zeiterfassung

Zeit in h, Was, Notizen:
2 Projekt aufsetzen, GIT Repo erstellen -<br>
2 http-server einfügen - Probleme bei git und den dependencies<br>
2 neues Repo erstellt, um http-server richtig einzufügen - falschen Namen gehabt<br> 
4 packages erstellt, Userservice angelegt und getestet - laut Yamlfile<br>
3 Database in Java zum Laufen gebracht - Probleme mit pom.xml<br>
2 UserService bearbeitet - <br>
5 Databaseaccess geupdatet - OUF, DBManager, Repos<br>
6 addUser verfeinert - Ziel war Optimale Lösung und Template für andere Klassen<br>
3 User GET/PUT - ähnlich wie addUser<br>
3 Sessions - login, saveToken, validate<br>
8 Cards - DB leicht geändert<br>
7 Package - db angepasst<br>
3 stats/score - yaml+curl sind unterschiedlich<br>
5 trading - mehr Error Handling als im yaml<br>
3 battle create - Schwierigkeit mit Queue und warten<br>
8 battle durchführen - anderes Elo versucht, aber aufgegeben<br>
2 eigenes Curl Skript - angepasst an existierendem, jetzt kann es Winner geben<br>
10 unit Tests - Aufbau von Request war anfangs schwierig<br>
4 unique Feature - Erweiterungen von bestehenden Controllern<br>

Gesamt: 82 Stunden


### GitHub Repo
https://github.com/LukasLedermueller/Swen1-MTCG