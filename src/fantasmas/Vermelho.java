/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fantasmas;

import static java.lang.Math.abs;
import pacman.App;
import pacman.Estatico;
import pacman.Fantasma;
import pacman.Vivo;
import processing.core.PImage;

/**
 *
 * @author matheus
 */
public class Vermelho extends Fantasma{

    public Vermelho(char IdElemento, int x, int y, PImage imagem, App app){
        super(IdElemento, x,y,imagem, app);
    }


    @Override
    public void atualiza() {
        
        // seu alvo é o pacman
        if(estaPerseguindo()){ 
            System.out.println("esta no modo de perseguir");
            this.ultimaTecla = this.calculaDirecao(this.app.game.getPacMan().getY(), this.app.game.getPacMan().getX());
        }
        // seu alvo é um canto superior esquerdo mais proximo
        else{
            System.out.println("nao esta no modo de perseguir");
            for(Estatico parede : app.game.getParedes()){
                // se for essa parede superior esquerda
                if(parede.getIdElemento() - '0' == 6){
                    this.ultimaTecla = this.calculaDirecao(parede.getY(), parede.getX());
                }
        }
            
        mover();
                 
            
        }
    }

    
    
    
    @Override
    public void mover(){
        if(this.ultimaTecla >= 37 && this.ultimaTecla <= 40){
            int[] posicao = fakeMover();
            if(!checaColisao()){
                this.y = posicao[0];
                this.x = posicao[1];
            }
            
            app.image(this.imagem, this.x, this.y);
        }
        
    }
    
    


    @Override
    public void estrategia() {
        
    }

    @Override
    public void draw(App app) {
        
    }
    
    @Override
    public int calculaDirecao(int y, int x){
        y /= 16;
        x /= 16;
        
        int meuX = getX() / 16;
        int meuY = getY() / 16;
        
        int diffX = abs(meuX - x);
        int diffY = abs(meuY - y);
        
        // se a distancia for maior em x, ande primeiro em x
        if(diffX > diffY){
            // se eu estiver na direita do alvo
            if(meuX > x) return 37;
            // esquerda do alvo
            else return 39;
        }
        
        else {
            // se eu estiver abaixo do alvo
            if(meuY > y) return 38;
            // acima do alvo
            else return 40;
        }
    }
    
}
