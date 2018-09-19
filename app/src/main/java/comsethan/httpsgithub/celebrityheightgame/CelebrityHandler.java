package comsethan.httpsgithub.celebrityheightgame;

import android.util.Log;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Collections;
import java.util.LinkedList;

public class CelebrityHandler {
    LinkedList<Actor> actorList;
    public CelebrityHandler(BufferedReader br)
    {
        actorList=new LinkedList<>();
        try
        {
            br.readLine();
            String currentLine=br.readLine();
            while(currentLine!=null)
            {

                String[] s = currentLine.split(",");
                Actor actor = new Actor(Short.valueOf(s[0]),s[1],Integer.valueOf(s[2]),Integer.valueOf(s[3]),Integer.valueOf(s[4]),Double.parseDouble(s[5]),Double.parseDouble(s[6]),Integer.valueOf(s[7]));
                actorList.add(actor);
                currentLine = br.readLine();
            }

        }
        catch (FileNotFoundException fnfe)
        {
            File f = new File("");
            Log.d("FileNotFound","The file was not found in loadList() "+f.listFiles());
        }
        catch (IOException ioe)
        {
            Log.d("IOException","The file faulty in loadList()");
        }
    }
    public void check()
    {
        Log.d("DebugCheck", actorList.getFirst().getName());
    }


    public void listScramble()
    {
        Collections.shuffle(actorList);
    }

    public void next()
    {
        this.actorList.pop();
    }

    public boolean compareHeight()
    {
        if(actorList.get(0).getHeight()>actorList.get(1).getHeight())
        {
            return true;
        }
        return false;
    }
    public boolean compareNetworth()
    {
        if(actorList.get(0).getNetworth()>actorList.get(1).getNetworth())
        {
            return true;
        }
        return false;
    }
    public boolean compareOscars()
    {
        if(actorList.get(0).getOscars()>actorList.get(1).getOscars())
        {
            return true;
        }
        return false;
    }
    public boolean compareFilms()
    {
        if(actorList.get(0).getFeatures()>actorList.get(1).getFeatures())
        {
            return true;
        }
        return false;
    }
    public boolean compareAge()
    {
        if(Math.abs(actorList.get(0).getbirthYear()-actorList.get(0).getDeathYear())>Math.abs(actorList.get(1).getbirthYear()-actorList.get(1).getDeathYear()))
        {
            return true;
        }
        return false;
    }
    public Actor getFirstActor()
    {
        return this.actorList.get(0);
    }
    public Actor getSecondActor()
    {
        return this.actorList.get(1);
    }

    public boolean pickCategory(int number)
    {

        switch (number)
        {
            case 0: return compareAge();
            case 1: return compareNetworth();
            case 2: return compareHeight();
            case 3: return compareFilms();
            case 4: return compareOscars();
        }
        return false;
    }


}
