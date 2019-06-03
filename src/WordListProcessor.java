import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Scanner;

public class WordListProcessor {
	private String [] wordList;
	private Word [] processedList;
	public WordListProcessor () {}
	
	private void QsortList (int start, int end) {
		if (end > start) {
			int s = partition (start, end);
			QsortList (start, s - 1);
			QsortList (s + 1, end);
		}
	}
	
	//Uses Hoare's partitioning
	private int partition (int start, int end) {
		Word p = processedList [start];
		int i = start, j = end + 1;		
		do {
			do { i++; } while (processedList [i].compareTo(p) < 0);
			do { j--; } while (processedList [j].compareTo(p) > 0);
			swap (i, j);
		} while (i < j);
		swap (i, j);
		swap (start, j);
		return j;
	}
	
	//Insertion sort code just for comparision. Caution: Be prepared to spend 15+ minutes
	//with your CPU running at > 50% load!
	private void IsortList (int start, int end) {
		int progress = 0; //display the sorting progress after every 5% so you don't sleep
		Word v;
		int j;
		for (int i = start + 1; i <= end; i++) {
			v = processedList [i];
			j = i - 1;
			while ((j >= start) && (processedList [j].compareTo(v) > 0)) {
				processedList [j + 1] = processedList [j];
				j--;
			}
			processedList [j + 1] = v;
			if ((((i - start)*100) / (end - start)) - progress >= 5) {
				progress =(int) (((i - start)*100) / (end - start));
				System.out.println("Progress: " + progress + "%");
			}
		}
	}
	
	private void swap (int first, int second) {
		Word temp = processedList[first];
		processedList[first] = processedList[second];
		processedList[second] = temp;
	}
	
	private ArrayList <Word> binarySearch (Word w) {
		//searches for word w in the processedList
		Word A = null; //first occurence
		int l = 0, r = processedList.length - 1, m = 0;
		while (l <= r) { //simple iterative binary search to find the first occurence
			m = (l + r) / 2;
			A = processedList [m];
			if (w.equals(A)) break;
			else if (w.compareTo(A) < 0) r = m - 1;
			else l = m + 1;
		}
		if (!w.equals(A)) //we didn't find any occurences
			return null;
		else {
			ArrayList <Word> returnList  = new ArrayList <Word> ();
			int left = -1, right = -1;
			boolean goLeft = true, goRight = true;
			
			returnList.add(A);
			for (int i = 1; ; i++) {
				if (m >= i) left = m - i; //go left if that doesn't cause the index to become less than 0
				if (m + i < processedList.length) right = m + i; //similar check as above
				
				if (goLeft && (left != -1) && (processedList [left].equals(w)))
					returnList.add(processedList [left]);
				else goLeft = false; //we found an unequal word, no need to go further left
				
				if (goRight && (right != -1) && (processedList [right].equals(w)))
					returnList.add(processedList [right]);
				else goRight = false;
				
				if (!goLeft && !goRight) break; //very crude and bad way to implement this, I know
			}
			return returnList;
		}
	}
	
	public void processWordList (File unprocessed_file) {
		Scanner in;
		String [] wordList = null;
		ObjectOutputStream out;
		long startTime, endTime, totalTime = 0L; //some time shenanigans
		
		//Read the input (text-based) word list
		startTime = System.currentTimeMillis();
		try {
			in = new Scanner (new FileInputStream (unprocessed_file));
			in.useDelimiter("\\Z");
			wordList = in.next().split("\n"); //entire file is read
			in.close();
		} catch (Exception e) {
			System.out.println("Error occured with processing " + unprocessed_file.getName());
			System.exit(0);
		}
		
		//Skip this block, it doesn't contribute to the method logic: Time shenanigans start here
		endTime = System.currentTimeMillis();
		totalTime += endTime - startTime;
		System.out.print(wordList.length + " words read from disk in just " + (endTime - startTime) + " ms! ");
		if (totalTime < 175) System.out.println("(You must be using an SSD!)"); //based on some adhoc experimentation
		startTime = System.currentTimeMillis();
		//Start reading again
		
		processedList = new Word [wordList.length + 1]; //+1 to include a sentinel element at the end to not cause index out of bounds
		processedList [wordList.length] = new Word (""); //sentinel: the blank string is greater than any string for out comparator
								 //Check: compareTo method in class Word
		for (int i = 0; i < wordList.length; i++)
			processedList[i] = new Word (wordList[i]);
		QsortList (0, processedList.length - 2);
		//IsortList (0, processedList.length - 2); //Uncomment for insertion sort... say goodbye to next 15-20 mins
		
		//Skip this block, it doesn't contribute to the method logic: Time shenanigans start here
		endTime = System.currentTimeMillis();
		totalTime += endTime - startTime;
		System.out.println((processedList.length - 1) + " words sorted in just " + (endTime - startTime) + " ms thanks to the power of Quick Sort!");
		System.out.println("Total processing time for " + (processedList.length - 1) + " words: " + totalTime + " ms!");
		//Start reading again
		
		//OLD CODE: Was using this block of code to write the sorted list of words on the disk when I used Insertion sort
		//Quicksort is so fast; its actually faster to sort a list of 370k words in RAM then to read a sorted list from disk
		/*try {
			out = new ObjectOutputStream (new FileOutputStream (new File("WORDLIST.DAT")));
			out.writeObject(processedList);
			out.close();
		} catch (Exception e) {
			System.out.println("Error occured with creating WORDLIST.DAT");
			System.exit(0);
		}*/
	}
	
	//OLD CODE: Was using this method to read sorted word list into memory when I was using insertion sort
	//The loadProcessedFile method is used if we write the sorted list to a file
	/*public void loadProcessedFile (File processedFile) {
		ObjectInputStream in;
		
		try {
			in = new ObjectInputStream (new FileInputStream (processedFile));
			processedList = (Word []) in.readObject();
			in.close();
		} catch (Exception e) {
			System.out.println("Could not load WordList into memory!");
			System.exit(0);
		}
	}*/
	
	public boolean unscramble (String jumble) {		
		Word jumbledWord = new Word (jumble);
		ArrayList <Word> unscrambledWord = binarySearch (jumbledWord);
		if (unscrambledWord == null)
			return false;
		else {
			System.out.println("Unscrambled word(s):");
			for (int i = 0; i < unscrambledWord.size(); i++)
				System.out.println(unscrambledWord.get(i));
			return true;
		}	
	}
}
