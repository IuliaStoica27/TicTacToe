package com.link.tictactoe;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;

public class SecondActivity extends AppCompatActivity {
    ArrayList<ImageButton> gridButtons = new ArrayList<>(9);
    ImageButton o, x;
    TextView status;
    ArrayList<ImageButton> computerPositions = new ArrayList<>();
    ArrayList<ImageButton> userPositions = new ArrayList<>();
    boolean userChose0, userChoseX;
    TextView chooseMark;
    //counts the players marks
    int counter = 0;

    // 0 - assign for computer
    // 1 - assign for user
    // -1 - when null
    int[] gameState = {-1, -1, -1, -1, -1, -1, -1, -1, -1};

    // winning positions based on indices of gameState
    int[][] winPositions ={{0, 1, 2}, {3, 4, 5}, {6, 7, 8},
                           {0, 3, 6}, {1, 4, 7}, {2, 5, 8},
                           {0, 4, 8}, {2, 4, 6}};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        getIntent();
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.mipmap.ic_launcher_round);

        status = findViewById(R.id.statusText);
        gridButtons.add(  findViewById(R.id.imgBtn0));
        gridButtons.add(  findViewById(R.id.imgBtn1));
        gridButtons.add(  findViewById(R.id.imgBtn2));
        gridButtons.add(  findViewById(R.id.imgBtn3));
        gridButtons.add(  findViewById(R.id.imgBtn4));
        gridButtons.add(  findViewById(R.id.imgBtn5));
        gridButtons.add(  findViewById(R.id.imgBtn6));
        gridButtons.add(  findViewById(R.id.imgBtn7));
        gridButtons.add(  findViewById(R.id.imgBtn8));

        for (ImageButton btn : gridButtons) {
            btn.setEnabled(false);
        }

    }

    // method that is called when user chooses X or 0 buttons.
    public void userMarkChoiceClicked(View view){
        o =  findViewById(R.id.imageButtonO);
        x =  findViewById(R.id.imageButtonX);
        chooseMark = findViewById(R.id.chooseMarkText);
        if(view == o){
            userChose0 = true;
        } else {
            userChoseX = true;
        }
        for (ImageButton btn : gridButtons) {
            btn.setEnabled(true);
        }
        computerChoice();
        o.setEnabled(false); x.setEnabled(false);
        o.setVisibility(View.INVISIBLE); x.setVisibility(View.INVISIBLE);
        chooseMark.setVisibility(View.INVISIBLE);
        Toast.makeText(this, "Game started! It's your turn", Toast.LENGTH_SHORT).show();

    }

    // method that enables computer to choose randomly from the gridButtons list
    // and add it's mark in an empty box of the grid
    public void computerChoice(){
        Random r = new Random();
        int i = r.nextInt(gridButtons.size());
        if(userChose0){
            gridButtons.get(i).setImageResource(R.drawable.x_mark);}
        if(userChoseX){
            gridButtons.get(i).setImageResource(R.drawable.o_mark);}
        gridButtons.get(i).setEnabled(false);

        // add the computer's chosen position to a new list of computer's chosen positions
        computerPositions.add(gridButtons.get(i));

        //mark computer's position in gameState
        int imgChosen = Integer.parseInt(gridButtons.get(i).getTag().toString());
        gameState[imgChosen] = 0;

        //update the initial grid list
        gridButtons.remove(i);
        counter++;
        verifyCompPositions();
    }

    // checks if computer won or if it's a tie
    public void verifyCompPositions() {
        if (counter == 9) {
            String matchDraw = "Match Draw! Click on Reset Game button to start a new game.";
            status.setText(matchDraw);
        }

        if(computerPositions.size()>=3) {
            for (int[] winPosition : winPositions) {
                if (gameState[winPosition[0]] == gameState[winPosition[1]] &&
                        gameState[winPosition[1]] == gameState[winPosition[2]] &&
                        gameState[winPosition[0]] != -1) {
                    for (ImageButton btn : gridButtons) {
                        btn.setEnabled(false);
                    }
                    String userLost = "You lost! Click on Reset Game button to start a new game.";
                    status.setText(userLost);
                }
            }
        }

    }
        //method that is called every time a user checks an empty box from the grid
        public void gridButtonsClicked (View view){
            ImageButton imgClicked = (ImageButton) view;
            counter++;
            int clickedImg = Integer.parseInt(imgClicked.getTag().toString());
            gameState[clickedImg] = 1;
            if (userChose0) {
                imgClicked.setImageResource(R.drawable.o_mark);
            } else if (userChoseX) {
                imgClicked.setImageResource(R.drawable.x_mark);
            }
            imgClicked.setEnabled(false);
            userPositions.add(imgClicked);

            gridButtons.remove(imgClicked);
            verifyUserPositions();

        }

        //checks if user won and calls computerChoice() method if the user didn't win
        public void verifyUserPositions () {
            String userWonMsg = "You won! Click on Reset Game button to start a new game.";
            boolean userWon = false;
            if (userPositions.size() >= 3) {
                for (int[] winPosition : winPositions) {
                    if (gameState[winPosition[0]] == gameState[winPosition[1]] &&
                            gameState[winPosition[1]] == gameState[winPosition[2]] &&
                            gameState[winPosition[0]] != -1) {
                        userWon = true;
                        break;
                    }
                }
            } else {
                computerChoice();
            }
            if(userWon){
                for (ImageButton btn : gridButtons) {
                    btn.setEnabled(false); }
                status.setText(userWonMsg);
            } else if(userPositions.size() >= 3){
                computerChoice();
            }

        }

        // resets game by returning all the variables and objects to their initial states
        public void resetGame (View view){
            if (computerPositions.size() == 0) {
                return;
            }
            gridButtons.addAll(computerPositions);
            gridButtons.addAll(userPositions);

            for (ImageButton btn : gridButtons) {
                btn.setImageResource(R.drawable.grid_white);
            }
            computerPositions.clear();
            userPositions.clear();

            o.setVisibility(View.VISIBLE); x.setVisibility(View.VISIBLE);
            chooseMark.setVisibility(View.VISIBLE);

            status.setText("");
            counter = 0;
            o.setEnabled(true);
            x.setEnabled(true);
            userChoseX = false;
            userChose0 = false;
            gameState = new int[]{-1, -1, -1, -1, -1, -1, -1, -1, -1};
        }



}