import java.util.StringTokenizer;
public class Parser implements Runnable {
    private String text;
    private String url;
    private static int numParsers = 0;
    public Parser(String text, String url) {
        this.text = text;
        this.url = url;
        numParsers++;
    }

    public void run(){
        StringTokenizer st = new StringTokenizer(text, " /\\…\",.;:?!()\n");

        while (Engine.runThreads && st.hasMoreElements()) {
            Engine.table.add(((String)st.nextElement()), url);
        }
        numParsers--;
    }

    public static int getNumParsers(){
        return numParsers;
    }
}