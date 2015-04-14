import java.util.Scanner;
import java.io.*;
import java.util.ArrayList;
public class Engine {
    private static Scanner scan = new Scanner(System.in);
    public static CrawlSet knownURLS;
    public static TableList table = new TableList();

    public static final int MENUC = 6;
    public static final int PROG = 2;
    public static final int HIGHLIGHT = 9;

    public static boolean runThreads = true;
    public static boolean runStatus = false;

    public static final String TMPPATH = "wordData/";
    public static void main (String[] args) {
        System.out.print(colorWrapR(MENUC,"--Engine main--\nInit..."));

        File theDir = new File("engine/");
        try{
            theDir.mkdirs();
        } 
        catch(SecurityException se){ } 
        try{
            knownURLS = (CrawlSet) recover("engine/knownURLS.bin");
        } catch(IOException e){}
        if(knownURLS == null) {
            knownURLS = new CrawlSet();
            System.out.println("\n\tCreating new url list...");
        }

        System.out.print(colorWrap(HIGHLIGHT,MENUC,"Done.") + "\nStarting Crawlers...");

        System.out.println(colorWrapR(HIGHLIGHT,"Done."));

        boolean run = true, search = false;
        String in;
        while (run) {
            if (search) {
                System.out.print(colorWrapR(PROG,"Search > "));
                in = scan.nextLine();
                if (in.toLowerCase().equals("quit")) {
                    search = false;
                } else {
                    search(in);
                }
            } else if (runStatus) {
                System.out.print(colorWrapR(PROG,"Status > "));
                new Thread(new Status()).start();
                scan.nextLine();
                runStatus = false;
                try {
                    Thread.sleep(200);
                } catch(InterruptedException ex) {
                    Thread.currentThread().interrupt();
                }
            } else {
                System.out.print(colorWrapR(PROG,"Engine > "));
                in = scan.nextLine();
                if (in.indexOf("?") != -1) {
                    help();
                } else if (in.toLowerCase().equals("search")) {
                    search = true;
                } else if (in.toLowerCase().equals("status")) {
                    runStatus = true;
                } else if (in.toLowerCase().equals("table")) {
                    System.out.println("\n" + table + "\n");
                } else if (in.toLowerCase().equals("quit")) {
                    quit();
                    run = false;
                } else if (in.toLowerCase().equals("crawl")) {
                    new Thread(new Crawler("http://www.nickguys.com/")).start();
                } else if (in.toLowerCase().indexOf("crawl") != -1) {
                    String seed = in.toLowerCase().substring(in.toLowerCase().indexOf(" "));
                    if(seed.indexOf("http") == -1) {
                        seed = "http://" + seed;
                    }
                    new Thread(new Crawler(seed)).start();
                }
            }
        }
    }

    public static void help() {
        System.out.println("Help:");
        System.out.println("\t" + colorWrapR(MENUC,"?") + " - Displays this message");
        System.out.println("\t" + colorWrapR(MENUC,"Search") + " - Open the search dialog");
        System.out.println("\t" + colorWrapR(MENUC,"Table") + " - View the Table of known words");
        System.out.println("\t" + colorWrapR(MENUC,"Status") + " - See status information");
        System.out.println("\t" + colorWrapR(MENUC,"Crawl \"seed\"") + " - Starts a new Crawler with the \"seed\"");
        System.out.println("\t" + colorWrapR(MENUC,"Quit") + " - Closes the program");
    }

    public static void search (String terms) {
        String[] words = terms.split(" ");
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
    }

    private static void quit(){
        runThreads = false;

        System.out.print(colorWrap(MENUC,HIGHLIGHT,"Attempting to close threads..."));

        while(Crawler.getNumCrawlers() + Parser.getNumParsers() != 0){
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) { }
        }

        System.out.println("Done." + restoreColor());
        serialize(knownURLS,"engine/knownURLS.bin");
        //System.out.println(table);
    }

    public static String setColor(int n) {
        return "\033[38;5;"+n+"m";
    }

    public static String restoreColor() {
        return "\033[38;5;231m";
    }

    public static String colorWrap(int before, int after, String str) {
        return setColor(before) + str + setColor(after);
    }

    public static String colorWrapR(int before, String str) {
        return setColor(before) + str + restoreColor();
    }

    public static void serialize(Object o, String filename){
        try{
            //File theDir = new File(filename);
            //theDir.mkdirs();
            FileOutputStream fileOut = new FileOutputStream(filename);
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(o);
            out.close();
            fileOut.close();
            //System.out.println("Serialized data is saved in "+filename);
        }catch(IOException i){
            i.printStackTrace();
        }//catch(SecurityException se){ } 
    }

    public static Object recover(String filename) throws IOException {
        Object saveData;
        try{
            FileInputStream fileIn = new FileInputStream(filename);
            ObjectInputStream in = new ObjectInputStream(fileIn);
            saveData = in.readObject();
            in.close();
            fileIn.close();
            return saveData;
        } catch (ClassNotFoundException e) {
            return null;
        }
        
    }
}