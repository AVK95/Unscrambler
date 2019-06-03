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
	
	private void IsortList (int start, int end) {
		int progress = 0;
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
		while (l <= r) {
			m = (l + r) / 2;
			A = processedList [m];
			if (w.equals(A)) break;
			else if (w.compareTo(A) < 0) r = m - 1;
			else l = m + 1;
		}
		if (!w.equals(A))
			return null;
		else {
			ArrayList <Word> returnList  = new ArrayList <Word> ();
			int left = -1, right = -1;
			boolean goLeft = true, goRight = true;
			
			returnList.add(A);
			for (int i = 1; ; i++) {
				if (m >= i) left = m - i;;
				if (m + i < processedList.length) right = m + i;
				
				if (goLeft && (left != -1) && (processedList [left].equals(w)))
					returnList.add(processedList [left]);
				else goLeft = false;
				
				if (goRight && (right != -1) && (processedList [right].equals(w)))
					returnList.add(processedList [right]);
				else goRight = false;
				
				if (!goLeft && !goRight) break;
			}
			return returnList;
		}
	}
	
	public void processWordList (File unprocessed_file) {
		Scanner in;
		String [] wordList = null;
		ObjectOutputStream out;
		long startTime, endTime, totalTime = 0L;
		
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
		endTime = System.currentTimeMillis();
		totalTime += endTime - startTime;
		System.out.print(wordList.length + " words read from disk in just " + (endTime - startTime) + " ms! ");
		if (totalTime < 175) System.out.println("(You must be using an SSD!)");
		
		startTime = System.currentTimeMillis();
		processedList = new Word [wordList.length + 1];
		processedList [wordList.length] = new Word (""); //sentinel
		for (int i = 0; i < wordList.length; i++)
			processedList[i] = new Word (wordList[i]);
		QsortList (0, processedList.length - 2);
		endTime = System.currentTimeMillis();
		totalTime += endTime - startTime;
		System.out.println((processedList.length - 1) + " words sorted in just " + (endTime - startTime) + " ms thanks to the power of Quick Sort!");
		System.out.println("Total processing time for " + (processedList.length - 1) + " words: " + totalTime + " ms!");
		
		//Quicksort is so fast, its faster than reading the sorted list from disk
		/*try {
			out = new ObjectOutputStream (new FileOutputStream (new File("WORDLIST.DAT")));
			out.writeObject(processedList);
			out.close();
		} catch (Exception e) {
			System.out.println("Error occured with creating WORDLIST.DAT");
			System.exit(0);
		}*/
	}
	
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
