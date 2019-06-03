import java.io.File;
import java.util.Scanner;

public class Unscrambler3 {
	public static void main(String[] args) {
		Scanner in = new Scanner (System.in);
		WordListProcessor handle = new WordListProcessor ();;
		
		System.out.println("Welcome to Unscrambler 3.0!");
		File unprocessedList = new File ("words_alpha.txt");
		
		if (!unprocessedList.exists()) {
			System.out.println("Could not locate the word \"list words_alpha.txt\"...");
			in.close();
			System.exit(0);
		} else {
			System.out.println("Processing wordlist \"words_alpha.txt\"...");
			System.out.println();
			handle.processWordList(unprocessedList);
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
			}
			System.out.println("-------------------------------------------");
		} while (again);
		
		System.out.println("Thank you for using Unscrambler!");
		in.close();
		System.exit(0);
	}
}
