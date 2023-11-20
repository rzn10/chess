package pieces;
import utility.Board;


public class Piece {
	protected boolean white;  	//team of the piece
	protected boolean alive;	//status of the piece
	protected int x;			//x position of the piece
	protected int y;			//y position of the piece
	public String rep;			//two characters representing the different pieces
	public int moveCount;		//represents how many times the piece has moved
	Board brd;					//access to the Board
	public boolean castled;		//representation if king has castled or not
	public boolean castledRight;//representation of king's castling direction
	public boolean castledLeft; //representation of king's castling direction

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
		this.castled = false;
		this.castledLeft = false;
		this.castledRight = false;
	}

	//converts integer cordinates to string cordinates
	String intCordToString(int x, int y){
		char ch1= (char) ((char)x+64);
		char ch2 = (char) ((char)y+48);
		String str = "" + ch1 + ch2;
		return str;
	}

//canMove
//recieves: 2 dimensional int array representing the piece's position
//returns: boolean represnting if the piece can move to the position or not	
	public boolean canMove(int x, int y){
		boolean flag = false;
		//System.out.print("PIECE: ATTEMPTING MOVE: " + this.x + ", " + this.y + " to ");
		//System.out.println(x + ", " + y);

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
						if( tmp != null && (tmp.getColor() != this.getColor())){    //if there is an enemy piece there, then it can move and kill it
							flag=true;
							tmp.die();
						}
					} else if ( ((this.x-1) == x) && ((this.y+1) == y) ){  //diagonal move case up and left
						//check if a piece is in the position we are moving to
						Piece tmp = brd.findPiece(intCordToString(x, y));
						if( tmp != null && (tmp.getColor() != this.getColor())){//if there is an enemy piece there, then it can move and kill it
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
						if( tmp != null  && (tmp.getColor() != this.getColor())){//if there is an enemy piece there, then it can move and kill it
							flag=true;
							tmp.die();
						}
					} else if ( ((this.x-1) == x) && ((this.y+1) == y) ){  //diagonal move case up and left
						//check if a piece is in the position we are moving to
						Piece tmp = brd.findPiece(intCordToString(x, y));
						if( tmp != null  && (tmp.getColor() != this.getColor())){//if there is an enemy piece there, then it can move and kill it
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
						if( tmp != null && (tmp.getColor() != this.getColor())){    //if there is an enemy piece there, then it can move and kill it
							flag=true;
							tmp.die();
						}
					} else if ( ((this.x-1) == x) && ((this.y-1) == y) ){  //diagonal move case down and left
						//check if a piece is in the position we are moving to
						Piece tmp = brd.findPiece(intCordToString(x, y));
						if( tmp != null && (tmp.getColor() != this.getColor())){    //if there is an enemy piece there, then it can move and kill it
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
						System.out.print("Error attempting to move!");
						System.out.println(" A piece is in the path OR it is not a valid move!");
					}
				} else if ( (this.x < x) && (this.y < y)){	//up right diagonal case
					if( !pieceIsInPath(this.x,this.y,x,y,this.rep, "upRight") 
							&& isInMovesArray(this.x,this.y,x,y,rep) ){
						flag = true;
					} else {
						System.out.print("Error attempting to move!");
						System.out.println(" A piece is in the path OR it is not a valid move!");
					}					
				} else if( (this.x > x) && (this.y > y) ){		//down left diagonal case
					if( !pieceIsInPath(this.x,this.y,x,y,this.rep, "downLeft") 
							&& isInMovesArray(this.x,this.y,x,y,rep) ){
						flag = true;
					} else {
						System.out.print("Error attempting to move!");
						System.out.println(" A piece is in the path OR it is not a valid move!");
					}
				} else if ( (this.x < x) && (this.y > y)){	//down right diagonal case
					if( !pieceIsInPath(this.x,this.y,x,y,this.rep, "downRight") 
							&& isInMovesArray(this.x,this.y,x,y,rep) ){
						flag = true;
					} else {
						System.out.print("Error attempting to move!");
						System.out.println(" A piece is in the path OR it is not a valid move!");
					}					
				}
				
			break;
			case "bB":
				if( (this.x > x) && (this.y < y) ){		//up left diagonal case
					if( !pieceIsInPath(this.x,this.y,x,y,this.rep, "upLeft")
							&& isInMovesArray(this.x,this.y,x,y,rep) ){
						flag = true;
					} else {
						System.out.print("Error attempting to move!");
						System.out.println(" A piece is in the path OR it is not a valid move!");
					}
				} else if ( (this.x < x) && (this.y < y)){	//up right diagonal case
					if( !pieceIsInPath(this.x,this.y,x,y,this.rep, "upRight") 
							&& isInMovesArray(this.x,this.y,x,y,rep) ){
						flag = true;
					} else {
						System.out.print("Error attempting to move!");
						System.out.println(" A piece is in the path OR it is not a valid move!");
					}					
				} else if( (this.x > x) && (this.y > y) ){		//down left diagonal case
					if( !pieceIsInPath(this.x,this.y,x,y,this.rep, "downLeft") 
							&& isInMovesArray(this.x,this.y,x,y,rep) ){
						flag = true;
					} else {
						System.out.print("Error attempting to move!");
						System.out.println(" A piece is in the path OR it is not a valid move!");
					}
				} else if ( (this.x < x) && (this.y > y)){	//down right diagonal case
					if( !pieceIsInPath(this.x,this.y,x,y,this.rep, "downRight") 
							&& isInMovesArray(this.x,this.y,x,y,rep) ){
						flag = true;
					} else {
						System.out.print("Error attempting to move!");
						System.out.println(" A piece is in the path OR it is not a valid move!");
					}					
				}
			
			//CASE: WHITE AND BLACK QUEEN
			//can move horizontally, vertically, and diagonally across the map
			//    as long as no pieces are in its path
				case "wQ":
					if( this.y < y && this.x == x ){		//up  case
						if( !pieceIsInPath(this.x,this.y,x,y,this.rep, "up")
								&& isInMovesArray(this.x,this.y,x,y,rep) ){
							flag = true;
						} else {
							System.out.print("Error attempting to move!");
							System.out.println(" A piece is in the path OR it is not a valid move!");
						}
					}

					if( this.y > y && this.x == x ){		//down  case
						if( !pieceIsInPath(this.x,this.y,x,y,this.rep, "down")
								&& isInMovesArray(this.x,this.y,x,y,rep) ){
							flag = true;
						} else {
							System.out.print("Error attempting to move!");
							System.out.println(" A piece is in the path OR it is not a valid move!");
						}
					}

					if( this.x > x && this.y == y){		//left  case
						if( !pieceIsInPath(this.x,this.y,x,y,this.rep, "left")
								&& isInMovesArray(this.x,this.y,x,y,rep) ){
							flag = true;
						} else {
							System.out.print("Error attempting to move!");
							System.out.println(" A piece is in the path OR it is not a valid move!");
						}
					}

					if( this.x < x && this.y == y){		//right  case
						if( !pieceIsInPath(this.x,this.y,x,y,this.rep, "right")
								&& isInMovesArray(this.x,this.y,x,y,rep) ){
							flag = true;
						} else {
							System.out.print("Error attempting to move!");
							System.out.println(" A piece is in the path OR it is not a valid move!");
						}
					}

					if( (this.x > x) && (this.y < y) ){		//up left diagonal case
						if( !pieceIsInPath(this.x,this.y,x,y,this.rep, "upLeft")
								&& isInMovesArray(this.x,this.y,x,y,rep) ){
							flag = true;
						} else {
							System.out.print("Error attempting to move!");
							System.out.println(" A piece is in the path OR it is not a valid move!");
						}
					} else if ( (this.x < x) && (this.y < y)){	//up right diagonal case
						if( !pieceIsInPath(this.x,this.y,x,y,this.rep, "upRight") 
								&& isInMovesArray(this.x,this.y,x,y,rep) ){
							flag = true;
						} else {
							System.out.print("Error attempting to move!");
							System.out.println(" A piece is in the path OR it is not a valid move!");
						}					
					} else if( (this.x > x) && (this.y > y) ){		//down left diagonal case
						if( !pieceIsInPath(this.x,this.y,x,y,this.rep, "downLeft") 
								&& isInMovesArray(this.x,this.y,x,y,rep) ){
							flag = true;
						} else {
							System.out.print("Error attempting to move!");
							System.out.println(" A piece is in the path OR it is not a valid move!");
						}
					} else if ( (this.x < x) && (this.y > y)){	//down right diagonal case
						if( !pieceIsInPath(this.x,this.y,x,y,this.rep, "downRight") 
								&& isInMovesArray(this.x,this.y,x,y,rep) ){
							flag = true;
						} else {
							System.out.print("Error attempting to move!");
							System.out.println(" A piece is in the path OR it is not a valid move!");
						}					
					}					
								
				break;

				case "bQ":
					if( this.y < y && this.x == x ){		//up  case
						if( !pieceIsInPath(this.x,this.y,x,y,this.rep, "up")
								&& isInMovesArray(this.x,this.y,x,y,rep) ){
							flag = true;
						} else {
							System.out.print("Error attempting to move!");
							System.out.println(" A piece is in the path OR it is not a valid move!");
						}
					}

					if( this.y > y && this.x == x ){		//down  case
						if( !pieceIsInPath(this.x,this.y,x,y,this.rep, "down")
								&& isInMovesArray(this.x,this.y,x,y,rep) ){
							flag = true;
						} else {
							System.out.print("Error attempting to move!");
							System.out.println(" A piece is in the path OR it is not a valid move!");
						}
					}

					if( this.x > x && this.y == y){		//left  case
						if( !pieceIsInPath(this.x,this.y,x,y,this.rep, "left")
								&& isInMovesArray(this.x,this.y,x,y,rep) ){
							flag = true;
						} else {
							System.out.print("Error attempting to move!");
							System.out.println(" A piece is in the path OR it is not a valid move!");
						}
					}

					if( this.x < x && this.y == y){		//right  case
						if( !pieceIsInPath(this.x,this.y,x,y,this.rep, "right")
								&& isInMovesArray(this.x,this.y,x,y,rep) ){
							flag = true;
						} else {
							System.out.print("Error attempting to move!");
							System.out.println(" A piece is in the path OR it is not a valid move!");
						}
					}

					if( (this.x > x) && (this.y < y) ){		//up left diagonal case
						if( !pieceIsInPath(this.x,this.y,x,y,this.rep, "upLeft")
								&& isInMovesArray(this.x,this.y,x,y,rep) ){
							flag = true;
						} else {
							System.out.print("Error attempting to move!");
							System.out.println(" A piece is in the path OR it is not a valid move!");
						}
					} else if ( (this.x < x) && (this.y < y)){	//up right diagonal case
						if( !pieceIsInPath(this.x,this.y,x,y,this.rep, "upRight") 
								&& isInMovesArray(this.x,this.y,x,y,rep) ){
							flag = true;
						} else {
							System.out.print("Error attempting to move!");
							System.out.println(" A piece is in the path OR it is not a valid move!");
						}					
					} else if( (this.x > x) && (this.y > y) ){		//down left diagonal case
						if( !pieceIsInPath(this.x,this.y,x,y,this.rep, "downLeft") 
								&& isInMovesArray(this.x,this.y,x,y,rep) ){
							flag = true;
						} else {
							System.out.print("Error attempting to move!");
							System.out.println(" A piece is in the path OR it is not a valid move!");
						}
					} else if ( (this.x < x) && (this.y > y)){	//down right diagonal case
						if( !pieceIsInPath(this.x,this.y,x,y,this.rep, "downRight") 
								&& isInMovesArray(this.x,this.y,x,y,rep) ){
							flag = true;
						} else {
							System.out.print("Error attempting to move!");
							System.out.println(" A piece is in the path OR it is not a valid move!");
						}					
					}
				break;

				//BLACK AND WHITE ROCK
				//Can move up,left,down, and right across the map
				//as long as no piece is in the path
				case "wR":
					if( this.y < y && this.x == x ){		//up  case
						if( !pieceIsInPath(this.x,this.y,x,y,this.rep, "up")
								&& isInMovesArray(this.x,this.y,x,y,rep) ){
							flag = true;
						} else {
							System.out.print("Error attempting to move!");
							System.out.println(" A piece is in the path OR it is not a valid move!");
						}
					}

					if( this.y > y && this.x == x ){		//down  case
						if( !pieceIsInPath(this.x,this.y,x,y,this.rep, "down")
								&& isInMovesArray(this.x,this.y,x,y,rep) ){
							flag = true;
						} else {
							System.out.print("Error attempting to move!");
							System.out.println(" A piece is in the path OR it is not a valid move!");
						}
					}

					if( this.x > x && this.y == y){		//left  case
						if( !pieceIsInPath(this.x,this.y,x,y,this.rep, "left")
								&& isInMovesArray(this.x,this.y,x,y,rep) ){
							flag = true;
						} else {
							System.out.print("Error attempting to move!");
							System.out.println(" A piece is in the path OR it is not a valid move!");
						}
					}

					if( this.x < x && this.y == y){		//right  case
						if( !pieceIsInPath(this.x,this.y,x,y,this.rep, "right")
								&& isInMovesArray(this.x,this.y,x,y,rep) ){
							flag = true;
						} else {
							System.out.print("Error attempting to move!");
							System.out.println(" A piece is in the path OR it is not a valid move!");
						}
					}
				break;
				case "bR":
					if( this.y < y && this.x == x ){		//up  case
						if( !pieceIsInPath(this.x,this.y,x,y,this.rep, "up")
								&& isInMovesArray(this.x,this.y,x,y,rep) ){
							flag = true;
						} else {
							System.out.print("Error attempting to move!");
							System.out.println(" A piece is in the path OR it is not a valid move!");
						}
					}

					if( this.y > y && this.x == x ){		//down  case
						if( !pieceIsInPath(this.x,this.y,x,y,this.rep, "down")
								&& isInMovesArray(this.x,this.y,x,y,rep) ){
							flag = true;
						} else {
							System.out.print("Error attempting to move!");
							System.out.println(" A piece is in the path OR it is not a valid move!");
						}
					}

					if( this.x > x && this.y == y){		//left  case
						if( !pieceIsInPath(this.x,this.y,x,y,this.rep, "left")
								&& isInMovesArray(this.x,this.y,x,y,rep) ){
							flag = true;
						} else {
							System.out.print("Error attempting to move!");
							System.out.println(" A piece is in the path OR it is not a valid move!");
						}
					}

					if( this.x < x && this.y == y){		//right  case
						if( !pieceIsInPath(this.x,this.y,x,y,this.rep, "right")
								&& isInMovesArray(this.x,this.y,x,y,rep) ){
							flag = true;
						} else {
							System.out.print("Error attempting to move!");
							System.out.println(" A piece is in the path OR it is not a valid move!");
						}
					}
				break;

				case "wN":
					if( isInMovesArray(this.x,this.y,x,y,rep) ){
						flag = true;
					} else {
						System.out.println("Error attempting to move!");
					}
				break;

				case "bN":
					if( isInMovesArray(this.x,this.y,x,y,rep) ){
						flag = true;
					} else {
						System.out.println("Error attempting to move!");
					}
				break;

				case "wK":
					if( isInMovesArray(this.x,this.y,x,y,rep) ){
						flag = true;
					} else if ( x == 8 && y == 1 ){	//check if castling to the right
						if( this.moveCount == 0 ){  //if king hasnt moved yet
							if (brd.findPiece("H1").moveCount == 0){ //if right rook hasnt moved yet
								//if no piece is between eachother
								if( !pieceIsInPath(this.x, this.y, x, y, "wK", "castleRight")){
									flag = true;
									castled = true;
									castledRight = true;
								}
							}
						}
					} else if ( x == 1 && y == 1 ){	//check if castling to the left
						if( this.moveCount == 0 ){  //if king hasnt moved yet
							if (brd.findPiece("A1").moveCount == 0){ //if left rook hasnt moved yet
								//if no piece is between eachother
								if( !pieceIsInPath(this.x, this.y, x, y, "wK", "castleLeft")){
									flag = true;
									castled = true;
									castledLeft = true;
								}
							}
						}
					} else {
						System.out.println("Error attempting to move!");
					}
				break;

				case "bK":
					if( isInMovesArray(this.x,this.y,x,y,rep) ){
						flag = true;
					} else if ( x == 8 && y == 8 ){	//check if castling to the right
						if( this.moveCount == 0 ){  //if king hasnt moved yet
							if (brd.findPiece("H8").moveCount == 0){ //if right rook hasnt moved yet
								//if no piece is between eachother
								if( !pieceIsInPath(this.x, this.y, x, y, "bK", "castleRight")){
									flag = true;
									castled = true;
									castledRight = true;
								}
							}
						}
					} else if ( x == 1 && y == 8 ){	//check if castling to the left
						if( this.moveCount == 0 ){  //if king hasnt moved yet
							if (brd.findPiece("A8").moveCount == 0){ //if left rook hasnt moved yet
								//if no piece is between eachother
								if( !pieceIsInPath(this.x, this.y, x, y, "bK", "castleLeft")){
									flag = true;
									castled = true;
									castledLeft = true;
								}
							}
						}
					} else {
						System.out.println("Error attempting to move!");
					}
				break;

			default:
					System.out.println("canMove() ERROR: INVALID PIECE...");
		}

		return flag;
	}

	//isInMovesArray
	//recieves the cordinates the piece is in, the cordinates it wants to move to
	// 	and the string representation of the piece type
	//description: creates an array of possible moves
	//returns true if the destination cordinate is in the created array, false otherwise
	private boolean isInMovesArray(int x2, int y2, int x3, int y3, String rep2) {
		boolean flag = false;
		int [][] bishopMovesArr = new int[8][2];  //each spot on the x-axis can have two possible y-axis moves.	

		switch( rep2 ){
			case "wB" :
			//each spot on the x-axis can have two possible y-axis moves.
			//so each iteration of mapArr represents each x-axis
			//   and each map represents the two y-axis posssibilities for each spot on the x-axis
				for( int i = 0; i < 8; i++ ){
					for( int j = 0; j < 2; j++ ){
						bishopMovesArr[i][0] = y2+i+1;
						bishopMovesArr[i][1] = y2-i-1;
						//System.out.println( i+x2+1 +":" + bishopMovesArr[i][0] + "," + bishopMovesArr[i][1]);
					}
				}
				//map now contains possible y-cordinates for the 8 x-axis spots to the right of x2

				//check each y-cordinate to the right and left of x2
				//if a match is made, then the piece is allowed to move there
				for( int i = 0; i < 8; i++ ){
					for( int j = 0; j < 2; j++ ){
						if( x3 == i+x2+1 && ((y3 == bishopMovesArr[i][0]) || (y3 == bishopMovesArr[i][1])) ){
							flag = true;
							//System.out.println("Cordinate was found in: isInMovesArray()!!!");
						} else if( x3 == x2-i-1 && ((y3 == bishopMovesArr[i][0]) || (y3 == bishopMovesArr[i][1])) ){
							flag = true;
							//System.out.println("Cordinate was found in: isInMovesArray()!!!");
						} else {
							//System.out.println("Cordinate was NOT found in: isInMovesArray()");
						}
					}
				}

					

			break;
			case "bB" :
			//each spot on the x-axis can have two possible y-axis moves.
			//so each iteration of mapArr represents each x-axis
			//   and each map represents the two y-axis posssibilities for each spot on the x-axis
				for( int i = 0; i < 8; i++ ){
					for( int j = 0; j < 2; j++ ){
						bishopMovesArr[i][0] = y2+i+1;
						bishopMovesArr[i][1] = y2-i-1;
						//System.out.println( i+x2+1 +":" + bishopMovesArr[i][0] + "," + bishopMovesArr[i][1]);
					}
				}
				//map now contains possible y-cordinates for the 8 x-axis spots to the right of x2

				//check each y-cordinate to the right and left of x2
				//if a match is made, then the piece is allowed to move there
				for( int i = 0; i < 8; i++ ){
					for( int j = 0; j < 2; j++ ){
						if( x3 == i+x2+1 && ((y3 == bishopMovesArr[i][0]) || (y3 == bishopMovesArr[i][1])) ){
							flag = true;
							//System.out.println("Cordinate was found in: isInMovesArray()!!!");
						} else if( x3 == x2-i-1 && ((y3 == bishopMovesArr[i][0]) || (y3 == bishopMovesArr[i][1])) ){
							flag = true;
							//System.out.println("Cordinate was found in: isInMovesArray()!!!");
						} else {
							//System.out.println("Cordinate was NOT found in: isInMovesArray()");
						}
					}
				}
			break;
			//CASE WHITE AND BLACK QUEEN
			//combines bishop, rock, and king moves
			case "wQ":
				//check in rockMovesArr if x3,y3 is a valid move
				if( (x3 == x2 && y3 != y2) || (x3 != x2 && y3 == y2) ){
					flag = true;
					//System.out.println("Cordinate was found in: isInMovesArray()!!!");
				} else {
					//System.out.println("Cordinate was NOT found in: isInMovesArray()");
				}

			//each spot on the x-axis can have two possible y-axis moves.
			//so each iteration of mapArr represents each x-axis
			//   and each map represents the two y-axis posssibilities for each spot on the x-axis
				for( int i = 0; i < 8; i++ ){
					for( int j = 0; j < 2; j++ ){
						bishopMovesArr[i][0] = y2+i+1;
						bishopMovesArr[i][1] = y2-i-1;
						//System.out.println( i+x2+1 +":" + bishopMovesArr[i][0] + "," + bishopMovesArr[i][1]);
					}
				}
				//map now contains possible y-cordinates for the 8 x-axis spots to the right of x2

				//check each y-cordinate to the right and left of x2
				//if a match is made, then the piece is allowed to move there
				for( int i = 0; i < 8; i++ ){
					for( int j = 0; j < 2; j++ ){
						if( x3 == i+x2+1 && ((y3 == bishopMovesArr[i][0]) || (y3 == bishopMovesArr[i][1])) ){
							flag = true;
							//System.out.println("Cordinate was found in: isInMovesArray()!!!");
						} else if( x3 == x2-i-1 && ((y3 == bishopMovesArr[i][0]) || (y3 == bishopMovesArr[i][1])) ){
							flag = true;
							//System.out.println("Cordinate was found in: isInMovesArray()!!!");
						} else {
							//System.out.println("Cordinate was NOT found in: isInMovesArray()");
						}
					}
				}
			break;

			case "bQ":
				//check in rockMovesArr if x3,y3 is a valid move
				if( (x3 == x2 && y3 != y2) || (x3 != x2 && y3 == y2) ){
					flag = true;
					//System.out.println("Cordinate was found in: isInMovesArray()!!!");
				} else {
					//System.out.println("Cordinate was NOT found in: isInMovesArray()");
				}

			//each spot on the x-axis can have two possible y-axis moves.
			//so each iteration of mapArr represents each x-axis
			//   and each map represents the two y-axis posssibilities for each spot on the x-axis
				for( int i = 0; i < 8; i++ ){
					for( int j = 0; j < 2; j++ ){
						bishopMovesArr[i][0] = y2+i+1;
						bishopMovesArr[i][1] = y2-i-1;
						//System.out.println( i+x2+1 +":" + bishopMovesArr[i][0] + "," + bishopMovesArr[i][1]);
					}
				}
				//map now contains possible y-cordinates for the 8 x-axis spots to the right of x2

				//check each y-cordinate to the right and left of x2
				//if a match is made, then the piece is allowed to move there
				for( int i = 0; i < 8; i++ ){
					for( int j = 0; j < 2; j++ ){
						if( x3 == i+x2+1 && ((y3 == bishopMovesArr[i][0]) || (y3 == bishopMovesArr[i][1])) ){
							flag = true;
							//System.out.println("Cordinate was found in: isInMovesArray()!!!");
						} else if( x3 == x2-i-1 && ((y3 == bishopMovesArr[i][0]) || (y3 == bishopMovesArr[i][1])) ){
							flag = true;
							//System.out.println("Cordinate was found in: isInMovesArray()!!!");
						} else {
							//System.out.println("Cordinate was NOT found in: isInMovesArray()");
						}
					}
				}

			break;

			case "wR":
				//check in rockMovesArr if x3,y3 is a valid move
				if( (x3 == x2 && y3 != y2) || (x3 != x2 && y3 == y2) ){
					flag = true;
					//System.out.println("Cordinate was found in: isInMovesArray()!!!");
				} else {
					//System.out.println("Cordinate was NOT found in: isInMovesArray()");
				}
			break;

			case "bR":
				//check in rockMovesArr if x3,y3 is a valid move
				if( (x3 == x2 && y3 != y2) || (x3 != x2 && y3 == y2) ){
					flag = true;
					//System.out.println("Cordinate was found in: isInMovesArray()!!!");
				} else {
					//System.out.println("Cordinate was NOT found in: isInMovesArray()");
				}
			break;

			case "wN":
				if( x3 == x2 - 2 && y3 == y2 + 1 ){ 		//up left
					flag = true;
				} else if ( x3 == x2 - 1 && y3 == y2 + 2 ){ //up left
					flag = true;
				} else if ( x3 == x2 - 2 && y3 == y2 - 1 ){ //down left
					flag = true;
				} else if ( x3 == x2 - 1 && y3 == y2 - 2 ){	//down left
					flag = true;
				} else if ( x3 == x2 + 1 && y3 == y2 + 2 ){ //up right
					flag = true;
				} else if ( x3 == x2 + 2 && y3 == y2 + 1 ){ //up right
					flag = true;
				} else if ( x3 == x2 + 2 && y3 == y2 - 1 ){ //down right
					flag = true;
				} else if ( x3 == x2 + 1 && y3 == y2 - 2 ){ //down right
					flag = true;
				}

			break;

			case "bN":
				if( x3 == x2 - 2 && y3 == y2 + 1 ){ 		//up left
					flag = true;
				} else if ( x3 == x2 - 1 && y3 == y2 + 2 ){ //up left
					flag = true;
				} else if ( x3 == x2 - 2 && y3 == y2 - 1 ){ //down left
					flag = true;
				} else if ( x3 == x2 - 1 && y3 == y2 - 2 ){	//down left
					flag = true;
				} else if ( x3 == x2 + 1 && y3 == y2 + 2 ){ //up right
					flag = true;
				} else if ( x3 == x2 + 2 && y3 == y2 + 1 ){ //up right
					flag = true;
				} else if ( x3 == x2 + 2 && y3 == y2 - 1 ){ //down right
					flag = true;
				} else if ( x3 == x2 + 1 && y3 == y2 - 2 ){ //down right
					flag = true;
				}			
			break;

			//CASE WHITE AND BLACK KING 
			//can only move one space adjacent to its current space
			case "wK":
				if( x3 == x2 - 1 && y3 == y2 + 1 ){			//up left
					flag = true;
				} else if ( x3 == x2 - 1 && y3 == y2 ){ 	//left
					flag = true;
				} else if ( x3 == x2 - 1 && y3 == y2 - 1 ){ //down left
					flag = true;
				} else if ( x3 == x2 && y3 == y2 + 1 ){		//up
					flag = true;
				} else if ( x3 == x2 && y3 == y2 - 1 ){		//down
					flag = true;
				} else if ( x3 == x2 + 1 && y3 == y2 + 1){	//up right
					flag = true;
				} else if ( x3 == x2 + 1 && y3 == y2 ){		//right
					flag = true;
				} else if ( x3 == x2 + 1 && y3 == y2 - 1 ){	//down right
					flag = true;
				}
			break;

			case "bK":
				if( x3 == x2 - 1 && y3 == y2 + 1 ){			//up left
					flag = true;
				} else if ( x3 == x2 - 1 && y3 == y2 ){ 	//left
					flag = true;
				} else if ( x3 == x2 - 1 && y3 == y2 - 1 ){ //down left
					flag = true;
				} else if ( x3 == x2 && y3 == y2 + 1 ){		//up
					flag = true;
				} else if ( x3 == x2 && y3 == y2 - 1 ){		//down
					flag = true;
				} else if ( x3 == x2 + 1 && y3 == y2 + 1){	//up right
					flag = true;
				} else if ( x3 == x2 + 1 && y3 == y2 ){		//right
					flag = true;
				} else if ( x3 == x2 + 1 && y3 == y2 - 1 ){	//down right
					flag = true;
				}
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
		if( rep2 == "wB" || rep2 == "bB" ){		//WHITE AND BLACK BISHOP
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
	} else if ( rep2 == "wQ" || rep2 == "bQ" ){		//WHITE AND BLACK QUEEN
		if( mvmnt == "up" ){
				yCpy = y3;
				xCpy = x3;
				yCpy -= 1;
				while( yCpy != y2 && yCpy > 0){ //loop through each diagonal space,between points,checking if a piece is there
					tmp = brd.findPiece(intCordToString(xCpy,yCpy));
					if( tmp != null ){
						flag = true;   //case a piece is found on the path
					}
					yCpy -= 1;
				}

		} else if ( mvmnt == "down" ){
				yCpy = y3;
				xCpy = x3;
				yCpy += 1;
				while( yCpy != y2 && yCpy > 0){ //loop through each diagonal space,between points,checking if a piece is there
					tmp = brd.findPiece(intCordToString(xCpy,yCpy));
					if( tmp != null ){
						flag = true;   //case a piece is found on the path
					}
					yCpy += 1;
				}
		} else if ( mvmnt == "left" ){
				yCpy = y3;
				xCpy = x3;
				xCpy += 1;
				while( xCpy != x2 && xCpy > 0){ //loop through each diagonal space,between points,checking if a piece is there
					tmp = brd.findPiece(intCordToString(xCpy,yCpy));
					if( tmp != null ){
						flag = true;   //case a piece is found on the path
					}
					xCpy += 1;
				}
			
		} else if ( mvmnt == "right" ){
				yCpy = y3;
				xCpy = x3;
				xCpy -= 1;
				while( xCpy != x2 && xCpy > 0){ //loop through each diagonal space,between points,checking if a piece is there
					tmp = brd.findPiece(intCordToString(xCpy,yCpy));
					if( tmp != null ){
						flag = true;   //case a piece is found on the path
					}
					xCpy -= 1;
				}
		} else if( mvmnt == "upLeft"){  //check case for moving up left		
				while( xCpy != x2 && yCpy > 0){ //loop through each diagonal space,between points,checking if a piece is there
					tmp = brd.findPiece(intCordToString(xCpy,yCpy));
					if( tmp != null ){
						flag = true;   //case a piece is found on the path
					}
					xCpy += 1;
					yCpy -= 1;
				}
			} else if( mvmnt == "upRight" ){//check from a different angle (case for moving up right)
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
			} else if( mvmnt == "downRight" ){//check from a different angle (case for moving down right)
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
			} else if( mvmnt == "downLeft" ){//check from a different angle (case for moving down left)
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
	//end of white and black queen
	} else if ( rep2 == "wR" || rep2 == "bR" ){
			if( mvmnt == "up" ){
					yCpy = y3;
					xCpy = x3;
					yCpy -= 1;
					while( yCpy != y2 && yCpy > 0){ //loop through each diagonal space,between points,checking if a piece is there
						tmp = brd.findPiece(intCordToString(xCpy,yCpy));
						if( tmp != null ){
							flag = true;   //case a piece is found on the path
						}
						yCpy -= 1;
					}

			} else if ( mvmnt == "down" ){
					yCpy = y3;
					xCpy = x3;
					yCpy += 1;
					while( yCpy != y2 && yCpy > 0){ //loop through each diagonal space,between points,checking if a piece is there
						tmp = brd.findPiece(intCordToString(xCpy,yCpy));
						if( tmp != null ){
							flag = true;   //case a piece is found on the path
						}
						yCpy += 1;
					}
			} else if ( mvmnt == "left" ){
					yCpy = y3;
					xCpy = x3;
					xCpy += 1;
					while( xCpy != x2 && xCpy > 0){ //loop through each diagonal space,between points,checking if a piece is there
						tmp = brd.findPiece(intCordToString(xCpy,yCpy));
						if( tmp != null ){
							flag = true;   //case a piece is found on the path
						}
						xCpy += 1;
					}
				
			} else if ( mvmnt == "right" ){
					yCpy = y3;
					xCpy = x3;
					xCpy -= 1;
					while( xCpy != x2 && xCpy > 0){ //loop through each diagonal space,between points,checking if a piece is there
						tmp = brd.findPiece(intCordToString(xCpy,yCpy));
						if( tmp != null ){
							flag = true;   //case a piece is found on the path
						}
						xCpy -= 1;
					}		
				}
		//end of white and black rock
		} else if ( rep2 == "wK" || rep2 == "bK"){
			if( mvmnt == "castleRight" ){
				yCpy = y3;
				xCpy = x3;
				xCpy -= 1;
				while( xCpy != x2 && xCpy > 0){ //loop through each diagonal space,between points,checking if a piece is there
					tmp = brd.findPiece(intCordToString(xCpy,yCpy));
					if( tmp != null ){
						flag = true;   //case a piece is found on the path
					}
					xCpy -= 1;
				}				
			} else if ( mvmnt == "castleLeft" ){
				yCpy = y3;
				xCpy = x3;
				xCpy += 1;
				while( xCpy != x2 && xCpy > 0){ //loop through each diagonal space,between points,checking if a piece is there
					tmp = brd.findPiece(intCordToString(xCpy,yCpy));
					if( tmp != null ){
						flag = true;   //case a piece is found on the path
					}
					xCpy += 1;
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