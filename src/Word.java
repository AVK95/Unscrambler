import java.io.Serializable;

@SuppressWarnings("serial")
public class Word implements Comparable <Word>, Serializable {
	private String word;
	private int dbArray [];
	private int wordLen;
	
	Word (String myWord) {
		word = myWord.toLowerCase().strip();
		int charVal;
		wordLen = word.length();
		dbArray = new int [26];
		for (int i = 0; i < 26; i++)
			dbArray [i] = 0;
		
		for (int i = 0; i < wordLen; i++) {
			charVal = Character.getNumericValue(word.charAt(i)) - 10; //getNumericValue returns between 10 and 35 (both inclusive)
			if ((charVal >= 0) && (charVal <= 25)) //very important because users sometimes enter non-alphabetical characters
							       //just to "test" the system
				dbArray [charVal]++;
		}
	}
	
	public String toString () {
		return word;
	}
	
	public boolean equals (Object other) {
		if (other == null) return false;
		else if (getClass() != other.getClass()) return false;
		else {
			Word otherWord = (Word) other;
			if (wordLen != otherWord.wordLen) return false;
			for (int i = 0; i < 26; i++)
				if (dbArray[i] != otherWord.dbArray[i])
					return false;
			return true;
		}
	}
	
	/********************************How this works? Lets compare aa and ab:***************************************\
	For the first word, dbArray [0] is higher, so the word must come earlier in the lex ordering, hence we return -1.
	Hence scanning across the dbArray of the two words A and B, if we find an entry dbArray [i] that is higher
	in A, then A must come before in lex sequence. This idea is not wholly correct however, since aaa will be
	placed before aa, or bcqqq will come before bcq by this logic. But this OK, since our method gives a comparator
	that satisties the two key rules: (A < B => B > A) and vice versa and also (! (A < B) && ! (A > B) => (A == B))
	\**************************************************************************************************************/
	public int compareTo (Word other) {
		if (other == null) 
			throw new NullPointerException ();
		else if (getClass() != other.getClass()) 
			throw new ClassCastException ();
		else {
			for (int i = 0; i < 26; i++) {
				if (dbArray [i] > other.dbArray[i])
					return -1;
				else if (dbArray [i] < other.dbArray[i])
					return 1;
			}
			return 0;
		}
	}
}
