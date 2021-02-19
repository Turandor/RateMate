# RateMate - culture texts rating app



## Table of contents
* [General info](#general-info)
* [Screen shots](#screen-schots)
* [Technologies](#technologies)
* [Setup](#setup)
* [Features](#features)

## General info
Application that allows the user to save, rate and review cultural texts. The main inspiration for the creation of this application were such websites as [IMDB](https://www.imdb.com/), [Filmweb](https://www.filmweb.pl/), [MAL](https://myanimelist.net/) but in local, diary-like way.

Aplikacja nazwana „Rate Mate”, służy do oceniania dzieł kultury, które widział użytkownik. Działa w oparciu o bazę danych. Użytkownik dodaje do aplikacji różne filmy, książki, gry itp. które obejrzał/przeczytał/zagrał i wystawia im ocenę oraz ewentualnie może dodać opis dzieła oraz jego recenzje. Aplikacja pozwala na przeglądanie, filtrowanie i sortowanie dodanych pozycji. Dodatkowo można wyświetlać proste statystyki w postaci ilości wszystkich obejrzanych dzieł i ich średniej oceny, a także statystyki w poszczególnych kategoriach np. filmów.
	Do realizacji aplikacji użyłem bazy danych w której przechowywane są dane wprowadzone przez użytkownika.

## Features
a.	Menu główne
Po włączeniu aplikacji ukazuje się użytkownikowi ekran główny zaprezentowany na zdj. 1.

![Main menu](./images/MainManu.png)
Z poziomu tego ekranu użytkownik ma dostęp do trzech głównych funkcjonalności. Każdy z przycisków otwiera nową aktywność i przenosi do niej użytkownika: 
•	Dodawania nowego tekstu – ADD NEW,
•	Wyświetlania listy swoich tekstów kultury – VIEW YOUR CULTURE LIST,
•	Wyświetlania statystyk użytkownika – VIEW STATISTICS.

b.	Dodawanie nowego tekstu
Dodawanie nowego tekstu zostało zaprezentowane za zdj. 2. i 3.

Zdj. 2
  
Zdj. 3
 
Zdj. 4

Jak widać na zdj. 2. użytkownik podaje nazwę tekstu kultury, wybiera z rozwijanej listy kategorie tekstu i podaje datę wydania za pomocą przycisku, który otwiera kalendarz (zdj. 3.) Następnie użytkownik może wpisać opis tekstu oraz swoją krótką recenzje. Co ważne, jedynymi obowiązkowymi polami do wypełnienia jest nazwa i data wydania. 
Ta aktywność opiera się o typ layoutu „ScrollView”. Polega ona na tym, że użytkownik może przewijać ekran niczym przeglądarkę na urządzeniu mobilnym. Dzięki takiemu rozwiązaniu wpisany opis i recenzja nie „zwijają się” we własnych polach tekstowych tylko rozszerzają widok, który można przewinąć przez co ekran jest bardziej przejrzysty.
Na dole ekranu umieszczone zostały dwa przyciski: ADD – służący do dodania pozycji, którą użytkownik wpisał oraz VIEW YOUR CULTURE LIST – służący do wyświetlenia listy tekstów użytkownika. Po naciśnięciu przycisku ADD pojawia się komunikat o tym czy udało się dodać pozycję do listy czy nie.

c.	Wyświetlanie listy tekstów kultury
Wyświetlanie listy tekstów kultury zostało zaprezentowane na zdj. 5. i zdj. 6.

Zdj. 5

Zdj. 6
Użytkownik może zobaczyć tutaj dodane przez siebie teksty w postaci listy utworzonej przy użyciu „ListView”. Dodatkowo można organizować wyświetlanie listy przez:
•	Wyszukiwanie (przycisk lupy w prawym górnym rogu) po nazwie, kategorii, roku wydania itp.
•	Wyświetlanie kategorii (przy użyciu górnego „Spinnera” – rozwijanej listy) np. film, książka
•	Sortowanie (przy użyciu drugiego „Spinnera” oraz strzałki obok sortującej rosnąco lub malejącą):
o	Alfabetycznie
o	Daty dodania wpisu
o	Daty wydania
o	Oceny

d.	Edytowanie tekstu kultury
Edytowanie listy tekstów kultury zostało zaprezentowane na zdj. 7. i polega wybraniu tekstu poprzez kliknięcie na niego z poziomu widoku listy tekstów kultury.

Zdj. 7
Ten widok jest zmodyfikowaną wersją dodawania nowej pozycji. Poza informacją o tym, że użytkownik edytuje teraz pozycje dodano datę dodania pozycji. Zmodyfikowano również przyciski na dole. Są to teraz przyciski DELETE i SAVE. DELETE służy do usunięcia wybranego rekordu, a SAVE do zapisania zmian w pozycji. Po kliknięciu każdego z tych przycisków użytkownik jest przekierowywany do widoku listy tekstów kultury. 

e.	Statystyki
Statystyki użytkownika zostały pokazana na zdj. 8.
 
Zdj. 8
Ta aktywność pozwala na wyświetlanie statystyk dla poszczególnych kategorii tekstów lub statystyk zbiorczych. Widać tu liczbę dodanych pozycji oraz ich średnią ocenę wystawioną przez użytkownika.

## Technologies
Project is created with:
* Android studio 4.0.1
* SQlite Database (build-in android)

## Setup
### Instalation
To run this project, clone this repository and import into Android Studio
```
$ git clone git@github.com:Turandor/RateMate.git
```
### Generating signed APK
From Android Studio:
1. ***Build*** menu
2. ***Generate Signed APK...***
3. Fill in the keystore information *(you only need to do this once manually and then let Android Studio remember it)*


