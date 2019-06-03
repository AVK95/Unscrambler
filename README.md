<p><h1>Unscrambler</h1></p>
<br/>
<ul>
<li>A Simple JAVA console application to find all English words that match to a given sequence of letters. It takes a raw word list, such as the one provided ("words_alpha.txt") and creates a memory map with the Word and an integer array of its letter distribution. The map is sorted in memory according to this distribution array using QuickSort. On my PC, this takes around 0.5 seconds. I tested this with Insertion sort which took 15 minutes.</li>
<li>Program simply searches this map using the distribution array of the jumbled word as the key and uses binary search to look for all possible solutions. It does this by finding the first matching solution and then simultaneously moving left and right until it encounters a solution that is different. This process is near instant and independent of length of the word!</li>
<li>To compile, run javac *.java in the src/ folder and place the words_alpha.txt file in the same folder.
