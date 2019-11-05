package com.example.webscrappingjobs;

public class JobsView {
    String name;
    String link;
    String date;
    String company;
    JobsView(String newname/*,ImageView image*/)
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
    String getCompay()
    {
        return company;
    }
    void setCompany(String nname)
    {
        company = nname;
    }
    String getDate()
    {
        return date;
    }
    void setDate(String nname)
    {
        date = nname;
    }
}
