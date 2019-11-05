package com.example.webscrappingjobs;

import android.widget.ImageView;

public class CatigoriesView {
    String name;
    ImageView image;
    String link;
    CatigoriesView(String newname/*,ImageView image*/)
    {
        name = newname;
        this.image =image;

    }
    String getLink(){return link;};
    void setLink(String link)
    {
        this.link = link;
    }
    String getName()
    {
        return name;
    }
    void setName(String nname)
    {
        name = nname;
    }
    void SetImage(ImageView image)
    {
        this.image = image;
    }
    ImageView GetImage()
    {
        return  image;
    }
}
