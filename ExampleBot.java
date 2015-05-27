import java.lang.Math;
import java.util.Random;
/**
 * This bot picks the candidate wth the highest personal payoff, ignoring everyone else's actions.
 * 
 * @author PhiNotPi 
 * @version 5/27/15
 */
public class ExampleBot implements Player
{
    Random rnd;
    String name;
    /**
     * Constructor for objects of class ExampleBot
     */
    public ExampleBot(int e)
    {
       rnd = new Random(); 
       name = "ExampleBot" + rnd.nextInt(1000);
    }

    public String getName()
    {
        return name;
    }
    
    public int getVote(int [] voteCounts, int votersRemaining, int [] payoffs, int[] totalPayoffs)
    {
        //return rnd.nextInt(3);
        int maxloc = 0;
        for(int i = 1; i< 3; i++)
        {
            if(payoffs[i] > payoffs[maxloc])
            {
                maxloc = i;
            }
        }
        return maxloc;
    }
    
    public void recieveResults(int[] voteCounts, double result)
    {
        
    }
}
