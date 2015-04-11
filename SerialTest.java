import java.io.*;
public class SerialTest{
    public static void main(String[] args){
        if(args[0].equals("write")){
            try{
                SaveObject saveData = new SaveObject("This is fjafyegfjhsfhdabfhjwbchjafhjadhjvhjdskavfhjds adjs hjdsabcd bbdha dsbc dsb hbchjsabcjs dbcydsa fcatvwuwyabcs hawybcfu vc adsc avucg kadscas a test!!");
                FileOutputStream fileOut =
                new FileOutputStream("test.ser");
                ObjectOutputStream out = new ObjectOutputStream(fileOut);
                out.writeObject(saveData);
                out.close();
                fileOut.close();
                System.out.println("Serialized data is saved in test.ser");
            }catch(IOException i){
                i.printStackTrace();
            }
        }else if(args[0].equals("read")){
            SaveObject saveData;
            try{
                FileInputStream fileIn = new FileInputStream("test.ser");
                ObjectInputStream in = new ObjectInputStream(fileIn);
                saveData = (SaveObject) in.readObject();
                in.close();
                fileIn.close();
            }catch(IOException i){
                i.printStackTrace();
                return;
            }catch(ClassNotFoundException c){
                System.out.println("Employee class not found");
                c.printStackTrace();
                return;
            }
            for(char s : saveData.getData()){
                System.out.println(s);
            }
        }
    }
}