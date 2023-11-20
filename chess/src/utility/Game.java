package utility;
import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashMap;
import java.util.Map;
import java.awt.*;
import pieces.Bishop;
import pieces.King;
import pieces.Knight;
import pieces.Pawn;
import pieces.Queen;
import pieces.Rock;
import pieces.Piece;
import java.util.*;

public class Game {
	public boolean turn;	//Game's access to piece control is based off the turn
							//   false - white
							//   true - black

	public static Board brd;
	private static final int ROWS = 8;
    private static final int COLS = 8;
    public static JLabel selected = null;    //a seleced piece
    public static JLabel[][] gameBoardSquares = new JLabel[COLS][ROWS];
	public static Map<JLabel,String> cordMap = new HashMap(64);

	// Unicode characters for chess pieces
	private static final String[] UNICODE_PIECES = {
			"\u265A", "\u265B", "\u265C", "\u265D", "\u265E", "\u265F",
            "\u2654", "\u2655", "\u2656", "\u2657", "\u2658", "\u2659"
            
    };

    private static final String[] STRING_PIECES = {
        "bK", "bQ", "bR", "bB", "bN", "bP",
		"wK", "wQ", "wR", "wB", "wN", "wP"
    };

	// Custom lighter colors for the board
	Color lightColor = new Color(240, 217, 181);
	Color darkColor = new Color(181, 136, 99);

	//nextTurn changes the turn flag to allow the alternate player to move
	public void nextTurn(){
		if( turn ){
			turn = false;
		} else if (!turn){
			turn = true;
		}
	}

	/*
	 * move
	 * recieves: JLabel a to represent the JLabel square we are moving from
	 * 			 JLabel b to represent the JLabel square we are moving to
	 * description: searches for a's string cordinates and b's cordinates and
	 * 				plugs them into findPiece method
	 * 				once the piece of a is found, test if it can move to b or not
	 * returns: true if the piece can move there, false if not
	 */
	public static boolean move(JLabel a, JLabel b) {
		boolean flag = false;
		String cordA = getSquareCordinate(a);
		String cordB = getSquareCordinate(b);
		//System.out.println("CORDINATE A: " + cordA);
		//System.out.println("CORDINATE B: " + cordB);
		Piece tmp = brd.findPiece(cordA);	//tmp now holds cordinate A's Piece object
		Piece tmp1 = brd.findPiece(cordB);
		//check if a castle attempt was made


		
		int x1 = (int)(cordB.charAt(0))-64;
		int y1 = ((int)(cordB.charAt(1))-48);
		//System.out.println("GAME: ATTEMPTING TO MOVE TO " + x1 + "," + y1);
			if( tmp.canMove(x1, y1) ){
				if( !tmp.castled ){
					if( tmp1 == null ){		//friendly fire check
						brd.move(cordA,cordB);
						flag = true;
					} else if ( tmp1 != null ){
						if( tmp1.getColor() != tmp.getColor() ){ //move there is the colors are different
							brd.move(cordA,cordB);
							flag = true;							
						}
					}
				} else if ( tmp.getColor() && tmp.castledRight && tmp.moveCount == 0 ){ //case if white king castled right
					flag = true;
					brd.move("E1", "G1");		//move king to 7,1
					brd.move("H1", "F1");		//move rock to 6,1
					tmp.castledRight = false; 			//reset
					tmp.castled = false;				//reset
				} else if ( tmp.getColor() && tmp.castledLeft && tmp.moveCount == 0 ){ //case if white king castled left
					flag = true;
					brd.move("E1", "C1");		//move king to 7,1
					brd.move("A1", "D1");		//move rock to 6,1
					tmp.castledLeft = false; 			//reset
					tmp.castled = false;				//reset
				} else if ( !tmp.getColor() && tmp.castledRight && tmp.moveCount == 0 ){ //case if black king castled right
					flag = true;
					brd.move("E8", "G8");		
					brd.move("H8", "F8");		
					tmp.castledRight = false; 			//reset
					tmp.castled = false;				//reset
				} else if ( !tmp.getColor() && tmp.castledLeft && tmp.moveCount == 0 ){ //case if black king castled left
					flag = true;
					brd.move("E8", "C8");		
					brd.move("A8", "D8");		
					tmp.castledLeft = false; 			//reset
					tmp.castled = false;				//reset
				}
				
			}
			else
				System.out.println("Cant move there!");
	
		brd.printField();
		return flag;
	}

	/*
	 * getStringCordinate
	 * 
	 * recieves: int x to represent the x cordinate that the panel is located at
	 * 			 int y to represent the y cordinate that the panel is located at
	 * 
	 * returns:  string value to represent the cordinate name the panel is located at
	 */
	static String getStringCordinate(float x, float y){
		float firstChar = 65 + x;
		float secondChar = y + 1;

		String str = "" + (char)firstChar + (int)secondChar;

		return str;
	}


