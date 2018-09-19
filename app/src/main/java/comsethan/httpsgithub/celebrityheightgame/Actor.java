package comsethan.httpsgithub.celebrityheightgame;

public class Actor extends Celebrity
{
    int oscars;
    int features;
    public Actor(short ii, String na, int a,int f,int o,double h, double n,int d)
    {
        super(a,d,h,n,ii,na);
        oscars=o;
        features=f;
    }
    public int getOscars()
    {
        return this.oscars;
    }
    public int getFeatures()
    {
        return this.features;
    }
}
