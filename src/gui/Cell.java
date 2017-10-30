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
import java.util.ArrayList;

/**
 *
 * @author Chakib
 */
public class Cell {

    public boolean isConstant = false;
    public ArrayList<Integer> values = new ArrayList();

    public Cell(int i) {
        values.add(i);
        isConstant = true;
    }

    public Cell() {
        isConstant = false;
    }
}

