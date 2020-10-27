package sample;

import sample.object.User;

import java.io.*;
import java.util.*;

public class SerializeSession implements Serializable {

    private static SerializeSession instance;

    private static final long serialVersionUID = 4L;
    private static final String storeDir = "dat";
    private static final String storeFile = "users.dat";

    private List<User> users = new ArrayList<User>();
    private User currentUser;

    //singleton instance of SerializeSession
    public static SerializeSession getInstance() {
        try {

            if(instance == null){
                instance = read();
            }
            return instance;

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }


    public void setCurrentUser(User user){
        this.currentUser = user;
    }

    public User getCurrentUser() {
        return this.currentUser;
    }

    public List<User> getUserList() {
        return users;
    }

    public User getUser(String name) {
        for(User u: users) {
            if(u.getUserName().equals(name)){
                return u;
            }
        }
        return null;
    }

    public void addToUsers(User user) {
        users.add(user);
    }

    public void removeUser(User user) {
        users.remove(user);
    }

    public boolean userExists(String user) {
        for(User u: users) {
            if(u.getUserName().equals(user)){
                return true;
            }
        }
        return false;
    }

    public static SerializeSession read() throws IOException, ClassNotFoundException {
        File file = new File(storeDir + File.separator + storeFile);
        file.createNewFile();
        try{

            ObjectInputStream ois = new ObjectInputStream(new FileInputStream(storeDir + File.separator + storeFile));
            SerializeSession usersList = (SerializeSession) ois.readObject();
            ois.close();
            return usersList;

        }catch (EOFException e){
            return new SerializeSession();
        }

    }

    public static void write() throws IOException {
        ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(storeDir + File.separator + storeFile));
        oos.writeObject(instance);
        oos.close();
    }
}
