/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fantasmas;

import pacman.App;
import pacman.Fantasma;
import pacman.Vivo;
import processing.core.PImage;

/**
 *
 * @author matheus
 */
public class Vermelho extends Vivo implements Fantasma{

    public Vermelho(char IdElemento, int x, int y, PImage imagem){
        super(IdElemento, x,y,imagem);
    }

    @Override
    public boolean checaColisao() {
        return false;
    }

    @Override
    public void atualiza() {
        
    }

    @Override
    public void mover() {
        
    }
    
    @Override
    public void mover(int y, int x){}

    @Override
    public void estrategia() {
        
    }

    @Override
    public void draw(App app) {
        
    }
    
}
