package com.me.fourseasons;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;

import static android.preference.PreferenceManager.getDefaultSharedPreferences;


public class MainActivity extends AppCompatActivity {

    private ArrayList<Bitmap> bitmaps = new ArrayList<>();
    private ArrayList<Bitmap> resizedBitmaps = new ArrayList<>();
    private ArrayList<Card> aceShuffle = new ArrayList<>();
    private ArrayList<Card>  cardsShuffle = new ArrayList<>();
    private HashSet<Integer> ids = new HashSet<>();
    private ArrayDeque<Card> cards = new ArrayDeque<>();
    private ArrayList<ArrayDeque<Card>> arrDeck = new ArrayList<>();
    private Bitmap missing;
    private Bitmap lost;
    private Bitmap newGame;
    private Bitmap wonGame;
    public ImageView image1;
    public ImageView image2;
    public ImageView deck1, deck2, deck3, deck4, deck5, deck6, deck7, deck8, deck9;
    public LinearLayout layout;
    private int c=5;
    int idElement1, idElement2;
    private ImageView sendingDeck, receivingDeck;
    int flagFor2Click = 0;
    int r;
    private boolean isMoved  = true;
    private boolean isEasy;
    private int winStreakNumber;
    private TextView winStreak;
    SharedPreferences sharedPreferences;
    TextView RULE;
    private InterstitialAd interstitialAd;
    private int scores;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
       setSupportActionBar(toolbar);

        interstitialAd = new InterstitialAd(this);
        interstitialAd.setAdUnitId("ca-app-pub-9469424738513188/9277578934");
        AdRequest adRequest = new AdRequest.Builder().build();
        interstitialAd.loadAd(adRequest);


        View root = findViewById(android.R.id.content).getRootView();
        loadBitmaps();
        image1 = findViewById(R.id.imageView);
        image1.setImageBitmap(resizedBitmaps.get(0));

        winStreak = findViewById(R.id.win_streak);
        winStreak.setTypeface(Typeface.createFromAsset(getAssets(), "nick.ttf"));
        sharedPreferences = getDefaultSharedPreferences(getApplicationContext());
        winStreakNumber = sharedPreferences.getInt("winStreak", 0);

        Boolean loose = sharedPreferences.getBoolean("loose", true);
        int scoresGet = sharedPreferences.getInt("scores", 0);

        int lostGames = sharedPreferences.getInt("lostGames", 0);
        if (loose && scoresGet>3) lostGames++;

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("lostGames", lostGames);
        editor.putBoolean("loose", true);
        editor.apply();


        winStreak.setText("Longest Win Streak: "+ winStreakNumber+"\n"+
                "Longest Loose Streak: "+ lostGames);
        TextView rules = findViewById(R.id.rules);
        rules.setTypeface(Typeface.createFromAsset(getAssets(), "nick.ttf"));
        TextView about = findViewById(R.id.about);
        about.setTypeface(Typeface.createFromAsset(getAssets(), "nick.ttf"));




        //root.setOnTouchListener(new TL());

        for (int ar=0; ar<10; ar++){
    arrDeck.add(new ArrayDeque<Card>());
    }

        /** initialize cards 52*/
        for (int i=1; i<5; i++) {
            for (int j=1; j<14; j++) {
                c++;
                cardsShuffle.add(new Card(j, i, resizedBitmaps.get(c)));
            }
        }

      image2 = findViewById(R.id.imageView2);
      image2.setImageBitmap(resizedBitmaps.get(2));

      Card cardToDecks[] = reShuffleAces();
        Card ace1 = cardToDecks[0];


        isEasy = sharedPreferences.getBoolean("isEasy", true);

