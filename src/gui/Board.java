/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;


/**
 *
 * @author Chakib
 */
public class Board {

    public Cell[][] grid = new Cell[9][9];

    public void fillGrid(int[][] b) {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (b[i][j] == 0) {
                    grid[i][j] = new Cell();
                } else {
                    grid[i][j] = new Cell(b[i][j]);
                }
            }
        }
    }
}
