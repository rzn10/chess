package pieces;
import utility.Board;
import java.util.HashMap;
import java.util.Map;

public class Piece {
	protected boolean white;  //team of the piece
	protected boolean alive;	//status of the piece
	protected int x;			//x position of the piece
	protected int y;			//y position of the piece
	public String rep;			//two characters representing the different pieces
	int moveCount;				//represents how many times the piece has moved
	Board brd;					//access to the Board

//constructor
//sets initial position
//recieves: color to determine which turn the piece is active
// true - white,
// false - black
	public Piece(boolean color, int x, int y, String r, Board brd){
		white = color;
		alive = true;
		this.x = x;
		this.y = y;
		this.rep = r;
		this.moveCount = 0;
		this.brd = brd;
	}

	private static final String[] PIECE_MOVEMENTS = {
		"upLeft", "upRight", "downLeft", "downRight"
	};

	//converts integer cordinates to string cordinates
	String intCordToString(int x, int y){
		char ch1= (char) ((char)x+64);
		char ch2 = (char) ((char)y+48);
		String str = "" + ch1 + ch2;
		return str;
	}

//canMove
//recieves: 2 dimensional int array representing the piece's position
//returns: boolean represnting if the piece can move to the pisition or not	
	public boolean canMove(int x, int y){
		boolean flag = false;
		System.out.print("PIECE: ATTEMPTING MOVE: " + this.x + ", " + this.y + " to ");
		System.out.println(x + ", " + y);

		switch( this.rep ){
			//CASE : WHITE AND BLACK PAWNS
			//RULES: Pawns can only move forward two spaces on the pieces first move
			//		-can only move 1 space after first move
			//		-can only move forward 1 space if a piece isnt in the space its moving to
			//		-can only move diagonal 1 space if a piece IS in a space its moving to
			case "wp":
				if( moveCount == 0 ){	//Pawns very first move can move two spaces forward
					if((y-this.y <= 2 && y-this.y > 0) && (x==this.x)){
						//check if a piece is in the position we are moving to
						Piece tmp = brd.findPiece(intCordToString(x, y));
						if( tmp == null){    //if there is not a piece there, then it can move forward
							flag=true;
						}
					} else if ( ((this.x+1) == x) && ((this.y+1) == y) ){  //diagonal move case up and right
						//check if a piece is in the position we are moving to
						Piece tmp = brd.findPiece(intCordToString(x, y));
						if( tmp != null){    //if there is a piece there, then it can move and kill it
							flag=true;
							tmp.die();
						}
					} else if ( ((this.x-1) == x) && ((this.y+1) == y) ){  //diagonal move case up and left
						//check if a piece is in the position we are moving to
						Piece tmp = brd.findPiece(intCordToString(x, y));
						if( tmp != null){    //if there is a piece there, then it can move and kill it
							flag=true;
							tmp.die();
						}
					}
				} else if (moveCount > 0){	//case for which the pawn's move isnt the first move its made
					if((y-this.y <= 1 && y-this.y > 0) && (x==this.x)){
						//check if a piece is in the position we are moving to
						Piece tmp = brd.findPiece(intCordToString(x, y));
						if( tmp == null){    //if there is not a piece there, then it can move forward
							flag=true;
						}
					} else if ( ((this.x+1) == x) && ((this.y+1) == y) ){  //diagonal move case up and right
						//check if a piece is in the position we are moving to
						Piece tmp = brd.findPiece(intCordToString(x, y));
						if( tmp != null){    //if there is a piece there, then it can move and kill it
							flag=true;
							tmp.die();
						}
					} else if ( ((this.x-1) == x) && ((this.y+1) == y) ){  //diagonal move case up and left
						//check if a piece is in the position we are moving to
						Piece tmp = brd.findPiece(intCordToString(x, y));
						if( tmp != null){    //if there is a piece there, then it can move and kill it
							flag=true;
							tmp.die();
						}
					}
				}

			break;
			case "bp":
				if( moveCount == 0 ){	//Pawns very first move can move two spaces forward
					if((this.y-y <= 2 && this.y-y > 0) && (x==this.x)){
						//check if a piece is in the position we are moving to
						Piece tmp = brd.findPiece(intCordToString(x, y));
						if( tmp == null){    //if there is not a piece there, then it can move forward
							flag=true;
						}
					} else if ( ((this.x+1) == x) && ((this.y-1) == y) ){  //diagonal move case down and right
						//check if a piece is in the position we are moving to
						Piece tmp = brd.findPiece(intCordToString(x, y));
						if( tmp != null){    //if there is a piece there, then it can move and kill it
							flag=true;
							tmp.die();
						}
					} else if ( ((this.x-1) == x) && ((this.y-1) == y) ){  //diagonal move case down and left
						//check if a piece is in the position we are moving to
						Piece tmp = brd.findPiece(intCordToString(x, y));
						if( tmp != null){    //if there is a piece there, then it can move and kill it
							flag=true;
							tmp.die();
						}
					}
				} else if (moveCount > 0){	//case for which the pawn's move isnt the first move its made
					if((this.y-y <= 1 && this.y-y > 0) && (x==this.x)){
						//check if a piece is in the position we are moving to
						Piece tmp = brd.findPiece(intCordToString(x, y));
						if( tmp == null){    //if there is not a piece there, then it can move forward
							flag=true;
						}
					}
				}
			break;
			//CASE: WHITE AND BLACK BISHOPS
			//RULES: can move diagonally forward and backwards
			//		-must check each square on diagnoal path to see if a piece is there
			//		-if a piece is in the path then it cant move there
			//		-position it is moving to must be on the same square color as it is currently on
			case "wB":
				if( (this.x > x) && (this.y < y) ){		//up left diagonal case
					if( !pieceIsInPath(this.x,this.y,x,y,this.rep, "upLeft")
							&& isInMovesArray(this.x,this.y,x,y,rep) ){
						flag = true;
					} else {
						System.out.println("A piece is in the path!");
					}
				} else if ( (this.x < x) && (this.y < y)){	//up right diagonal case
					if( !pieceIsInPath(this.x,this.y,x,y,this.rep, "upRight") 
							&& isInMovesArray(this.x,this.y,x,y,rep) ){
						flag = true;
					} else {
						System.out.println("A piece is in the path!");
					}					
				} else if( (this.x > x) && (this.y > y) ){		//down left diagonal case
					if( !pieceIsInPath(this.x,this.y,x,y,this.rep, "downLeft") 
							&& isInMovesArray(this.x,this.y,x,y,rep) ){
						flag = true;
					} else {
						System.out.println("A piece is in the path!");
					}
				} else if ( (this.x < x) && (this.y > y)){	//down right diagonal case
					if( !pieceIsInPath(this.x,this.y,x,y,this.rep, "downRight") 
							&& isInMovesArray(this.x,this.y,x,y,rep) ){
						flag = true;
					} else {
						System.out.println("A piece is in the path!");
					}					
				}
				
			break;
			case "bB":
			default:
		}

		return flag;
	}

