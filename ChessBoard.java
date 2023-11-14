import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.*;

public class ChessBoard {
    static final int ROWS = 8;
    static final int COLS = 8;
    static JLabel selected = null;    //a seleced piece
    static JPanel[][] gameBoardSquares = new JPanel[ROWS][COLS];

    // Unicode characters for chess pieces
    private static final String[] UNICODE_PIECES = {
            "\u2654", "\u2655", "\u2656", "\u2657", "\u2658", "\u2659",
            "\u265A", "\u265B", "\u265C", "\u265D", "\u265E", "\u265F"
    };

    private static final String[] STRING_PIECES = {
        "wK", "wQ", "wR", "wB", "wK", "wP",
        "bK", "bQ", "bR", "bB", "bK", "bP"
    };

    public static void main(String[] args) {
        // Create the main frame for the chess board
        JFrame frame = new JFrame("Chess Board");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());


        // Create a JPanel that will hold the board
        JPanel boardPanel = new JPanel();
        boardPanel.setLayout(new GridLayout(8, 8));
        boardPanel.setPreferredSize(new Dimension(400, 400));

        // Custom lighter colors for the board
        Color lightColor = new Color(240, 217, 181);
        Color darkColor = new Color(181, 136, 99);

        // Create the chess board squares and pieces
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                // Create a new JLabel with a piece or an empty string
                JLabel square = new JLabel(getPieceUnicode(row, col));
                square.setFont(new Font("Serif", Font.BOLD, 32)); // Make the piece characters bigger
                square.setHorizontalAlignment(JLabel.CENTER);
                square.setVerticalAlignment(JLabel.CENTER);
                square.setName(getPieceString(row,col));
                square.setBorder(BorderFactory.createLineBorder(Color.GRAY));
                // Create a new JPanel square with a border to show the edges
                JPanel panelSquare = new JPanel(new BorderLayout());
                //panelSquare.setBorder(BorderFactory.createLineBorder(Color.GRAY));
                panelSquare.addMouseListener(new MouseAdapter() {    //mouse listener
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        handleMouseClick(square);
                    }
                });

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

    /*
     * handleMouseClick
     * 
     * recieves: JLabel curr to represent the most recent square clicked
     * description: performs actions depending on if a piece was previously selected or not
     *     if a piece was selected, then move the current square to the selected square (if valid)
     *     if a piece wasnt selected, the curr is the piece to be selected
     * returns: nothing
     */
    public static void handleMouseClick(JLabel curr) {
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
                    curr.setText(selected.getText());   //move selected piece to current square
                    curr.setName(selected.getName());
                    selected.setText("");
                    selected.setBorder(BorderFactory.createLineBorder(Color.GRAY));
                    selected = null;
                } else if ( curr.getName() != ""){ //otherwise if the current move is to another piece
                    curr.setText(selected.getText());   //move selected piece to current square
                    curr.setName(selected.getName());
                    selected.setText("");
                    selected.setBorder(BorderFactory.createLineBorder(Color.GRAY));
                    selected = null;
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
}