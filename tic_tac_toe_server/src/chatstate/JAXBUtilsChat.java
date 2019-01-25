package chatstate;

import commen.Messages;
import commen.Chat;
import java.io.File;
import java.io.FileWriter;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

public class JAXBUtilsChat {

    //Constructor
    public JAXBUtilsChat() {
    }

    //create xml file
    public void generateXml(Chat chat, String sender, String reciever) {
        try {
            JAXBContext jAXBContext = JAXBContext.newInstance((new Class[]{Chat.class, Messages.class}));
            Marshaller marshaller = jAXBContext.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            File folder = new File("./");
            File file = null;// gets you the list of files at this folder
            File[] listOfFiles = folder.listFiles();
// loop through each of the files looking for filenames that match
            for (int i = 0; i < listOfFiles.length; i++) {
                String filename = listOfFiles[i].getName();
                System.out.println("file names : " + filename);
                if (filename.equalsIgnoreCase(sender + "_" + reciever + "Xml" + ".xml")) {
                    System.out.println("case 1 :");
                    file = new File("./" + sender + "_" + reciever + "Xml" + ".xml");

                } else if (filename.equalsIgnoreCase(reciever + "_" + sender + "Xml" + ".xml")) {
                    System.out.println("case 2 :");

                    file = new File("./" + reciever + "_" + sender + "Xml" + ".xml");

                } else if (file == null) {
                    System.out.println("case 3 :");

                    file = new File("./" + sender + "_" + reciever + "Xml" + ".xml");

                }
            }
            /* if((file.getName().equalsIgnoreCase("./" + sender + "_" + reciever + "Xml" + ".xml"))
                    && (file.getName().equalsIgnoreCase(("./" + reciever + "_" + sender + "Xml" + ".xml"))))
            {*/
            //File file = new File("./" + sender + "_" + reciever + "Xml" + ".xml");

            marshaller.marshal(chat, file);
            marshaller.marshal(chat, System.out);
            System.out.println(file.getName() + "file nameeee");
            System.out.println(sender);
            System.out.println(reciever);

        } catch (JAXBException jAXBException) {
            jAXBException.printStackTrace();
        }
    }

    //read xml file
    public Chat readXml(String fileName) {
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance((new Class[]{Chat.class, Messages.class}));
            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
            File file = new File("./" + fileName);
            Chat chat;
            if (file.exists()) {
                chat = (Chat) jaxbUnmarshaller.unmarshal(file);
                return chat;
            }

        } catch (JAXBException jAXBException) {
            jAXBException.printStackTrace();
        }
        return null;

    }

}
