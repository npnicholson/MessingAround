import java.net.*; 
import java.io.*; 
import java.util.PriorityQueue; 
import org.jsoup.Jsoup; 
import org.jsoup.helper.Validate; 
import org.jsoup.nodes.Document; 
import org.jsoup.nodes.Element; 
import org.jsoup.select.Elements; 
import java.util.ArrayList; 
import java.util.Scanner;

public class Crawler { 
    private static CrawlSet urls = new CrawlSet(); 
    private static ArrayList<String> knownURLS;
    private static String logPath = "urls.txt";
    private static String logPathHTML = "urls-html.txt";
    private static String logPathText = "text.txt";
    private static int count = 0;
    private static String logText;
    private static String logHTML;
    private static String logHTMLText;
    private static String lastURL;
    private static String curURL;
    private static int urlCounter;

    public static void main(String[] args) { 
        knownURLS = fileIn(logPath);
        lastURL = "";
        curURL = args[0];
        urlCounter = 0;
        //domains = new ArrayList<Object[]>();
        urls.add(args[0]); 
        logText = "";
        logHTML = "";
        String u; 
        do {
            u = urls.remove(); 
            if(u != null) { 
                //System.out.println("Polling URL: " + u); 
                try { 
                    getUrl(u); 
                } catch (MalformedURLException m) { 
                    //System.out.println("ERR: " + m.getMessage()); 
                } catch (Exception e){}
            } 
        } while(u != null); 
        log(logText, logPath);
        log(logHTMLText, logPathText);
    } 

    private static void getUrl(String url) throws IOException{ 
        System.out.println(url);
        Links link = new Links(url); 
        URL lURL = new URL(url);
        lastURL = curURL;
        curURL = lURL.getHost();
        if(lastURL.equals(curURL)){
            urlCounter++;
        }else{
            urlCounter = 0;
        }

        if(urlCounter > 20){
            urls.removeContaining(curURL);
            System.out.println("REMOVED URL: " + curURL);
            curURL = "";
        } else {
        
            for(String u : link.getData()){ 
                //System.out.println("Found URL: " + u);
                if(!containsURL(u) && u.indexOf("#") == -1 && u.indexOf("?") == -1 && u.indexOf("facebook.com") == -1 && u.indexOf("twitter.com") == -1 
                    && u.indexOf("plus.google.com") == -1 && u.indexOf("youtube.com/channel") == -1 && u.indexOf("tumblr.com") == -1
                    && u.indexOf("blog/") == -1 && u.indexOf("search/") == -1 && u.indexOf("help/") == -1 && u.indexOf("support/") == -1 ){
                    count++;
                    urls.add(u);
                    knownURLS.add(u);
                    logText += u + "\n";
                }
            } 
            //logHTML += link.getHTML() + "\n";
            logHTMLText += "--URL: "+url+"\n"+link.getText()+"\n";
            if(logText.length()>10000){
                log(logText, logPath);
                logText = "";
                System.out.println(count);
            }

            if(logHTMLText.length()>10000){
                log(logHTMLText, logPathText);
                logHTMLText = "";
            }

            /*if(logHTML.length()>100000){
                log(logHTML, logPathHTML);
                logHTML = "";
            }*/
        }
    } 

    private static boolean containsURL(String in){
        for(String s : knownURLS){
            if(s.equals(in)){
                return true;
            }
        }
        return false;
    }

    public static void log(String s, String path){
        try(PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(path, true)))) {
            out.println(s);
            out.close();
        }catch (NullPointerException e){
            System.out.println(e); 
        }catch (IOException e) {}
    }

    private static ArrayList<String> fileIn(String fName) {
        ArrayList<String> temp = new ArrayList<String>();
        try {
            Scanner s = new Scanner(new File(fName));
            String line;
            while (s.hasNextLine()) {
                line = s.nextLine().toLowerCase();
                if(line.length() > 1)
                    temp.add(line.trim());
            }
        } catch (Exception e) {
            System.out.println("There was an unknown error.");
            System.out.println(e.getMessage());
        }
        return temp;
    }
}