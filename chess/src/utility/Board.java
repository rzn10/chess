package utility;
import pieces.Bishop;
import pieces.King;
import pieces.Knight;
import pieces.Pawn;
import pieces.Queen;
import pieces.Rock;
import pieces.Piece;

public class Board {
	//field represents the position of the field as a two dimensional array ;
	private int[][] field;
	//pieces is an 2 dimensional array of Piece objects
	//pieces[0][n] - all black pieces
	//pieces[1][n] - all white pieces
	private Piece [][] pieces;

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
	//pieces[0][n] - all black pieces
	//pieces[1][n] - all white pieces
		pieces = new Piece[][]{
			{   //create all white pieces
				new Rock(true, 1, 1, "wR"),
			    new Knight(true, 2, 1, "wN"),
				new Bishop(true, 3, 1, "wB"),
				new Queen(true, 4, 1, "wQ"),
				new King(true, 5, 1, "wK"),
				new Bishop(true, 6, 1, "wB"),
				new Knight(true, 7, 1, "wN"),
				new Rock(true, 8, 1, "wR"),
				new Pawn(true, 1, 2, "wp"),
				new Pawn(true, 2, 2, "wp"),
				new Pawn(true, 3, 2, "wp"),
				new Pawn(true, 4, 2, "wp"),
				new Pawn(true, 5, 2, "wp"),
				new Pawn(true, 6, 2, "wp"),
				new Pawn(true, 7, 2, "wp"),
				new Pawn(true, 8, 2, "wp")
			},
			{   //create all black pieces
				new Rock(true, 1, 8, "bR"),
			    new Knight(true, 2, 8, "bN"),
				new Bishop(true, 3, 8, "bB"),
				new Queen(true, 4, 8, "bQ"),
				new King(true, 5, 8, "bK"),
				new Bishop(true, 6, 8, "bB"),
				new Knight(true, 7, 8, "bN"),
				new Rock(true, 8, 8, "bR"),
				new Pawn(true, 1, 7, "bp"),
				new Pawn(true, 2, 7, "bp"),
				new Pawn(true, 3, 7, "bp"),
				new Pawn(true, 4, 7, "bp"),
				new Pawn(true, 5, 7, "bp"),
				new Pawn(true, 6, 7, "bp"),
				new Pawn(true, 7, 7, "bp"),
				new Pawn(true, 8, 7, "bp")
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
		System.out.println("Looking for piece matching with: " + x + " " + y);
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
			System.out.println("Error: no piece found at position.");
		}

		return tmp;
	}

	//pieceAt
	//scans through the list of positions
	//recieves two integers representing the x and y cordinates to look at
	//returns a String representing the piece (if any) in the cordinate
	public void pieceAt( int x, int y ){
		//check if the coordinate is a white or black square
		String str = "  ";
		if( field[x][y] == 1 ){
			str = "##";
		}

		//check if a piece currently occupies the coordinate
		for( int i = 0; i < 2; i++ ){
			for( int j = 0; j < 16; j++ ){
				//System.out.println("Finding piece with: "+ x + " " + y );
				if( pieces[i][j].getX()-1 == x && 8-(pieces[i][j].getY()) == y ){
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