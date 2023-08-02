# Inverted-Index & Positional Inverted Index
Inverted Index is a data structure which is used in information retrieval systems.
Inverted Index basically consists of 2 things:
a) Dictionary : Which stores [word:count]
b) Postings: Which stores [term: [index of docs]]

Given a list of documents, you can create a inverted index of documents, such that when a query is fired at the Retrieval System, your collection of docs can quickly retreive where the given query exists or not in the information.(Concept called as Boolean Retrieval)

Positional Inverted Index is like an add on inverted index.In addition to the inverted index information, it also stores the position of the word in the doc.
Positional Inverted Index consists of 2 data structures
a) Dictionary : Which stores [word:count]
b) PositionIndex: Which stores [term: [docX: PosY,PosZ] [docZ: PosA,PosB]]

So Position Index can be used to do proximity search, like suppose you queried "to be" so just search to the docs and then check if word "be" is the same doc and also check if its position is +1 of that of "to". So those will be the docs where "to be" would be occuring.


The above topics is related to information-retrieval.

The following project is developed in IntelliJ framework and Java language.
