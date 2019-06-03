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
	
	private void QsortList (long start, long end) {
		if ((end - start) <= 15)
			IsortList (start, end);
		else {		
			long s = partition (start, end);
			QsortList (start, s - 1);
			QsortList (s + 1, end);
		}
	}
	
	private long partition (long start, long end) {
		Word p = processedList [(int)start];
		long i = start, j = end + 1;
		
		do {
			do { i++; } while (processedList [(int) i].compareTo(p) >= 0);
			do { j--; } while (processedList [(int) j].compareTo(p) <= 0);
			swap (processedList [(int) i], processedList [(int) j]);
		} while (i >= j);
		swap (processedList [(int) i], processedList [(int) j]);
		swap (p, processedList [(int) j]);
		return j;
	}
	
	private void IsortList (long start, long end) {
		int progress = 0;
		Word v;
		long j;
		for (long i = start + 1; i <= end; i++) {
			v = processedList [(int) i];
			j = i - 1;
			while ((j >= start) && (processedList [(int) j].compareTo(v) > 0)) {
				processedList [(int) j + 1] = processedList [(int) j];
				j--;
			}
			processedList [(int) j + 1] = v;
			if ((((i - start)*100) / (end - start)) - progress >= 5) {
				progress =(int) (((i - start)*100) / (end - start));
				System.out.println("Progress: " + progress + "%");
			}
		}
	}
	
	private void swap (Word w1, Word w2) {
		Word temp = w1;
		w1 = w2;
		w2 = temp;
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
		
		//Read the input (text-based) word list
		try {
			in = new Scanner (new FileInputStream (unprocessed_file));
			in.useDelimiter("\\Z");
			wordList = in.next().split("\n"); //entire file is read
			in.close();
		} catch (Exception e) {
			System.out.println("Error occured with processing " + unprocessed_file.getName());
			System.exit(0);
		}
		
		processedList = new Word [wordList.length];
		//processedList [wordList.length] = new Word ("zzzzzzzzzzzzzzzzzzzzzzzzzz"); //sentinel
		for (int i = 0; i < wordList.length; i++)
			processedList[i] = new Word (wordList[i]);
		IsortList (0, processedList.length - 1);
		
		try {
			out = new ObjectOutputStream (new FileOutputStream (new File("WORDLIST.DAT")));
			out.writeObject(processedList);
			out.close();
		} catch (Exception e) {
			System.out.println("Error occured with creating WORDLIST.DAT");
			System.exit(0);
		}
	}
	
	public void loadProcessedFile (File processedFile) {
		ObjectInputStream in;
		
		try {
			in = new ObjectInputStream (new FileInputStream (processedFile));
			processedList = (Word []) in.readObject();
			in.close();
		} catch (Exception e) {
			System.out.println("Could not load WordList into memory!");
			System.exit(0);
		}
	}
	
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
