import org.jsoup.Jsoup;
import org.jsoup.helper.Validate;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.net.*;

public class JSoupTest{

    public static String html = "https://www.plus.google.com/ffdfffewqfqwefwef/fqww.fewqfw/efwq";
    public static void main(String[] args) throws MalformedURLException{
        /*Document doc = Jsoup.parse(html);
        /*Elements link = doc.text()
        for(Element e : link){
            System.out.println(e.text());
        }
        System.out.println(doc.text());*/

        URL lURL = new URL(html);
        String hostName = lURL.getHost();
        System.out.println(hostName);

        /*link = doc.select("*");
        for(Element e : link){
            System.out.println(e.text());
        }*/

        /*String text = doc.body().text(); // "An example link"
        String linkHref = link.attr("href"); // "http://example.com/"
        String linkText = link.text(); // "example""

        String linkOuterH = link.outerHtml(); 
            // "<a href="http://example.com"><b>example</b></a>"
        String linkInnerH = link.html(); // "<b>example</b>"*/
    }
}