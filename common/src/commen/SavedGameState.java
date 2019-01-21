
package commen;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "GameState")
public class SavedGameState implements Serializable{
    
    @XmlElement(name="userName")
    private String userName;    
    @XmlElement(name = "rowPosition")
    private int rowPosition;
    @XmlElement(name = "colPosition")
    private int colPosition; 
    @XmlElement(name = "Symbol")
    private char Symbol;
    
    //Constructor
    public SavedGameState(){}

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getRowPosition() {
        return rowPosition;
    }

    public void setRowPosition(int rowPosition) {
        this.rowPosition = rowPosition;
    }

    public int getColPosition() {
        return colPosition;
    }

    public void setColPosition(int colPosition) {
        this.colPosition = colPosition;
    }

    public char getSymbol() {
        return Symbol;
    }

    public void setSymbol(char Symbol) {
        this.Symbol = Symbol;
    }

}
