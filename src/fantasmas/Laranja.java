/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fantasmas;

import static java.lang.Math.abs;
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
public class Laranja extends Fantasma implements Estrategia{ 
    public Laranja(char idElemento, int x, int y, PImage imagem, App app){
        super(idElemento, x,y,imagem, app);
    }
    
    public int distanciaRetilineaAtePacMan(){
        int diffX = abs(this.x - this.app.game.pacMan.getX());
        int diffY = abs(this.y - this.app.game.pacMan.getY());
        
        return diffX + diffY;
    }
    
    @Override
    public void movimentar(Fantasma classe){}
    
    public int[] posicaoAlvo(){
        PacMan pacMan = this.app.game.pacMan;
        
        if(this.app.game.perseguindo){
            if(distanciaRetilineaAtePacMan() / 16 > 8){
                int[] resposta = {pacMan.getX(), pacMan.getY()};
                return resposta;
            }
            else{
                int[] resposta = {app.game.paredeInferiorEsquerda.getX(), app.game.paredeInferiorEsquerda.getY()};
                return resposta;
            }
            
            
        }
        
        else{
            int[] resposta = {app.game.paredeInferiorEsquerda.getX(), app.game.paredeInferiorEsquerda.getY()};
            return resposta;
        } 
    }

    @Override
    public void atualiza() {
        int[] posicaoAlvo = posicaoAlvo();
        this.calculaDirecao(posicaoAlvo[0], posicaoAlvo[1]);

        mover();
    }

    public int getIndModoAtual() {
        return indModoAtual;
    }

    public void setIndModoAtual(int indModoAtual) {
        this.indModoAtual = indModoAtual;
    }

    public int getDiffAcumuladaModos() {
        return diffAcumuladaModos;
    }

    public void setDiffAcumuladaModos(int diffAcumuladaModos) {
        this.diffAcumuladaModos = diffAcumuladaModos;
    }

    public PImage getFantasmaAssustado() {
        return fantasmaAssustado;
    }

    public void setFantasmaAssustado(PImage fantasmaAssustado) {
        this.fantasmaAssustado = fantasmaAssustado;
    }

}
