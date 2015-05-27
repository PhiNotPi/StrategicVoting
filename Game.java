import java.lang.Math;
import java.util.Random;
/**
 * Procedure for a single election
 * 
 * @author PhiNotPi 
 * @version 5/27/15
 */
public class Game
{
    Player[] players;
    int[][] payoffs;
    int[] totalPayoffs;
    int[] voteCounts;
    int votersRemaining;
    int debug;
    Random rnd;
    public Game(Player [] p, int dbg)
    {
        rnd = new Random();
        players = p;
        debug = dbg;
        shuffle(players);
        payoffs = new int[players.length][3];
        totalPayoffs = new int[]{0,0,0};
        for(int i = 0; i < players.length; i++){
            payoffs[i] = createPlayerPayoffs();
        }
        voteCounts = new int[]{0,0,0};
        votersRemaining = players.length;
        if(debug > 0){
            System.out.printf("%4d%4d%4d - %-40s%n",totalPayoffs[0],totalPayoffs[1],totalPayoffs[2],"Total");
            for(int i = 0; i < players.length; i++){
                System.out.printf("%4d%4d%4d - %-40s%n",payoffs[i][0],payoffs[i][1],payoffs[i][2],players[i].getName());
            }
        }
    }
    
    public void shuffle(Object[] ar)
    {
        for (int i = ar.length - 1; i > 0; i--)
        {
            int index = rnd.nextInt(i + 1);
            Object a = ar[index];
            ar[index] = ar[i];
            ar[i] = a;
        }
    }
    
    public int[] createPlayerPayoffs()
    {
        int cut1 = rnd.nextInt(101);
        int cut2 = rnd.nextInt(101);
        while(cut1 + cut2 > 100)
        {
          cut1 = rnd.nextInt(101);
          cut2 = rnd.nextInt(101);  
        }
        int rem = 100 - cut1 - cut2;
        int[] set = new int[]{cut1,cut2,rem};
        totalPayoffs[0] += set[0];
        totalPayoffs[1] += set[1];
        totalPayoffs[2] += set[2];
        return set;
    }
    
    public int[] copy(int[] source)  // I think this is needed to prevent players from modifying our stuff
    {
        int [] dest = new int[source.length];
        for(int i = 0; i<source.length; i++)
        {
            dest[i] = source[i];
        }
        return dest;
    }
    
    public double[] run()
    {
        for(int i = 0; i < players.length; i++)
        {
            int vote = players[i].getVote(copy(voteCounts),votersRemaining,copy(payoffs[i]),copy(totalPayoffs));
            if(vote >= 0 && vote <= 2)
            {
                voteCounts[vote]++;
            }
            else
            {
                voteCounts[rnd.nextInt(3)]++;
            }
            votersRemaining--;
        }
        int highest = 0;
        for(int count : voteCounts)
        {
            if(count > highest)
            {
                highest = count;
            }
        }
        boolean[] won = new boolean[]{false,false,false};
        int winCount = 0;
        double[] results = new double[players.length];
        for(int c = 0; c < 3; c++)
        {
            if(voteCounts[c] == highest)
            {
                winCount++;
                for(int p = 0; p < players.length; p++)
                {
                    results[p] += payoffs[p][c];
                }
            }
        }
        
        for(int p = 0; p < players.length; p++)
        {
            results[p] /= winCount;
            players[p].recieveResults(copy(voteCounts),results[p]);
        }
        return results;
    }
}
