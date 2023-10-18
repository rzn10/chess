package utility;
import utility.Player;

public class Game {
	public boolean turn;	//Game's access to piece control is based off the turn
			//   0 - white
			//   1 - black

	public Player[] plyr;  //array of player objects
	public Board brd;

	//nextTurn changes the turn flag to allow the alternate player to move
	public void nextTurn(){
		if( turn ){
			turn = false;
		} else if (!turn){
			turn = true;
		}
	}

	public Game(){
		turn = false;
		plyr = new Player[2];
		plyr[0] = new Player(true);	//create white player
		plyr[1] = new Player(false);  //create black player
		brd = new Board();
	}
}