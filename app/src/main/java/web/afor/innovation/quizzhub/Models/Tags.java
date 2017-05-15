package web.afor.innovation.quizzhub.Models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by wafa on 7/30/16.
 */
public class Tags {
    @SerializedName("tag_name")
    String name_Tag;// pas de _
    int id ;

    public Tags(String tag,int id) {
        name_Tag = tag;
        this.id=id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName_Tag() {
        return name_Tag;
    }

    public void setName_Tag(String name_Tag) {
        this.name_Tag = name_Tag;
    }
}
