package beatEmUp;

import java.awt.*;

public class Jugador {
    private int x, y;
    private int ancho = 50;
    private int altura = 100;
    private int vida = 100;
    
    private boolean estaSaltando = false;
    private int velocidadSalto = 0;
    private final int GRAVEDAD = 1;
    private final int JUMP_FORCE = -15;
    private final int GROUND_Y = 450;
    
    private boolean isAttacking = false;
    private int attackTimer = 0;
    
    private int dx = 0;
    private final int SPEED = 5;
    
    public Jugador(int x, int y) {
        this.x = x;
        this.y = y;
    }
    
    public void actualizar() {
        // Movimiento horizontal
        x += dx;
        
        // Evitar que el jugador salga de la pantalla
        if (x < 0) x = 0;
        if (x > 800 - ancho) x = 800 - ancho;
        
        // Manejo de salto
        if (estaSaltando) {
            y += velocidadSalto;
            velocidadSalto += GRAVEDAD;
            
            // Comprobar si ha tocado el suelo
            if (y >= GROUND_Y - altura) {
                y = GROUND_Y - altura;
                estaSaltando = false;
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
        g.fillRect(x, y, ancho, altura);
        
        // Dibujar cabeza
        g.setColor(Color.CYAN);
        g.fillOval(x + 10, y - 25, 30, 30);
        
        // Si está atacando, dibujar puño
        if (isAttacking) {
            g.setColor(Color.RED);
            g.fillRect(x + ancho, y + 30, 30, 10);
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
        if (!estaSaltando) {
            estaSaltando = true;
            velocidadSalto = JUMP_FORCE;
        }
    }
    
    public void attack() {
        if (!isAttacking) {
            isAttacking = true;
            attackTimer = 0;
        }
    }
    
    public void takeDamage(int damage) {
        vida -= damage;
        if (vida < 0) vida = 0;
    }
    
    public int getX() {
        return x;
    }
    
    public int getY() {
        return y;
    }
    
    public int getHealth() {
        return vida;
    }
    
    public boolean estaAtacando() {
        return isAttacking;
    }
    
    public int getAttackTimer() {
        return attackTimer;
    }
}