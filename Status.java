public class Status implements Runnable{
    public static int numStatus = 0;
    private int highlight;
    private int menu;
    public Status(){
        numStatus++;
        highlight = Engine.HIGHLIGHT;
        menu = Engine.MENUC;
    }

    public void run(){
        System.out.println("\n");
        while (Engine.runStatus == true) {
            String pr = Engine.colorWrap(menu,highlight,"Num Crawlers: ") + Crawler.getNumCrawlers()
                + Engine.colorWrap(menu,highlight," Num URL: ") + Crawler.numUrls
                + Engine.colorWrap(menu,highlight," Num Visits: ") + Crawler.numVisits
                + Engine.restoreColor();
            System.out.print(pr);
            sleep(200);
            for(int i = 0; i < pr.length(); i++) {
                System.out.print("\r");
            }
        }
        System.out.println();
        numStatus--;
    }

    private void sleep(int time){
        try {
            Thread.sleep(time);
        } catch(InterruptedException ex) {
            Thread.currentThread().interrupt();
        }
    }
}