import java.io.Serializable;
public class SaveObject implements Serializable{
    private char[] data;
    private TableList t;
    public SaveObject(String d){
        data = new char[d.length()];
        t = new TableList();
        String google = "www.google.com";
        for(int i = 0; i < d.length(); i++){
            data[i] = d.charAt(i);
            t.add(String.valueOf(d.charAt(i)),google);
            t.add(String.valueOf(d.charAt(i)),google);
        }
    }

    public char[] getData(){
        return data;
    }

    public String toString(){
        return t.toString();
    }
}