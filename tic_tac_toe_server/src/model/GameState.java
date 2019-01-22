/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author Ashraf_R
 */
public class GameState {

    private int gameCellCounter;
    private char gameBoard[][];
    private boolean isRecording;
    private String recriver;

    public GameState(String recriver) {
        gameCellCounter = 0;
        gameBoard = new char[3][3];
        isRecording = false;
        this.recriver = recriver;
    }

    public String getRecriver() {
        return recriver;
    }

    public boolean getIsRecording() {
        return isRecording;
    }

    public void setIsRecording(boolean isRecording) {
        this.isRecording = isRecording;
    }

    boolean isDraw() {
        return (gameCellCounter == 9 && !(isWin('x') || isWin('o')));
    }

    public int getGameCellCounter() {
        return gameCellCounter;
    }

    private void increamantGameCellCounter() {
        this.gameCellCounter++;
    }

    public char[][] getGameBoard() {
        return gameBoard;
    }

    public void setGameBoardCell(int row, int column, char symbol) {
        this.gameBoard[row][column] = symbol;
        increamantGameCellCounter();
    }

    // symbol is X or O
    boolean isWin(char symbol) {
        //check if symbol fill the first row
        //[0][0] [0][1] [0][2]

        if (((gameBoard[0][0] == symbol) && (gameBoard[0][1] == symbol) && (gameBoard[0][2] == symbol))) {
            return true;
            //check if symbol fill the second row
            //[1][0] [1][1] [1][2]
        } else if (((gameBoard[1][0] == symbol) && (gameBoard[1][1] == symbol) && (gameBoard[1][2] == symbol))) {
            return true;
            //check if symbol fill the third row
            //[2][0] [2][1] [2][2]
        } else if (((gameBoard[2][0] == symbol) && (gameBoard[2][1] == symbol) && (gameBoard[2][2] == symbol))) {
            return true;

            //check if symbol fill the first column
            //[0][0] [1][0] [2][0]
        } else if (((gameBoard[0][0] == symbol) && (gameBoard[1][0] == symbol) && (gameBoard[2][0] == symbol))) {
            return true;

            //check if symbol fill the second column
            //[0][1] [1][1] [2][1]
        } else if (((gameBoard[0][1] == symbol) && (gameBoard[1][1] == symbol) && (gameBoard[2][1] == symbol))) {
            return true;

            //check if symbol fill the third column
            //[0][2] [1][2] [2][2]
        } else if (((gameBoard[0][2] == symbol) && (gameBoard[1][2] == symbol) && (gameBoard[2][2] == symbol))) {
            return true;

            //check if symbol fill the first diagonal
            //[0][0] [0][1] [0][2]
            /* [ ][ ][X]
         *  [ ][X][ ]
         *  [X][ ][ ]  */
        } else if (((gameBoard[0][0] == symbol) && (gameBoard[1][1] == symbol) && (gameBoard[2][2] == symbol))) {
            return true;

            //check if symbol fill the second diagonal
            //[0][2] [1][1] [2][0]
        } else if (((gameBoard[0][2] == symbol) && (gameBoard[1][1] == symbol) && (gameBoard[2][0] == symbol))) {
            return true;

        }
        return false;
    }

}
