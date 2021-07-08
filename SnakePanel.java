package Projects.Snake;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;

import javax.swing.*;
import javax.swing.Timer;

public class SnakePanel extends JPanel implements ActionListener{
    
// Width and height of the screen in pixels    
    static final int SCREEN_WIDTH = 700;
    static final int SCREEN_HEIGHT = 700;
// Size of the objects in the screen
    static final int UNIT_SIZE = 25;
// How many objects that can fit the screen
    static final int GAME_UNITS = (SCREEN_WIDTH * SCREEN_HEIGHT) / UNIT_SIZE;
// Input a delay, the higher the value the slower the game
    static final int DELAY = 50;
// Size of the snake and his body
    final int x[] = new int[GAME_UNITS];
    final int y[] = new int[GAME_UNITS];
// Starting number of bodyparts
    int bodyparts = 3;
// Apples eaten
    int applesEaten = 0;
// Position of which the apple randomly appears
    int appleX;
    int appleY;
// Initial movement direction
    char direction = 'R';
// Movement of the snake
    boolean running = false;
// Set a timer
    Timer timer;
// Instance of Random()
    Random random;
    Font optimusPrinceps;



    SnakePanel() {

        random = new Random();
        this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        this.setBackground(Color.black);
        this.setFocusable(true);
        this.addKeyListener(new MyKeyAdapter());
        startGame();

    }

    public void startGame() {

        newApple();
        running = true;
        timer = new Timer(DELAY, this);
        timer.start();

    }

    
    public void paintComponent(Graphics g) {

        super.paintComponent(g);
        draw(g);

    }

    
    public void draw(Graphics g) {

        if (running) {
// draw a grid
            /* for(int i = 0; i < SCREEN_HEIGHT / UNIT_SIZE; i++) {

                g.drawLine(i * UNIT_SIZE, 0, i * UNIT_SIZE, SCREEN_HEIGHT);

                g.drawLine(0, i * UNIT_SIZE, SCREEN_WIDTH, i * UNIT_SIZE);

            } */

// draw the apple        
            g.setColor(Color.red);
            g.fillOval(appleX, appleY, UNIT_SIZE, UNIT_SIZE);

// draw the snake
            for (int i = 0; i < bodyparts; i++) {
                if (i == 0) {
                    g.setColor(Color.green);
                    g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
                }
                else {
                    g.setColor(new Color(45,180,0));
                    g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
                }
            }

            g.setColor(Color.red);
            g.setFont(new Font("OptimusPrinceps", Font.BOLD, 25));
            FontMetrics metrics2 = getFontMetrics(g.getFont());
            g.drawString("Score: " + applesEaten, (SCREEN_WIDTH - metrics2.stringWidth("Score: " + applesEaten))/2, g.getFont().getSize());
    
        }
        else
            gameOver(g);
    }

    public void newApple() {

        appleX = random.nextInt(SCREEN_WIDTH / UNIT_SIZE) * UNIT_SIZE;
        appleY = random.nextInt(SCREEN_HEIGHT / UNIT_SIZE) * UNIT_SIZE;

    }

    public void move() {

        for (int i = bodyparts; i > 0; i--) {
            x[i] = x[i - 1];
            y[i] = y[i - 1];
        }

        switch (direction) {
            case 'U':
                y[0] = y[0] - UNIT_SIZE;
                break;
            case 'D':
                y[0] = y[0] + UNIT_SIZE;
                break;
            case 'L':
                x[0] = x[0] - UNIT_SIZE;
                break;
            case 'R':
                x[0] = x[0] + UNIT_SIZE;
                break;
        }

    }
    
    public void checkApple() {

        if ((x[0] == appleX) && (y[0] == appleY)) {
            bodyparts++;
            applesEaten++;
            newApple();
        }

    }

    
    public void checkCollisions() {
// check if head collides with body
        for (int i = bodyparts; i > 0; i--) {
            if ((x[0] == x[i]) && (y[0] == y[i]))
                running = false;
        }

// check if head collides with left border
        if (x[0] < 0) {
            running = false;
        }
// check if head collides with right border
        if (x[0] > SCREEN_WIDTH - 1) {
            running = false;
        }
// check if head collides with top border
        if (y[0] < 0) {
            running = false;
        }
// check if head collides with bottom border
        if (y[0] > SCREEN_HEIGHT - 1) {
            running = false;
        }
// stops the timer when running = false
        if (!running) {
            timer.stop();
        }
    }

    
    public void gameOver(Graphics g) {
// Score screen
    g.setColor(new Color(240,128,128));
    g.setFont(new Font("OptimusPrinceps", Font.BOLD, 50));
    FontMetrics metrics1 = getFontMetrics(g.getFont());
    g.drawString("Score: " + applesEaten, (SCREEN_WIDTH - metrics1.stringWidth("Score: " + applesEaten))/2, (SCREEN_HEIGHT * 3 / 4));

// Game Over text
    try {

        optimusPrinceps = Font.createFont(Font.TRUETYPE_FONT, new File("OptimusPrinceps.ttf")).deriveFont(75f);
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File("OptimusPrinceps.ttf")));

    } catch (IOException|FontFormatException e) {

    }

        g.setColor(Color.red);
        g.setFont(new Font("OptimusPrinceps", Font.BOLD, 75));
        FontMetrics metrics2 = getFontMetrics(g.getFont());
		g.drawString("TG Adham", (SCREEN_WIDTH - metrics2.stringWidth("TG Adham"))/2, SCREEN_HEIGHT/2);

        
    }

    
    
    @Override
    public void actionPerformed(ActionEvent e) {

        if (running) {
            move();
            checkApple();
            checkCollisions();
        }
        repaint();

    }

    public class MyKeyAdapter extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            if (e.getKeyCode() == KeyEvent.VK_SPACE) {
                if (running = true) {
                    timer.stop();
                }
                if (running = false) {
                    timer.start();
                }
            }

            if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                System.exit(0);
            }
            
            switch (e.getKeyCode()) {
                case KeyEvent.VK_LEFT:
                    if (direction != 'R') {
                        direction = 'L';
                    }
                    break;
                case KeyEvent.VK_RIGHT:
                    if (direction != 'L') {
                        direction = 'R';
                    }
                    break;
                case KeyEvent.VK_UP:
                    if (direction != 'D') {
                        direction = 'U';
                    }
                    break;
                case KeyEvent.VK_DOWN:
                    if (direction != 'U') {
                        direction = 'D';
                    }
                    break;
            }

        }
    }

    

}
