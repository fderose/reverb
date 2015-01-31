# reverb
Reverb sample

The main class is Driver.

Pass your twitter username as a command line argument.

The file src/main/resources/stopwords.txt contains a list of stopwords (one
word per line) to filter out of tweets. Adding a word to this list will
cause no definitions to be retrieved for that word.

The order of the words and their definitions when output is:

First, words that start with q ordered alphabetically without respect to case.
Then, all other words ordered alphabetically without respect to case.

Language support level is Java 8 (so I can use stream functionality).
