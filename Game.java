public class Game {

    private Team redTeam;
    private Team blueTeam;

    public Game() {
        redTeam = new Team();
        blueTeam = new Team();
    }

    public Team getRedTeam()
    {
        return redTeam;
    }

    public Team getBlueTeam()
    {
        return blueTeam;
    }

}
