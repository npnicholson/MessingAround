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

    private CrawlSet urls = new CrawlSet();
    private TableList table = new TableList();
    private StringTokenizer st;

    private ArrayList<String> knownURLS;
    private String logPath = "urls.txt";
    private String tablePath = "table.txt";
    private String logPathHTML = "urls-html.txt";
    private String logPathText = "text.txt";
    private String logText;
    private String logHTML;
    private String logHTMLText;
    private String lastURL;
    private String curURL;
    private int urlCounter;

    private Scanner scan = new Scanner(System.in);
    private int count = 0;

    public Crawler(String seed){
        knownURLS = fileIn(logPath);
        lastURL = "";
        curURL = seed;
        urlCounter = 0;
        //domains = new ArrayList<Object[]>();
        urls.add(args[0]);
        logText = "";
        logHTML = "";
        crawl();

        log(logText, logPath);
        log(logHTMLText, logPathText);
        log("Table: \n" + table, tablePath);


        // --------- //

        search();

    }

    public void crawl(){
        u = urls.remove();
        while (u != null) {
            count++;
            if(count > 100)
                break;
            //System.out.println("Polling URL: " + u);
            try {
                getUrl(u);
            } catch (MalformedURLException m) {
                //System.out.println("ERR: " + m.getMessage());
            } catch (Exception e){}
            u = urls.remove();
        }
    }

    public void search(){
        System.out.print("Enter phrase to search: ");
        String in = scan.nextLine();

        String[] words = in.split(" ");
        String[] list;

        ArrayList<String> us = new ArrayList<String>();
        for(String s : words) {
            list = table.getSites(s);
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

        // --------- //
    }

    private void getUrl(String url) throws IOException{
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

        if(urlCounter > 20 || elapsed > 4){
            urls.removeContaining(curURL);
            System.out.println("REMOVED URL: " + curURL);
            curURL = "";
            urlCounter = 0;
        } else {

            for(String u : link.getData()){
                if(u.indexOf("#") == -1 && u.indexOf("?") == -1
                    && u.substring(0,4).equals("http") && !containsURL(u)) {
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

    private void parseWords(String words, String url) {
        st = new StringTokenizer(words, " ,.;:?!()\n");

        while (st.hasMoreElements()) {
            table.add(((String)st.nextElement()), url);
        }
    }

    private boolean containsURL(String in){
        for(String s : knownURLS){
            if(s.equals(in)){
                return true;
            }
        }
        return false;
    }

    public void write(String s, String path) {
        try {
            PrintWriter writer = new PrintWriter(new BufferedWriter(
                new FileWriter(path, false)));
            writer.print("");
            writer.close();
            log(s,path);
        } catch (NullPointerException e){
            System.out.println(e);
        } catch (IOException e) { }
    }

    public void log(String s, String path){
        try(PrintWriter out = new PrintWriter(new BufferedWriter(
            new FileWriter(path, true)))) {
            out.println(s);
            out.close();
        }catch (NullPointerException e){
            System.out.println(e);
        }catch (IOException e) {
            System.out.println("Error writing to file: " + path);
        }
    }

    private ArrayList<String> fileIn(String fName) {
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