import java.io.Serializable;
public class TableList  implements Serializable{

    private Word[] alph;
    private int numWords;

    public TableList() {
        String[] letters = {"a","b","c","d","e","f","g","h","i","j","k","l",
            "m","n","o","p","q","r","s","t","u","v","w","x","y","z","~"};
        alph = new Word[letters.length];
        for(int i = letters.length-1; i >= 0; i--) {
            alph[i] = new Word(letters[i]);
            if(i != letters.length-1)
                alph[i].next = alph[i+1];
        }
        alph[alph.length-1].next = alph[0];
        numWords = alph.length;
    }

    private Word addWord(String w) {
        Word index, prev;
        String word = w.toLowerCase();
        int firstLetter = word.charAt(0) - 97;
        if(firstLetter >= 0 && firstLetter <= 25) {
            index = alph[firstLetter];
            prev = index;
            while(index.compareTo(word) < 0){
                prev = index;
                index = index.next;
            }
            if (index.compareTo(word) > 0) {
                prev.next = new Word(word);
                prev.next.next = index;
                numWords++;
                return prev.next;
            } else {
                return index;
            }
        }
        return null;
    }

    public synchronized void add(String word, String url) {
        Word w = addWord(word);
        if (w != null) {
            w.addUrl(url);
        }
    }

    public synchronized Word getWord(String w) {
        Word index;
        String word = w.toLowerCase();
        int firstLetter = word.charAt(0) - 97;
        if(firstLetter >= 0 && firstLetter <= 25) {
            index = alph[firstLetter];
            while(index.compareTo(word) < 0){
                index = index.next;
            }
            if (index.compareTo(word) == 0) {
                return index;
            }
        }
        return null;
    }

    public synchronized String[] getSites(String w) {
        return getWord(w).getSites();
    }

    public int getNumWords() {
        return numWords;
    }

    public synchronized String toString() {
        String str = "";
        Word crawl = alph[0];
        for(int i = 0; i < numWords; i++) {
            str += crawl;
            crawl = crawl.next;
        }
        return str;
    }
}