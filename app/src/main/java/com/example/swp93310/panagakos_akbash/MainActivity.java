package com.example.swp93310.panagakos_akbash;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private Akbash game;
    private int boardSize = 6;
    private int moveCount;
    private TextView moveCountText;
    private MediaPlayer clink;
    private MediaPlayer tada;
    private MediaPlayer bgMusic;
    private boolean musicPlaying;

    /**
     * sets up the Gameboard and MediaPlayers for Akbash.
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        moveCount=0;
        setContentView(R.layout.activity_main);
        moveCountText = (TextView) findViewById(R.id.moveCount);
        moveCountText.setText("Moves: "+moveCount);

        game = new Akbash(boardSize, 1);//One shuffle when app is first loaded for demo purposes
        updateBoard();
        clink = MediaPlayer.create(this, R.raw.clink);
        clink.setLooping(false);
        tada = MediaPlayer.create(this, R.raw.tada);
        tada.setLooping(false);
        bgMusic = MediaPlayer.create(this, R.raw.background_music);
        bgMusic.setLooping(true);
        bgMusic.start();
        musicPlaying=true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * Shuffles the game board.
     * @param view
     */
    public void shuffleClick(View view)
    {
        /*
         * Alert Dialog code modified from:
         * https://stackoverflow.com/questions/36747369/how-to-show-a-pop-up-in-android-studio-to-confirm-an-order
         * posted April 20 2016 at 14:49
         */
        AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
        builder.setCancelable(true);
        builder.setTitle(getString(R.string.shuffle));
        builder.setMessage(getString(R.string.shuffleConfirmation));
        builder.setPositiveButton(getString(R.string.yes),
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        game = new Akbash(boardSize);//shuffles boardSize^2 times
                        moveCount=0;
                        moveCountText.setText("Moves: "+moveCount);
                        updateBoard();
                        clink.start();
                    }
                });
        builder.setNegativeButton(getString(R.string.no), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    /**
     * turns off background music if it is playing
     */
    @Override
    protected void onPause()
    {
        super.onPause();
        if(musicPlaying)
            bgMusic.pause();
    }

    /**
     * turns background music back on if it was already on
     */
    @Override
    protected void onResume()
    {
        super.onResume();
        if(musicPlaying)
            bgMusic.start();
    }

    /**
     * turns music on or off
     * @param view
     */
    public void musicClick(View view)
    {
        if(musicPlaying)
        {
            bgMusic.pause();
            musicPlaying=false;
        }
        else
        {
            bgMusic.start();
            musicPlaying=true;
        }

    }

    /**
     * Rotates the selected sub-grid when the corresponding button is pressed
     * @param view
     */
    public void gameBoardClick(View view)
    {
        Button button = (Button) view;
        if(!game.isOver())
        {
            try
            {
                /* Retrieves the text of the pressed button converts the char Sequence to a String
                 * then converts the String to an int to pass into the rotate method of the game
                 */
                game.rotate(Integer.parseInt(button.getText().toString()));
            } catch (NumberFormatException e)
            {
                return;
            }
            moveCountText.setText("Moves: "+ (++moveCount));
            updateBoard();
            clink.start();
        }
    }

    /**
     * Displays information about the game.
     * @param view
     */
    public void showInfo(View view)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
        builder.setMessage(getString(R.string.gameInfo)).setPositiveButton(getString(R.string.okay), new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    /**
     * updates the button texts in the gameboard to correspond to the state of the game.
     * displays an alert if the game is in winning state
     */
    private void updateBoard()
    {
        int[] buttonIds = {R.id.button0,R.id.button1,R.id.button2,R.id.button3,R.id.button4,R.id.button5,R.id.button6,R.id.button7,
                R.id.button8,R.id.button9,R.id.button10,R.id.button11,R.id.button12,R.id.button13,R.id.button14,R.id.button15,
                R.id.button16,R.id.button17,R.id.button18,R.id.button19,R.id.button20,R.id.button21,R.id.button22,R.id.button23,
                R.id.button24,R.id.button25,R.id.button26,R.id.button27,R.id.button28,R.id.button29,R.id.button30,R.id.button31,
                R.id.button32,R.id.button33,R.id.button34,R.id.button35};

        Button[] buttons = new Button[buttonIds.length];
        for(int i=0; i<buttons.length; i++) buttons[i] = findViewById(buttonIds[i]);

        for(int i=0,k=0; i<boardSize; i++)
            for(int j=0; j<boardSize; j++, k++)
                buttons[k].setText(Integer.toString(game.getGameBoard()[i][j]));

        if(game.isOver())
        {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage(getString(R.string.youWin)).setPositiveButton(getString(R.string.okay), new DialogInterface.OnClickListener()
            {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            AlertDialog dialog = builder.create();
            dialog.show();
            tada.start();
        }
    }

}