import java.util.*;
import java.util.concurrent.TimeUnit;
import java.io.*;
import java.lang.reflect.Array;
import java.sql.Time;

public class Projects {
    int number;
    static Data d;
    static class Obj extends Thread{
        int a,b;
        public void set(int a,int b){
            this.a=a;
            this.b=b;
        }
        public void run(){
            d.find(a,d.masterNOde);
            d.find(d.masterNOde,b);
        }
    }

    public static void main (String[] args) throws Exception {
        Scanner sc=new Scanner(System.in);
        Statistics stats=new Statistics();
        d=new Data(); 
        int number=d.read();
        Algo al=new Algo(number);
        d.selectMasterNode();

        while(true){
            System.out.println("Enter 0 to exit : ");
            System.out.println("Enter 1 to find distance : ");
            System.out.println("Enter 2 to check Active Status : ");
            System.out.println("Enter 3 to edit Active Status : ");
            System.out.println("Enter 4 to send the data simultaneously : ");
            System.out.println("Enter 5 to check stats : ");
            System.out.println("Enter 6 to check active status of single node : ");
            System.out.println("Enter 7 to elect master node in the network : ");
            System.out.println("Enter 8 to restrict some nodes from path : ");
            System.out.println("Enter 9 to stop communication for some time : ");
            System.out.println("Enter 10 to add more edges : ");
            int n=sc.nextInt();
            if(n==1){
                System.out.println("Enter two nodes : ");
                int a,b;
                a=sc.nextInt();
                b=sc.nextInt();
                if(d.isActive.get(a)==0){
                    System.out.println("Source is Dead");
                    continue;
                }
                if(d.isActive.get(b)==0){
                    System.out.println("Reciver is Dead");
                    continue;
                }
                System.out.println("Reciver is Active");
                d.find(a,d.masterNOde);
                d.find(d.masterNOde,b);
            }
            else if(n==2){
                d.checkActive();
            }else if(n==3){
                System.out.println("Enter node to toggle : ");
                int a=sc.nextInt();
                d.editStatus(a);
            }
            else if(n==4){
                System.out.println("Enter number of node want to send the data");
                int num=sc.nextInt();
                for(int i=0;i<num;i++){
                    int a,b;
                    a=sc.nextInt();
                    b=sc.nextInt();
                    Obj o=new Obj();
                    o.set(a,b);
                    o.start();
                }
            }else if(n==5){
                stats.show();
            }else if(n==6){
                System.out.println("Enter node : ");
                int node=sc.nextInt();
                System.out.println("Node is : "+d.isActive.get(node));
            }else if(n==7){
                System.out.println("Processing : ");
                int master=al.process(d.arr);
                d.set(master);
            }else if(n==8){
                System.out.println("Enter number of nodes to restrict : ");
                int num=sc.nextInt();
                ArrayList<Integer> a=new ArrayList<>();
                for(int i=0;i<num;i++){
                    int x=sc.nextInt();
                    a.add(x);
                }
                System.out.println("Enter number source and destination : ");
                int s,des;
                s=sc.nextInt();
                des=sc.nextInt();
                d.restrict(a,s,des);

            }else if(n==9){
                TimeUnit.SECONDS.sleep(5);
            }else if(n==10){
                d.addEdge();
            }
            else break;
        }
    }
}