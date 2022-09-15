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
import pacman.PacMan;
import processing.core.PImage;

/**
 *
 * @author matheus
 */
public class Rosa extends Fantasma implements Estrategia{
    public Rosa(char IdElemento, int x, int y, PImage imagem, App app){
        super(IdElemento, x,y,imagem, app);
    }
    
    @Override
    public void movimentar(Fantasma classe){}
    
    public int[] posicaoAlvoPacMan(){
        PacMan pacMan = this.app.game.pacMan;
        int direcaoPacMan = pacMan.ultimaTecla;
        int X = 0, Y = 0;
        
        if(direcaoPacMan == 37) X = pacMan.getX() + (4 * app.game.velocidade);
        
        else if(direcaoPacMan == 38) Y = pacMan.getY() + (4 * app.game.velocidade);
        
        else if(direcaoPacMan == 39) X = pacMan.getX() - (4 * app.game.velocidade);
        
        else if(direcaoPacMan == 40) Y = pacMan.getY() - (4 * app.game.velocidade);
        
        if(X < 0) X = 0;
        else if(X > 448) X = 448;
        
        if(Y < 0) Y = 0;
        else if(Y > 576) Y = 576;
        
        int[] resposta = {X,Y};
        return resposta;   
    }
    
    
    @Override
    public void atualiza() {
        // seu alvo é o pacman
        if(this.app.game.perseguindo){
            //System.out.println("perseguindo");
            int[] posicaoAlvoPacMan = posicaoAlvoPacMan();
            this.calculaDirecao(posicaoAlvoPacMan[0], posicaoAlvoPacMan[1]);
        }
        // seu alvo é o canto superior esquerdo mais proximo
        else{
            //System.out.println("disperso");
            this.calculaDirecao(app.game.paredeSuperiorDireita.getX(), app.game.paredeSuperiorDireita.getY());
        }
        
        mover();
        
    }


}
