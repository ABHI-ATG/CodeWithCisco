import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

public class Statistics{
    public void show(){
        try {
            Scanner read=new Scanner(new File("Stats.txt"));
            while(read.hasNextLine()){
                String[] s=read.nextLine().split(" ");
                System.out.println("Source is  : "+s[0]+" and Reciever is : "+s[1]);
                System.out.println("Path : "+read.nextLine());
                System.out.println();
            }   

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}