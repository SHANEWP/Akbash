package com.example.swp93310.panagakos_akbash;

import java.util.Random;

/**
 * A game that requires the player to rearrange tiles into numerical order by
 * rotating a series of 2x2 sub-grids clockwise. The player will select a number
 * representing the top left tile of the 2x2 sub-grid they wish to rotate.
 * @author Shane_Panagakos
 */
public class Akbash
{
    // The current state of the gameboard.
    private int gameBoard[][];

    /**
     * Creates a game board with a size between 3 and 9 and is shuffled n^2 times
     * where n is the size of the game board.
     */
    public Akbash()
    {
        Random r = new Random();
        int s = r.nextInt(7)+3;
        gameBoard = winningState(s);

        for(int i=0; i<s*s;)
            if(rotateCounterClockwise(r.nextInt(s*s)+1))
                i++;
    }

    /**
     * Creates a game board.
     * @param s size of the game board.
     * @param n number of random counter-clockwise rotations for shuffling the game board.
     */
    public Akbash(int s, int n)
    {
        Random r = new Random();

        gameBoard = winningState(s);

        for(int i=0; i<n;)
            if(rotateCounterClockwise(r.nextInt(s*s)+1))
                i++;
    }

    /**
     * Creates a game board that is shuffled s^2 times;
     * @param s size of the game board.
     */
    public Akbash(int s)
    {
        Random r = new Random();

        gameBoard = winningState(s);

        for(int i=0; i<s*s;)
            if(rotateCounterClockwise(r.nextInt(s*s)+1))
                i++;
    }

    /**
     * Rotates the specified 2x2 set clockwise.
     * @param n the number at the top left of the 2x2 section to be rotated.
     * @return true if n is an acceptable selection.
     */
    public boolean rotate(int n)
    {
        for(int i=0; i<gameBoard.length; i++)
        {
            for(int j=0; j<gameBoard.length; j++)
            {
                if(gameBoard[i][j] == n)
                {
                    if(i<gameBoard.length-1&&j<gameBoard.length-1)
                    {
                        int temporary = gameBoard[i][j];
                        gameBoard[i][j]=gameBoard[i+1][j];
                        gameBoard[i+1][j]=gameBoard[i+1][j+1];
                        gameBoard[i+1][j+1]=gameBoard[i][j+1];
                        gameBoard[i][j+1]=temporary;
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * Rotates the specified 2x2 set counter-clockwise.
     * @param n the number at the top left of the 2x2 section to be rotated.
     * @return true if n is an acceptable selection.
     */
    private boolean rotateCounterClockwise(int n)
    {
        for(int i=0; i<gameBoard.length; i++)
        {
            for(int j=0; j<gameBoard.length; j++)
            {
                if(gameBoard[i][j] == n)
                {
                    if(i<gameBoard.length-1&&j<gameBoard.length-1)
                    {
                        int temporary = gameBoard[i][j];
                        gameBoard[i][j]=gameBoard[i][j+1];
                        gameBoard[i][j+1]=gameBoard[i+1][j+1];
                        gameBoard[i+1][j+1]=gameBoard[i+1][j];
                        gameBoard[i+1][j]=temporary;
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * @return true when game board is in the winning state.
     */
    public boolean isOver()
    {
        boolean over = true;
        int[][] winningBoard = winningState(gameBoard.length);

        for(int i=0; i<gameBoard.length; i++)
        {
            for(int j=0; j<gameBoard.length; j++)
            {
                if(gameBoard[i][j]!=winningBoard[i][j])
                    over=false;
            }
        }

        return over;
    }

    /**
     * Determines the winning state of the game board.
     * @param s size of the game board.
     * @return winning state of the game board.
     */
    private int[][] winningState(int s)
    {
        int winningBoard[][] = new int[s][s];
        int counter = 1;

        for(int i=0; i<s; i++)
            for(int j=0; j<s; j++)
                winningBoard[i][j]=counter++;

        return winningBoard;
    }

    /**
     * @return the gameBoard 2D array
     */
    public int[][] getGameBoard(){return gameBoard;}

    /**
     * @return the current state of the game board.
     */
    @Override
    public String toString()
    {
        String s="";
        for(int i=0; i<gameBoard.length; i++)
        {
            for(int j=0; j<gameBoard.length; j++)
            {
                if(gameBoard[i][j]<=9)
                    s+=" "+gameBoard[i][j];
                else
                    s+=gameBoard[i][j];
                s+=" ";
            }
            s+="\n";
        }
        return s;
    }
}
