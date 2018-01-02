package com.example.iamon.connect_three;

import android.graphics.Color;
import android.graphics.Typeface;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {
    // 0 = blue,  1 = red;

    int [][]gameState = new int[3][3];  // -1 means unplayed
    int player = 0;
    int moves_count = 0;
    boolean gameActive = true;
    ConstraintLayout constraintLayout;
    LinearLayout linearVerticalLayout;

    public void dropIn(View view){
        ImageView imageView = (ImageView)view;

        String tag = imageView.getTag().toString();
//        Log.i("Tag", tag);
        int x = getXVal(tag);
        int y = getYVal(tag);

//        Log.i("TagX", Integer.toString(x));
//        Log.i("TagY", Integer.toString(y));

        if(gameState[x][y] == -1 && gameActive == true){
            if(player == 1){
                constraintLayout.setBackgroundColor(Color.BLUE);
            }
            else{
                constraintLayout.setBackgroundColor(Color.RED);
            }
            imageView.setTranslationY(-1000f);
            if(player == 0)
                imageView.setImageResource(R.drawable.blue);
            else
                imageView.setImageResource(R.drawable.red);

            imageView.animate().translationYBy(1000f).rotation(360).setDuration(700);
            gameState[x][y] = player;
            if(playerWin()){
                linearVerticalLayout.setVisibility(View.VISIBLE);
                gameActive = false;
                TextView textView = (TextView)findViewById(R.id.textView);
                if(player == 0){
                    String message = "Blue Wins!";
                    textView.setText(message);
                    constraintLayout.setBackgroundColor(Color.BLUE);
                    textView.setTextColor(Color.BLUE);
                }
                else{
                    String message = "Red Wins!";
                    textView.setText(message);
                    constraintLayout.setBackgroundColor(Color.RED);
                    textView.setTextColor(Color.RED);
                }
                linearVerticalLayout.setBackgroundColor(Color.WHITE);
                textView.setTypeface(textView.getTypeface(), Typeface.BOLD);

            }
            player ^= 1;
            moves_count++;
        }
        if(moves_count == 9 && gameActive){
            playAgainOption();
        }
    }

    public void playAgainOption(){
        linearVerticalLayout.setVisibility(View.VISIBLE);
        TextView textView = (TextView) findViewById(R.id.textView);
        textView.setText("Draw");
        textView.setTextColor(Color.GRAY);
    }

    public void reset(View view){
        resetBoard();
        player = 0;
        constraintLayout.setBackgroundColor(Color.BLUE);
        GridLayout gridLayout = (GridLayout) findViewById(R.id.gridLayout);
        for(int i=0; i<gridLayout.getChildCount(); i++){
            ((ImageView) gridLayout.getChildAt(i)).setImageResource(0);
        }
        gameActive = true;
        TextView textView = (TextView)findViewById(R.id.textView);
        textView.setText("");
        linearVerticalLayout.setVisibility(View.INVISIBLE);
        moves_count = 0;
    }

    public boolean playerWin(){
        if(gameState[0][1] != -1){  // top horizontal
            if(gameState[0][0] == gameState[0][1] && gameState[0][1] == gameState[0][2])
                return true;
        }
        if(gameState[2][1] != -1){  // bottom horizontal
            if(gameState[2][0] == gameState[2][1] && gameState[2][1] == gameState[2][2])
                return true;
        }

        if(gameState[1][0] != -1){  // first vertical
            if(gameState[0][0] == gameState[1][0] && gameState[1][0] == gameState[2][0])
                return true;
        }
        if(gameState[1][2] != -1){  // last vertical
            if(gameState[0][2] == gameState[1][2] && gameState[1][2] == gameState[2][2])
                return true;
        }

        if(gameState[1][1] != -1){
            if(gameState[1][1] == gameState[0][0] && gameState[1][1] == gameState[2][2])    // "\"
                return true;

            if(gameState[1][1] == gameState[0][2] && gameState[1][1] == gameState[2][0])    // "/"
                return true;

            if(gameState[1][1] == gameState[0][1] && gameState[1][1] == gameState[2][1])    // "|"
                return true;

            if(gameState[1][1] == gameState[1][0] && gameState[1][1] == gameState[1][2])    // "--"
                return true;
        }
        return  false;
    }

    public int getXVal(String str){
        if(str.length() < 2){
            Log.i("Error", "Invalid argument tag { public int getXval(String str) }");
            return -1;
        }
        char X = str.charAt(0);
        return Character.getNumericValue(X);
    }

    public int getYVal(String str){
        if(str.length() < 2){
            Log.i("Error", "Invalid argument tag { public int getYval(String str) }");
            return -1;
        }
        char Y = str.charAt(1);
        return Character.getNumericValue(Y);
    }

    public void resetBoard(){
        for(int i=0; i<3; i++){
            for(int j=0; j<3; j++){
                gameState[i][j] = -1;
            }
        }
        player = 0;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        resetBoard();
        constraintLayout = (ConstraintLayout)findViewById(R.id.constraintLayout);
        constraintLayout.setBackgroundColor(Color.BLUE);
        linearVerticalLayout = (LinearLayout) findViewById(R.id.linearVertical);
        linearVerticalLayout.setVisibility(View.INVISIBLE);
    }
}
