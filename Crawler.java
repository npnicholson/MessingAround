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

import java.util.StringTokenizer;

public class Crawler {
    private static CrawlSet urls = new CrawlSet();
    private static InverseTable table = new InverseTable();
    private static StringTokenizer st;

    private static ArrayList<String> knownURLS;
    private static String logPath = "urls.txt";
    private static String tablePath = "table.txt";
    private static String logPathHTML = "urls-html.txt";
    private static String logPathText = "text.txt";
    private static String logText;
    private static String logHTML;
    private static String logHTMLText;
    private static String lastURL;
    private static String curURL;
    private static int urlCounter;

    private static Scanner scan = new Scanner(System.in);

    private static int count = 0;

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
                count++;
                if(count > 100)
                    break;
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
        log("Table: \n" + table.toString(), tablePath);

        System.out.print("Enter phrase to search: ");
        String in = scan.nextLine();

        String[] words = in.split(" ");
        ArrayList<String> list;

        ArrayList<String> us = new ArrayList<String>();
        for(String s : words) {
            list = table.getWord(s);
            if(list == null)
                continue;

            for(String a : list) {
                us.add(a);
            }
        }


        ArrayList<String> fin = new ArrayList<String>();
        ArrayList<Integer> finint = new ArrayList<Integer>();
        Integer tmp; int index; int max = 0; int tmpint; int maxIndex = -1;
        for(int i = 0; i < us.size(); i++) {
            index = fin.indexOf(us.get(i));
            if(index != -1) {
                tmpint = finint.get(index)+1;
                tmp = new Integer(finint.get(index)+1);
                finint.set(index,tmp);

                if(tmpint > max) {
                    max = tmpint;
                    maxIndex = index;
                }
            }else{
                fin.add(us.get(i));
                finint.add(new Integer(1));
            }
        }

        System.out.println("Result: " + fin.get(maxIndex) + "\n\n");

        for(int i = 0; i < fin.size(); i++) {
            System.out.println(fin.get(i) + " :: " + finint.get(i));
        }


    }

    private static void getUrl(String url) throws IOException{
        //System.out.print();
        long time = System.nanoTime();
        Links link = new Links(url);
        URL lURL = new URL(url);
        double elapsed = (System.nanoTime() - time)/1000000000.;
        lastURL = curURL.trim();
        curURL = lURL.getHost().trim();


        if(lastURL.equals(curURL)){
            urlCounter++;
        }else{
            urlCounter = 0;
        }

        System.out.println(curURL + " (" + urlCounter + ") [" + (elapsed) + "] :: " + url);

        if(urlCounter > 20 || elapsed > 2){
            urls.removeContaining(curURL);
            System.out.println("REMOVED URL: " + curURL);
            curURL = "";
            urlCounter = 0;
        } else {

            for(String u : link.getData()){
                if(u.indexOf("#") == -1 && u.indexOf("?") == -1 && u.substring(0,4).equals("http") && !containsURL(u)) {
                    urls.add(u);
                    knownURLS.add(u);
                    logText += u + "\n";
                }
            }

            parseWords(link.getText(), url);

            logHTMLText += "--URL: "+url+"\n"+link.getText()+"\n";
            if(logText.length()>10000){
                //log(logText, logPath);
                //logText = "";
            }

            if(logHTMLText.length()>10000){
                //log(logHTMLText, logPathText);
                //logHTMLText = "";
                //log("Table: \n" + table.toString(), tablePath);
            }
        }
    }

    private static void parseWords(String words, String url) {
        st = new StringTokenizer(words, " ,.;:?!()\n");

        while (st.hasMoreElements()) {
            table.addWord(((String)st.nextElement()).toLowerCase());
            table.addUrl(((String)st.nextElement()).toLowerCase(), url);
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

    public static void write(String s, String path) {
        try {
            PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter(path, false)));
            writer.print("");
            writer.close();
            log(s,path);
        } catch (NullPointerException e){
            System.out.println(e);
        } catch (IOException e) {}
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