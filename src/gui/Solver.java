/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;


import java.util.ArrayList;
import java.util.Arrays;
import javax.swing.JTextField;

/**
 *
 * @author Chakib
 */
public class Solver {

    public Board board = new Board();
    public static int[][] blocks = {{0, 1, 2}, {3, 4, 5}, {6, 7, 8}};

    public static enum TYPE {
        BLOCK, ROW, COLUMN
    };
    
    //Gets value from the textview
    public static int g(JTextField j){
        if(j.getText().equals("")){
            return 0;
        } else {
            return Integer.valueOf(j.getText());
        }
    }

    //iterations number
    private int it = 0;

    public void fillBoard(int[][] b) {
        board.fillGrid(b);
    }
    
    //Prints the puzzle to the console
    public void printGrid() {
        System.out.println(" _ _ _ _ _ _ _ _ _ ");
        for (Cell[] row : board.grid) {
            System.out.print("|");
            for (Cell c : row) {
                String toPrint = " ";
                if (c.isConstant) {
                    toPrint = String.valueOf(c.values.get(0));
                }
                System.out.print(toPrint + "|");
            }
            System.out.print("\n");

        }
        System.out.println(" - - - - - - - - - ");

    }
    
    //Iterates through cells to eliminate impossible numbers
    private void eliminate() {
        for (int i = 0; i < 9; i++) {
            it++;
            for (int j = 0; j < 9; j++) {
                it++;
                Cell c = board.grid[i][j];
                if (c.isConstant == false) {
                    c.values = retrievePossibleCases(i, j);
                    if (c.values.size() == 1) {
                        c.isConstant = true;
                    }
                }
            }
        }
    }
    
    /*
    Iterates through cells and checks if there is a possible
    number that exist once in the row, column and block 
    respectively
    */
    public void checkForSingleCases(){
        for (int i = 0; i < 9; i++) {
                it++;
                for (int j = 0; j < 9; j++) {
                    it++;
                    if (!checkSingleCases(i, j, TYPE.ROW)) {
                        if (!checkSingleCases(i, j, TYPE.COLUMN)) {
                            if (checkSingleCases(i, j, TYPE.BLOCK)) {
                                eliminate();
                            }
                        } else {
                            eliminate();
                        }
                    } else {
                        eliminate();
                    }
                }

            }
    }
    
    //Main method to solve the puzzle
    public boolean solve() {
        while (!isCompleted()) {
            it++;

            eliminate();
            checkForSingleCases();
            if(it > 2000000){
                return false;
            }

        }
        System.out.print("\nTotal iterations: ");
        System.out.print(it);
        System.out.println();
        return true;
    }
    
    //Returns a list of possible candidates
    public ArrayList<Integer> retrievePossibleCases(int rid, int cid) {
        ArrayList<Integer> possibleCases = new ArrayList<>();
        int[] ns = {1, 2, 3, 4, 5, 6, 7, 8, 9};
        for (int n : ns) {
            it++;
            possibleCases.add(n);
        }
        //Scanning the block
        int[] brid = bid(rid);
        int[] bcid = bid(cid);
        for (int i : brid) {
            it++;
            for (int j : bcid) {
                it++;
                //skip checking the same cell
                if (i == rid && j == cid) {
                    continue;
                }
                //current cell
                Cell cc = board.grid[i][j];
                if (cc.isConstant) {
                    possibleCases.remove(new Integer(cc.values.get(0)));
                }
            }
        }

        //scanning the row 
        for (int i = 0; i < 9; i++) {

            it++;
            //skip checking the same cell
            if (i == cid) {
                continue;
            }
            //current cell
            Cell cc = board.grid[rid][i];
            if (cc.isConstant) {
                possibleCases.remove(new Integer(cc.values.get(0)));
            }
        }

        //scanning the column
        for (int i = 0; i < 9; i++) {

            it++;
            //skip checking the same cell
            if (i == rid) {
                continue;
            }

            //current cell
            Cell cc = board.grid[i][cid];

            if (cc.isConstant) {
                possibleCases.remove(new Integer(cc.values.get(0)));
            }
        }

        return possibleCases;
    }

    //sets a unique value for a given cell and makes it constant
    public void setCellValueAndConstant(int rid, int cid, int val) {
        ArrayList<Integer> nl = new ArrayList();
        nl.add(val);
        board.grid[rid][cid].values = nl;
        board.grid[rid][cid].isConstant = true;
    }
    
    /*
    Iterates through cells and checks if there is a possible
    number that exist once in the row, column and block 
    respectively
    */
    public boolean checkSingleCases(int rid, int cid, TYPE t) {
        mainloop:
        for (int k = 0; k < board.grid[rid][cid].values.size(); k++) {
            int v = board.grid[rid][cid].values.get(k);
            it++;
            if (t == TYPE.BLOCK) {
                //Scanning the block
                int[] brid = bid(rid);
                int[] bcid = bid(cid);
                for (int i : brid) {
                    it++;
                    for (int j : bcid) {
                        it++;
                        //skip checking the same cell
                        if (i == rid && j == cid) {
                            continue;
                        }
                        //current cell
                        Cell cc = board.grid[i][j];
                        if (!cc.isConstant) {
                            if (cc.values.contains(v)) {
                                continue mainloop;
                            }
                        }
                    }
                }
            } else if (t == TYPE.ROW) {
                //scanning the row 
                for (int i = 0; i < 9; i++) {
                    it++;
                    //skip checking the same cell
                    if (i == cid) {
                        continue;
                    }
                    //current cell
                    Cell cc = board.grid[rid][i];
                    if (!cc.isConstant) {
                        if (cc.values.contains(v)) {
                            continue mainloop;
                        }
                    }
                }
            } else if (t == TYPE.COLUMN) {
                for (int i = 0; i < 9; i++) {

                    it++;
                    //skip checking the same cell
                    if (i == rid) {
                        continue;
                    }

                    //current cell
                    Cell cc = board.grid[i][cid];

                    if (!cc.isConstant) {
                        if (cc.values.contains(v)) {
                            continue mainloop;
                        }
                    }
                }
            }
            setCellValueAndConstant(rid, cid, v);
            return true;
        }
        return false;
    }

    //returns block id
    private int[] bid(int id) {
        for (int i[] : blocks) {
            it++;
            if (contains(i, id)) {
                return i;
            }
        }
        return null;
    }
    
    //checks if an array contains a given value
    private boolean contains(int[] array, int value) {
        for (int i : array) {
            it++;
            if (i == value) {
                return true;
            }
        }
        return false;
    }

    //returns true if all cells have a unique value and constant
    private boolean isCompleted() {
        for (Cell[] row : board.grid) {
            it++;
            for (Cell c : row) {
                it++;
                if (c.isConstant == false) {
                    return false;
                }
            }
        }
        return true;
    }

}
