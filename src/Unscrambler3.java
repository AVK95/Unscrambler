import java.io.File;
import java.util.Scanner;

public class Unscrambler3 {
	public static void main(String[] args) {
		Scanner in = new Scanner (System.in);
		WordListProcessor handle = new WordListProcessor ();;
		System.out.println("Welcome to Unscrambler 3.0!");
		
		File unprocessedList = new File ("words_alpha.txt");
		File processedList = new File ("WORDLIST.DAT");
		
		boolean alreadyProcessed = false;
		
		if (!unprocessedList.exists() && !processedList.exists()) {
			System.out.println("Could not locate neither the unprocessed word list nor the processed word list!");
			in.close();
			System.exit(0);
		} else if (unprocessedList.exists() && !processedList.exists()) {
			System.out.print("Processing wordlist \"words_alpha.txt\"...");
			handle.processWordList(unprocessedList);
			alreadyProcessed = true;
		}
		
		if (!processedList.exists()) {
			System.out.println("Failed to process \"words_alpha.txt\"!");
			in.close();
			System.exit(0);
		} else {
			if (alreadyProcessed) System.out.println(" Success!");
			System.out.print("Reading word list in memory...");
			if (!alreadyProcessed) handle.loadProcessedFile(processedList);
			System.out.println(" Success!");
		}
		
		boolean again = true;
		String jumbledWord;
		boolean unscramble;
		System.out.println();
		do {
			System.out.println("Enter the jumbled word (0 to exit program):");
			jumbledWord = in.next().strip();
			System.out.println();
			if (jumbledWord.equals("0"))
				again = false;
			else {
				unscramble = handle.unscramble(jumbledWord);
				if (!unscramble)
					System.out.println("No suitable match found!");
				/*else {
					System.out.println("Unscrambled word(s):");
					//System.out.println(unscrambledWord);
				}*/
			}
			System.out.println("-------------------------------------------");
		} while (again);
		
		System.out.println("Thank you for using Unscrambler!");
		in.close();
		System.exit(0);
	}
}
