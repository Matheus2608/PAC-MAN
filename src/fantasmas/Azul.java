/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fantasmas;

import pacman.App;
import pacman.Fantasma;
import processing.core.PImage;
/**
 *
 * @author matheus
 */
public class Azul extends Fantasma{

    public Azul(char IdElemento, int x, int y, PImage imagem, App app){
        super(IdElemento, x,y,imagem, app);
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
    public void estrategia() {
        
    }

    @Override
    public void desenha(App app) {
        
    }
    
    
    
    @Override
    public void calculaDirecao(int y, int x){
        
    }
}
