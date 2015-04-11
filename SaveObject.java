import java.io.Serializable;
public class SaveObject implements Serializable{
    private char[] data;

    public SaveObject(String d){
        data = new char[d.length()];
        for(int i = 0; i < d.length(); i++){
            data[i] = d.charAt(i);
        }
    }

    public char[] getData(){
        return data;
    }
}