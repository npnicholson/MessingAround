import java.io.Serializable;
import java.io.*;
public class Word implements Serializable{

    public Word next;
    private String word;
    private UrlHolder head;
    private UrlHolder tail;
    private int numUrls;
    private String tmpPath;

    public Word(String str) {
        next = null;
        word = str;
        head = null;
        tail = null;
        tmpPath = Engine.TMPPATH;
        File theDir = new File(tmpPath + str + "/");
        //System.out.println(tmpPath + str + "/");
        tmpPath += str + "/";
        try{
            theDir.mkdirs();


        } 
        catch(SecurityException se){ } 

        File[] files = theDir.listFiles();
        numUrls = files.length;
        //System.out.println("NUM URLS: "+numUrls);
        files = null;
    }

    public void addUrl(String url) {
        //System.out.println("Word Name: " + word + "\t\tURL: " + url + "\t\tNumURL: " + numUrls);
        if (url != null) {
            //System.out.println("URL: " + url);
            if (numUrls == 0) {
                //System.out.println("New URL");
                //System.out.println("\t\tADDING NEW URL: " + url);
                head = new UrlHolder(url);
                numUrls++;
                tail = head;
                serialize(head);
            } else {
                recover();
                UrlHolder index = contains(url);
                if(index != null) {
                    //System.out.println("Existing URL");
                    index.inc();
                    sort(index);
                } else {
                    //System.out.println("New URL");
                        // TODO Place this new UrlHolder at the front of the
                        //      elements with a freq of 1 rather than the back.
                    //System.out.println("\t\tADDING NEW URL: " + url);
                    index = new UrlHolder(url);
                    
                    //System.out.println("\t\t"+numUrls+"\t"+(tail==null) + "\t"+word + "\t" + ((tail == null)? "" : tail.getUrl()));
                    numUrls++;
                    tail.next = index;
                    index.prev = tail;
                    tail = index;
                }
                serialize(index);
                unlink();
            }
        }
        //System.out.println("\t\tAfter: " + numUrls);
    }

    private int locator(UrlHolder u){
        UrlHolder crawl = head;
        for(int i = 0; i < numUrls; i++){
            if(crawl == u)
                return i;
            crawl = crawl.next;
        }
        return -1;
    }

    private UrlHolder contains(String url) {
        UrlHolder crawl = head;
        for(int i = 0; i < numUrls; i++) {
            if (crawl.getUrl().toLowerCase().equals(url)) {
                return crawl;
            } else {
                crawl = crawl.next;
            }
        }
        return null;
    }

    private void sort(UrlHolder elt) {
        if (numUrls > 1) {
            UrlHolder crawl = head;
            while (crawl.compareTo(elt) < 0) {
                crawl = crawl.next;
            }
            if (crawl != elt) { // put it in front of crawl;
                // Breaking Ties
                if (elt == tail) { // it is the tail
                    // brake ties
                    tail = elt.prev;
                    elt.prev.next = null;
                } else { // it is not the tail
                    // brake ties
                    elt.prev.next = elt.next;
                    elt.next.prev = elt.prev;
                }

                // Patching in
                if (crawl == head) { // moving to head
                    elt.prev = null;
                    head.prev = elt;
                    elt.next = head;
                    head = elt;
                } else {
                    crawl.prev.next = elt;
                    elt.prev = crawl.prev;
                    elt.next = crawl;
                    crawl.prev = elt;
                }
            } else {
                return;
            }
        } else {
            return;
        }
    }

    public String getWord() {
        return word;
    }

    public String[] getSites(int num) {
        recover();
        if(num > numUrls)
            num = numUrls;
        String[] arr = new String[num];
        UrlHolder crawl = head;
        for(int index = 0; index < num; index++){
            arr[index] = crawl.getUrl();
            crawl = crawl.next;
        }
        unlink();
        return arr;
    }

    public String[] getSites() {
        return getSites(numUrls);
    }

    public int compareTo(Word w) {
        return word.compareTo(w.getWord());
    }

    public int compareTo(String str) {
        return word.compareTo(str);
    }

    public String toString() {
        recover();
        String str = Engine.colorWrapR(Engine.MENUC,word) + "\n";

        UrlHolder crawl = head;
        for(int i = 0; i < numUrls; i++) {
            //System.out.println("START: " + crawl.getUrl());
            str += "\t" + crawl + "\n";
            //System.out.println(str);
            crawl = crawl.next;
        }
        unlink();
        return str;
    }

    public void serialize() {
        if (numUrls != 0) {
            UrlHolder crawl = head;
            head = null;
            for(int i = 0; i < numUrls; i++) {
                UrlHolder next = crawl.next;
                crawl.next = null;
                try{
                    FileOutputStream fileOut = new FileOutputStream(tmpPath + i + ".url");
                    ObjectOutputStream out = new ObjectOutputStream(fileOut);
                    out.writeObject(crawl);
                    out.close();
                    fileOut.close();
                }catch(IOException e){
                    e.printStackTrace();
                }
                crawl = next;
            }
            tail = null;
            crawl = null;
        }
    }

    public void serialize(UrlHolder u) {
        if (u != null) {
            int i = locator(u);
            if (i == -1)
                return;
            try{
                FileOutputStream fileOut = new FileOutputStream(tmpPath + i + ".url");
                ObjectOutputStream out = new ObjectOutputStream(fileOut);
                out.writeObject(u);
                out.close();
                fileOut.close();
            }catch(IOException e){
                e.printStackTrace();
            }
        }
    }

    public void unlink() {
        if (numUrls > 0) {
            UrlHolder crawl = head;
            head = null;
            for(int i = 0; i < numUrls; i++) {
                UrlHolder next = crawl.next;
                crawl.next = null;
                crawl = next;
            }
            tail = null;
            crawl = null;
        }
    }

    public void recover() {
        UrlHolder u = null;
        UrlHolder last = null;
        for(int i = 0; i < numUrls; i++) {
            //System.out.println("  " + i);
            try{
                FileInputStream fileIn = new FileInputStream(tmpPath + i + ".url");
                ObjectInputStream in = new ObjectInputStream(fileIn);
                u = (UrlHolder) in.readObject();
                in.close();
                fileIn.close();
            }catch(IOException e){
                e.printStackTrace();
            }catch(ClassNotFoundException e){
                e.printStackTrace();
            }

            /*if(u!=null)
                System.out.println("\t\t"+u.getUrl() + " :: " + u.getFreq());*/
            if (last != null)
                last.next = u;  

            if (i == 0) {
                head = u;
            }

            if (i == numUrls-1){
                tail = u;
            }
            last = u;
        }
        //System.out.println();
        u = null;
        last = null;
    }
}