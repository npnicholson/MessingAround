import org.jsoup.Jsoup;
import org.jsoup.helper.Validate;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.util.ArrayList;
import java.io.IOException;

/**
 * Example program to list links from a URL.
 */
public class Paragraphs {

    private String text;
    public Paragraphs(String url) throws IOException{
        Document doc = Jsoup.parse(url);
        text = doc.select("p").text();
    }

    public String getData(){
        return text;
    }

    public String toString(){
        return text;
    }

}