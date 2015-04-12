import java.io.Serializable;
public class UrlHolder implements Serializable{

    public UrlHolder next;
    public UrlHolder prev;
    private String url;
    private int freq;

    public UrlHolder (String url) {
        this.url = url;
        freq = 1;
    }

    public void inc() {
        freq++;
    }

    public String getUrl() {
        return url;
    }

    public int getFreq() {
        return freq;
    }

    public int compareTo(UrlHolder other) {
        return other.getFreq() - this.freq;
    }

    public String toString() {
        return url + " :: " + Engine.setColor(Engine.HIGHLIGHT) + freq + Engine.restoreColor();
    }
}