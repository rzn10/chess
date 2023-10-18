package pieces;
public class Piece{
	private boolean white;  //team of the piece
	private boolean alive;	//status of the piece
	private int x;			//x position of the piece
	private int y;			//y position of the piece
	public String rep;				//two characters representing the different pieces

//constructor
//sets initial position
//recieves: color to determine which turn the piece is active
// true - white,
// false - black
	public Piece(boolean color, int x, int y, String r){
		white = color;
		alive = true;
		this.x = x;
		this.y = y;
		this.rep = r;
	}

//canMove
//recieves: 2 dimensional int array representing the piece's position
//returns: boolean represnting if the piece can move to the pisition or not	
	public boolean canMove(int x, int y){
		boolean flag = true;

		return flag;
	}

//move changes the piece's position
//recieves: String representing the coordinate to move to
	public void move(String ch){
		//convert string to x y 
		int x;
		int y;
		x = (int)(ch.charAt(0))-64;
		y = ((int)(ch.charAt(1))-48);
		System.out.println("Moving to: " + x + " " + y);
		if( canMove(x,y) ){
			this.x = x;
			this.y = y;
		}
	}

	public void die(){ alive = false; }

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