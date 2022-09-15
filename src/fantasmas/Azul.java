/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fantasmas;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import pacman.App;
import pacman.Fantasma;
import pacman.Vivo;
import processing.core.PImage;
/**
 *
 * @author matheus
 */
public class Azul extends Fantasma implements Estrategia{

    public Azul(char IdElemento, int x, int y, PImage imagem, App app){
        super(IdElemento, x,y,imagem, app);
    }
    
    @Override
    public void movimentar(Fantasma classe){}
    
    public int[] posicaoAlvo(){
        
        int[] resposta = new int[2];
        
        int ultimaTecla = app.game.pacMan.ultimaTecla;
        int pacX = app.game.pacMan.getX();
        int pacY = app.game.pacMan.getY();
        
        Vivo fantasmaVermelho = null;
        for(Vivo fantasma : this.fantasmas){
            if(fantasma.getIdElemento() == 'c'){
                fantasmaVermelho = fantasma;
                break;
            }
        }
        
        if(ultimaTecla == 37) pacX -= 32; 
        else if(ultimaTecla == 38) pacY -= 32;
        else if(ultimaTecla == 39) pacX += 32;
        else pacY += 32;  
                
        resposta[0] = fantasmaVermelho.getX() + 2 * (pacX - fantasmaVermelho.getX());
        resposta[1] = fantasmaVermelho.getY() + 2 * (pacY - fantasmaVermelho.getY());
        
        if(resposta[0] < 0) resposta[0] = 0;
        else if(resposta[0] > 448) resposta[0] = 448;
        
        if(resposta[1] < 0) resposta[1] = 0;
        else if(resposta[1] > 576) resposta[1] = 576;
        
        return resposta;
    }
    
    @Override
    public void atualiza() {
        if(app.game.perseguindo){
            int[] res = posicaoAlvo();
            this.calculaDirecao(res[0], res[1]);
        }
        else{
            this.calculaDirecao(app.game.paredeInferiorDireita.getX(), app.game.paredeInferiorDireita.getY());
        }
        
        mover(); 
    }

    
    public void mover() {
        if(this.ultimaTecla >= 37 && this.ultimaTecla <= 40){
            int[] posicao = fakeMover(ultimaTecla);
            this.x = posicao[0];
            this.y = posicao[1];
        }
        
        app.image(this.imagem, this.x, this.y);
    }
}
