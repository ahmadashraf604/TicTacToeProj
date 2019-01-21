package gamestatexml;

import commen.SavedGameState;
import commen.Game;
import java.io.File;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

public class JAXBUtils {

    //Constructor
    public JAXBUtils() {
    }

    //create xml file
    public void generateXml(Game game, String sender, String reciever) {
        try {
            JAXBContext jAXBContext = JAXBContext.newInstance((new Class[]{Game.class, SavedGameState.class}));
            Marshaller marshaller = jAXBContext.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            marshaller.marshal(game, new File("./"+sender+"&"+reciever+"Xml"+".xml"));
            marshaller.marshal(game, System.out);
        } catch (JAXBException jAXBException) {
            jAXBException.printStackTrace();
        }
    }

    //read xml file
    public Game readXml(String fileName) {
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance((new Class[]{Game.class, SavedGameState.class}));
            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
            Game game = (Game) jaxbUnmarshaller.unmarshal(new File("./"+fileName+"Xml"+".xml"));
            return game;
            //System.out.println(game.GameStates.get(0).getUserName());
        } catch (JAXBException jAXBException) {
            jAXBException.printStackTrace();
            return null;
        }
    }

}
