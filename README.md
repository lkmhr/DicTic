#DicTic

**DicTic** is a simple Android Dictionay Appplication. The dictionary data/definition is taken from *Project Gutenburg's _OPTED_*.

##The Online Plain Text English Dictionary

OPTED is a public domain English word list dictionary, based on the public domain portion of "The Project Gutenberg Etext of Webster's Unabridged Dictionary" which is in turn based on the 1913 US Webster's Unabridged Dictionary. (See Project Gutenburg)

This version has been extensively stripped down and set out as one definition per line. All the Gutenburg EText tags and formatting have been removed by computer. Version 0.03 is a new processing of v0.47 of the websters dictionary and it has considerably fewer errors. Also the definition limit of 255 chars has been removed to give full justice of some of the more majestic of the originals. Some important errors in the parts-of-speech fields have been corrected and a lot of inflections/ alternatives and plurals that were missed due to software bugs in v0.01 and 0.02 are now included properly.


The dictionary is set as a word list with definitions, using minimal HTML markup. The only tags used are `<P>`, `<B>` and `<I>` and these serve to delimit the words (between `<B>`s) the part of speech or type (between `<I>`s) and the definitions (The rest of the line). Each entry is between a `<P>`, `</P>` pair. This will facilitate computer processing. The text was prepared on a macintosh, so the few accented and umlauted characters appear best if your browser is set to Western MacRoman encoding (this should look like an umlauted u : ü). If this causes problems and I get enough responses, I'll look into producing an ISO 8859-1 or even a Unicode version.

The dictionary can be viewed (with patience) directly online as you would a normal printed dictionary, otherwise a user can download the pages and process them in some way on their own machine. The only usage conditions are that if the material is redistributed, the content (not the formatting) remain in the public domain (ie free) and that the content be easily accessible in non-encoded plain text format at no cost to the end user. The origin of the content should also be acknowledged, including OPTED, Project Gutenburg and the 1913 edition of Webster's Unabridged Dictionary. If the material is to be included in commercial products, Project Gutenburg should be contacted first. There are no restrictions for personal or research uses of this material.

[Visit OPTED](http://www.mso.anu.edu.au/~ralph/OPTED/)

<hr/>
I have stored the dictionary definitions from HTML in *SQLite3* database named `dictionary_db` located in `assets\db\`. It is free to use as long as you coply with the [LICENSE](https://github.com/lkmhr/DicTic/blob/master/LICENSE.md)
