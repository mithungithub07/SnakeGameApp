import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.*;

public class SnakeGame extends JPanel implements ActionListener, KeyListener {
    private class Title {
        int x;
        int y;

        Title(int x, int y) {
            this.x = x;
            this.y = y;
        }

    }

    int boardWidth;
    int boardHeight;
    int titleSize = 25;
    // Snake
    Title snakeHead;
    ArrayList<Title> snakeBody;

    // Food
    Title food;
    Random random;

    // game logic
    Timer gameLoop;
    int velocityX;
    int velocityY;
    boolean gameOver = false;

    SnakeGame(int boardWidth, int boardHeight) {
        this.boardWidth = boardWidth;
        this.boardHeight = boardHeight;
        setPreferredSize(new Dimension(this.boardWidth, this.boardHeight));
        setBackground(Color.black);
        addKeyListener(this);
        setFocusable(true);

        snakeHead = new Title(5, 5);
        snakeBody = new ArrayList<Title>();

        food = new Title(10, 10);
        random = new Random();
        placeFood();

        velocityX = 1;
        velocityY = 0;

        gameLoop = new Timer(100, this);
        gameLoop.start();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
    }

    public void draw(Graphics g) {
        /*
         * for (int i = 0; i < boardWidth / titleSize; i++) {
         * 
         * g.drawLine(i * titleSize, 0, i * titleSize, boardHeight);
         * g.drawLine(0, i * titleSize, boardWidth, i * titleSize);
         * 
         * }
         */

        // food
        g.setColor(Color.red);
        // g.fillRect(food.x * titleSize, food.y * titleSize, titleSize, titleSize);
        g.fill3DRect(food.x * titleSize, food.y * titleSize, titleSize, titleSize, true);

        // snake Head
        g.setColor(Color.green);
        // g.fillRect(snakeHead.x * titleSize, snakeHead.y * titleSize, titleSize,
        // titleSize);
        g.fill3DRect(snakeHead.x * titleSize, snakeHead.y * titleSize, titleSize, titleSize, true);

        // snake Body
        for (int i = 0; i < snakeBody.size(); i++) {
            Title snakePart = snakeBody.get(i);
            g.fill3DRect(snakePart.x * titleSize, snakePart.y * titleSize, titleSize, titleSize, true);

        }
        // Score
        g.setFont(new Font("Arial", Font.PLAIN, 16));
        if (gameOver) {
            g.setColor(Color.red);
            g.drawString("Game Over : " + String.valueOf(snakeBody.size()), titleSize - 16, titleSize);
        } else {
            g.drawString("Score: " + String.valueOf(snakeBody.size()), titleSize - 16, titleSize);
        }
    }

    public void placeFood() {
        food.x = random.nextInt(boardWidth / titleSize);
        food.y = random.nextInt(boardHeight / titleSize);

    }

    public boolean collision(Title title1, Title title2) {
        return title1.x == title2.x && title1.y == title2.y;
    }

    public void move() {
        // eat food
        if (collision(snakeHead, food)) {
            snakeBody.add(new Title(food.x, food.y));
            placeFood();
        }
        // snake body
        for (int i = snakeBody.size() - 1; i >= 0; i--) {
            Title snakePart = snakeBody.get(i);
            if (i == 0) {
                snakePart.x = snakeHead.x;
                snakePart.y = snakeHead.y;
            } else {
                Title prevSnakePart = snakeBody.get(i - 1);
                snakePart.x = prevSnakePart.x;
                snakePart.y = prevSnakePart.y;

            }

        }
        // Snake Head
        snakeHead.x += velocityX;
        snakeHead.y += velocityY;

        // game over conditions
        for (int i = 0; i < snakeBody.size(); i++) {
            Title snakePart = snakeBody.get(i);
            // collide with the snake head
            if (collision(snakeHead, snakePart)) {
                gameOver = true;
            }
        }

        if (snakeHead.x * titleSize < 0 || snakeHead.x * titleSize > boardWidth
                || snakeHead.y * titleSize < 0 || snakeHead.y * titleSize > boardHeight) {
            gameOver = true;
        }

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        move();
        repaint();
        if (gameOver) {
            gameLoop.stop();
        }

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_UP && velocityY != 1) {
            velocityX = 0;
            velocityY = -1;
        } else if (e.getKeyCode() == KeyEvent.VK_DOWN && velocityY != -1) {
            velocityX = 0;
            velocityY = 1;
        } else if (e.getKeyCode() == KeyEvent.VK_LEFT && velocityX != 1) {
            velocityX = -1;
            velocityY = 0;
        } else if (e.getKeyCode() == KeyEvent.VK_RIGHT && velocityX != -1) {
            velocityX = 1;
            velocityY = 0;
        }

    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

}
