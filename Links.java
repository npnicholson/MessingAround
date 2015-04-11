import org.jsoup.Jsoup;
import org.jsoup.helper.Validate;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.util.ArrayList;
import java.io.IOException;
import java.net.URL;

/**
 * Example program to list links from a URL.
 */
public class Links {

    private ArrayList<String> data;
    private String html;
    private String text;
    private double elapsed;
    private URL url;

    public Links(String url) throws IOException{

        URL this.url = new URL(url);


        data = new ArrayList<String>();
        //System.out.println("belwqig");
        text = "";
        long time = System.nanoTime();
        Document doc = Jsoup.connect(url).get();
        elapsed = (System.nanoTime() - time)/1000000000.;

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

    public double getElapsed() {
        return elapsed;
    }

    public URL getUrl() {
        return url;
    }
}