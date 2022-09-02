/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pacman;

import java.util.ArrayList;
import processing.core.PImage;

/**
 *
 * @author matheus
 */
public class PacMan extends Vivo{
    private boolean checarX, checarY;
    
    public PacMan(char idElemento, int x, int y, PImage imagem){
        super(idElemento, x, y, imagem);
    }
    
    @Override
    public boolean checaColisao(){
        if(checaColisaoFantasmas()){
            return true;
        }
        
        if(checaColisaoComSuperPastilha()){
            return true;
        }
        
        if(checaColisaoComParede()){
            return true;
        }
        
        // se nao chocou com nenhum dos anterioes, chocou com uma pastilha, q nao vamos considerar com uma colisao
        
        return false;
    }
    
    public boolean checaColisaoFantasmas(){
        ArrayList<Vivo> fantasmas = getApp().game.fantasmas;
        
        int tecla = getUltimaTecla();
        if(tecla == 37 || tecla == 39){
            this.checarX = true;
            this.checarY = false;
        }
        else{
            this.checarX = false;
            this.checarY = true;
        }
        
        for(Vivo fanstasma : fantasmas){
            if(checarX){
                if(tecla == 37 && this.getX() - 16 == fanstasma.getX()) return true;
                if(tecla == 39 && this.getX() + 16 == fanstasma.getX()) return true;
            }
            
            if(checarY){
                if(tecla == 38 && this.getY() - 16 == fanstasma.getY()) return true;
                if(tecla == 40 && this.getY() + 16 == fanstasma.getY()) return true;
            }
        }
        
        return false;
        
    }
    public boolean checaColisaoComSuperPastilha(){
        ArrayList<Estatico> superPastilha = getApp().game.superPastilhas;
        
        int tecla = getUltimaTecla();
        if(tecla == 37 || tecla == 39){
            this.checarX = true;
            this.checarY = false;
        }
        else{
            this.checarX = false;
            this.checarY = true;
        }
        
        for(Estatico pastilha : superPastilha){
            if(checarX){
                if(tecla == 37 && this.getX() - 16 == pastilha.getX()) return true;
                if(tecla == 39 && this.getX() + 16 == pastilha.getX()) return true;
            }
            
            if(checarY){
                if(tecla == 38 && this.getY() - 16 == pastilha.getY()) return true;
                if(tecla == 40 && this.getY() + 16 == pastilha.getY()) return true;
            }
        }
        
        return false;
    }
    
    public boolean checaColisaoComParede(){
        ArrayList<Estatico> paredes = getApp().game.paredes;
        int tecla = getUltimaTecla();
        if(tecla == 37 || tecla == 39){
            this.checarX = true;
            this.checarY = false;
        }
        else{
            this.checarX = false;
            this.checarY = true;
        }
        
        for(Estatico parede :  paredes){
            if(checarX){
                if(tecla == 37 && this.getX() - 16 == parede.getX()) return true;
                if(tecla == 39 && this.getX() + 16 == parede.getX()) return true;
            }
            
            if(checarY){
                if(tecla == 38 && this.getY() - 16 == parede.getY()) return true;
                if(tecla == 40 && this.getY() + 16 == parede.getY()) return true;
            }
        }
        
        return false;
    }

    @Override
    public void atualiza(){
        
    }
        
    
    @Override
    public void mover(){
        
    }
    
    public void desenhar() {
        this.getApp().image(getImagem(), getX(), getY());
    }
}
