# SpellChecker-Project
It is simple spellChecker that will provide user with some possible words for wrong forms and some little features for user.


We did this project for cs102
Our team consist of 5 people. Me and one of my friend did most of the project. One of my friend did the part to find wrong words and their true forms. I did almost all of the other parts ( mainly gui parts).
The author of each code file is written at the top.

in SpellChecker-Project/SpellChecker/src/View/freeWritingMode.java / 
We need to put this at the beginning, that is we need to create dictionary once when user open freeWritingMode page. Otherwise, when user click "Check" we recreate dictionary,
so there will be small lag because of process
"186			SpellCorrector corrector = new SpellCorrector();"


![image](https://user-images.githubusercontent.com/73609846/154993447-66ec9e6d-abcc-4f7e-9560-ec86a7b6d7ce.png)
![image](https://user-images.githubusercontent.com/73609846/154993777-e9e55e45-726e-40a8-ac3f-e91b51ccdff1.png)