	/*
	 * getSquareCordinate(JLabel sqr)
	 * recieves: the JLabel square we want to find the cordinates of
	 * description: searches through cordMap HashMap and returns the string value
	 * 		representing the cordinates of the JLabel sqr
	 */
	static String getSquareCordinate(JLabel sqr){
		String str = "";

		if(cordMap.containsKey(sqr)){
			str = cordMap.get(sqr);
        } else {
            System.out.println("ERROR: Key not found.");
        }

		if( str == "" ){
			System.out.println("ERROR: getSquareCordinate in Game.java did not find the coordinate...");
		}

		return str;
	}


	/*
     * handleMouseClick(JLabel)
     * 
     * recieves: JLabel curr to represent the most recent square clicked
     * description: performs actions depending on if a piece was previously selected or not
     *     if a piece was selected, then move the current square to the selected square (if valid)
     *     if a piece wasnt selected, the curr is the piece to be selected
     * returns: nothing
     */
    public static void handleMouseClick(JLabel curr) {
		//System.out.println("SQUARES NAME: " + curr.getName() );
        if( selected == null ){          //if a piece isnt already being selected
            if( curr.getName() != ""){      //if the current click is on a piece
                //System.out.println(curr.getName());
                selected = curr;
                curr.setBorder(BorderFactory.createLineBorder(Color.YELLOW));
                //TODO: make case statement to highlight possible moves according to piece type
            }
        } else if( selected != null ){   //if a piece has already been selected
                if( curr.hashCode() == selected.hashCode() ){ //check if current is the same as the previous piece
                    //if so, unhighlight current and nullify selected
                    selected.setBorder(BorderFactory.createLineBorder(Color.GRAY));
                    selected = null;                                         
                }
                else if( curr.getName() == "" ){      //if the current click is a blank square
					if( move(selected,curr) ){  //if the piece can move there
                    	curr.setText(selected.getText());   //move selected piece to current square
                    	curr.setName(selected.getName());
                    	selected.setText("");
						selected.setName("");
                    	selected.setBorder(BorderFactory.createLineBorder(Color.GRAY));
                    	selected = null;
					} else {	//deselect if the piece cant move there
                    	selected.setBorder(BorderFactory.createLineBorder(Color.GRAY));
                    	selected = null;
					}
                } else if ( curr.getName() != ""){ //otherwise if the current move is to another piece
					if( move(selected,curr) ){  //if the piece can move there
						if( curr.getName() != "wK" && curr.getName() != "wR"
							&& (curr.getName() != "bK" && curr.getName() != "bR") ){
								curr.setText(selected.getText());   //move selected piece to current square
								curr.setName(selected.getName());
								selected.setText("");
								selected.setName("");
								selected.setBorder(BorderFactory.createLineBorder(Color.GRAY));
								selected = null;
						} else if ( selected.getName() == "wK" && curr.getName() == "wR" ){ //check if white king castling
							if( cordMap.get(curr).equals("H1") ){	//case castling right
								gameBoardSquares[5][7].setName("wR");
								gameBoardSquares[5][7].setText("\u2656");
								gameBoardSquares[6][7].setName("wK");
								gameBoardSquares[6][7].setText("\u2654");
								selected.setText("");
								selected.setName("");
								selected.setBorder(BorderFactory.createLineBorder(Color.GRAY));
								selected = null;
								curr.setText("");
								curr.setName("");
								curr = null;
							} else if ( cordMap.get(curr).equals("A1") ){ //case castling left
								gameBoardSquares[3][7].setName("wR");
								gameBoardSquares[3][7].setText("\u2656");
								gameBoardSquares[2][7].setName("wK");
								gameBoardSquares[2][7].setText("\u2654");
								selected.setText("");
								selected.setName("");
								selected.setBorder(BorderFactory.createLineBorder(Color.GRAY));
								selected = null;
								curr.setText("");
								curr.setName("");
								curr = null;								
							}
						} else if ( selected.getName() == "bK" && curr.getName() == "bR" ){ //check if black king castling
							if( cordMap.get(curr).equals("H8") ){	//case castling right
								gameBoardSquares[5][0].setName("bR");
								gameBoardSquares[5][0].setText("\u265C");
								gameBoardSquares[6][0].setName("bK");
								gameBoardSquares[6][0].setText("\u265A");
								selected.setText("");
								selected.setName("");
								selected.setBorder(BorderFactory.createLineBorder(Color.GRAY));
								selected = null;
								curr.setText("");
								curr.setName("");
								curr = null;
							} else if ( cordMap.get(curr).equals("A8") ){ //case castling left
								gameBoardSquares[3][0].setName("bR");
								gameBoardSquares[3][0].setText("\u265C");
								gameBoardSquares[2][0].setName("bK");
								gameBoardSquares[2][0].setText("\u265A");
								selected.setText("");
								selected.setName("");
								selected.setBorder(BorderFactory.createLineBorder(Color.GRAY));
								selected = null;
								curr.setText("");
								curr.setName("");
								curr = null;								
							}
						}
					} else {	//if the piece cant move there
                    	selected.setBorder(BorderFactory.createLineBorder(Color.GRAY));
                    	selected = null;
					}
                }
        }
    }

