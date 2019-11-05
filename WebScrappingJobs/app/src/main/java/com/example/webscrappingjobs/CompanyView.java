package com.example.webscrappingjobs;

public class CompanyView {
    String companyname;
    String link;
    String imgsrc ;
    String location;

    public CompanyView(String name)
    {
        this.companyname = name;
    }
public void setname(String name)
{
    companyname= name;
}
public String getname()
{
    return companyname;
}
public String getLink()
{
    return link;
}
public void setLink(String link)
{
    this.link = link;
}
    public String getlocation()
    {
        return location;
    }
    public void setLocation(String location)
    {
        this.location = location;
    }
    public String getimgsrc()
    {
        return imgsrc;
    }
    public void setImgsrc(String imgsrc)
    {
        this.imgsrc = imgsrc;
    }

}
