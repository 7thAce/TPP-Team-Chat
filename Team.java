import java.util.ArrayList;

public class Team {

    private ArrayList<Move> moveList = new ArrayList<Move>(4);
    private ArrayList<String> userList = new ArrayList<String>();
    private int totalBet;
    private int activeBet;
    private String topBetter;

    public Team()
    {
        for (int i = 0; i <= 3; i++)
            moveList.add(0, new Move());
        totalBet = 0;
    }

    public ArrayList<Move> getMoveList()
    {
        return moveList;
    }

    public int getTotalBet()
    {
        return totalBet;
    }

    public int addTotalBet(int bet)
    {
        totalBet += bet;
        return totalBet;
    }

    public int addActiveBet(int bet)
    {
        activeBet += bet;
        return activeBet;
    }

    public int removeActiveBet(int bet)
    {
        activeBet -= bet;
        return activeBet;
    }

    public int getActiveBet()
    {
        return activeBet;
    }

    public ArrayList<String> getUserList()
    {
        return userList;
    }

    public boolean addToTeam(String name)
    {
        if (!userList.contains(name))
        {
            userList.add(name);
            return true;
        }
        else
        {
            return false;
        }
    }

    public String getTopBetter()
    {
        return topBetter;
    }

    public void setTopBetter(String newTopBetter)
    {
        topBetter = newTopBetter;
    }
}
