package comsethan.httpsgithub.celebrityheightgame;



import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Timer;
import java.util.TimerTask;


public class MainActivity extends AppCompatActivity {

    ImageView lImage;
    ImageView nImage;
    TextView questionText;
    TextView topTextBox;
    TextView bottomTextBox;
    TextView topCounter;
    TextView bottomCounter;
    Timer timer = new Timer();
    boolean pause=true;
    boolean canContinue;
    CelebrityHandler celebrityHandler;
    int topCounterValue=0;
    int bottomCounterValue=0;
    boolean topClicked;
    int categoryNr=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lImage = (ImageView) findViewById(R.id.lastImage);
        nImage = (ImageView) findViewById(R.id.nextImage);
        questionText = (TextView) findViewById(R.id.editText);
        topTextBox = (TextView) findViewById(R.id.topText);
        bottomTextBox = (TextView) findViewById(R.id.bottomText);
        topCounter = findViewById(R.id.counterTop);
        bottomCounter = findViewById(R.id.counterBottom);
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new InputStreamReader(getAssets().open("list.txt")));
            celebrityHandler = new CelebrityHandler(reader);
        } catch (IOException ioe) {
            Log.d("IOE", "Error loading file :( " + ioe);
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    Log.d("IOE", "Error closing file");
                }
            }
        }
        celebrityHandler.listScramble();
        categoryNr=ImageLoader.progress(questionText,nImage,lImage,celebrityHandler,getAssets(),topTextBox,bottomTextBox);

        lImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                topClicked=false;
                click();
            }
        });
        nImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                topClicked=true;
                click();
            }
        });

    }

    public void click()
    {

        if(pause)
        {
            runTimer();
            checkCont();
        }
        else
        {
            if(canContinue)
            {
                celebrityHandler.next();
                categoryNr=ImageLoader.progress(questionText,nImage,lImage,celebrityHandler,getAssets(),topTextBox,bottomTextBox);
                canContinue=false;
            }
            else
            {
                finish();
            }
            timer.cancel();
            timer=new Timer();

        }
        bottomCounter.setText("");
        topCounter.setText("");
        pause=!pause;
    }

    public void runTimer()
    {
        double valueFirst=0;
        double valueSecond=0;
        switch (categoryNr)
        {
                case 0: valueFirst=formatAge(celebrityHandler.getFirstActor()); valueSecond=formatAge(celebrityHandler.getSecondActor());break;
                case 1: valueFirst=celebrityHandler.getFirstActor().getNetworth(); valueSecond=celebrityHandler.getSecondActor().getNetworth();break;
                case 2: valueFirst=celebrityHandler.getFirstActor().getHeight(); valueSecond=celebrityHandler.getSecondActor().getHeight();break;
                case 3: valueFirst=celebrityHandler.getFirstActor().getFeatures(); valueSecond=celebrityHandler.getSecondActor().getFeatures();break;
                case 4: valueFirst=celebrityHandler.getFirstActor().getOscars(); valueSecond=celebrityHandler.getSecondActor().getOscars();break;

        }
        pause=true;
        CounterTimer ct = new CounterTimer(200,valueFirst,bottomCounter);
        timer.scheduleAtFixedRate(ct,200,100);
        CounterTimer ct2= new CounterTimer(200, valueSecond, topCounter);
        timer.scheduleAtFixedRate(ct2,200, 100 );
    }


    public int formatAge(Actor actor)
    {
        return Math.abs(actor.getDeathYear()-actor.getbirthYear());
    }

    public void checkCont()
    {
        if(topClicked&&celebrityHandler.pickCategory(categoryNr))
        {
            canContinue =true;
        }
        else if(!topClicked&&!celebrityHandler.pickCategory(categoryNr))
        {
            canContinue =true;
        }
    }


    private class CounterTimer extends TimerTask
    {
        private int frequency;
        private double value;
        private double displayNumber;
        TextView textView;
        public CounterTimer(int f,double value, TextView t)
        {
            this.frequency=f;
            this.value=value;
            this.displayNumber=0;
            this.textView=t;
        }
        @Override
        public void run()
        {
            displayNumber=displayNumber+value/10;
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    String s="";
                    if(displayNumber==(int) displayNumber)
                    {
                        s =""+(int)displayNumber;
                    }
                    else
                    {
                        s = String.format("%.2f",displayNumber);
                    }

                    textView.setText(String.valueOf(s));
                }});
            frequency=frequency-frequency/10;
            if(displayNumber>=value)
            {
                pause=false;
                this.cancel();
            }

        }
        public int getFrequency()
        {
            return this.frequency;
        }

     /*   public void speedUp()
        {
            CounterTimer temp = new CounterTimer(frequency,value,textView);
            timer.scheduleAtFixedRate(temp,frequency,frequency);
            this.cancel();
        }*/
    }


    public void tryCont()
    {
        if(topClicked&&celebrityHandler.pickCategory(categoryNr))
        {
            celebrityHandler.next();
            categoryNr=ImageLoader.progress(questionText,nImage,lImage,celebrityHandler,getAssets(),topTextBox,bottomTextBox);
        }
        else if(!topClicked&&!celebrityHandler.pickCategory(categoryNr))
        {
            celebrityHandler.next();
            categoryNr=ImageLoader.progress(questionText,nImage,lImage,celebrityHandler,getAssets(),topTextBox,bottomTextBox);
        }
    }

}
