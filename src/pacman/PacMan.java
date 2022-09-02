/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pacman;

import processing.core.PImage;

/**
 *
 * @author matheus
 */
public class PacMan extends Vivo{
    
    public PacMan(int x, int y, PImage imagem){
        super(x,y,imagem);
    }
    
    @Override
    public boolean checaColisao(){
        return false;
    }
    
    public boolean checaColisaoComSuperPastilha(){
        return false;
    }

    @Override
    public void atualiza(){
        
    }
        
    
    @Override
    public void mover(){
        
    }
    
    public void draw(App app) {
        
    }
}
