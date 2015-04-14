import java.util.Random;
import java.io.Serializable;
public class CrawlSet implements Serializable{
    private String[] contents;
    private final int SIZEINC = 100;
    private Random rand;
    private int count;

    public CrawlSet(int s){
        contents = new String[s];
        rand = new Random();
        count = 0;
    }

    public CrawlSet(){
        this(100);
    }

    public int size() { return count; }

    public synchronized boolean add(String elt) {
        if (!contains(elt)) {
            if (count == contents.length)
                expandCapacity();
            contents[count] = elt;
            count++;
            return true;
        }
        return false;
    }

    public synchronized boolean contains(String elt) {
        for (String t : contents)
            if (t==elt)
                return true;
        return false;
    }

    public synchronized boolean containsContent(String elt) {
        for (String t : contents)
            if (t != null && t.equals(elt))
                return true;
        return false;
    }

    private void expandCapacity() {
        String[] newArray = new String[count + SIZEINC];
        for(int i = 0; i < contents.length; i++)
            newArray[i] = contents[i];
        contents = newArray;
    }

    public synchronized String removeRandom(){
        if(count == 0)
            return null;
        int choice = rand.nextInt(count);
        String result = contents[choice];
        contents[choice] = contents[count-1];
        contents[count-1] = null;
        count--;
        return result;
    }

    public synchronized String remove(String elt){
        for(int i = 0; i < count; i++){
            if(contents[i].equals(elt)){
                String result = contents[i];
                contents[i] = contents[count-1];
                contents[count-1] = null;
                count--;
                return result;
            }
        }
        return null;
    }

    public synchronized String remove(){
        if(count == 0)
            return null;
        int choice = count-1;
        String result = contents[choice];
        contents[count-1] = null;
        count--;
        return result;
    }

    public synchronized String remove(int index) {
        if(count == 0)
            return null;
        String result = contents[index];
        contents[index] = contents[count-1];
        count--;
        return result;
    }

    public synchronized void removeContaining(String content){

        for(int i = 0; i < count; i++) {
            while(contents[i].indexOf(content) != -1) {
                remove(i);
            }
        }

        /*int prevCount = count;
        for(int i = 0; i < count; i ++){
            if(contents[i].indexOf(content)!=-1){
                contents[i]=null;
                count --;
            }
        }

        int remCount = 1;
        for(int i = 0; i < count; i ++){
            if(contents[i] == null){
                contents[i] = contents[prevCount - remCount];
                remCount++;
            }
        }*/
    }

}