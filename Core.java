import org.jibble.pircbot.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.HashMap;

public class Core extends PircBot implements ActionListener
{
    private boolean inGame;
    private boolean acceptingBets;
    private HashMap<String, String> userMoveMap = new HashMap<String, String>();
    private HashMap<String, Integer> userBetMap = new HashMap<String, Integer>();

    private TeamChatGUI gui;
    private Game currentGame;
    private Timer waitForBets;
    private int lastGameStartTime;

    private List<String> moveArray = Arrays.asList("a", "b", "c", "d", "-");
    private Color[] colorArray = {new Color(255,0,0), new Color(0,0,255), new Color(0,255,0), new Color(255,255,0), new Color(0,0,0)};

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
            gui.redText.setText("[Error] Unable to connect to IRC.");
            gui.blueText.setText("[Error] Unable to connect to IRC.");
            e.printStackTrace();
        } catch (IrcException e) {
            gui.redText.setText("[Error] Unable to connect to Twitch.");
            gui.blueText.setText("[Error] Unable to connect to Twitch.");
            e.printStackTrace();
        }
        joinChannel("#twitchplayspokemon");
        inGame = false;
        acceptingBets = false;
        waitForBets = new Timer(35000, this);
        lastGameStartTime = -1;
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        acceptingBets = true;
        waitForBets.stop();
    }

    public void onMessage(String channel, String sender, String login, String hostname, String message)
    {
        if (message.endsWith("won the match!") && sender.equalsIgnoreCase("tppinfobot"))
        {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
            int minutes = Integer.parseInt(sdf.format(new Date()).split(" ")[1].split(":")[1]);
            if (minutes < lastGameStartTime)
                waitForBets.setDelay(300000);
            else
                waitForBets.setDelay(35000);
            waitForBets.start();

            lastGameStartTime = minutes;

            inGame = true;
            //acceptingBets = true;
            userBetMap.clear();
            userMoveMap.clear();

            currentGame = new Game();

            //GUI Reset
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

            gui.blueText.setText("Blue Total Active Bets: None");
            gui.redText.setText("Red Total Active Bets: None");

        }

        if (message.endsWith("has just begun!") && sender.equalsIgnoreCase("tppinfobot"))
        {
            acceptingBets = false;
        }

        if (!inGame)
            return;

        String[] messageArgs = message.split(" ");

        /* Check for bets */
        if (messageArgs[0].equalsIgnoreCase("!bet") && acceptingBets && inGame)
        {
            if (messageArgs.length >= 3)
            {
                String team;
                int betCount;

                try {
                betCount = Integer.parseInt(messageArgs[1]);
                team = messageArgs[2];
                } catch (Exception e)
                {
                    try {
                        betCount = Integer.parseInt(messageArgs[2]);
                        team = messageArgs[1];
                    }
                    catch (Exception e1)
                    {
                        return;
                    }
                }

                if (!team.equalsIgnoreCase("red") && !team.equalsIgnoreCase("blue"))
                    return;

                if (betCount < 1 || betCount > 9999999)
                    return;

                //Betting on red, hasn't bet on blue yet (allows for increasing bets)
                if (team.equalsIgnoreCase("red") && !currentGame.getBlueTeam().getUserList().contains(sender))
                {
                    if (!currentGame.getRedTeam().getUserList().contains(sender))
                    {
                        //Hasn't bet yet
                        userMoveMap.put(sender, "-");
                        currentGame.getRedTeam().addToTeam(sender);
                        userBetMap.put(sender, betCount);
                        currentGame.getRedTeam().addTotalBet(betCount);
                    }
                    else
                    {
                        //Check for increasing bet
                        if (userBetMap.get(sender) < betCount)
                        {
                            currentGame.getRedTeam().addTotalBet(betCount - userBetMap.get(sender));
                            userBetMap.put(sender, betCount);
                        }
                    }
                    userBetMap.put(sender, betCount);
                    //if (betCount > userBetMap.get(currentGame.getRedTeam().getTopBetter()))
                        //currentGame.getRedTeam().setTopBetter(sender);
                }

                //Betting on blue, hasn't bet on red yet
                if (team.equalsIgnoreCase("blue") && !currentGame.getRedTeam().getUserList().contains(sender))
                {
                    if (!currentGame.getBlueTeam().getUserList().contains(sender))
                    {
                        //Hasn't bet yet
                        userMoveMap.put(sender, "-");
                        currentGame.getBlueTeam().addToTeam(sender);
                        userBetMap.put(sender, betCount);
                        currentGame.getBlueTeam().addTotalBet(betCount);
                    }
                    else
                    {
                        //Check for increasing bet
                        if (userBetMap.get(sender) <= betCount)
                            userBetMap.put(sender, betCount);
                    }
                    userBetMap.put(sender, betCount);
                    //if (betCount > userBetMap.get(currentGame.getBlueTeam().getTopBetter()))
                        //currentGame.getBlueTeam().setTopBetter(sender);
                }
            }
        }

        //Check moves
        //Red team
        if (currentGame.getRedTeam().getUserList().contains(sender))
        {
            if (!message.contains(" "))
                return;

            String[] moveArgs = message.split(" ");
            if (moveArgs[0].equalsIgnoreCase("!move"))
            {

                if (moveArgs.length >= 2)
                {
                    String move = moveArgs[1];
                    if (move.equalsIgnoreCase("a") || move.equalsIgnoreCase("b") || move.equalsIgnoreCase("c") || move.equalsIgnoreCase("d") || move.equalsIgnoreCase("-"))
                    {
                        //
                        String prevMove = userMoveMap.get(sender);

                        if (!move.equals("-"))
                        {
                            currentGame.getRedTeam().getMoveList().get(move.compareTo("a")).addBet(userBetMap.get(sender));
                            currentGame.getRedTeam().addActiveBet(userBetMap.get(sender));
                        }

                        if(!prevMove.equals("-"))
                        {
                            currentGame.getRedTeam().getMoveList().get(prevMove.compareTo("a")).removeBet(userBetMap.get(sender));
                            currentGame.getRedTeam().removeActiveBet(userBetMap.get(sender));
                        }

                        userMoveMap.put(sender, move);

                        for (int i = 0; i < 4; i++)
                        {
                            currentGame.getRedTeam().getMoveList().get(i).calcPercent(currentGame.getRedTeam().getActiveBet());
                        }
                        gui.redPctA.setText(Math.round(currentGame.getRedTeam().getMoveList().get(0).getBetPercent() * 100) + "%");
                        gui.redPctB.setText(Math.round(currentGame.getRedTeam().getMoveList().get(1).getBetPercent() * 100) + "%");
                        gui.redPctC.setText(Math.round(currentGame.getRedTeam().getMoveList().get(2).getBetPercent() * 100) + "%");
                        gui.redPctD.setText(Math.round(currentGame.getRedTeam().getMoveList().get(3).getBetPercent() * 100) + "%");

                        gui.redBarA.setValue((int)Math.round(currentGame.getRedTeam().getMoveList().get(0).getBetPercent() * 100));
                        gui.redBarB.setValue((int)Math.round(currentGame.getRedTeam().getMoveList().get(1).getBetPercent() * 100));
                        gui.redBarC.setValue((int)Math.round(currentGame.getRedTeam().getMoveList().get(2).getBetPercent() * 100));
                        gui.redBarD.setValue((int)Math.round(currentGame.getRedTeam().getMoveList().get(3).getBetPercent() * 100));
                    }
                }
                if (messageArgs.length > 2)
                {
                    if (userBetMap.get(sender) > 1000)
                        gui.writeToRedBox(sender + " (" + userBetMap.get(sender) + "): " + message + "\n", colorArray[moveArray.indexOf(userMoveMap.get(sender))], true);
                    else
                        gui.writeToRedBox(sender + " (" + userBetMap.get(sender) + "): " + message + "\n", colorArray[moveArray.indexOf(userMoveMap.get(sender))], false);
                }
            }
            else
            {
                if (!message.equalsIgnoreCase("!balance"))
                {
                    if (userBetMap.get(sender) > 1000)
                        gui.writeToRedBox(sender + " (" + userBetMap.get(sender) + "): " + message + "\n", colorArray[moveArray.indexOf(userMoveMap.get(sender))], true);
                    else
                        gui.writeToRedBox(sender + " (" + userBetMap.get(sender) + "): " + message + "\n", colorArray[moveArray.indexOf(userMoveMap.get(sender))], false);
                }
            }

            gui.redText.setText("Red Total Active Bets: " + currentGame.getRedTeam().getActiveBet() + " / " + currentGame.getRedTeam().getTotalBet());
        }

        //Blue team
        if (currentGame.getBlueTeam().getUserList().contains(sender))
        {
            if (!message.contains(" "))
                return;

            String[] moveArgs = message.split(" ");
            if (moveArgs[0].equalsIgnoreCase("!move"))
            {
                if (moveArgs.length >= 2)
                {
                    String move = moveArgs[1];
                    if (move.equalsIgnoreCase("a") || move.equalsIgnoreCase("b") || move.equalsIgnoreCase("c") || move.equalsIgnoreCase("d") || move.equalsIgnoreCase("-"))
                    {
                        //
                        String prevMove = userMoveMap.get(sender);

                        if (!move.equals("-"))
                        {
                            currentGame.getBlueTeam().getMoveList().get(move.compareTo("a")).addBet(userBetMap.get(sender));
                            currentGame.getBlueTeam().addActiveBet(userBetMap.get(sender));
                        }

                        if(!prevMove.equals("-"))
                        {
                            currentGame.getBlueTeam().getMoveList().get(prevMove.compareTo("a")).removeBet(userBetMap.get(sender));
                            currentGame.getBlueTeam().removeActiveBet(userBetMap.get(sender));
                        }

                        userMoveMap.put(sender, move);
                        //ystem.out.println(sender + " switched from " + prevMove + " to " + move);

                        for (int i = 0; i < 4; i++)
                        {
                            currentGame.getBlueTeam().getMoveList().get(i).calcPercent(currentGame.getBlueTeam().getActiveBet());
                        }
                        gui.bluePctA.setText(Math.round(currentGame.getBlueTeam().getMoveList().get(0).getBetPercent() * 100) + "%");
                        gui.bluePctB.setText(Math.round(currentGame.getBlueTeam().getMoveList().get(1).getBetPercent() * 100) + "%");
                        gui.bluePctC.setText(Math.round(currentGame.getBlueTeam().getMoveList().get(2).getBetPercent() * 100) + "%");
                        gui.bluePctD.setText(Math.round(currentGame.getBlueTeam().getMoveList().get(3).getBetPercent() * 100) + "%");

                        gui.blueBarA.setValue((int)Math.round(currentGame.getBlueTeam().getMoveList().get(0).getBetPercent() * 100));
                        gui.blueBarB.setValue((int)Math.round(currentGame.getBlueTeam().getMoveList().get(1).getBetPercent() * 100));
                        gui.blueBarC.setValue((int)Math.round(currentGame.getBlueTeam().getMoveList().get(2).getBetPercent() * 100));
                        gui.blueBarD.setValue((int)Math.round(currentGame.getBlueTeam().getMoveList().get(3).getBetPercent() * 100));
                    }
                }
                if (messageArgs.length > 2)
                {
                    if (userBetMap.get(sender) > 1000)
                        gui.writeToRedBox(sender + " (" + userBetMap.get(sender) + "): " + message + "\n", colorArray[moveArray.indexOf(userMoveMap.get(sender))], true);
                    else
                        gui.writeToRedBox(sender + " (" + userBetMap.get(sender) + "): " + message + "\n", colorArray[moveArray.indexOf(userMoveMap.get(sender))], false);
                }
            }
            else
            {
                if (!message.equalsIgnoreCase("!balance"))
                {
                    if (userBetMap.get(sender) > 1000)
                        gui.writeToBlueBox(sender + " (" + userBetMap.get(sender) + "): " + message + "\n", colorArray[moveArray.indexOf(userMoveMap.get(sender))], true);
                    else
                        gui.writeToBlueBox(sender + " (" + userBetMap.get(sender) + "): " + message + "\n", colorArray[moveArray.indexOf(userMoveMap.get(sender))], false);
                }
            }

            gui.blueText.setText("Blue Total Active Bets: " + currentGame.getBlueTeam().getActiveBet() + " / " + currentGame.getBlueTeam().getTotalBet());
        }
    }
}
