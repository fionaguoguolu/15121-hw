/**
 * @author Guoguo Lu <guoguol>
 * @section A
 */

// YOU MAY NOT IMPORT ANY ADDITIONAL
// CLASSES OR PACKAGES

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;

import java.util.Random;

public class ColumnPuzzle implements MouseListener {

	// DO NOT ADD, CHANGE, OR DELETE ANY OF THE GLOBAL VARIABLES OR YOU WILL RECEIVE A ZERO (0)
	public JFrame window;
	public int numMoves;
	public Color[][] grid;
	public Color[] colorsList;
	
	// DO NOT TOUCH THIS METHOD EXCEPT TO CHANGE THE NUMBER OF ROWS FOR TESTING
	public static void main(String[] args) {
		new ColumnPuzzle(3);
	}
	
	// DO NOT TOUCH THIS METHOD
	public ColumnPuzzle(Integer numRows) {
		numMoves = 0;
		setup(numRows);
		shuffle();
		createGUI();
	}
	
	/**
	 * The setup method initializes the Column Puzzle.
	 * 
	 * 1. Initialize the grid array with the specified rows
	 *    and 6 columns
	 * 2. Initialize each column with the color corresponding to
	 *    the same index in colorsList
	 * 3. Set the bottom-right cell to BLACK, since its the cell
	 *    we pivot around
	 * @param numRows
	 */
	public void setup(Integer numRows) {
		// use ONLY these colors
		colorsList = new Color[] {Color.RED, Color.YELLOW, Color.GREEN, Color.BLUE, Color.CYAN, Color.MAGENTA};
		
		// initialize the grid
		grid = new Color[numRows][6];
		
		// initialize each column with colors
		for(int row=0; row<grid.length; row++){
			for(int col=0; col<grid[0].length; col++){
				grid[row][col]=colorsList[col];
			}
		}
			
		// set the bottom right cell to BLACK
		grid[grid.length-1][grid[0].length-1]=Color.black;
	}
	
	/**
	 * Returns the number of rows in the ColumnPuzzle
	 * @return int representing the number of rows in the Column Puzzle
	 */
	public int getNumRows() {
		return grid.length;
	}
	
	/**
	 * Returns the number of columns in the ColumnPuzzle
	 * @return int representing the number of columns in the Column Puzzle
	 */
	public int getNumCols() {
		return grid[0].length;
	}
	
	/**
	 * Returns the current grid
	 * @return Color[][] of the current grid
	 */
	public Color[][] getGrid() {
		return grid;
	}
	
	/**
	 * Returns the number of moves
	 * @return int number of moves
	 */
	public int getNumMoves() {
		return numMoves;
	}
	
	/**
	 * Swaps the colors at each point.
	 * 
	 * @param row1, col1 - Point 1
	 * @param row2, col2 - Point 2
	 */
	public void swap(Integer row1, Integer col1, Integer row2, Integer col2) {
		// write the code to swap the item at row1,col1 with row2,col2
		Color temp = grid[row1][col1];
		grid[row1][col1]=grid[row2][col2];
		grid[row2][col2]=temp;
	}
	
	/**
	 * Complete the shuffle method. You must use the
	 * following algorithm:
	 * 
	 * 1. Create a new Random object.
	 * 2. Pick a random color location in the grid
	 *    array (using a random row and random column)
	 * 3. Swap the color with another color at a random
	 *    location using the swap method you wrote
	 * 4. Repeat this process until you have performed 100
	 *    color swaps
	 */
	public void shuffle() {
		// create a new random object
		Random random = new Random();
		
		// perform 100 swaps - make sure you call the swap method you previously wrote
		for(int i=0;i<100;i++){
			swap(random.nextInt(grid.length),random.nextInt(grid[0].length)
					,random.nextInt(grid.length),random.nextInt(grid[0].length));
		}
	}
	
	/**
	 * Sets/Updates the title on the Grid Window
	 * 
	 * You should look at the API for JFrame to
	 * learn how to update the title of the Frame
	 * @param title - String of the new title
	 */
	public void setTitle(String title) {
		// write your code here
		window.setTitle(title);
	}
	
	/** 
	 * Gets the title on the Grid Window
	 */
	public String getTitle() {
		return window.getTitle();
	}
	
