ClueHelper Ideas:

Main Purpose:

A program that keeps track of every detail you know about every player in the game and helps
you narrow down possibilities for what's in the envelope and make strategic rumors/accusations.

Structure:

- A player class to hold info for each player
- a person card class, weapon card class, and room card class
- a REPL that follows the course of game and allows user
  to enter bits of information to update game account

Console Options:

1) Register a rumor
2) Check My Score Card (what I know about what I and everyone else have)
3) Check An Opponent's Score Card (what I am able to know that a given other player knows about everyone else)
4) Evaluate Accusation Probability (based on my and possibly others' score cards)
5) Suggest Useful Rumor (based on my and others' score cards)
6) Write everyone's score cards to file
6) End game and output score cards to file

Thoughts:

- All you know at the beginning of the game is the cards in your own hand

- When there's a rumor:
    - go around in a circle and each person says whether or not they can disprove it: if they can, they show you
    one of the cards and you know they have that; if they can't, you know they have none of the cards in concern.
    - if you're not the asker, and someone cant disprove, you also know they have none of the cards, but if they can,
    you only know they have at least one of the cards, but not which one (here's where probability and fuzzy certainty
    enters the question).

    - for now, i'll just handle rumors asked by myself and register everything; or i'll handle rumors asked by others
    and only register non-disprovals

    Rumors that I asked:
        - have set of non-disprovals and possibly one disproval (consisting of the name of a card)
        - each response given by a player name

    Rumors that others have asked:
        - have set of non-disprovals and possibly one disproval (but no card name)
