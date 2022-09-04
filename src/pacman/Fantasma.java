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
public abstract class Fantasma extends Vivo{
    protected int indModoAtual;
    protected int diffAcumuladaModos;
        
    public Fantasma(char idElemento, int x, int y, PImage imagem, App app){
        super(idElemento, x,y,imagem, app);
        this.indModoAtual = 0;
        this.diffAcumuladaModos = 0;
    }
    
    public boolean estaPerseguindo() {
        //System.out.println(app.tempo / 60);
        // se esta no ultimo segundo do modo
        if(app.tempo / 60 - this.diffAcumuladaModos == this.app.game.tamanhoModos.get(indModoAtual)){
            // segue para o proximo modo e muda o estado
            this.diffAcumuladaModos += this.app.game.tamanhoModos.get(indModoAtual);
            this.indModoAtual++;
            this.app.game.setPerseguindo(!(this.app.game.isPerseguindo()));
        }
        
        // se ja tiver iterado por todos os modos reinicia e faz denovo
        if(indModoAtual == this.app.game.getTamanhoModos().size()){
            indModoAtual = 0;
            this.app.game.setPerseguindo(false);
        }
        
        // retorna se esta perseguindo
        return this.app.game.isPerseguindo();
    }
    
    @Override
    public boolean checaColisao(){
        int[] posicao = fakeMover(); 
        int y = posicao[0];
        int x = posicao[1];

        boolean foraEscopoX = x < 0 || x > 448;
        boolean foraEscopoY = y < 0 || y > 576;
        if (foraEscopoX || foraEscopoY) {
            return true;
        }

        int coordEsq = x;
        int coordDir = x + 16;
        int coordCima = y;
        int coordBaixo = y + 16;

        if(checaColisaoComParede(coordEsq, coordDir, coordCima, coordBaixo)){
            //System.out.println("colide com parede");
            return true;
        }


        if(checaColisaoComPastilha(coordEsq, coordDir, coordCima, coordBaixo)){
            //System.out.println("colide com pastilha");
        }

        if(checaColisaoComSuperPastilha(coordEsq, coordDir, coordCima, coordBaixo)){
            //System.out.println("colide com superpastilha");
        }

        return false;
    }

    public abstract void atualiza();
    public abstract void estrategia();
    public abstract void draw(App app);
    public abstract int calculaDirecao(int y, int x); // recebe a posicao do alvo
    
    @Override
    public int[] fakeMover(){
        int tecla = this.getUltimaTecla();
        //System.out.println("coordenada antes: " + ((this.getY() / 16) + 1) + " " + ((this.getX() / 16) + 1));
        int x = this.getX(), y = this.getY();
        int velocidade = app.game.getVelocidade();
        switch (tecla) {
            case 37:
                x = this.getX() - velocidade;
                break;
            case 38:
                y = this.getY() - velocidade;
                break;
            case 39:
                x = this.getX() + velocidade;
                break;
            case 40:
                y = this.getY() + velocidade;
                break;
            default:
                break;       
        }
        
        int[] res = new int[2];
        res[0] = y;
        res[1] = x;
        
        return res;
    }
    
    @Override
    public boolean checaColisaoComPastilha(int coordEsq, int coordDir, int coordCima, int coordBaixo){
        return false;
    }
    
    @Override
    public boolean checaColisaoComSuperPastilha(int coordEsq, int coordDir, int coordCima, int coordBaixo){
        return false;
    }

}
