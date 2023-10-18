package utility;

public class Player {
    private boolean white;   //represents which team the Player is on
                     // true - white
                     // false - black

    //constructor
    Player(boolean b){
        white = b;
    }

    boolean getColor(){ return white; }
}
