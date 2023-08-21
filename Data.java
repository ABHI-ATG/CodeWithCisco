import java.util.*;
import java.io.*;


public class Data {
    int masterNOde;
    ArrayList<ArrayList<Pair>> arr;
    ArrayList<Integer> isActive;
    int currentNodeIndex;
    int number;
    int edges;
    int[] res=new int[1001];

    static Data d;

    public Data(){
        number=1001;
        isActive=new ArrayList<>();
        for(int i=0;i<number;i++){
            isActive.add(1);
        }
    }

    public void selectMasterNode() {
        try {
            Scanner sc=new Scanner(new File("masterNode.txt"));
            masterNOde=sc.nextInt();
            System.out.println("Master Node selected: " + masterNOde);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public int read(){
        arr=new ArrayList<>();
        int num=0;
        Set<Integer> st=new HashSet();
        try {
            Scanner read=new Scanner(new File("input.txt"));
            for(int i=0;i<this.number;i++){
                arr.add(new ArrayList<>());
            }
            while(read.hasNext()){
                this.edges++;
                int a=0,b=0,c=0;
                a=read.nextInt();
                b=read.nextInt();
                c=read.nextInt();
                st.add(a);
                st.add(b);
                st.add(c);
                System.out.println(a+" "+b);
                arr.get(a).add(new Pair(b,c));
                arr.get(b).add(new Pair(a,c));
            }
            num=st.size();
            for(int i=1;i<number;i++){
                if(arr.get(i).size()==0)isActive.set(i,0);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return num;
    }

    public void find(int a,int b){

        PriorityQueue<Pair> pq=new PriorityQueue<>((x,y)->x.dis-y.dis);
        pq.add(new Pair(a,0));
        ArrayList<Integer> dis=new ArrayList<>();
        ArrayList<Integer> val=new ArrayList<>();
        for(int i=0;i<number;i++){
            dis.add((int)1e9);
            val.add(0);
        }
        dis.set(a,0);
        val.set(a,a);


        while(!pq.isEmpty()){
            Pair tp=pq.poll();
            for(Pair node:arr.get(tp.b)){
                if(tp.dis+node.dis<dis.get(node.b) && res[node.b]==0){
                    pq.add(new Pair(node.b,tp.dis+node.dis));
                    dis.set(node.b,tp.dis+node.dis);
                    val.set(node.b,tp.b);
                }
            }
        }

        ArrayList<Integer> res=new ArrayList<>();

        Statistics v=new Statistics();

        int x=b;
        res.add(x);
        System.out.print("Distance is : ");
        if(dis.get(b)==(int)1e9){
            dis.set(b,-1);
        }
        System.out.println(dis.get(b));
        if(dis.get(b)==-1){
            res.clear();
            res.add(-1);
            return;
        }else{
            while(x!=a){
                x=val.get(x);
                res.add(x);
            }
            Collections.reverse(res);
            for(int ele:res){
                System.out.print(ele+" ");
            }
            System.out.println();
        }
        
        try {
            BufferedWriter bw=new BufferedWriter(new FileWriter("Stats.txt",true));
            bw.write(a+" "+b+"\n");
            String s="";
            for(int it:res){
                s+=it+" ";
            }
            System.out.println(s);
            bw.write(s+"\n");
            bw.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void checkActive(){
        for(int i=1;i<number;i++){
            System.out.println(i+" : "+isActive.get(i));
        }
    }

    public void editStatus(int node){
        isActive.set(node,(isActive.get(node)>0)?0:1);
        if(isActive.get(node)==1){
            System.out.println("Node is Active now");
        }else{
            System.out.println("Node " + node +" is Dead now");
        }
    }

    public void set(int master){
        try {
            BufferedWriter fw=new BufferedWriter(new FileWriter("masterNode.txt"));
            System.out.println("Master node is : "+master);
            masterNOde=master;
            fw.write(masterNOde+"");
            fw.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void restrict(ArrayList<Integer> a,int s,int d){
        for(int ele:a){
            res[ele]=1;
        }
        if(res[s]>0 || res[d]>0){
            System.out.println("Source and Destination Node is restricted");
            return;
        }
        find(s,masterNOde);
        find(masterNOde,d);
        for(int ele:a){
            res[ele]=0;
        }
    }
}