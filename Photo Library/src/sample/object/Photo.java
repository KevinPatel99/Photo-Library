package sample.object;

import javafx.scene.image.Image;

import java.io.Serializable;
import java.util.*;

public class Photo implements Serializable {

    private static final long serialVersionUID = 4L;

    private String path;
    private String photoName;
    private String caption;
    private List<Tag> tags;
    private Calendar date;

    public Photo(String path, String photoName) {
        this.path = path;
        this.photoName = photoName;
        this.caption = "";
        this.tags = new ArrayList<>();
    }

    public boolean addTag(String tagName, String tagValue) {
        for (Tag tag: this.tags) {
            if (tag.getTagName().equals(tagName) && tag.getTagValue() == tagValue) {
                return true;
            }
        }
        return false;
    }

    public void showTags() {
        for (Tag tag:this.tags) {
            System.out.println(tag.toString());
        }
    }


    public String getPhotoPath() {
        return this.path;
    }

    public String getPhotoName() {
        return photoName;
    }

    public void setPhotoName(String photoName) {
        this.photoName = photoName;
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public List<Tag> getTags() {
        return tags;
    }

    public void setTags(ArrayList<Tag> tags) {
        this.tags = tags;
    }

    public Calendar getDate() {
        return date;
    }

    public void setDate(Calendar date) {
        this.date = date;
    }
}
