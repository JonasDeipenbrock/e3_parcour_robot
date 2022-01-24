# Regelwerk

## Allgemeines

Der Parkour gilt als Bestanden wenn man ihn in unter 25 Minuten beendet.

Der Roboter darf während des Parkours nicht berührt werden.

Alle beweglichen Hindernisse bleiben für alle Gruppen gleich. Sobald die Position der Hindernisse feststeht darf der Code dafür nicht mehr angepasst werden. Die Position wird am Anfang des Tages zufällig gewählt.

Jeder Abschnitt des Parkours muss in 5 Minuten geschafft werden,
ausgenommen ist das Linienfolgen, hier hat man maximal 10 Minuten.

Wird ein Abschnitt nicht geschafft erhält man die obige Maximalzeit als Wertung.

Ein Abschnitt darf neu begonnen werden. Dabei wird der Roboter an die blaue Linie gesetzt und es dürfen Tasten gedrückt werden. Die Zeit für diesen Abschnitt wird zurückgesetzt und eine Minute Strafe hinzugefügt.

<!-- Zeitstrafen werden zusätzlich auf die Endzeit addiert und zählen nicht in das Maximum rein. -->

Wenn innerhalt von wenigen Sekunden nach einem Tastendruck und ohne, dass sich ein Motor gedraht hat, zu einer Java-Exception kommt, die begründet auf einen Sensorfehler zurückzuführen ist, wird dieser Versuch ignoriert und darf wiederholt werden.

Zurücksetzen oder Korrigieren gibt 30 Sekunden Strafe. Dabei läuft die Uhr weiter. Die Rotation des Roboters darf dabei angepasst werden. Es dürfen keine Tasten gedrückt werden.

---

Beginn des Parkours:
Der Farbsensor des Roboters muss auf dem erst geraden Stück der Linie aufgestellt sein. Er muss in der Nähe des queren Streifens am Start stehen.

Ein Abschnitt endet sobald die blaue Linie überfahren wurde oder der letzte Abschnitt normal beendet. Dabei muss ein akustisches oder visueles Signal ertönen.

---

Falls während des Rennens ein Fall auftritt der nicht durch dieses Regelwerk abgedeckt ist trifft das Rennkommitee eine Einzelfallentscheidung durch Abstimmung.

## Linie folgen

Der Roboter muss auf dem ganzen Abschnitt sichtbar der Linie folgen. Ausgenommen sind Lücken.

Abkürzungen sind nicht erlaubt und der Roboter muss zurückgesetzt werden.

Für das Zurücksetzen gilt zusätzlich:

-   Es nur erlaubt den Roboter hinter die Stelle zu stellen an der abgekommen ist. Dabei läuft die Zeit weiter.

Überspringen eines Teilabschnitts ist gegen eine Zeitstrafe von 2 Minuten erlaubt. Es darf dabei exakt eine Problemstelle übersprungen werden. Problemstellen sind:

-   Lücken
-   90° Kurven
-   Holzblock

Die Übergänge der Bretter zählen als Lücke falls sie nicht abgeklebt werden können. Dann greifen dafür die obigen Regeln.

## Box schieben

Im Laufe des Abschnitt muss die Box vollständig in die markierte Ecke geschoben werden.

Für jede Ecke die nicht in der markierten Ecke ist bekommt das Team 1 Minute Zeitstrafe.

## Brücke

Die Brücke muss überquert werden ohne dass der Roboter herunterfällt. Sollte dies passieren erhält das Team eine Minute Zeitstrafe und muss den Abschnitt neu beginnen.

In diesem Abschnitt beträgt die Strafe für Korrekturen und Zurücksetzen 1 Minute.

## Kreuze finden

Die Kreuze haben einen Mindestabstand von 13 Zentimeter von den Wänden.

Es müssen alle markierten Kreuze gefunden werden. Nach dem Finden des letzten muss ein Signalton ertönen.

Korrekturen und Zurücksetzen sind nicht erlaubt.
