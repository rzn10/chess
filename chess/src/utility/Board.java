package utility;
import pieces.Bishop;
import pieces.King;
import pieces.Knight;
import pieces.Pawn;
import pieces.Queen;
import pieces.Rock;
import pieces.Piece;
import java.util.*;
import javax.swing.*;


public class Board {
	//field represents the position of the field as a two dimensional array ;
	public int[][] field;
	//pieces is an 2 dimensional array of Piece objects
	//pieces[0][n] - all black pieces
	//pieces[1][n] - all white pieces
	public Piece [][] pieces;

	//constructor
	//creates the total 32  pieces (16 each color)
	//creates the field as a two dimensional array (8x8)
	//shows where black a white squares are to be 0 - white, 1 - black
	Board(){
		field = new int[][] {
			{0, 1, 0, 1, 0, 1, 0, 1},
			{1, 0, 1, 0, 1, 0, 1, 0},
			{0, 1, 0, 1, 0, 1, 0, 1},
			{1, 0, 1, 0, 1, 0, 1, 0},
			{0, 1, 0, 1, 0, 1, 0, 1},
			{1, 0, 1, 0, 1, 0, 1, 0},
			{0, 1, 0, 1, 0, 1, 0, 1},
			{1, 0, 1, 0, 1, 0, 1, 0},
		};

	//set up two dimensinal Piece array	
	//pieces[1][n] - all black pieces
	//pieces[0][n] - all white pieces
		pieces = new Piece[][]{
			{   //create all white pieces
				new Rock(true, 1, 1, "wR", this),
			    new Knight(true, 2, 1, "wN", this),
				new Bishop(true, 3, 1, "wB", this),
				new Queen(true, 4, 1, "wQ", this),
				new King(true, 5, 1, "wK", this),
				new Bishop(true, 6, 1, "wB", this),
				new Knight(true, 7, 1, "wN", this),
				new Rock(true, 8, 1, "wR", this),
				new Pawn(true, 1, 2, "wp", this),
				new Pawn(true, 2, 2, "wp", this),
				new Pawn(true, 3, 2, "wp", this),
				new Pawn(true, 4, 2, "wp", this),
				new Pawn(true, 5, 2, "wp", this),
				new Pawn(true, 6, 2, "wp", this),
				new Pawn(true, 7, 2, "wp", this),
				new Pawn(true, 8, 2, "wp", this)
			},
			{   //create all black pieces
				new Rock(true, 1, 8, "bR", this),
			    new Knight(true, 2, 8, "bN", this),
				new Bishop(true, 3, 8, "bB", this),
				new Queen(true, 4, 8, "bQ", this),
				new King(true, 5, 8, "bK", this),
				new Bishop(true, 6, 8, "bB", this),
				new Knight(true, 7, 8, "bN", this),
				new Rock(true, 8, 8, "bR", this),
				new Pawn(true, 1, 7, "bp", this),
				new Pawn(true, 2, 7, "bp", this),
				new Pawn(true, 3, 7, "bp", this),
				new Pawn(true, 4, 7, "bp", this),
				new Pawn(true, 5, 7, "bp", this),
				new Pawn(true, 6, 7, "bp", this),
				new Pawn(true, 7, 7, "bp", this),
				new Pawn(true, 8, 7, "bp", this)
			}
		};
	}

	//prints every Piece object's data to the screen
	public void printPieces(){
		for( int i = 0; i < 2; i++ ){
			for( int j = 0; j < 16; j++ ){
				System.out.println("Team: " + pieces[i][j].getColor());
				System.out.println("Status: " + pieces[i][j].isAlive());
				pieces[i][j].printPosition();
				System.out.println("Type: " + pieces[i][j].rep);
			}
		}
	}

//canMove
//recieves: string ch to represent the coordinate we are moving from
//			string ch1 to represent the coordinate we are moving to
//description: searches for the Piece object at cordinate ch
//				then calls the canMove() function on it
//	
//returns: true if ch can move there, false otherwise
public Boolean canMove(String ch, String ch1){
	boolean flag = false;
	Piece tmp = findPiece(ch);
	if( tmp == null )
		System.out.println("canMove ERROR! piece is null.");

	int x1 = 65-(int)ch1.charAt(0);
	int y1 = 59-(int)ch1.charAt(0);
	System.out.println(58-(int)ch1.charAt(1));	
	flag = tmp.canMove(x1,y1);
	if( !flag )
		System.out.println("Cant move there!");

	return flag;
}

//move changes the piece's position
//description: checks if a piece is already in the position it is moving to
//				if a piece is there, kill it
//recieves: String representing the coordinate to move to
public void move(String ch, String ch1){
	//convert string to x y 
	int x;
	int y;
	x = (int)(ch.charAt(0))-64;
	y = ((int)(ch.charAt(1))-48);
	//System.out.println("Moving to: " + x + " " + y);

	Piece tmp1 = findPiece(ch1);
	if( tmp1 != null ){
		tmp1.die();
	}
	Piece tmp = findPiece(ch);
	if( tmp != null ){
		tmp.move(ch1);
	}
	

}

//findPiece
//recieves the cordinates(as a string) of the Piece object to be found
//the first char represents the x axis, the second char represents the y axis
//searches through the list of pieces for a match
//returns the Piece object, throws exception if none is found
public Piece findPiece( String xy ) {
	//convert string to x y user-cordinate (From field coordinate)
	int x;
	int y;
	x = (int)(xy.charAt(0))-64;
	y = ((int)(xy.charAt(1))-48);
	//System.out.println("Looking for piece matching with: " + x + " " + y);
	//check if a piece currently occupies the coordinate
	Piece tmp = null;
	for( int i = 0; i < 2; i++ ){
		for( int j = 0; j < 16; j++ ){
			if( pieces[i][j].getX() == x && pieces[i][j].getY() == y ){
				tmp = pieces[i][j];
			}
		}
	}

	if( tmp == null ){
		//System.out.println("Error: no piece found at position.");
	}

	return tmp;
}

	//pieceAt
	//scans through the list of positions
	//recieves two integers representing the x and y cordinates to look at
	//prints a String representing the piece (if any) in the cordinate
	public void pieceAt( int x, int y ){
		//check if the coordinate is a white or black square
		String str = "  ";
		if( field[x][y] == 1 ){
			str = "##";
		}

		//check if a piece currently occupies the coordinate and is alive
		for( int i = 0; i < 2; i++ ){
			for( int j = 0; j < 16; j++ ){
				//System.out.println("Finding piece with: "+ x + " " + y );
				if( pieces[i][j].getX()-1 == x && 8-(pieces[i][j].getY()) == y ){
					if(pieces[i][j].isAlive())
						str = pieces[i][j].rep;
				}
			}
		}
		System.out.print(" " + str);
	}
	

	//prints the field array including the pieces
	public void printField(){
		//print the alphabetical representations of the x axis
		char ch = 'A';
		System.out.println('\n');
		System.out.print("   ");
		for( int i = 0; i < 8; i++ ){
			System.out.print(ch + "  ");
			ch++;
		}
		System.out.println();

		//print the rest of the field( as the user sees it )
		//must convert from array coordinates to how the user coordinates the board
		for(int i = 0; i < 8; i++){ //rows
			System.out.print(8-i + " ");
			for(int j = 0; j < 8; j++){ //columns
				pieceAt(j,i);
			}
			System.out.println();
		}
	}
}