import org.jibble.pircbot.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class Core extends PircBot
{
    private boolean inGame;
    private boolean acceptingBets;

    private ArrayList<String> teamRed = new ArrayList<String>();
    private ArrayList<String> teamBlue = new ArrayList<String>();
    private HashMap<String, String> userMoveMap = new HashMap<String, String>();
    private HashMap<String, Integer> userBetMap = new HashMap<String, Integer>();

    private int redA;
    private int redB;
    private int redC;
    private int redD;

    private int blueA;
    private int blueB;
    private int blueC;
    private int blueD;

    private double redPctA;
    private double redPctB;
    private double redPctC;
    private double redPctD;

    private double bluePctA;
    private double bluePctB;
    private double bluePctC;
    private double bluePctD;

    private int redActiveBetTotal;
    private int blueActiveBetTotal;

    TeamChatGUI gui;

    public Core() { }
    public Core(String username)
    {
        setLogin(username);
        setName(username);
        int port = 6667;
        gui = new TeamChatGUI();

        try {
            connect("irc.twitch.tv", port, "");

        } catch (IOException e) {
            System.out.println("Unable to connect to IRC.");
            e.printStackTrace();
        } catch (IrcException e) {
            System.out.println("Unable to connect to twitch.");
            e.printStackTrace();
        }
        joinChannel("#twitchplayspokemon");
        inGame = false;
        acceptingBets = false;
    }

    public void onMessage(String channel, String sender, String login, String hostname, String message)
    {
        if (message.endsWith("won the match!") && sender.equalsIgnoreCase("tppinfobot"))
        {
            inGame = true;
            acceptingBets = true;
            teamRed.clear();
            teamBlue.clear();
            userBetMap.clear();
            userMoveMap.clear();

            redA = 0;
            redB = 0;
            redC = 0;
            redD = 0;

            blueA = 0;
            blueB = 0;
            blueC = 0;
            blueD = 0;

            redPctA = 0.0;
            redPctB = 0.0;
            redPctC = 0.0;
            redPctD = 0.0;

            bluePctA = 0.0;
            bluePctB = 0.0;
            bluePctC = 0.0;
            bluePctD = 0.0;

            redActiveBetTotal = 0;
            blueActiveBetTotal = 0;
            gui.redBox.setText("");
            gui.blueBox.setText("");

            gui.redBarA.setValue(0);
            gui.redBarB.setValue(0);
            gui.redBarC.setValue(0);
            gui.redBarD.setValue(0);

            gui.blueBarA.setValue(0);
            gui.blueBarB.setValue(0);
            gui.blueBarC.setValue(0);
            gui.blueBarD.setValue(0);

            gui.redPctA.setText("0%");
            gui.redPctB.setText("0%");
            gui.redPctC.setText("0%");
            gui.redPctD.setText("0%");

            gui.bluePctA.setText("0%");
            gui.bluePctB.setText("0%");
            gui.bluePctC.setText("0%");
            gui.bluePctD.setText("0%");

            gui.blueText.setText("Blue Total Active Bets: " + blueActiveBetTotal);
            gui.redText.setText("Red Total Active Bets: " + redActiveBetTotal);

        }

        if (message.endsWith("has just begun!") && sender.equalsIgnoreCase("tppinfobot"))
        {
            acceptingBets = false;
        }

        if (!inGame)
            return;

        String[] messageArgs = message.split(" ");
        if (messageArgs[0].equalsIgnoreCase("!bet") && (!teamRed.contains(sender) && !teamBlue.contains(sender)) && acceptingBets && inGame)
        {
            if (messageArgs.length >= 3)
            {
                int betCount;
                try {
                betCount = Integer.parseInt(messageArgs[1]);
                } catch (Exception e)
                { return; }

                String team = messageArgs[2];
                if (!team.equalsIgnoreCase("red") && !team.equalsIgnoreCase("blue"))
                    return;
                if (team.equalsIgnoreCase("red"))
                {
                    teamRed.add(sender);
                    userMoveMap.put(sender, "-");
                    userBetMap.put(sender, betCount);
                }
                if (team.equalsIgnoreCase("blue"))
                {
                    teamBlue.add(sender);
                    userMoveMap.put(sender, "-");
                    userBetMap.put(sender, betCount);
                }
            }
        }
        if (teamRed.contains(sender))
        {
            if (message.startsWith("!move"))
            {
                String[] moveArgs = message.split(" ");
                if (moveArgs.length >= 2)
                {
                    String move = moveArgs[1];
                    if (move.equalsIgnoreCase("a") || move.equalsIgnoreCase("b") || move.equalsIgnoreCase("c") || move.equalsIgnoreCase("d") || move.equalsIgnoreCase("-"))
                    {
                        String prevMove = userMoveMap.get(sender);
                        if (prevMove.equalsIgnoreCase("a"))
                            redA -= userBetMap.get(sender);
                        if (prevMove.equalsIgnoreCase("b"))
                            redB -= userBetMap.get(sender);
                        if (prevMove.equalsIgnoreCase("c"))
                            redC -= userBetMap.get(sender);
                        if (prevMove.equalsIgnoreCase("d"))
                            redD -= userBetMap.get(sender);

                        if (move.equalsIgnoreCase("a"))
                            redA += userBetMap.get(sender);
                        if (move.equalsIgnoreCase("b"))
                            redB += userBetMap.get(sender);
                        if (move.equalsIgnoreCase("c"))
                            redC += userBetMap.get(sender);
                        if (move.equalsIgnoreCase("d"))
                            redD += userBetMap.get(sender);

                        userMoveMap.put(sender, move);

                        System.out.println(sender + " switched from " + prevMove + " to " + move);
                    }
                    redActiveBetTotal = redA + redB + redC + redD;
                    redPctA = redA * 1.0 / redActiveBetTotal;
                    redPctB = redB * 1.0 / redActiveBetTotal;
                    redPctC = redC * 1.0 / redActiveBetTotal;
                    redPctD = redD * 1.0 / redActiveBetTotal;
                    gui.redPctA.setText(Math.round(redPctA * 100) + "%");
                    gui.redPctB.setText(Math.round(redPctB * 100) + "%");
                    gui.redPctC.setText(Math.round(redPctC * 100) + "%");
                    gui.redPctD.setText(Math.round(redPctD * 100) + "%");
                    gui.redBarA.setValue((int)Math.round(redPctA * 100));
                    gui.redBarB.setValue((int)Math.round(redPctB * 100));
                    gui.redBarC.setValue((int)Math.round(redPctC * 100));
                    gui.redBarD.setValue((int)Math.round(redPctD * 100));
                }
            }
            gui.writeToRedBox(sender + " (" + userBetMap.get(sender) + "): " + message + "\n");
            gui.redText.setText("Red Total Active Bets: " + redActiveBetTotal);
        }
        if(teamBlue.contains(sender))
        {
            if (message.startsWith("!move"))
            {
                String[] moveArgs = message.split(" ");
                if (moveArgs.length >= 2)
                {
                    String move = moveArgs[1];
                    if (move.equalsIgnoreCase("a") || move.equalsIgnoreCase("b") || move.equalsIgnoreCase("c") || move.equalsIgnoreCase("d") || move.equalsIgnoreCase("-"))
                    {
                        String prevMove = userMoveMap.get(sender);
                        if (prevMove.equalsIgnoreCase("a"))
                            blueA -= userBetMap.get(sender);
                        if (prevMove.equalsIgnoreCase("b"))
                            blueB -= userBetMap.get(sender);
                        if (prevMove.equalsIgnoreCase("c"))
                            blueC -= userBetMap.get(sender);
                        if (prevMove.equalsIgnoreCase("d"))
                            blueD -= userBetMap.get(sender);

                        if (move.equalsIgnoreCase("a"))
                            blueA += userBetMap.get(sender);
                        if (move.equalsIgnoreCase("b"))
                            blueB += userBetMap.get(sender);
                        if (move.equalsIgnoreCase("c"))
                            blueC += userBetMap.get(sender);
                        if (move.equalsIgnoreCase("d"))
                            blueD += userBetMap.get(sender);

                        userMoveMap.put(sender, move);
                    }
                    blueActiveBetTotal = blueA + blueB + blueC + blueD;
                    bluePctA = blueA * 1.0 / blueActiveBetTotal;
                    bluePctB = blueB * 1.0 / blueActiveBetTotal;
                    bluePctC = blueC * 1.0 / blueActiveBetTotal;
                    bluePctD = blueD * 1.0 / blueActiveBetTotal;
                    gui.bluePctA.setText(Math.round(bluePctA * 100) + "%");
                    gui.bluePctB.setText(Math.round(bluePctB * 100) + "%");
                    gui.bluePctC.setText(Math.round(bluePctC * 100) + "%");
                    gui.bluePctD.setText(Math.round(bluePctD * 100) + "%");
                    gui.blueBarA.setValue((int)Math.round(bluePctA * 100));
                    gui.blueBarB.setValue((int)Math.round(bluePctB * 100));
                    gui.blueBarC.setValue((int)Math.round(bluePctC * 100));
                    gui.blueBarD.setValue((int)Math.round(bluePctD * 100));
                }
            }
            gui.writeToBlueBox(sender + " (" + userBetMap.get(sender) + "): " + message + "\n");
            gui.blueText.setText("Blue Total Active Bets: " + blueActiveBetTotal);
        }
    }
}
