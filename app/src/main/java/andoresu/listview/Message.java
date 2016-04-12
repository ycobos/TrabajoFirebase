package andoresu.listview;

/**
 * Created by Yesid on 11/04/2016.
 */
public class Message {

    private String name;


    public Message() {
        // necessary for Firebase's deserializer
    }
    public Message(String name) {
        this.name = name;

    }

    public String getName() {
        return name;
    }


}

