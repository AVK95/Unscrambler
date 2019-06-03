<p><h1>Unscrambler</h1></p>
<br/>
<ul>
<li>A Simple JAVA console application to find all English words that match to a given sequence of letters. It takes a raw word list, such as the one provided ("words_alpha.txt") and creates a memory map with the Word and an integer array of its letter distribution. The map is sorted in memory according to this array using iterative QuickSort (otherwise this list 370k+ words will cause a stack overflow). The sorted map is written into a new file "WORDLIST.DAT".</li>
<li>On running the program subsequently, it simply loads this map into memory and uses binary search to look for all possible solutions by finding the first matching solution and then simultaneously moving left and right until it encounters a solution that is different. This process is near instant and independent of length of the word!</li>
<li>To compile, run javac *.java in the src/ folder and place the words_alpha.txt file in the same folder. The presorting may take upto 10 minutes but its a one-time thing!</li>
