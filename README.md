# SpellChecker-Project

We did this project for cs102
Our team consist of 5 people. 2 of us contribute enough. One of my friend did the part to find wrong words and their true forms. I did almost all of the other parts ( mainly gui parts).
The author of each code file is written at the top.

in SpellChecker-Project/SpellChecker/src/View/freeWritingMode.java / 
We need to put this at the beginning, that is we need to create dictionary once when user open freeWritingMode page. Otherwise, when user click "Check" we recreate dictionary,
so there will be small lag because of process
"186			SpellCorrector corrector = new SpellCorrector();"
