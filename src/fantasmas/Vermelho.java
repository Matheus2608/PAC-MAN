/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fantasmas;

import static java.lang.Math.abs;
import java.util.ArrayList;
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
    protected Estatico paredeSuperiorEsquerda;
    
    public Vermelho(char IdElemento, int x, int y, PImage imagem, App app){
        super(IdElemento, x,y,imagem, app);
        ArrayList<Estatico> paredes = app.game.getParedes();
        paredeSuperiorEsquerda = paredes.get(0);
        for(Estatico parede: paredes){
            if(parede.getY() < paredeSuperiorEsquerda.getY()){
                paredeSuperiorEsquerda = parede;
            }
            
            else if(parede.getY() == paredeSuperiorEsquerda.getY()){
                if(parede.getX() < paredeSuperiorEsquerda.getX()){
                    paredeSuperiorEsquerda = parede;
                }
            }

        }
        
        System.out.println(paredeSuperiorEsquerda.getY() / 16 + " " + paredeSuperiorEsquerda.getX() / 16);
        
    }
    
    public int calculaDistancia(int y, int x){
        int diffX = abs(this.x / 16 - x / 16);
        int diffY = abs(this.y / 16 - y / 16);
        return diffY + diffX;
    }

    @Override
    public void atualiza() {
        
        // seu alvo é o pacman
        if(estaPerseguindo()){ 
            this.calculaDirecao(this.app.game.getPacMan().getY(), this.app.game.getPacMan().getX());
        }
        // seu alvo é o canto superior esquerdo mais proximo
        else{
            this.calculaDirecao(paredeSuperiorEsquerda.getY(), paredeSuperiorEsquerda.getX());
        }
        
        mover(); 
    }

    
    
    
    @Override
    public void mover(){
        if(this.ultimaTecla >= 37 && this.ultimaTecla <= 40){
            int[] posicao = fakeMover();
            
            this.y = posicao[0];
            this.x = posicao[1];
            
            
            app.image(this.imagem, this.x, this.y);
        }
        
    }
    
    


    @Override
    public void estrategia() {
        
    }

    @Override
    public void desenha(App app) {
        
    }
    
    @Override
    // vai retornar a primeira opcao, senao puder usa a segunda
    public void calculaDirecao(int y, int x){
        int diffX = abs(this.x - x);
        int diffY = abs(this.y - y);
        
        // se a distancia for maior em x, ande primeiro em x
        if(diffX > diffY){
            // se eu estiver na direita do alvo
            if(this.x > x) {
                //System.out.println("esquerda");
                this.ultimaTecla = 37;
            }
            // esquerda do alvo
            else {
                //System.out.println("direita");
                this.ultimaTecla = 39;
            }
            
            if(checaColisao()){
                if(this.y > y) {
                    //System.out.println("pra cima"); 
                    this.ultimaTecla = 38;
                }
                // acima do alvo
                else {
                    //System.out.println("pra baixo");
                    this.ultimaTecla = 40;
                }
            }
        }
        
        
        
        if(diffX <= diffY){
            if(this.y > y) {
                //System.out.println("pra cima"); 
                this.ultimaTecla = 38;
            }
            // acima do alvo
            else {
                //System.out.println("pra baixo");
                this.ultimaTecla = 40;
            }
            
            if(checaColisao()){
                if(this.x > x) {
                    //System.out.println("esquerda");
                    this.ultimaTecla = 37;
                }
                // esquerda do alvo
                else {
                    //System.out.println("direita");
                    this.ultimaTecla = 39;
                } 
            }
        }
        
        if(checaColisao()){
            this.ultimaTecla = 37;
            if(checaColisao())
            this.ultimaTecla = 38;
            if(checaColisao())
            this.ultimaTecla = 39;
            if(checaColisao())
            this.ultimaTecla = 40;
        }
    }
    
}
