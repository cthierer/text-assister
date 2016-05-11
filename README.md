Text Assister 
=============

Java implementation of an engine to predict words based on provided fragments.

Dependencies
------------

  - Java JDK 1.8 

Building
--------

Build using javac: 

```
mkdir bin
javac -sourcepath src -d bin src/com/github/cthierer/ta/app/TextAssister.java
```

Running
-------

Run using java: 

```
java -cp bin com.github.cthierer.ta.app.TextAssister
```

The application will prompt the user to select an option: 

  1.  **Train the engine**: enter a phrase to help the prediction engine 
  	  build a dictionary of words that it can use to identify predictions. 
  2.  **Search for matches**: search for one or more word matches, ordered by 
      confidence level.  
  3.  **Quit**: terminate the application. 
  
Implementation notes 
--------------------

This problem includes two parts: 
  1.  An efficient mechanism to search for candidate words that are similar to 
      a string fragment. 
  2.  The capability to learn and improve future matches by training the 
      algorithm. 

### Searching 

This implementation stores candidate words in a Tree Map, ordered 
alphabetically, where the key is the String value and the value is a Word 
object. A Tree structure provides for efficient searching (O(logN)) when the 
exact value being searched for is not known. In this case, we are searching for 
one or more values that are _similar_ to a given string. This similarity is 
determined using a custom Comparator. Since the values of the map are sorted 
alphabetically, we can start by finding the first value in the map that matches 
the Comparator, and traversing the tree until we find a value that no longer 
matches the Comparator. This implementation is left to the standard Java 
implementation of a Tree Map. 

## Training and confidence  

Tree data structures have additional costs for inserting new data, particularly 
if the tree must be re-balanced. However, this problem emphasizes the 
importance of searching for values; this implementation makes the trade-off of 
search performance for possibly degraded training performance. 

The implementation to train words is simple: break apart a training phrase, and 
for each word: 
  - If the word is already in the tree, increment the frequency counter for 
    the word. 
  - Otherwise, add the word to the tree with a frequency of 1. 
  
Confidence is based off of the frequency that a word occurs in a training 
phrase. The more times a word occurs, the higher confidence level the algorithm 
will have. 

Future exploration 
------------------

An alternative way to determine confidence would be to incorporate: 
  - Tracking which words often follow other words
  - Using user feedback (the word that was the "correct" match) to improve 
    future predictions
    
This could be implemented using a graph data structure, where each node is a 
word, and each edge has a direction and a weight reflecting how often one 
word followed another word. Once a user selected a word as a match, the weight 
of the edge connecting that word to the previous word would be incremented, 
allowing the application to "learn" and favor that word in the future. 

The application would have to keep track of the user's last word when 
predicting his next word, and use that as the root node for the next search. 

In order for this to be effective, the algorithm would have to be trained 
against a large training set of realistic data to populate the dictionary. 
Otherwise, there would be very few matches when the user uses the data. This 
large amount of data would have a negative impact on performance. 