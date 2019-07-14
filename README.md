# Lucene-Autocompleter
A Lucene 8.0.0 API based java application that, given a snippet of word from the user, scans an english dictionary and tries to guess the whole word

An index is created based on an english dictionary that is later scanned to look out for five words based on the word snippet given by the user.

It takes some time to execute the first time due to the creation and then scanning of the index from the dictionary. After that, the execution time depends on your specs.

### Examples
![](http://i64.tinypic.com/8yylup.png)

![](http://i64.tinypic.com/2f04ryc.png)

English dictionary taken from [here](https://github.com/Coursal/crack-a-lackin/blob/master/words.english).

Essential Lucene 8.0.0 API `.jar` files included.
