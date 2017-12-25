package com.rajeshk.connect3;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    // 0 Yellow 1 Red
    int currentPlayer = 0;
    boolean gameIsActive= true;
    // 2 means unplayed
    int[] gameState = {2, 2, 2, 2, 2, 2, 2, 2, 2};
    int[][] winningPositions={{0,1,2},{3,4,5},{6,7,8},{0,3,6},{1,4,7},{2,5,8},{0,4,8},{2,4,6}};

    public void dropIn(View view) {
        ImageView counter = (ImageView) view;
        System.out.println(counter.getTag().toString());

        int tappedCounter = Integer.parseInt(counter.getTag().toString());
        if (gameState[tappedCounter] == 2 && gameIsActive ) {
            counter.setTranslationY(-1000f);
            if (currentPlayer == 0) {
                counter.setImageResource(R.drawable.yellow);
                currentPlayer = 1;
            } else {
                counter.setImageResource(R.drawable.red);
                currentPlayer = 0;
            }

            counter.animate().translationYBy(1000f).rotation(100).setDuration(300);
            gameState[tappedCounter] = currentPlayer;
            for(int[] winningposition: winningPositions){
                if(gameState[winningposition[0]]== gameState[winningposition[1]]&& gameState[winningposition[1]] ==gameState[winningposition[2]] && gameState[winningposition[0]]!=2){
                    //Toast.makeText(this, ""+gameState[winningposition[0]] +"has won!", Toast.LENGTH_SHORT).show();
                    //Someone has won
                    String winner="Red";
                    if(gameState[winningposition[0]]==1){
                        winner="Yellow";
                    }

                    TextView winnerMessage= (TextView) findViewById(R.id.winnerMessage);
                    winnerMessage.setText(winner+" has won!");

                    LinearLayout layout= (LinearLayout) findViewById(R.id.playAgainLayout);
                    layout.setVisibility(View.VISIBLE);
                    gameIsActive= false;
                    break;
                }
                else{
                    boolean gameIsOver= true;
                    for(int counterState: gameState){
                        if(counterState==2){
                            gameIsOver= false;
                        }

                    }
                    if(gameIsOver){
                        TextView winnerMessage= (TextView) findViewById(R.id.winnerMessage);
                        winnerMessage.setText("It's a draw");
                        LinearLayout layout= (LinearLayout) findViewById(R.id.playAgainLayout);
                        layout.setVisibility(View.VISIBLE);
                    }
                }
            }
        }
    }

    public void playAgain(View view){
        LinearLayout layout= (LinearLayout) findViewById(R.id.playAgainLayout);
        layout.setVisibility(View.INVISIBLE);
        currentPlayer=0;
        for(int i=0;i<gameState.length;i++) {
            gameState[i] = 2;
        }
        GridLayout gridlayout= (GridLayout) findViewById(R.id.gridLayout);
        for(int i=0;i<gridlayout.getChildCount();i++){
            ((ImageView)gridlayout.getChildAt(i)).setImageResource(0);
        }
        gameIsActive= true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
