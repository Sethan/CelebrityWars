package comsethan.httpsgithub.celebrityheightgame;



import android.os.CountDownTimer;
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
    CountDownTimer cdt;
    Timer timer = new Timer();
    boolean pause=true;
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
                runTimer();
                topClicked=false;
                if(pause)
                {
                    pause=!pause;
                }

            }
        });
        nImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                runTimer();
                topClicked=true;
                if(pause)
                {
                    pause=!pause;
                }
            }
        });

    }

    public void runTimer(boolean topImage)
    {
        CounterTimer ct = new CounterTimer(100,(int) celebrityHandler.getFirstActor().getNetworth(),bottomCounter);
        timer.scheduleAtFixedRate(ct,100,100);
        CounterTimer ct2= new CounterTimer(100, (int) celebrityHandler.getSecondActor().getNetworth(), topCounter);
        timer.scheduleAtFixedRate(ct2,100, 100 );
        if(!pause)
        {
            tryCont(topImage);
        }
    }


    public void tryCont(boolean topImage)
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




    private class CounterTimer extends TimerTask
    {
        private int frequency;
        private int value;
        private double displayNumber;
        TextView textView;
        public CounterTimer(int f,int value, TextView t)
        {
            this.frequency=f;
            this.value=value;
            this.displayNumber=0;
            this.textView=t;
            pause=true;
        }
        @Override
        public void run()
        {
            displayNumber=displayNumber+value/40;
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    textView.setText("" + displayNumber);
                }});
            frequency=frequency-frequency/10;
            if(displayNumber>=value)
            {
                if(!pause)
                {
                    tryCont();
                    }
                }
                pause=false;
                this.cancel();
            }

        }
        public int getFrequency()
        {
            return this.frequency;
        }

        public void speedUp()
        {
            CounterTimer temp = new CounterTimer(frequency,value,textView);
            timer.scheduleAtFixedRate(temp,frequency,frequency);
            this.cancel();
        }
    }


}
