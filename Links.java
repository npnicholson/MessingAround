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
public class Links {

    private ArrayList<String> data;
    private String html;
    private String text;

    public Links(String url) throws IOException{
        data = new ArrayList<String>();
        //System.out.println("belwqig");
        text = "";
        Document doc = Jsoup.connect(url).get();
        Elements links = doc.select("a[href]");

        for (Element link : links) {
            data.add(link.attr("abs:href"));
            //System.out.println("URL");
        }

        html = doc.html();
        links = doc.select("p");
        for(Element e : links){
            text += e.text()+"\n";
        }
    }

    public ArrayList<String> getData(){
        return data;
    }

    public String getHTML(){
        return html;
    }

    public String getText(){
        return text;
    }
}