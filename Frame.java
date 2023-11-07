import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Frame {

    private static final int ROWS = 8;
    private static final int COLS = 8;
    private final JPanel[][] gameBoardSquares = new JPanel[ROWS][COLS];
    private JPanel selectedPiece = null;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Frame().createAndShowGUI());
    }

    private void createAndShowGUI() {
        JFrame frame = new JFrame("Chess");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new GridLayout(ROWS, COLS));
        frame.setSize(500, 500);


        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLS; j++) {
                JPanel panel = new JPanel();
                panel.setBackground((i + j) % 2 == 0 ? Color.LIGHT_GRAY : Color.WHITE); //background panel
                panel.setBorder(BorderFactory.createLineBorder(Color.BLACK));           //border panel to have buttons visible
                panel.addMouseListener(new MouseAdapter() {    //mouse listener
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        handleMouseClick(panel);
                    }
                });
                gameBoardSquares[i][j] = panel;    //add panel to gameBoardSquares array
                frame.add(panel);                  //add panel to frame
            }
        }

        // Set up the game pieces in different colors
        setupGamePieces();

        frame.pack();    //align
        frame.setVisible(true);
    }


    private void setupGamePieces() {
        for( int i = 0 ; i < 8; i++ ){
         gameBoardSquares[6][i].setBackground(Color.BLUE);
         gameBoardSquares[7][i].setBackground(Color.BLUE);
         gameBoardSquares[1][i].setBackground(Color.RED);
         gameBoardSquares[0][i].setBackground(Color.RED); 
        }
        


    }

    
    private void handleMouseClick(JPanel clickedPanel) {
        Color background = clickedPanel.getBackground();
        // If we haven't selected a piece yet, check if we're clicking on one of the pieces
        if (selectedPiece == null) {
            if (background == Color.BLUE || background == Color.RED) {
                selectedPiece = clickedPanel;
                clickedPanel.setBorder(BorderFactory.createLineBorder(Color.YELLOW));  // Highlight the selected piece
            }
        } else {
            // Move selected piece to the new location if it's an empty space
            if (background == Color.LIGHT_GRAY || background == Color.WHITE) {
                clickedPanel.setBackground(selectedPiece.getBackground());  // Move piece to the new spot
                selectedPiece.setBackground(selectedPiece.getBackground() == Color.LIGHT_GRAY ? Color.LIGHT_GRAY : Color.WHITE);  // Set the color of the old spot back to default
                selectedPiece.setBorder(BorderFactory.createLineBorder(Color.BLACK));  // Remove highlight from the selected piece
                selectedPiece = null;  // Clear the current selection after move
            }
        }
    }
}