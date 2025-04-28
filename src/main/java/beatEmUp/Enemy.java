/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package beatEmUp;

import java.awt.Color;
import java.awt.Graphics;

/**
 *
 * @author Ricardo
 */
public class Enemy {
    private int x, y;
    private int width = 50;
    private int height = 100;
    private int health = 50;
    
    private boolean isAttacking = false;
    private int attackTimer = 0;
    
    private int dx = 0;
    private final int SPEED = 2;
    
    public Enemy (int x, int y) {
        this.x = x;
        this.y = y;
    }
    
    public void update() {
        // Movimiento horizontal
        x += dx;
        
        // Manejo de ataque
        if (isAttacking) {
            attackTimer++;
            if (attackTimer > 30) {  // Duración del ataque
                isAttacking = false;
                attackTimer = 0;
            }
        }
    }
    
    public void draw(Graphics g) {
        // Dibujar cuerpo
        g.setColor(Color.RED);
        g.fillRect(x, y, width, height);
        
        // Dibujar cabeza
        g.setColor(Color.ORANGE);
        g.fillOval(x + 10, y - 25, 30, 30);
        
        // Si está atacando, dibujar puño
        if (isAttacking) {
            g.setColor(Color.YELLOW);
            int punchX = (x > 400) ? x - 30 : x + width; // Dirección del puño según la posición
            g.fillRect(punchX, y + 30, 30, 10);
        }
        
        // Barra de salud
        g.setColor(Color.GREEN);
        g.fillRect(x, y - 40, health, 5);
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