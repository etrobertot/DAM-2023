package co.edu.unal.tictactoe;

import android.graphics.Color;
import android.os.Bundle;

import android.support.v7.app.AppCompatActivity;
import android.view.*;
import android.widget.*;

public class TicTacToeActivity extends AppCompatActivity {

	 //It represents the game's internal state
    private TicTacToeGame ticTacToeGame;
    //The buttons make up the board
    private Button boardButtons[];
    //Text displayed as game's information (Turn and winner's game)
    private TextView infoGame, infoNumberHumanWins, infoNumberAndroidWins, infoNumberTies;

    //This variables allow to control the game's statistics (Number of games and wins per player)
    private int numberGame = 1, numberHumanWins = 0, numberAndroidWins = 0, numberTies = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tic_tac_toe);

        boardButtons = new Button[TicTacToeGame.numberSpots];
        boardButtons[0] = findViewById(R.id.one);
        boardButtons[1] = findViewById(R.id.two);
        boardButtons[2] = findViewById(R.id.three);
        boardButtons[3] = findViewById(R.id.four);
        boardButtons[4] = findViewById(R.id.five);
        boardButtons[5] = findViewById(R.id.six);
        boardButtons[6] = findViewById(R.id.seven);
        boardButtons[7] = findViewById(R.id.eight);
        boardButtons[8] = findViewById(R.id.nine);
        infoGame = findViewById(R.id.game_information);
        infoNumberHumanWins = findViewById(R.id.number_human_wins);
        infoNumberAndroidWins = findViewById(R.id.number_android_wins);
        infoNumberTies = findViewById(R.id.number_ties);
        ticTacToeGame = new TicTacToeGame();
        startNewGame();
    }

    private void startNewGame(){
        ticTacToeGame.clearBoard();

        //It resets all buttons
        for(int spot = 0; spot < boardButtons.length; spot++) {
            boardButtons[spot].setText("");
            boardButtons[spot].setEnabled(true);
            boardButtons[spot].setOnClickListener(new ButtonClickListener(spot));
        }

        //According to the turn, human or computer player goes first
        if(numberGame % 2 == 1){
            infoGame.setText(R.string.first_turn_human);
        }else{
            infoGame.setText(R.string.first_turn_computer);
            int move = ticTacToeGame.getComputerMove();
            ticTacToeGame.setMove(TicTacToeGame.computerPlayer, move);
            boardButtons[move].setEnabled(false);
            boardButtons[move].setText(String.valueOf(TicTacToeGame.computerPlayer));
            boardButtons[move].setTextColor(Color.rgb(46, 185, 179));
            infoGame.setText(R.string.turn_human);
        }
        infoNumberHumanWins.setText("Jugador: " + numberHumanWins);
        infoNumberAndroidWins.setText("Maquina: " + numberAndroidWins);
        infoNumberTies.setText("Empates: " + numberTies);
    }

    private void setMove(char player, int location){
        ticTacToeGame.setMove(player, location);
        boardButtons[location].setEnabled(false);
        boardButtons[location].setText(String.valueOf(player));

        if(player == TicTacToeGame.humanPlayer){
            boardButtons[location].setTextColor(Color.rgb(207,41,175));
        }else{
            boardButtons[location].setTextColor(Color.rgb(46,185,179));
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        super.onCreateOptionsMenu(menu);
        menu.add(R.string.new_game);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        numberGame++;
        startNewGame();
        return true;
    }

    //It handles clicks on the gameboard buttons
    private class ButtonClickListener implements View.OnClickListener{
        int location;
        public ButtonClickListener(int location){
            this.location = location;
        }

        public void onClick(View view){
            if(boardButtons[location].isEnabled()){
                setMove(TicTacToeGame.humanPlayer, location);
                //If there isn't winner yet, then it lets the computer make a move
                int winner = ticTacToeGame.checkForWinner();
                if(winner == TicTacToeGame.gameNotFinished){
                    infoGame.setText(R.string.turn_computer);
                    int move = ticTacToeGame.getComputerMove();
                    setMove(TicTacToeGame.computerPlayer, move);
                    winner = ticTacToeGame.checkForWinner();
                }

                if(winner == TicTacToeGame.gameNotFinished){
                    infoGame.setText(R.string.turn_human);
                }else if(winner == TicTacToeGame.gameTied){
                    infoGame.setText(R.string.result_tie);
                    for(int spot = 0; spot < boardButtons.length; spot++){
                        boardButtons[spot].setEnabled(false);
                    }
                    numberTies++;
                    infoNumberTies.setText("Empate: " + numberTies);
                }else if(winner == TicTacToeGame.gameWithHumanWinner){
                    infoGame.setText(R.string.result_human_wins);
                    for(int spot = 0; spot < boardButtons.length; spot++){
                        boardButtons[spot].setEnabled(false);
                    }
                    numberHumanWins++;
                    infoNumberHumanWins.setText("Jugador: " + numberHumanWins);
                }else{
                    infoGame.setText(R.string.result_computer_wins);
                    for(int spot = 0; spot < boardButtons.length; spot++){
                        boardButtons[spot].setEnabled(false);
                    }
                    numberAndroidWins++;
                    infoNumberAndroidWins.setText("Maquina: " + numberAndroidWins);
                }
            }
        }
    }

}
