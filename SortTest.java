import java.util.ArrayList;

public class SortTest {


    public static void main(String[] args) {


        //System.out.println("zoo".compareTo("z"));

        TableList list = new TableList();

        String google = "http://www.google.com/";
        String yahoo = "http://www.yahoo.com/";
        String bing = "http://www.bing.com/";
        String nickguys = "http://www.nickguys.com/";

        list.add("the", google);
        list.add("the", google);
        list.add("the", google);

        //System.out.println("google");

        list.add("the", yahoo);
        list.add("the", yahoo);

        list.add("the", bing);
        list.add("the", bing);

        list.add("the", bing+" lol");

        list.add("zoo", google);

        System.out.println(list);

        /*for(String s : list.getWord("zoo").getSites()) {
            System.out.println(s);
        }*/
    }
}