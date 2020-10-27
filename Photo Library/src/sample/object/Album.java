package sample.object;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class Album implements Serializable {

    private static final long serialVersionUID = 4L;

    private String albumName;
    private List<Photo> photos;

    public Album(String name){
        this.albumName = name;
        this.photos = new ArrayList<>();
    }

    public boolean addPhoto(Photo photo) {
        this.photos.add(photo);
        return true;
    }

    public void removePhoto(Photo photo) {

    }

    public Calendar getEarliestDate() {
        if (this.photos.size() < 1) {
            return null;
        }
        Calendar earliestDate = this.photos.get(0).getDate();
        for (Photo photo: this.photos) {
            if ( photo.getDate().compareTo(earliestDate) > 0 ) {
                earliestDate = photo.getDate();

            }
        }
        return earliestDate;
    }

    public Calendar getLatestDate() {
        if (this.photos.size() < 1) {
            return null;
        }
        Calendar latestDate = this.photos.get(0).getDate();
        for (Photo photo: this.photos) {
            if ( photo.getDate().compareTo(latestDate) < 0 ) {
                latestDate = photo.getDate();

            }
        }
        return latestDate;
    }

    public String getAlbumName() {
        return albumName;
    }

    public void setAlbumName(String albumName) {
        this.albumName = albumName;
    }

    public List<Photo> getPhotos() {
        return photos;
    }

    public Photo getPhotoFromPath(String path){

        for(Photo p : photos){
            if(p.getPhotoPath().equals(path)){
                return p;
            }
        }
        return null;
    }

    public void setPhotos(ArrayList<Photo> photos) {
        this.photos = photos;
    }

}
