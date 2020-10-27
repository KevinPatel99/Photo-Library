package sample.object;

import java.io.*;
import java.util.ArrayList;

public class User implements Serializable {

    private static final long serialVersionUID = 4L;
    private String userName = "";

    public ArrayList<Album> albums = new ArrayList<Album>();


    public User(String userName) {
        this.userName = userName;
    }


    public void addAlbum(Album album) {
        albums.add(album);
    }

    public void removeAlbum(Album album) {
        albums.remove(album);
    }

    public void removeAlbum(String album) {
        removeAlbum(getAlbum(album));
    }

    public Album getAlbum(String name) {
        for(Album a : albums){
            if(a.getAlbumName().equals(name)){
                return a;
            }
        }
        return null;
    }

    public void setUserName(String name) {
        this.userName = name;
    }

    public void setAlbums(ArrayList<Album> albums) {
        this.albums = albums;
    }

    public String getUserName() {
        return userName;
    }

    public ArrayList<Album> getAlbums() {
        return albums;
    }

    public String toString() {
        return userName;
    }
}
