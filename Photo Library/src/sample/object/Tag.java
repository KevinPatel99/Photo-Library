package sample.object;

import java.util.*;
import java.io.Serializable;

public class Tag implements Serializable {

    private static final long serialVersionUID = 4L;

    public static final String[] tagList = {"Person", "Location"};
    private String tagName, tagValue;

    public Tag(String tagName, String tagValue)
    {
        this.tagName = tagName;
        this.tagValue = tagValue;

    }



    public void setTagName(String tagName) {
        this.tagName = tagName;
    }

    public void setTagValue(String tagValue) {
        this.tagValue = tagValue;
    }

    public static String[] getTagList() {
        return tagList;
    }

    public String getTagName() {
        return tagName;
    }

    public String getTagValue() {
        return tagValue;
    }

    @Override
    public String toString() {
        return "Tag{" + this.tagName + this.tagValue +
                '}';
    }
}
