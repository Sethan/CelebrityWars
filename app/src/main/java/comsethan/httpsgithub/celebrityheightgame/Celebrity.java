package comsethan.httpsgithub.celebrityheightgame;



public class Celebrity {
    int birthYear;
    int deathYear;
    double height;
    double networth;
    short imageID;
    String name;
    public Celebrity(int a, int d, double h, double n, short ii, String na)
    {
        birthYear=a;
        if(d==0)
        {
            deathYear=2018;
        }
        else
        {
            deathYear=d;
        }

        height=h;
        networth=n;
        imageID=ii;
        name=na;
    }
    public int getbirthYear()
    {
        return this.birthYear;
    }
    public int getDeathYear()
    {
        return this.deathYear;
    }
    public double getHeight()
    {
        return this.height;
    }
    public double getNetworth()
    {
        return this.networth;
    }
    public short getimageID()
    {
        return this.imageID;
    }
    public String getName()
    {
        return this.name;
    }

}