	/**
     * Determine the appropriate piece for the given board position.
     * 
     * recieves: row the row of the position
     *           col the column of the position
     * returns: a string representing the chess piece
     */
    private static String getPieceUnicode(int row, int col) {
        if (row == 0 || row == 7) {
            int offset = (row == 0) ? 0 : 6; // Determines white or black pieces
            switch (col) {
                case 0:
                case 7:
                    return UNICODE_PIECES[2 + offset]; // Rook
                case 1:
                case 6:
                    return UNICODE_PIECES[4 + offset]; // Knight
                case 2:
                case 5:
                    return UNICODE_PIECES[3 + offset]; // Bishop
                case 3:
                    return (row == 0) ? UNICODE_PIECES[1] : UNICODE_PIECES[7]; // Queen
                case 4:
                    return (row == 0) ? UNICODE_PIECES[0] : UNICODE_PIECES[6]; // King
            }
        } else if (row == 1 || row == 6) {
            return (row == 1) ? UNICODE_PIECES[5] : UNICODE_PIECES[11]; // Pawns
        }
        return ""; // Empty space for non-piece areas
    }

	/**
     * Determine the appropriate piece for the given board position.
     * 
     * recieves: row the row of the position
     *           col the column of the position
     * returns:  a string representing the chess piece
     */
    private static String getPieceString(int row, int col) {
        if (row == 0 || row == 7) {
            int offset = (row == 0) ? 0 : 6; // Determines white or black pieces
            switch (col) {
                case 0:
                case 7:
                    return STRING_PIECES[2 + offset]; // Rook
                case 1:
                case 6:
                    return STRING_PIECES[4 + offset]; // Knight
                case 2:
                case 5:
                    return STRING_PIECES[3 + offset]; // Bishop
                case 3:
                    return (row == 0) ? STRING_PIECES[1] : STRING_PIECES[7]; // Queen
                case 4:
                    return (row == 0) ? STRING_PIECES[0] : STRING_PIECES[6]; // King
            }
        } else if (row == 1 || row == 6) {
            return (row == 1) ? STRING_PIECES[5] : STRING_PIECES[11]; // Pawns
        }
        return ""; // Empty space for non-piece areas
    }

	public Game(){
		turn = false;
		brd = new Board();

		// Create the main frame for the chess board
        JFrame frame = new JFrame("Chess Board");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

		// Create a JPanel that will hold the board
        JPanel boardPanel = new JPanel();
        boardPanel.setLayout(new GridLayout(8, 8));
        boardPanel.setPreferredSize(new Dimension(400, 400));

		        // Create the chess board squares and pieces
				for (int row = 0; row < 8; row++) {
					for (int col = 0; col < 8; col++) {
						// Create a new JLabel with a piece or an empty string
						JLabel square = new JLabel(getPieceUnicode(row, col));
						square.setFont(new Font("Serif", Font.BOLD, 32)); // Make the piece characters bigger
						square.setHorizontalAlignment(JLabel.CENTER);
						square.setVerticalAlignment(JLabel.CENTER);
						//System.out.println("SETTING SQUARES CORDINATE: " + getSquareCordinate(row, col));
						square.setName(getPieceString(row,col));
						square.setBorder(BorderFactory.createLineBorder(Color.GRAY));
						square.setAlignmentX((float)col);
						square.setAlignmentY((float)7-row);
						square.addMouseListener(new MouseAdapter() {    //mouse listener for JLabel
							@Override
							public void mouseClicked(MouseEvent e) {
									handleMouseClick(square);
								}
						});
						gameBoardSquares[col][row] = square;    //add square to JLabel 2-D array
						cordMap.put(square, getStringCordinate(col, 7-row)); //add square and coordinates to a hashmap

						// Create a new JPanel square
						JPanel panelSquare = new JPanel(new BorderLayout());
		
						// Check the row and column numbers to decide the color
						if ((row + col) % 2 == 0) {
							panelSquare.setBackground(lightColor);
						} else {
							panelSquare.setBackground(darkColor);
						}
		
						// Add the JLabel to the square and the square to the board
						panelSquare.add(square,0);
						boardPanel.add(panelSquare);
						//gameBoardSquares[row][col].add(square);    //add each square panel to gameBoardSquares arr
		
					}
				}
		
				// Add board panel to the frame
				frame.add(boardPanel);
				frame.pack();  // Pack the frame to fit the board
				frame.setLocationRelativeTo(null);  // Center the frame
				frame.setVisible(true);
	}
}