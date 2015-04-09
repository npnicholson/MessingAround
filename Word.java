public class Word {

    public Word next;
    private String word;
    private UrlHolder head;
    private UrlHolder tail;
    private int numUrls;

    public Word(String str) {
        next = null;
        word = str;
        head = null;
        tail = null;
        numUrls = 0;
    }

    public void addUrl(String url) {
        if (url != null) {
            if (numUrls == 0) {
                head = new UrlHolder(url);
                numUrls++;
                tail = head;
            } else {
                UrlHolder index = contains(url);
                if(index != null) {
                    index.inc();
                    sort(index);
                } else { // TODO Place this new UrlHolder at the front of the
                         //      elements with a freq of 1 rather than the back.
                    index = new UrlHolder(url);
                    numUrls++;
                    tail.next = index;
                    index.prev = tail;
                    tail = index;
                }
            }
        }
    }

    private UrlHolder contains(String url) {
        if (numUrls != 0) {
            UrlHolder crawl = head;
            while(crawl != tail) {
                if (crawl.getUrl().equals(url)) {
                    return crawl;
                } else {
                    crawl = crawl.next;
                }
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

    public UrlHolder[] getSites(int num) {
        if(num > numUrls)
            num = numUrls;
        String[] arr = new String[num];
        UrlHolder crawl = head;
        for(int index = 0; index < num; index++){
            uh[i] = crawl.getUrl();
            crawl = crawl.next;
        }
        return arr;
    }



    public int compareTo(Word w) {
        return word.compareTo(w.getWord());
    }

    public int compareTo(String str) {
        return word.compareTo(str);
    }
}