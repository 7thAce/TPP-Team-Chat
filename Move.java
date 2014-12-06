public class Move {

    private int betAmount;
    private double betPercent;

    public Move() {
        betAmount = 0;
        betPercent = 0.0;
    }

    public void addBet(int amount)
    {
        betAmount += amount;
    }

    public void removeBet(int amount)
    {
        betAmount -= amount;
    }

    public int getBetAmount()
    {
        return betAmount;
    }

    public double getBetPercent()
    {
        return betPercent;
    }

    public double calcPercent(int totalBet)
    {
        betPercent = (1.0 * betAmount) / totalBet;
        return betPercent;
    }
}
