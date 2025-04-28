package beatEmUp;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class BeatEmUpGame extends JPanel implements ActionListener, KeyListener {
    // Dimensiones de la ventana
    private static final int WIDTH = 800;
    private static final int HEIGHT = 600;
    private static final int GROUND_Y = 450;
    
    // Timer para actualizar el juego
    private Timer timer;
    private final int DELAY = 10;
    
    // Jugador
    private Player player;
    
    // Enemigos
    private ArrayList<Enemy> enemies;
    private int enemySpawnCounter = 0;
    
    // Estado del juego
    private boolean gameOver = false;
    private int score = 0;
    
    public BeatEmUpGame() {
        initGame();
    }
    
    private void initGame() {
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setBackground(Color.BLACK);
        setFocusable(true);
        addKeyListener(this);
        
        // Inicializar jugador
        player = new Player(100, GROUND_Y - 100);
        
        // Inicializar enemigos
        enemies = new ArrayList<>();
        
        // Iniciar timer
        timer = new Timer(DELAY, this);
        timer.start();
    }
    
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        // Dibujar escenario
        drawGame(g);
        
        // Información de depuración y puntuación
        drawHUD(g);
        
        Toolkit.getDefaultToolkit().sync();
    }
    
    private void drawGame(Graphics g) {
        // Dibujar suelo
        g.setColor(Color.DARK_GRAY);
        g.fillRect(0, GROUND_Y, WIDTH, HEIGHT - GROUND_Y);
        
        // Dibujar jugador si está vivo
        if (!gameOver) {
            player.draw(g);
        }
        
        // Dibujar enemigos
        for (Enemy enemy : enemies) {
            enemy.draw(g);
        }
        
        // Mostrar mensaje de Game Over
        if (gameOver) {
            g.setColor(Color.RED);
            g.setFont(new Font("Arial", Font.BOLD, 50));
            g.drawString("GAME OVER", WIDTH / 2 - 150, HEIGHT / 2);
            g.setFont(new Font("Arial", Font.BOLD, 30));
            g.drawString("Puntuación final: " + score, WIDTH / 2 - 120, HEIGHT / 2 + 50);
            g.drawString("Presiona R para reiniciar", WIDTH / 2 - 160, HEIGHT / 2 + 100);
        }
    }
    
    private void drawHUD(Graphics g) {
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 20));
        g.drawString("Salud: " + player.getHealth(), 20, 30);
        g.drawString("Puntuación: " + score, 20, 60);
    }
    
    private void update() {
        if (gameOver) {
            return;
        }
        
        // Actualizar jugador
        player.update();
        
        // Generar enemigos
        enemySpawnCounter++;
        if (enemySpawnCounter >= 200) {  // Cada ~2 segundos
            int spawnX = Math.random() < 0.5 ? -50 : WIDTH + 50;  // Izquierda o derecha
            enemies.add(new Enemy(spawnX, GROUND_Y - 100));
            enemySpawnCounter = 0;
        }
        
        // Actualizar enemigos y comprobar colisiones
        for (int i = 0; i < enemies.size(); i++) {
            Enemy enemy = enemies.get(i);
            enemy.update();
            
            // Perseguir al jugador
            if (enemy.getX() < player.getX()) {
                enemy.moveRight();
            } else {
                enemy.moveLeft();
            }
            
            // Comprobar si el enemigo debe atacar
            if (Math.abs(enemy.getX() - player.getX()) < 70) {
                enemy.attack();
                
                // Si el ataque conecta y el jugador no está atacando
                if (enemy.isAttacking() && !player.isAttacking() && enemy.getAttackTimer() < 5) {
                    player.takeDamage(10);
                    if (player.getHealth() <= 0) {
                        gameOver = true;
                    }
                }
            }
            
            // Si el jugador está atacando y golpea al enemigo
            if (player.isAttacking() && 
                Math.abs(player.getX() - enemy.getX()) < 70 &&
                player.getAttackTimer() < 5) {
                enemy.takeDamage(25);
                
                // Quitar enemigo si muere
                if (enemy.getHealth() <= 0) {
                    enemies.remove(i);
                    score += 100;
                    i--;  // Ajustar índice después de eliminar
                }
            }
        }
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        update();
        repaint();
    }
    
    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        
        if (gameOver) {
            if (key == KeyEvent.VK_R) {
                // Reiniciar juego
                player = new Player(100, GROUND_Y - 100);
                enemies.clear();
                score = 0;
                gameOver = false;
            }
            return;
        }
        
        switch (key) {
            case KeyEvent.VK_LEFT:
                player.moveLeft();
                break;
            case KeyEvent.VK_RIGHT:
                player.moveRight();
                break;
            case KeyEvent.VK_UP:
                player.jump();
                break;
            case KeyEvent.VK_SPACE:
                player.attack();
                break;
        }
    }
    
    @Override
    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();
        
        if (key == KeyEvent.VK_LEFT || key == KeyEvent.VK_RIGHT) {
            player.stopMoving();
        }
    }
    
    @Override
    public void keyTyped(KeyEvent e) {
        // No se utiliza pero es necesario implementarlo
    }
    
    public static void main(String[] args) {
        JFrame frame = new JFrame("Beat Em Up Game");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.add(new BeatEmUpGame());
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
