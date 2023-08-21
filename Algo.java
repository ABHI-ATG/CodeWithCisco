import java.util.*;

public class Algo{
    int number;
    int[] val=new int[1001];
    int[] sz_sub=new int[1001];

    public Algo(int number){
        this.number=number;
    }
    
    public void init(){
        for(int i=1;i<number;i++){
            val[i]=i;
        }
    }
    public int find(int a){
        if(a==val[a])return a;
        return val[a]=find(val[a]);
    }
    public void merge(int a,int b){
        int x=find(a);
        int y=find(b);
        val[x]=y;
    }

    int dfs(int s, int par,int[] vis,ArrayList<ArrayList<Integer>> g)
    {
        sz_sub[s] = 1;
        for (int it : g.get(s))
        {
            if (it != par && vis[it] == 0)
            {
                sz_sub[s] += dfs(it, s, vis,g);
            }
        }
        return sz_sub[s];
    }

    int centroid(int s,ArrayList<ArrayList<Integer>> g)
    {
        int[] vis=new int[number];
        dfs(s, s,vis,g);
        while (true)
        {
            int tmp = 0;
            sz_sub[s] = 0;
            for (int it : g.get(s))
            {
                if (sz_sub[it] > number/2 && vis[it] == 0)
                {
                    tmp = 1;
                    s = it;
                    break;
                }
            }
            if (tmp == 0)
            {
                break;
            }
        }

        return s;
    } 

    public int process(ArrayList<ArrayList<Pair>> arr){

        // Minimum Spanning Tree;
        ArrayList<Pair> vec=new ArrayList<>();
        ArrayList<ArrayList<Integer>> gg=new ArrayList<>();
        for(int i=0;i<number;i++){
            gg.add(new ArrayList<>());
        }
        int cnt=1;
        for(ArrayList<Pair> it:arr){
            for(Pair p:it){
                vec.add(new Pair(cnt,p.b,p.dis));
            }
            cnt++;
        }
        Collections.sort(vec,(x,y)->x.dis-y.dis);
        int[] vis=new int[number];
        init();
        for(Pair t:vec){
            if(find(t.a)==find(t.b))continue;
            merge(t.a,t.b);
            gg.get(t.a).add(t.b);
            gg.get(t.b).add(t.a);
        }

        // Centroid
        int masterNOde=centroid(1,gg);
        System.out.println("Master Node selected: " + masterNOde);
        return masterNOde;
    }
}
