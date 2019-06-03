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
			if ((charVal >= 0) && (charVal <= 25))
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
