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
public class Laranja extends Fantasma{

    public Laranja(char idElemento, int x, int y, PImage imagem, App app){
        super(idElemento, x,y,imagem, app);
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
    
    @Override
    public boolean estaPerseguindo() {
        return false;
    }
}
