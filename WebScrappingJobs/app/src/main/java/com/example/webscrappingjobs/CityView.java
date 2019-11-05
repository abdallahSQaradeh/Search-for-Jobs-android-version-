package com.example.webscrappingjobs;

public class CityView  {
    String name;
    String link;
    CityView(String newname/*,ImageView image*/)
    {
        name = newname;

    }
    String getLink()
    {
        return link;
    }
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
}

