import java.net.*;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import org.jsoup.Jsoup;
import org.jsoup.helper.Validate;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.util.ArrayList;

public class Crawl{
    public Crawl(){

    }

    public static void main(String[] args) {
        URL url;
        InputStream is = null;
        BufferedReader br;
        String line;

        try {
            String urlString = "http://www.magickeys.com/books/";
            url = new URL(urlString);
            Links l = new Links(urlString);

            ArrayList<String> data = l.getData();
            System.out.println(data.size());


            for(String s : data){
                System.out.println(s);
            }

            //System.out.println(new Paragraphs(urlString));

            Document doc = Jsoup.parse(urlString);
            String text = doc.select("a").text();
            System.out.println(text);

            /*PrintWriter pw = new PrintWriter("output.txt");
            while (false) {
                pw.println(line);
            }
            pw.close();*/


        } catch (Exception e) {
            e.getMessage();
        }
    }


}