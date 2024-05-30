import java.awt.*;

// TODO Charmaine Guo

public class Square {

    private boolean isMine, isRevealed, isFlagged;  // Is this a mine?  Is this revealed?
    private int numNeighborMines;  // how many mines are around this square
    private int r, c;  // what index this Square is in board
    private Square[][] board; // the entire board

    public Square(boolean isMine, int r, int c, Square[][] board) {
        this.isMine = isMine;
        this.r = r;
        this.c = c;
        this.isRevealed = false;
        this.board = board;
        this.numNeighborMines = 0;

        isFlagged = false;
    }

    // TODO 3: Counts how many Squares around this have mines,
    //  updates the numNeighborMines instance field
    public void calcNeighborMines(){
        for (int numr = r - 1; numr < r + 2; numr++) {
            for (int numc = c - 1; numc < c + 2; numc++) {
                if (isInBounds(numr, numc) && board[numr][numc].isMine && !board[r][c].isMine) {
                    numNeighborMines++;
                }
            }
        }
    }
    public void draw(Graphics2D g2){
        int size = MineSweeper.SIZE;
        if (isRevealed) {
            if(isMine) {
                g2.setColor(Color.RED);
                g2.fillRect(c * size, r * size, size, size);
            }else{
                g2.setColor(Color.lightGray);
                g2.fillRect(c * size, r * size, size, size);
                g2.setColor(Color.BLACK);
                g2.drawString("" + numNeighborMines, c * size+size/3, r * size + size/2);
            }

        }else if(isFlagged){
            g2.setColor(Color.GRAY);
            g2.fillRect(c*size,r*size,size,size);
            g2.setColor(Color.red);
            int[] flagX = {c*size, c*size + size, c*size};
            int[] flagY = {r*size, r*size + size/2, r*size + size};
            g2.fillPolygon(flagX, flagY, 3);
        }else{
            g2.setColor(Color.GRAY);
            g2.fillRect(c * size, r * size, size, size);
        }
        g2.setColor(Color.BLACK);
        g2.drawRect(c * size, r * size, size, size);
    }

    public boolean flag(){
        if(isFlagged) {
            isFlagged = false;
            MineSweeper.mines ++;
        }
        else {
            isFlagged = true;
            MineSweeper.mines --;
        }
        return isFlagged;
    }




    public boolean isInBounds(int row, int col){
        return row < board.length && row > -1 && col < board[0].length && col > -1;
    }

    //TODO 5: revealCell method.
    public void revealCell(){
        if (!isRevealed){
            isRevealed = true;
            if (numNeighborMines == 0 && !isMine) {
                if (isInBounds(r - 1, c - 1))
                    board[r - 1][c - 1].revealCell();

                if (isInBounds(r-1, c))
                    board[r-1][c].revealCell();

                if (isInBounds(r-1, c+1))
                    board[r-1][c+1].revealCell();

                if (isInBounds(r, c-1))
                    board[r][c-1].revealCell();

                if (isInBounds(r, c+1))
                    board[r][c+1].revealCell();

                if (isInBounds(r+1, c-1))
                    board[r+1][c-1].revealCell();

                if (isInBounds(r+1, c))
                    board[r+1][c].revealCell();

                if (isInBounds(r+1, c+1))
                    board[r+1][c+1].revealCell();
            }
        }
    }
    public void click(){
        //TODO 6. modify to call revealCell
        revealCell();
    }

    public boolean isMine() {
        return isMine;
    }
    public void makeNotMine(int r, int c){
        board[r][c].isMine = false;
    }

    public boolean isRevealed() {
        return isRevealed;
    }

    public int getNumNeighborMines() {
        return numNeighborMines;
    }
}