import java.util.ArrayList;

public class InverseTable {
    private ArrayList<String> words;
    private ArrayList<ArrayList<String>> urls;

    public InverseTable() {
        words = new ArrayList<String>();
        urls = new ArrayList<ArrayList<String>>();
    }

    public void addWord(String word) {
        if(!words.contains(word)) {
            words.add(word);
            urls.add(new ArrayList<String>());
        }
    }

    public void addUrl(String word, String url) {
        int index = words.indexOf(word);
        if(index == -1) {
            addWord(word);
            addUrl(word,url);
        } else {
            if(!urls.get(index).contains(url))
                urls.get(index).add(url);
        }
    }

    public ArrayList<String> getWord(String word) {
        int index = words.indexOf(word);
        if(index != -1) {
            return urls.get(index);
        } else {
            return null;
        }
    }

    public int indexOf(String word) {
        return words.indexOf(word);
    }

    public boolean contains (String word) {
        return words.contains(word);
    }

    public String toString() {
        String str = "";
        for(int i = 0; i < words.size(); i++) {
            str += words.get(i) + "\n";
            for(String s : urls.get(i)) {
                str += "\t" + s + "\n";
            }
        }
        return str;
    }
}