        deck1 = findViewById(R.id.deck1);
        deck1.setImageBitmap(ace1.getBitmap());
        arrDeck.get(0).push(ace1);


if (!isEasy) {


    deck2 = findViewById(R.id.deck2);
    deck2.setImageBitmap(cardToDecks[1].getBitmap());
    arrDeck.get(1).push(cardToDecks[1]);


    deck3 = findViewById(R.id.deck3);
    deck3.setImageBitmap(resizedBitmaps.get(1));


    deck4 = findViewById(R.id.deck4);
    deck4.setImageBitmap(cardToDecks[2].getBitmap());
    arrDeck.get(3).push(cardToDecks[2]);

    deck5 = findViewById(R.id.deck5);
    deck5.setImageBitmap(cardToDecks[3].getBitmap());
    arrDeck.get(4).push(cardToDecks[3]);

    deck6 = findViewById(R.id.deck6);
    deck6.setImageBitmap(cardToDecks[4].getBitmap());
    arrDeck.get(5).push(cardToDecks[4]);

    deck7 = findViewById(R.id.deck7);
    deck7.setImageBitmap(resizedBitmaps.get(1));

    deck8 = findViewById(R.id.deck8);
    deck8.setImageBitmap(cardToDecks[5].getBitmap());
    arrDeck.get(7).push(cardToDecks[5]);
}

else {

    deck2 = findViewById(R.id.deck2);
    deck2.setImageBitmap(resizedBitmaps.get(2));


    deck3 = findViewById(R.id.deck3);
    deck3.setImageBitmap(resizedBitmaps.get(1));


    deck4 = findViewById(R.id.deck4);
    deck4.setImageBitmap(resizedBitmaps.get(2));

    deck5 = findViewById(R.id.deck5);
    deck5.setImageBitmap(resizedBitmaps.get(2));

    deck6 = findViewById(R.id.deck6);
    deck6.setImageBitmap(resizedBitmaps.get(2));

    deck7 = findViewById(R.id.deck7);
    deck7.setImageBitmap(resizedBitmaps.get(1));

    deck8 = findViewById(R.id.deck8);
    deck8.setImageBitmap(resizedBitmaps.get(2));

}




        deck9 = findViewById(R.id.deck9);
        deck9.setImageBitmap(resizedBitmaps.get(1));

        ImageView newGame = findViewById(R.id.newGame);
       // newGame.setImageBitmap(resizedBitmaps.get(4));






        image1.setOnClickListener(new CL());
        image2.setOnClickListener(new CL());
        deck1.setOnClickListener(new CL());
        deck2.setOnClickListener(new CL());
        deck3.setOnClickListener (new CL());
        deck4.setOnClickListener(new CL());
        deck5.setOnClickListener(new CL());
        deck6.setOnClickListener (new CL());
        deck7.setOnClickListener(new CL());
        deck8.setOnClickListener(new CL());
        deck9.setOnClickListener(new CL());
        findViewById(R.id.LinearLayout1).setOnClickListener(new CL());
        newGame.setOnClickListener(new CL());
        rules.setOnClickListener(new CL());
        about.setOnClickListener(new CL());