	/**
	 * Returns true if (and only if) the puzzle
	 * has been solved.
	 * 
	 * 1. A puzzle is "solved" when each column consists
	 *    of exactly one color.
	 * @return
	 */
	public boolean isSolved() {
		for(int i=0; i<grid.length; i++){
			for(int j=0; j<grid[0].length; j++){
				if(grid[i][j]!=grid[0][j] && grid[i][j]!=Color.BLACK)
					return false;
			}
			
		}
		return true;
	}	
	
	/**
	 * Returns the index of the closest black
	 * square (horizontally or vertically). It
	 * returns a int[] of (-1,-1) if no adjacent 
	 * black square is found.
	 * 
	 * @param cellId
	 * @return
	 */
	public int[] closestBlackSquare(Integer row, Integer col) {
		
		if(row+1<grid.length)
			if(grid[row+1][col]==Color.black)
				return new int[] {row+1,col};
		if(row>0)
			if(grid[row-1][col]==Color.black)
				return new int[] {row-1,col};
		
		if(col+1<grid[0].length)
			if(grid[row][col+1]==Color.black)
				return new int[] {row,col+1};
		if(col>0)
			if(grid[row][col-1]==Color.black)
				return new int[] {row,col-1};
		
		return new int[] {-1,-1};
	}
	
	/**
	 * Implement this mouseClicked method in the following order:
	 * 
	 * 1. If the puzzle isSolved, update the title to say "YOU WON"
	 * 2. Otherwise, find the closestBlackSquare to the point
	 * 3. If there is no black square, update the title to say
	 * 	  "ILLEGAL MOVE" and return
	 * 4. If there is a black square, swap the black square and the 
	 *    clicked square, simulating a shift into the black spot
	 * 5. Update the number of moves
	 * 6. Update the title to reflect the number of moves
	 */
	public void mousePressed(MouseEvent e) {
		// if the puzzle isn't solved...
		if(!isSolved()) {
	
			// get the row and column of the clicked square
			int row = e.getY()/100;
			int col = e.getX()/100;		
	
			// find the closest black square - using the method
			// you wrote previously
			int blackRow=closestBlackSquare(row,col)[0];
			int blackCol=closestBlackSquare(row,col)[1];
			
			// if no black square was found, update title
			// and return
			if(blackRow==-1 || blackCol==-1) {
				setTitle("Illegal Move");
				return;
			}
			
			// swap the clicked and black square
			swap(row,col,blackRow,blackCol);
			
			// update the total number of moves
			numMoves++;
			
			// update the title
			setTitle("Moves Number "+numMoves);
			
		} else {
			// update the title to say "YOU'VE WON!"
			setTitle("You've Won!");
		}
	}

	
	
	
	/*
	 * 
	 * 
	 * 
	 * Do NOT touch anything below this line.
	 * YOU WILL RECEIVE A ZERO IF YOU CHANGE
	 * ANYTHING BELOW THIS LINE!
	 * 
	 * 
	 * 
	 */
	
	public void mouseEntered(MouseEvent e) {
		
	}
	
	public void mouseExited(MouseEvent e) {
		
	}
	
	public void mouseReleased(MouseEvent e) {
		paint();
	}
	
	public void mouseClicked(MouseEvent e) {
		
	}
	
	private void createGUI() {
		if(grid == null || grid.length == 0) {
			System.out.println("You did not initialize the grid! You must initialize the grid to run the puzzle!");
			return;
		}
		
		// create the window
		JFrame.setDefaultLookAndFeelDecorated(true);
		window = new JFrame("Column Puzzle");
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setResizable(false);
		
		// setup the content pane
		Container cp = window.getContentPane();
		cp.setLayout(new GridLayout(grid.length, 6));
		cp.addMouseListener(this);
		
		// now add panels
		for(int row = 0; row < grid.length; row++) {
			for(int col = 0; col < grid[row].length; col++) {
				JPanel cell = new JPanel();
				cell.setPreferredSize(new Dimension(100, 100));
				cell.setBackground(grid[row][col]);
				cell.setBorder(BorderFactory.createLineBorder(Color.BLACK));
				cp.add(cell);
			}
		}
		
		paint();
	}
	
	public void dispose() {
		if(window != null) {
			window.setVisible(false);
			window.dispose();
		}
	}
	
	public void paint() {
		Container cp = window.getContentPane();
		
		for(int row = 0; row < grid.length; row++) {
			for(int col = 0; col < grid[row].length; col++) {
				cp.getComponent(getCellId(row, col)).setBackground(grid[row][col]);
			}
		}
		
		window.pack();
		window.setVisible(true);
	}

	private int getCellId(int row, int col) {
		return row*6 + col;
	}
}