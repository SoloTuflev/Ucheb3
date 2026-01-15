package game;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;

// @author Семёнов Даниил 

public class Game extends JFrame { 

    private static Game game_game;
    private static long last_frame_time;
    private static Image office;
    private static Image car;
    private static Image end;
    private static float drop_left = 120;
    private static float drop_top = 120;
    private static float drop_v = 120;
    private static int score = 0;
    private static boolean game_over = false; 

    public static void main(String[] args) throws IOException {
        try {
            office = ImageIO.read(Game.class.getResourceAsStream("office.png"));
            car = ImageIO.read(Game.class.getResourceAsStream("car.png"));
            end = ImageIO.read(Game.class.getResourceAsStream("end.png"));
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        game_game = new Game();
        game_game.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        game_game.setLocation(200, 50);
        game_game.setSize(900, 600);
        game_game.setResizable(false);
        last_frame_time = System.nanoTime();
        GameField game_field = new GameField();
        
        game_field.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (game_over) return; 
                
                int x = e.getX();
                int y = e.getY();
                float drop_right = drop_left + car.getWidth(null); 
                float drop_bottom = drop_top + car.getHeight(null); 
                boolean is_drop = x >= drop_left && x <= drop_right && y >= drop_top && y <= drop_bottom;

                if (is_drop) {
                    drop_top = -100;
                    drop_left = (int) (Math.random() * (game_field.getWidth() - car.getWidth(null)));
                    drop_v += 10;
                    score++;
                    game_game.setTitle("Score: " + score);
                }
            }
        });
        
        game_game.add(game_field);
        game_game.setVisible(true);
        
        
        Thread gameThread = new Thread(() -> {
            while (!game_over) {
                game_field.repaint();
                try {
                    Thread.sleep(16); // 
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        gameThread.start();
    }

    public void onRepaint(Graphics g) {
        long current_time = System.nanoTime();
        float delta_time = (current_time - last_frame_time) * 0.000000001f;
        last_frame_time = current_time;
        
        
        if (!game_over) {
            drop_top = drop_top + drop_v * delta_time;
        }
        
        
        g.drawImage(office, -100, -100, this);
        
        
        g.drawImage(car, (int) drop_left, (int) drop_top, this);
        
        
        if (drop_top > getHeight() && !game_over) {
            game_over = true;
            game_game.setTitle("Game Over! Final Score: " + score);
        }
        
        
        if (game_over) {
            g.drawImage(end, 200, 370, this);
        }
    }

    public static class GameField extends JPanel {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            game_game.onRepaint(g);
        }
    }
}