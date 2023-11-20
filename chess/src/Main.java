import utility.Game;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws Exception {
        Scanner scnr = new Scanner(System.in);

        Game myGame = new Game();
        //myGame.brd.printPieces();
        Game.brd.printField();


        String ch0;    //String ch0 represents the position of the piece to be moved from
        String ch1;    //String ch1 represents the position of the piece to be moved to
        while(true){
            //determine who's turn it is
            // false - white
            // true - black
            if(!myGame.turn){
                //System.out.print("White's turn. Please enter a move: ");
                ch0 = scnr.next();
                ch1 = scnr.next();
                myGame.brd.findPiece(ch0).move(ch1); //Move from ch0 to ch1 (needs exception testing)
                myGame.nextTurn();
            } else if(myGame.turn){
                //System.out.print("Black's turn. Please enter a move: ");
                ch0 = scnr.next();
                ch1 = scnr.next();
                myGame.brd.findPiece(ch0).move(ch1); //Move from ch0 to ch1 (needs exception testing)
                myGame.nextTurn(); 
            }

        //print field
        //myGame.brd.printPieces();
        Game.brd.printField();
        scnr.close();
        }
        
    }
}