	//isInMovesArray
	//recieves the cordinates the piece is in, the cordinates it wants to move to
	// 	and the string representation of the piece type
	//description: creates an array of possible moves
	//retrusn true if the destination cordinate is in the created array, false otherwise
	private boolean isInMovesArray(int x2, int y2, int x3, int y3, String rep2) {
		boolean flag = false;
		int [][] intArr = new int[8][2];  //each spot on the x-axis can have two possible y-axis moves.

		switch( rep2 ){
			case "wB" :
			//each spot on the x-axis can have two possible y-axis moves.
			//so each iteration of mapArr represents each x-axis
			//   and each map represents the two y-axis posssibilities for each spot on the x-axis
				for( int i = 0; i < 8; i++ ){
					for( int j = 0; j < 2; j++ ){
						intArr[i][0] = y2+i+1;
						intArr[i][1] = y2-i-1;
						System.out.println( i+x2+1 +":" + intArr[i][0] + "," + intArr[i][1]);
					}
				}
				//map now contains possible y-cordinates for the 8 x-axis spots to the right of x2

				//check each y-cordinate to the right and left of x2
				//if a match is made, then the piece is allowed to move there
				for( int i = 0; i < 8; i++ ){
					for( int j = 0; j < 2; j++ ){
						if( x3 == i+x2+1 && ((y3 == intArr[i][0]) || (y3 == intArr[i][1])) ){
							flag = true;
							System.out.println("Cordinate was found in: isInMovesArray()!!!");
						} else if( x3 == x2-i-1 && ((y3 == intArr[i][0]) || (y3 == intArr[i][1])) ){
							flag = true;
							System.out.println("Cordinate was found in: isInMovesArray()!!!");
						}
					}
				}

					

			break;
			case "bB" :
			break;
			default:
				System.out.print("isInMovesArray error! string rep2 not recognized.");
		}

		return flag;
	}

