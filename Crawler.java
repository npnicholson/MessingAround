
import java.io.IOException;
public class Crawler implements Runnable {

    private CrawlSet urlList;
    private int urlCount;
    private boolean run;
    private String seed;

    private static int numCrawlers = 0;
    public  static int numUrls = 0;
    public  static int numVisits = 0;

    private static final int URL_COUNT_MAX = 20;
    private static final double ELAPSED_TIME_MAX = 4;

    public Crawler(String seed) {
        numCrawlers ++;
        urlList = new CrawlSet();
        urlList.add(seed);
        this.seed = seed;
    }

    public void run(){
        crawl();
    }

    public void crawl() {
        run = true;
        Links link;
        String url, curURL = seed, lastURL;
        while (run && urlList.size() > 0 && Engine.runThreads) {
            url = urlList.remove().trim();
            numVisits++;
            //System.out.println(url);
            try{
                link = new Links(url);
            }catch(IOException e){
                continue;
            }
            lastURL = curURL.trim();
            curURL = link.getUrl().getHost().trim();

            urlCount = 0;
            if(lastURL.equals(curURL)){
                urlCount++;
            }else{
                urlCount = 0;
            }
            //System.out.println(curURL);

            if(urlCount > URL_COUNT_MAX || link.getElapsed() > ELAPSED_TIME_MAX){
                urlList.removeContaining(curURL);
                //System.out.println("REMOVED URL: " + curURL);
                curURL = "";
                urlCount = 0;
            } else {

                for(String u : link.getData()){
                    if(u.indexOf("#") == -1 && u.indexOf("?") == -1
                        && !Engine.knownURLS.containsContent(u)) {
                        urlList.add(u);
                        Engine.knownURLS.add(u);
                        numUrls++;
                        //logText += u + "\n";
                    }
                }

                parseWords(link.getText(), url);

            }

        }
        numCrawlers--;
    }

    public void kill() {
        run = false;
    }

    public static int getNumCrawlers() {
        return numCrawlers;
    }

    private void parseWords(String text, String url){
        if(!Engine.runThreads)
            return;

        new Thread(new Parser(text,url)).start();


    }
}