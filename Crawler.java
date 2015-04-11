

public class Crawler {

    private CrawlSet urlList;
    private int urlCount;
    private boolean run;

    private static int numCrawlers = 0;

    private static final int URL_COUNT_MAX = 20;
    private static final double ELAPSED_TIME_MAX = 4;

    public Crawler(String seed) {
        numCrawlers ++;
        urlList = new CrawlSet()
        urlList.add(seed);
    }

    public void crawl() {
        run = true;
        Links link;
        String url, curURL = seed, lastURL;
        while (run && urlList.size() > 0 && Engine.runThreads) {
            url = urlList.remove().trim();
            link = new Links(url);
            lastURL = curURL.trim();
            curURL = link.getUrl().getHost().trim();

            urlCount = 0;
            if(lastUrl.equals(curUrl)){
                urlCount++;
            }else{
                urlCount = 0;
            }
            System.out.println(curURL);

            if(urlCount > URL_COUNT_MAX || link.getElapsed() > ELAPSED_TIME_MAX){
                urls.removeContaining(curURL);
                System.out.println("REMOVED URL: " + curURL);
                curURL = "";
                urlCounter = 0;
            } else {

                ////
                for(String u : link.getData()){
                    if(u.indexOf("#") == -1 && u.indexOf("?") == -1
                        && u.substring(0,4).equals("http") && !containsURL(u)) {
                        urls.add(u);
                        knownURLS.add(u);
                        logText += u + "\n";
                    }
                }
                ////

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
}