	//pieceIsInPath
	//recieves cordinates that the piece is moving from and to
	//   	-recieves string representation of the piece
	//		-recieves string representing the type of movement the piece is making
	//returns true if a piece is in the path, false otherwise
	private boolean pieceIsInPath(int x2, int y2, int x3, int y3, String rep2, String mvmnt) {
		boolean flag = false;
		Piece tmp = null;

		int xCpy = x3;
		int yCpy = y3;
		xCpy += 1;
		yCpy -= 1;
		if( rep2 == "wB" || rep2 == "bB" ){	
			if( mvmnt == "upLeft"){  //check case for moving up left		
				while( xCpy != x2 && yCpy > 0){ //loop through each diagonal space,between points,checking if a piece is there
					tmp = brd.findPiece(intCordToString(xCpy,yCpy));
					if( tmp != null ){
						flag = true;   //case a piece is found on the path
					}
					xCpy += 1;
					yCpy -= 1;
				}
			}

			if( mvmnt == "upRight" ){//check from a different angle (case for moving up right)
				xCpy = x3;
				yCpy = y3;
				xCpy -= 1;
				yCpy -= 1;				
				while( xCpy != x2 && yCpy > 0){ //loop through each diagonal space,between points,checking if a piece is there
					tmp = brd.findPiece(intCordToString(xCpy,yCpy));
					if( tmp != null ){
						flag = true;   //case a piece is found on the path
					}
					xCpy -= 1;
					yCpy -= 1;
				}

			if( mvmnt == "downRight" ){//check from a different angle (case for moving down right)
				xCpy = x3;
				yCpy = y3;
				xCpy -= 1;
				yCpy += 1;
				while( xCpy != x2 && yCpy > 0){ //loop through each diagonal space,between points,checking if a piece is there
					tmp = brd.findPiece(intCordToString(xCpy,yCpy));
					if( tmp != null ){
						flag = true;   //case a piece is found on the path
					}
					xCpy -= 1;
					yCpy += 1;
				}				
			}

			if( mvmnt == "downLeft" ){//check from a different angle (case for moving down left)
				xCpy = x3;
				yCpy = y3;
				xCpy += 1;
				yCpy += 1;
				while( xCpy != x2 && yCpy > 0){ //loop through each diagonal space,between points,checking if a piece is there
					tmp = brd.findPiece(intCordToString(xCpy,yCpy));
					if( tmp != null ){
						flag = true;   //case a piece is found on the path
					}
					xCpy += 1;
					yCpy += 1;
				}
			}
		}
	}

		return flag;
	}

	//move changes the piece's position
//description: checks if a piece is already in the position it is moving to
//				if a piece is there, kill it
//recieves: String representing the coordinate to move to
	public void move(String ch){
		//convert string to x y 
		int x;
		int y;
		x = (int)(ch.charAt(0))-64;
		y = ((int)(ch.charAt(1))-48);
		//System.out.println("Moving to: " + x + " " + y);
		this.x = x;
		this.y = y;
		moveCount++;
	}

	public void die(){ 
		alive = false;
		this.x = -1;
		this.y = -1;
	 }

	//isAlive
	//returns true if alive, false if dead
	public boolean isAlive(){
		boolean flag = true;
		if( !alive )
			flag = false;

		return flag;
	}


	public boolean getColor(){ return white; }
	public void printPosition(){
		System.out.println("Cordinates: " + x + " " + y);
	}

	public int getX() { return this.x; }
	public int getY() { return this.y; }
}