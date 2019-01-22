/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import common.Player;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ashraf
 */
public class DataBaseConnection {

    Statement statement;

    public DataBaseConnection() {

        try {
            DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/tic-tac-toe", "root", "1529");
            statement = con.createStatement();
        } catch (SQLException ex) {
            Logger.getLogger(DataBaseConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public boolean registerPlayer(Player player) {
        try {
            return !(statement.execute(
                    "INSERT INTO players (username, email, points, active ,password) VALUES ('" + player.getUsername()
                    + "', '" + player.getEmail() + "', '0', '0', '" + player.getPassword() + "')"));
        } catch (SQLException ex) {
            return false;
        }
    }

    public Player login(String username, String password) {
        try {
            ResultSet resultSet = statement.executeQuery(
                    "SELECT * FROM players where username='" + username + "'");
            if (resultSet.first()) {
                if (resultSet.getString("password").equals(password)) {
                    if (setActive(username)) {
                        return getPlayer(username);
                    }
                }
            }
        } catch (SQLException ex) {
            return null;
        }
        return null;
    }

        public boolean isNotActive(String username) {
        try {
            ResultSet resultSet = statement.executeQuery(
                    "SELECT * FROM players where (username='" + username + "' AND  active = '0' )");
            if (resultSet.first()) {
                if (resultSet.getInt("active") == 0) {

                    System.out.println("offline user");
                    return true;
                } else {

                    System.out.println("online user");
                    return false;
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
         return false;
    }

    private boolean setActive(String username) {
        try {
            return !(statement.execute("UPDATE players SET active = 1 WHERE (username = '" + username + "')"));
        } catch (SQLException ex) {
            Logger.getLogger(DataBaseConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public boolean logout(String username) {
        try {
            return !(statement.execute("UPDATE players SET active = 0 WHERE (username = '" + username + "')"));
        } catch (SQLException ex) {
            Logger.getLogger(DataBaseConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public Player getPlayer(String username) {
        try {
            ResultSet resultSet = statement.executeQuery(
                    "SELECT * FROM players where username='" + username + "'");
            if (resultSet.first()) {
                Player player = new Player();
                player.setId(resultSet.getInt("idplayer"));
                player.setUsername(resultSet.getString("username"));
                player.setPassword(resultSet.getString("password"));
                player.setEmail(resultSet.getString("email"));
                player.setPoints(resultSet.getInt("points"));
                if (resultSet.getInt("active") == 0) {
                    player.setIsActive(false);
                } else {
                    player.setIsActive(true);
                }
                return player;
            }
        } catch (SQLException ex) {
            return null;
        }
        return null;
    }

    public List<Player> getActivePlayers() {
        List<Player> players = new ArrayList<>();
        try {
            ResultSet resultSet = statement.executeQuery(
                    "SELECT * FROM players where active = 1 order by points DESC");
            while (resultSet.next()) {
                Player player = new Player();
                player.setId(resultSet.getInt("idplayer"));
                player.setUsername(resultSet.getString("username"));
                player.setEmail(resultSet.getString("email"));
                player.setPoints(resultSet.getInt("points"));
                player.setIsActive(true);

                players.add(player);
            }
        } catch (SQLException ex) {
            return players;
        }
        return players;
    }

    public List<Player> getAllPlayers() {
        List<Player> players = new ArrayList<>();
        try {
            ResultSet resultSet = statement.executeQuery(
                    "SELECT * FROM players order by points DESC");
            while (resultSet.next()) {
                Player player = new Player();
                player.setId(resultSet.getInt("idplayer"));
                player.setUsername(resultSet.getString("username"));
                player.setEmail(resultSet.getString("email"));
                player.setPoints(resultSet.getInt("points"));
                if (resultSet.getInt("active") == 0) {
                    player.setIsActive(false);
                } else {
                    player.setIsActive(true);
                }
                players.add(player);
            }
        } catch (SQLException ex) {
            return players;
        }
        return players;
    }

    public boolean incrementPointsWin(String username) {
        try {
            int points = getPlayer(username).getPoints();
            points += 3;
            return !(statement.execute("UPDATE players SET points = " + points + " WHERE (username = '" + username + "')"));
        } catch (SQLException ex) {
            Logger.getLogger(DataBaseConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public boolean incrementPointsDraw(String username) {
        try {
            int points = getPlayer(username).getPoints();
            points++;
            return !(statement.execute("UPDATE players SET points = " + points + " WHERE (username = '" + username + "')"));
        } catch (SQLException ex) {
            Logger.getLogger(DataBaseConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public List<String> getAllrecords(String username) {
        List<String> fileName = new ArrayList<>();
        try {
            ResultSet resultSet = statement.executeQuery("SELECT * FROM recordgame where firstPlayer = '"
                    + username + "' or secondPlayer = '" + username + "'");
            while (resultSet.next()) {
                fileName.add(resultSet.getString("recordName"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(DataBaseConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
        return fileName;
    }

    public boolean setRecordName(String firstPlayer, String secondPlayer, String fileName) {
        boolean flag = false;
        try {
            flag = !(statement.execute("INSERT INTO recordgame (firstPlayer, secondPlayer, recordName) VALUES ('"
                    + firstPlayer + "', '" + secondPlayer + "', '" + fileName + "')"));
            if (!flag) {
                flag = !(statement.execute("UPDATE recordgame SET recordName = '" + fileName + "' WHERE (firstPlayer = '"
                        + firstPlayer + "' , secondPlayer = '" + secondPlayer + "')"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(DataBaseConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
        return flag;
    }

    public boolean colseServer() {
        boolean flag = false;
        try {
            flag = !(statement.execute("UPDATE players SET active = '0'"));
        } catch (SQLException ex) {
            Logger.getLogger(DataBaseConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
        return flag;
    }

    public List<String> getAllSecondPlayerNamesInRecords(String username) {
        List<String> secondUserName = new ArrayList<>();
        try {
            ResultSet resultSet = statement.executeQuery("SELECT secondPlayer FROM recordgame where firstPlayer = '"
                    + username + "'");
            while (resultSet.next()) {
                secondUserName.add(resultSet.getString("secondPlayer"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(DataBaseConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
        return secondUserName;
    }
}
