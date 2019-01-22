package common;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "Game")
public class Game implements Serializable {

    @XmlElement(name = "GameState")
    List<SavedGameState> GameStates;
    @XmlElement(name = "Result")
    String Result;

    //Constructor
    public Game() {
    }

    //Setter
    public void setGame(List<SavedGameState> GameStates) {
        this.GameStates = GameStates;
    }

    public void setResult(String Result) {
        this.Result = Result;
    }

    //Getter
    public List<SavedGameState> getGameStates() {
        return GameStates;
    }

    public String getResult() {
        return Result;
    }
    
    //add game state to the list
    public void add(SavedGameState gameState) {
        if (this.GameStates == null) {
            this.GameStates = new ArrayList<SavedGameState>();
        }
        this.GameStates.add(gameState);
    }

}