        ids.add(R.id.imageView2); // all ids of layout to check it as goal in onClick
        ids.add(R.id.deck1);
        ids.add(R.id.deck2);
        ids.add(R.id.deck3);
        ids.add(R.id.deck4);
        ids.add(R.id.deck5);
        ids.add(R.id.deck6);
        ids.add(R.id.deck7);
        ids.add(R.id.deck8);
        ids.add(R.id.deck9);

    }

   synchronized private int idDeckCoordination(int id){
        int i=0;
        switch (id){
            case R.id.deck1:
                i=0;
                break;
            case R.id.deck2:
                i=1;
                break;
            case R.id.deck3:
                i=2;
                break;
            case R.id.deck4:
                i=3;
                break;
            case R.id.deck5:
                i=4;
                break;
            case R.id.deck6:
                i=5;
                break;
            case R.id.deck7:
                i=6;
                break;
            case R.id.deck8:
                i=7;
                break;
            case R.id.deck9:
                i=8;
                break;
            case R.id.imageView2:
                i=9;
                break;


        }
        return i;
    }
    private void reShaffle(){

       // Collections.shuffle(bitmaps);
    }

    private Card[] reShuffleAces(){
        Card[] cardsDistr = new Card[6];
        Card cardRemove=null;
        for (Card card: cardsShuffle
             ) {
            if (card.getValue()==1) aceShuffle.add(card);
        }

        Collections.shuffle(aceShuffle);

        for (Card card: cardsShuffle
                ) {

            if (card.getValue()==aceShuffle.get(0).getValue() && card.getSuit()==aceShuffle.get(0).getSuit())
                cardRemove = card;
        }
        cardsShuffle.remove(cardRemove);
        Collections.shuffle(cardsShuffle);
        cards = new ArrayDeque<>(cardsShuffle);
        cardsDistr[0] = aceShuffle.get(0);
        isEasy = sharedPreferences.getBoolean("isEasy", true);


        if (!isEasy) {
        for (int i = 1; i < cardsDistr.length; i++) {
           cardsDistr[i]  = cards.removeFirst();
        }}

        return cardsDistr;

    }

    private void loadBitmaps() {

        Matrix m = new Matrix();
        m.setScale(0.7f, 0.7f);

        bitmaps.add(BitmapFactory.decodeResource(getResources(), R.drawable.back));

        Bitmap green2 = BitmapFactory.decodeResource(getResources(), R.drawable.green2);
        bitmaps.add(adjustOpacity(green2, 100));

        Bitmap green = BitmapFactory.decodeResource(getResources(), R.drawable.green);
        bitmaps.add(adjustOpacity(green, 100));


        bitmaps.add(BitmapFactory.decodeResource(getResources(), R.drawable.lost));
        bitmaps.add(BitmapFactory.decodeResource(getResources(), R.drawable.newgame));
        bitmaps.add(BitmapFactory.decodeResource(getResources(), R.drawable.won));
        bitmaps.add(BitmapFactory.decodeResource(getResources(), R.drawable.spades1));
        bitmaps.add(BitmapFactory.decodeResource(getResources(), R.drawable.spades2));
        bitmaps.add(BitmapFactory.decodeResource(getResources(), R.drawable.spades3));
        bitmaps.add(BitmapFactory.decodeResource(getResources(), R.drawable.spades4));
        bitmaps.add(BitmapFactory.decodeResource(getResources(), R.drawable.spades5));
        bitmaps.add(BitmapFactory.decodeResource(getResources(), R.drawable.spades6));
        bitmaps.add(BitmapFactory.decodeResource(getResources(), R.drawable.spades7));
        bitmaps.add(BitmapFactory.decodeResource(getResources(), R.drawable.spades8));
        bitmaps.add(BitmapFactory.decodeResource(getResources(), R.drawable.spades9));
        bitmaps.add(BitmapFactory.decodeResource(getResources(), R.drawable.spades10));
        bitmaps.add(BitmapFactory.decodeResource(getResources(), R.drawable.spades11));
        bitmaps.add(BitmapFactory.decodeResource(getResources(), R.drawable.spades12));
        bitmaps.add(BitmapFactory.decodeResource(getResources(), R.drawable.spades13));
        bitmaps.add(BitmapFactory.decodeResource(getResources(), R.drawable.hearts1));
        bitmaps.add(BitmapFactory.decodeResource(getResources(), R.drawable.hearts2));
        bitmaps.add(BitmapFactory.decodeResource(getResources(), R.drawable.hearts3));
        bitmaps.add(BitmapFactory.decodeResource(getResources(), R.drawable.hearts4));
        bitmaps.add(BitmapFactory.decodeResource(getResources(), R.drawable.hearts5));
        bitmaps.add(BitmapFactory.decodeResource(getResources(), R.drawable.hearts6));
        bitmaps.add(BitmapFactory.decodeResource(getResources(), R.drawable.hearts7));
        bitmaps.add(BitmapFactory.decodeResource(getResources(), R.drawable.hearts8));
        bitmaps.add(BitmapFactory.decodeResource(getResources(), R.drawable.hearts9));
        bitmaps.add(BitmapFactory.decodeResource(getResources(), R.drawable.hearts10));
        bitmaps.add(BitmapFactory.decodeResource(getResources(), R.drawable.hearts11));
        bitmaps.add(BitmapFactory.decodeResource(getResources(), R.drawable.hearts12));
        bitmaps.add(BitmapFactory.decodeResource(getResources(), R.drawable.hearts13));
        bitmaps.add(BitmapFactory.decodeResource(getResources(), R.drawable.diamonds1));
        bitmaps.add(BitmapFactory.decodeResource(getResources(), R.drawable.diamonds2));
        bitmaps.add(BitmapFactory.decodeResource(getResources(), R.drawable.diamonds3));
        bitmaps.add(BitmapFactory.decodeResource(getResources(), R.drawable.diamonds4));
        bitmaps.add(BitmapFactory.decodeResource(getResources(), R.drawable.diamonds5));
        bitmaps.add(BitmapFactory.decodeResource(getResources(), R.drawable.diamonds6));
        bitmaps.add(BitmapFactory.decodeResource(getResources(), R.drawable.diamonds7));
        bitmaps.add(BitmapFactory.decodeResource(getResources(), R.drawable.diamonds8));
        bitmaps.add(BitmapFactory.decodeResource(getResources(), R.drawable.diamonds9));
        bitmaps.add(BitmapFactory.decodeResource(getResources(), R.drawable.diamonds10));
        bitmaps.add(BitmapFactory.decodeResource(getResources(), R.drawable.diamonds11));
        bitmaps.add(BitmapFactory.decodeResource(getResources(), R.drawable.diamonds12));
        bitmaps.add(BitmapFactory.decodeResource(getResources(), R.drawable.diamonds13));
        bitmaps.add(BitmapFactory.decodeResource(getResources(), R.drawable.clubs1));
        bitmaps.add(BitmapFactory.decodeResource(getResources(), R.drawable.clubs2));
        bitmaps.add(BitmapFactory.decodeResource(getResources(), R.drawable.clubs3));
        bitmaps.add(BitmapFactory.decodeResource(getResources(), R.drawable.clubs4));
        bitmaps.add(BitmapFactory.decodeResource(getResources(), R.drawable.clubs5));
        bitmaps.add(BitmapFactory.decodeResource(getResources(), R.drawable.clubs6));
        bitmaps.add(BitmapFactory.decodeResource(getResources(), R.drawable.clubs7));
        bitmaps.add(BitmapFactory.decodeResource(getResources(), R.drawable.clubs8));
        bitmaps.add(BitmapFactory.decodeResource(getResources(), R.drawable.clubs9));
        bitmaps.add(BitmapFactory.decodeResource(getResources(), R.drawable.clubs10));
        bitmaps.add(BitmapFactory.decodeResource(getResources(), R.drawable.clubs11));
        bitmaps.add(BitmapFactory.decodeResource(getResources(), R.drawable.clubs12));
        bitmaps.add(BitmapFactory.decodeResource(getResources(), R.drawable.clubs13));

        for (Bitmap b: bitmaps
             ) {
            int cardWidth = b.getWidth();
            int cardHeight = b.getHeight();

            resizedBitmaps.add(Bitmap.createBitmap(b, 0, 0, cardWidth, cardHeight, m, false));

        }


    }

    public class CL implements View.OnClickListener{

        @Override
        public synchronized void onClick(View v) {

                switch (v.getId()) {
                    case R.id.imageView:


                        if (cards.size()>1) {
                            image2.setImageBitmap(cards.peek().getBitmap());
                            arrDeck.get(9).push(cards.pop()); //putted to seen arrdeck image2
                            r++;
                        } else if (cards.size()==1) {
                            image2.setImageBitmap(cards.peek().getBitmap());
                            image1.setImageBitmap(resizedBitmaps.get(2));
                            arrDeck.get(9).push(cards.pop());
                        }


                       /* if (r < 45) {
                            image2.setImageBitmap(cards.peek().getBitmap());
                            arrDeck.get(9).push(cards.pop()); //putted to seen arrdeck image2
                            r++;
                        } else if (r == 45) {
                            image2.setImageBitmap(cards.peek().getBitmap());
                            image1.setImageBitmap(resizedBitmaps.get(2));
                            arrDeck.get(9).push(cards.pop());
                            r = 55;
                        }*/

                        break;
                    case R.id.imageView2: //OR operator for switch among all decks
                    case R.id.deck1:
                    case R.id.deck3:
                    case R.id.deck7:
                    case R.id.deck9:

                        flagFor2Click++;



                        if ((flagFor2Click % 2)!= 0)
                            idElement1 = v.getId();


                        if ((flagFor2Click % 2) == 0) {
                            isMoved = false;
                            idElement2 = v.getId();
                            if (!ids.contains(idElement2)) idElement2 = idElement1;
                            if (!ids.contains(idElement1)) return;


                             //if one more non movable touch happened, rearrange order again

                            transferPiles();

                            if (!isMoved) {
                                idElement1 = v.getId();
                                flagFor2Click++;
                            }

                        }
                        break;
                    case R.id.deck2:
                    case R.id.deck4:
                    case R.id.deck5:
                    case R.id.deck6:
                    case R.id.deck8:
                    case R.id.LinearLayout1:
                    case R.id.LinearLayout2:
                    case R.id.LinearLayout3:
                    case R.id.LinearLayout4:
                    case R.id.LinearLayout5:
                    case R.id.LinearLayout6:

                        flagFor2Click++;


                        if ((flagFor2Click % 2)!= 0)
                            idElement1 = v.getId();



                        if ((flagFor2Click % 2) == 0) {
                            isMoved = false;
                            idElement2 = v.getId();
                            if (!ids.contains(idElement2)) idElement2 = idElement1;
                            if (!ids.contains(idElement1)) return;

                             //if one more non movable touch happened, rearrange order again

                            transferDecks();

                            if (!isMoved) {
                                idElement1 = v.getId();
                                flagFor2Click++;
                            }
                        }

                        break;

                    case R.id.newGame:

                        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                        builder
                                .setTitle("Start NEW game?")
                                .setIcon(R.drawable.ic_autorenew_black_24dp)
                                .setCancelable(true)
                                .setNegativeButton("Start..",
                                        new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int id) {
                                                dialog.cancel();
                                                recreate();
                                            }
                                        });
                        AlertDialog alert = builder.create();
                        alert.show();
                        alert.getWindow().setBackgroundDrawableResource(R.color.yellow_color);
                        break;

                    case R.id.rules:

                        ScrollView view = (ScrollView) getLayoutInflater()
                                .inflate(R.layout.rules_layout, null);
                        CheckBox isEasy = view.findViewById(R.id.easier);

                        isEasy.setChecked( sharedPreferences.getBoolean("isEasy", true));

                        isEasy.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

                            @Override
                            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                               if (isChecked) {
                                   SharedPreferences.Editor editor = sharedPreferences.edit();
                                   editor.putBoolean("isEasy", true);
                                   editor.apply();
                               }

                               else {
                                   SharedPreferences.Editor editor = sharedPreferences.edit();
                                   editor.putBoolean("isEasy", false);
                                   editor.apply();
                               }
                            }
                        });




                        AlertDialog.Builder builderRul = new AlertDialog.Builder(MainActivity.this);



                        RULE = view.findViewById(R.id.rules_text);
                        RULE.setText(R.string.rules_descr);
                        RULE.setPadding(10,10,10,10);
                        RULE.setTextSize(18);
                        RULE.setTypeface(Typeface.createFromAsset(getAssets(), "yellow_t.ttf"));

                        builderRul

                                .setView(view)
                                .setCancelable(true)

                                .setPositiveButton("Clear Stats..",
                                        new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int whichButton) {
                                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                                winStreakNumber  = 0;
                                                editor.putInt("winStreak", winStreakNumber);
                                                editor.putInt("lostGames", 0);
                                                editor.apply();
                                                winStreak.setText("Longest Win Streak: "+ winStreakNumber+"\n"+
                                                        "Longest Loose Streak: 0");
                                            }
                                        }
                                )

                                .setNegativeButton("Close",
                                        new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int id) {
                                                dialog.cancel();
                                            }
                                        });




                        AlertDialog alertRul = builderRul.create();
                        alertRul.show();
                        alertRul.getWindow().setBackgroundDrawableResource(R.color.yellow_color);
                        break;


                    case R.id.about:

                        final TextView aboutNew = new TextView (MainActivity.this);
                        aboutNew.setText("Maxim Chikunov \n Copyright © 2019 \n For all suggestions, " +
                                "support and information feel free to contact on email \n cmax5644@gmail.com");
                        aboutNew.setPadding(10,10,10,10);
                        aboutNew.setGravity(Gravity.CENTER);
                        aboutNew.setTextSize(18);
                        aboutNew.setTypeface(Typeface.createFromAsset(getAssets(), "yellow_t.ttf"));


                        AlertDialog.Builder builderAbout = new AlertDialog.Builder(MainActivity.this);
                        builderAbout

                                .setView(aboutNew)
                                .setCancelable(true)
                                .setNegativeButton("Close",
                                        new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int id) {
                                                dialog.cancel();
                                            }
                                        });
                        AlertDialog alertAb = builderAbout.create();
                        alertAb.show();
                        alertAb.getWindow().setBackgroundDrawableResource(R.color.yellow_color);
                        break;




                }

        }







        synchronized void transferDecks(){

            if (idElement2 == R.id.imageView2) return;

            Card cardTrans =  arrDeck.get(idDeckCoordination(idElement1)).peekFirst();
            Card cardDest = arrDeck.get(idDeckCoordination(idElement2)).peekFirst();
            if (cardTrans==null) return;
            if (cardDest==null) {    //new card played to deck
                arrDeck.get(idDeckCoordination(idElement1)).removeFirst();
                arrDeck.get(idDeckCoordination(idElement2)).push(cardTrans);
                isMoved = true;
                scores++;
                transferBitmaps();
            }

            //other cards regardless suit descending
            if (cardDest != null && (cardDest.getValue()-cardTrans.getValue())==1) {
                arrDeck.get(idDeckCoordination(idElement1)).removeFirst();
                arrDeck.get(idDeckCoordination(idElement2)).push(cardTrans);
                isMoved = true;
                scores++;
                transferBitmaps();
            }
        }

       synchronized void transferPiles(){

           if (idElement2 == R.id.imageView2) return;

            Card cardTrans =  arrDeck.get(idDeckCoordination(idElement1)).peekFirst();
            Card cardDest = arrDeck.get(idDeckCoordination(idElement2)).peekFirst();
            if (cardTrans==null) return;

            if (cardDest==null && cardTrans.getValue()==1) {    //ace in pile
                arrDeck.get(idDeckCoordination(idElement1)).removeFirst();
                arrDeck.get(idDeckCoordination(idElement2)).push(cardTrans);
                isMoved = true;
                scores++;
                transferBitmaps();
            }

            //other cards by suit ascending
            if (cardDest != null && cardDest.getSuit()==cardTrans.getSuit() && (cardDest.getValue()-cardTrans.getValue())==-1
                    ) {
                arrDeck.get(idDeckCoordination(idElement1)).removeFirst();
                arrDeck.get(idDeckCoordination(idElement2)).push(cardTrans);
                isMoved = true;
                scores++;
                transferBitmaps();
            }

            if (arrDeck.get(0).peekFirst()!=null&& //win all Kings up
                    arrDeck.get(2).peekFirst()!=null&&
                    arrDeck.get(6).peekFirst()!=null &&
                    arrDeck.get(8).peekFirst()!=null&&

                    arrDeck.get(0).peekFirst().getValue()==13&& //win all Kings up
                    arrDeck.get(2).peekFirst().getValue()==13&&
                    arrDeck.get(6).peekFirst().getValue()==13&&
                    arrDeck.get(8).peekFirst().getValue()==13){


                winStreakNumber = sharedPreferences.getInt("winStreak", 0);
                winStreakNumber +=1;
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putInt("winStreak", winStreakNumber);
                editor.apply();
                winStreak.setTypeface(Typeface.createFromAsset(getAssets(), "nick.ttf"));
                winStreak.setText("Longest Win Streak: "+ winStreakNumber);






                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder
                        .setTitle("CONGRATULATIONS! YOU WIN!!!")
                        .setIcon(R.drawable.ic_face_black_24dp)
                        .setCancelable(true)
                        .setNegativeButton("Start NEW?",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        dialog.cancel();
                                        recreate();

                                    }
                                });
                AlertDialog alert = builder.create();
                alert.show();
                alert.getWindow().setBackgroundDrawableResource(R.color.yellow_color);
                SharedPreferences.Editor editor1 = sharedPreferences.edit();
                editor1.putBoolean("loose", false);
                editor1.apply();


            }


        }

        void transferBitmaps(){
            sendingDeck =findViewById(idElement1);
            if (arrDeck.get(idDeckCoordination(idElement1)).peekFirst() == null)
                sendingDeck.setImageBitmap(resizedBitmaps.get(2));
            else
                sendingDeck.setImageBitmap(arrDeck.get(idDeckCoordination(idElement1)).peekFirst().getBitmap());
            receivingDeck = findViewById(idElement2);
            receivingDeck.setImageBitmap(arrDeck.get(idDeckCoordination(idElement2)).peekFirst().getBitmap());
        }
    }
    public class TL implements View.OnTouchListener {
        Float eXs, eYs = 0f;
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        eXs = event.getX();
                        eYs = event.getY();
                        Context context = getApplicationContext();
                        Toast toast = Toast.makeText(context, eXs + "\n" + eYs + "\n", Toast.LENGTH_SHORT);
                        toast.show();
                    case MotionEvent.ACTION_MOVE:
                        float eX = event.getX();
                        float eY = event.getY();
                        float ass = eX-eXs;
                        Context context1 = getApplicationContext();
                        Toast toast1 = Toast.makeText(context1, eX + "\n" + eY + "\n"+ ass + "\n", Toast.LENGTH_SHORT);
                        toast1.show();




                    case MotionEvent.ACTION_UP:
                        float eXu = event.getX();
                        float eYu = event.getY();
                        ObjectAnimator oax = ObjectAnimator.ofFloat(image1, "x", eXu-100, eXu-100);
                        ObjectAnimator oay = ObjectAnimator.ofFloat(image1, "y", eYu-250, eYu-250);
                        oax.setDuration(1000);
                        oay.setDuration(1000);
                        AnimatorSet animatorSet = new AnimatorSet();
                        animatorSet.playTogether(oax, oay);
                        animatorSet.start();

                }

                return false;
            }

    }

    /*private Bitmap adjustOpacity(Bitmap src, int value)
    {
        int width = src.getWidth();
        int height = src.getHeight();
        Bitmap transBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(transBitmap);
        canvas.drawARGB(0, 0, 0, 0);
        // config paint
        final Paint paint = new Paint();
        paint.setAlpha(value);
        canvas.drawBitmap(src, 0, 0, paint);
        return transBitmap;
    }*/

    private Bitmap adjustOpacity(Bitmap bitmap, int opacity)
    {
        Bitmap mutableBitmap = bitmap.isMutable()
                ? bitmap
                : bitmap.copy(Bitmap.Config.ARGB_8888, true);
        Canvas canvas = new Canvas(mutableBitmap);
        int colour = (opacity & 0xFF) << 24;
        canvas.drawColor(colour, PorterDuff.Mode.DST_IN);
        return mutableBitmap;
    }

    protected void onPause() {
        super.onPause();
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("scores", scores);
        editor.apply();


    }

    @Override
    public void onBackPressed(){
        if (interstitialAd.isLoaded()) {
            interstitialAd.show();
            interstitialAd.setAdListener(new AdListener() {
                @Override
                public void onAdClosed() {
                    super.onAdClosed();
                    finish();
                }
            });
        }else{
            super.onBackPressed();
        }

    }

}
