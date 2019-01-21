/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package commen;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author abdullah
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "Chat")
public class Chat implements Serializable {

    @XmlElement(name = "Messages")
    List<Messages> messges;

    public List<Messages> getMessges() {
        return messges;
    }

    public void setMessges(List<Messages> messges) {
        this.messges = messges;
    }

    public List<Messages> getGameStates() {
        return messges;
    }

    public void add(Messages messages) {
        if (this.messges == null) {
            this.messges = new ArrayList<Messages>();
        }
        this.messges.add(messages);
    }

}
