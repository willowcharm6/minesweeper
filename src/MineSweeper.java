import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;


public class MineSweeper extends JPanel {
    private Square[][] board;

    public static final int SIZE = 40;

    public int r, c;
    public int clicks;
    public static int mines;
    public boolean gamePlay = false;

    public MineSweeper(int width, int height) {
        setSize(width, height);

        board = new Square[15][15];

        for (int r = 0; r < board.length; r++) {
            for (int c = 0; c < board[0].length; c++) {
                if (Math.random() < 0.1) {
                    board[r][c] = new Square(true, r, c, board);
                    mines ++;
                } else
                    board[r][c] = new Square(false, r, c, board);
            }
        }
        //TODO 1. Go to each index in board and assign a new Square object
        //     Each square should have a 15% chance of being a mine
        //         ALT: have a fixed number of squares contain mines.


        // TODO 4. Here is a good spot to calc each Square's neighborMines
        //  (after all squares are initialized-not in the above loops
        for (int r = 0; r < board.length; r++) {
            for (int c = 0; c < board[0].length; c++) {
                board[r][c].calcNeighborMines();
            }

        }
        setupMouseListener();
    }


    public void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        // TODO 2. Go to each square in board and tell it to draw.
        for (int r = 0; r < board.length; r++) {
            for (int c = 0; c < board[0].length; c++) {
                board[r][c].draw(g2);
            }
        }
        if (!gamePlay) {
            g2.setFont(new Font("SansSerif", Font.PLAIN, 33));
            g2.drawString("Click to start, right click to set", 20, 300);
            g2.drawString("flags, and try not to click on a mine!", 20, 350);
        }

        g2.setColor(Color.white);
        g2.setFont(new Font("SansSerif", Font.PLAIN, 20));
        g2.drawString("Number of Flags:" + mines, 400, 50);
        if (!gamePlay && clicks > 1){
            g2.setFont(new Font("SansSerif", Font.PLAIN, 50));
            g2.drawString("Game Over!", 180, 300);
        }
        if (mines == 0){
            gamePlay = false;
            g2.setFont(new Font("SansSerif", Font.PLAIN, 50));
            g2.drawString("You Win!", 180, 300);
        }

    }

    public void setupMouseListener(){
        addMouseListener(new MouseListener() {
            @Override
            public void mousePressed(MouseEvent e) {
                clicks ++;
                int x = e.getX();
                int y = e.getY();

                r = y / SIZE;
                c = x / SIZE;
                gamePlay = true;
                if (gamePlay) {
                    if (board[r][c].isMine() && clicks < 2) {
                        board[r][c].makeNotMine(r, c);
                    }
                    if (e.getButton() == MouseEvent.BUTTON1) {
                        //regular click!
                        //reveal the cell
                        board[r][c].click();
                        if (board[r][c].flag()) {
                            mines++;
                        }
                        if (board[r][c].isMine()){
                            gamePlay = false;
                        }
                    } else if (e.getButton() == MouseEvent.BUTTON3) {  //2 finger or ctrl click!
                        //flag the cell
                        board[r][c].flag();
                    }
                    repaint();
                }
            }
            @Override
            public void mouseClicked(MouseEvent e) {}
            @Override
            public void mouseReleased(MouseEvent e) {}
            @Override
            public void mouseEntered(MouseEvent e) {}
            @Override
            public void mouseExited(MouseEvent e) {}
        });

        addMouseMotionListener(new MouseMotionListener() {
            @Override
            public void mouseDragged(MouseEvent e) {

            }

            @Override
            public void mouseMoved(MouseEvent e) {
//                mouseX = e.getX();
//                mouseY = e.getY();
            }
        });
    }

    //sets ups the panel and frame.  Probably not much to modify here.
    public static void main(String[] args) {
        JFrame window = new JFrame("Minesweeper");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setBounds(0, 0, 600, 600 + 22); //(x, y, w, h) 22 due to title bar.

        MineSweeper panel = new MineSweeper(600, 600);

        panel.setFocusable(true);
        panel.grabFocus();

        window.add(panel);
        window.setVisible(true);
        window.setResizable(false);
    }

}
