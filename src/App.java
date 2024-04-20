import javax.swing.*;
public class App {
    public static void main(String[] args)
    {
        int boardWidth = 600;
        int boardHeight = boardWidth;            
        JFrame frame = new JFrame();
        frame.setTitle("Snake");
        frame.setSize(boardWidth,boardHeight);
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        SnakeGame snakeGame = new SnakeGame(boardWidth, boardHeight);
        frame.add(snakeGame);
        frame.pack();
        snakeGame.requestFocus();

    }
}
