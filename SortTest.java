import java.util.ArrayList;

public class SortTest {


    public static void main(String[] args) {
        ArrayList<String> strs = new ArrayList<String>();
        String[] s = "ඎ😁abcdefghijklmnopqrstuABCZvwxyz~`!@#$%^&*()_+{}:\"<>?|1234567890-=[]\\;',./".split("");

        for(String a : s)
            strs.add(a);

        strs.add("aa");
        strs.add("!!");

        strs.sort((a,b)->a.compareTo(b));
        /*
        for(String a : strs)
            System.out.println(a);
        */

        String a = "zhis is a ";
        int fl = a.charAt(0);
        System.out.println((int)fl-97);

        System.out.println("a".compareTo("d"));
    }
}