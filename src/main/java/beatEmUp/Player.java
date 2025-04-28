package beatEmUp;

import java.awt.*;

public class Player {
    private int x, y;
    private int width = 50;
    private int height = 100;
    private int health = 100;
    
    private boolean isJumping = false;
    private int jumpSpeed = 0;
    private final int GRAVITY = 1;
    private final int JUMP_FORCE = -15;
    private final int GROUND_Y = 450;
    
    private boolean isAttacking = false;
    private int attackTimer = 0;
    
    private int dx = 0;
    private final int SPEED = 5;
    
    public Player(int x, int y) {
        this.x = x;
        this.y = y;
    }
    
    public void update() {
        // Movimiento horizontal
        x += dx;
        
        // Evitar que el jugador salga de la pantalla
        if (x < 0) x = 0;
        if (x > 800 - width) x = 800 - width;
        
        // Manejo de salto
        if (isJumping) {
            y += jumpSpeed;
            jumpSpeed += GRAVITY;
            
            // Comprobar si ha tocado el suelo
            if (y >= GROUND_Y - height) {
                y = GROUND_Y - height;
                isJumping = false;
            }
        }
        
        // Manejo de ataque
        if (isAttacking) {
            attackTimer++;
            if (attackTimer > 20) {  // Duración del ataque
                isAttacking = false;
                attackTimer = 0;
            }
        }
    }
    
    public void draw(Graphics g) {
        // Dibujar cuerpo
        g.setColor(Color.BLUE);
        g.fillRect(x, y, width, height);
        
        // Dibujar cabeza
        g.setColor(Color.CYAN);
        g.fillOval(x + 10, y - 25, 30, 30);
        
        // Si está atacando, dibujar puño
        if (isAttacking) {
            g.setColor(Color.RED);
            g.fillRect(x + width, y + 30, 30, 10);
        }
    }
    
    public void moveLeft() {
        dx = -SPEED;
    }
    
    public void moveRight() {
        dx = SPEED;
    }
    
    public void stopMoving() {
        dx = 0;
    }
    
    public void jump() {
        if (!isJumping) {
            isJumping = true;
            jumpSpeed = JUMP_FORCE;
        }
    }
    
    public void attack() {
        if (!isAttacking) {
            isAttacking = true;
            attackTimer = 0;
        }
    }
    
    public void takeDamage(int damage) {
        health -= damage;
        if (health < 0) health = 0;
    }
    
    public int getX() {
        return x;
    }
    
    public int getY() {
        return y;
    }
    
    public int getHealth() {
        return health;
    }
    
    public boolean isAttacking() {
        return isAttacking;
    }
    
    public int getAttackTimer() {
        return attackTimer;
    }
}