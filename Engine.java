import java.util.Scanner;

public class Engine {
    private static Scanner scan = new Scanner(System.in);



    public static boolean runThreads = true;

    public static void main (String[] args) {
        System.out.print("--Engine main--\nInit...");

        System.out.print("Done.\nStarting Crawlers...");

        System.out.println("Done.");

        boolean run = true, search = false;
        String in;
        while (run) {
            if (search) {
                System.out.print("Search > ");
                in = scan.nextLine();
                if (in.toLowerCase().equals("quit")) {
                    search = false;
                } else {
                    search(in);
                }
            } else {
                System.out.print("Engine > ");
                in = scan.nextLine();
                if (in.indexOf("?") != -1) {
                    help();
                } else if (in.toLowerCase().equals("search")) {
                  search = true;
                }
            }
        }
    }

    public static void help() {
        System.out.println("Help:");
        System.out.println("? - Displays this message");
        System.out.println("Search - Open the search dialog");
        System.out.println("Quit - Closes the program");
    }

    public static void search (String terms) {

    }
}