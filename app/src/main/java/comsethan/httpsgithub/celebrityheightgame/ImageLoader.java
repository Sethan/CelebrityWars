package comsethan.httpsgithub.celebrityheightgame;

import android.content.res.AssetManager;

import android.graphics.drawable.Drawable;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;


public class ImageLoader {
    ImageView newImage;
    ImageView lastImage;
    public ImageLoader(ImageView n, ImageView l, boolean top)
    {
        newImage = n;
        lastImage = l;
    }
    public static int progress(TextView qt, ImageView n, ImageView l, CelebrityHandler cH, AssetManager am, TextView top, TextView bottom)
    {
        String s1 ="a"+Short.toString(cH.getFirstActor().getimageID())+".jpg";
        String s2 ="a"+Short.toString(cH.getSecondActor().getimageID())+".jpg";
        bottom.setText(cH.getFirstActor().getName());
        top.setText(cH.getSecondActor().getName());
        load(n,am,s1);
        load(l,am,s2);
        int number = (int) (Math.random()*4);
        switch (number)
        {
            case 0:qt.setText("Pick oldest"); break;
            case 1:qt.setText("Pick richest"); break;
            case 2:qt.setText("Pick tallest"); break;
            case 3:qt.setText("Pick most features"); break;
            case 4:qt.setText("Pick most won oscars"); break;
        }

        return number;
    }


    private static void load(ImageView mImage, AssetManager am, String url)
    {
        try {

            InputStream ims = am.open(url);

            Drawable d = Drawable.createFromStream(ims, null);

            mImage.setImageDrawable(d);
        }
        catch(IOException ex) {
        }
    }
}
