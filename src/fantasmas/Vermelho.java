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
import processing.core.PImage;

/**
 *
 * @author matheus
 */
public class Vermelho extends Fantasma implements Estrategia{
    
    public Vermelho(char IdElemento, int x, int y, PImage imagem, App app){
        super(IdElemento, x,y,imagem, app);   
    }
    
    @Override
    public void movimentar(Fantasma classe){}
    
    
    public boolean estaPerseguindo() {
        //System.out.println(app.tempo / 60);
        if(app.tempo / 60 - this.diffAcumuladaModos == this.app.game.tamanhoModos.get(indModoAtual)){
            // segue para o proximo modo e muda o estado
            this.diffAcumuladaModos += this.app.game.tamanhoModos.get(indModoAtual);
            this.indModoAtual++;
            this.app.game.perseguindo = !(this.app.game.perseguindo);
        }
        
        // se ja tiver iterado por todos os modos reinicia e faz denovo
        if(indModoAtual == this.app.game.tamanhoModos.size()){
            indModoAtual = 0;
            this.app.game.perseguindo = false;
        }
        
        // retorna se esta perseguindo
        return this.app.game.perseguindo;
    }

    @Override
    public void atualiza() {
        
        // seu alvo é o pacman
        if(estaPerseguindo()){ 
            this.calculaDirecao(this.app.game.pacMan.getX(), this.app.game.pacMan.getY());
        }
        // seu alvo é o canto superior esquerdo mais proximo
        else{
            this.calculaDirecao(app.game.paredeSuperiorEsquerda.getX(), app.game.paredeSuperiorEsquerda.getY());
        }
        
        if(!app.game.fantasmasAssustados) mover(); 
    }

}
