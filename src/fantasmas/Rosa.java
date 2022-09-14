/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fantasmas;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import pacman.App;
import pacman.Atualizavel;
import pacman.Fantasma;
import pacman.PacMan;
import processing.core.PImage;

/**
 *
 * @author matheus
 */
public class Rosa extends Fantasma implements Atualizavel{
    public Rosa(char IdElemento, int x, int y, PImage imagem, App app){
        super(IdElemento, x,y,imagem, app);
    }
    
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

    
    public void mover(){
        if(this.ultimaTecla >= 37 && this.ultimaTecla <= 40){
            int[] posicao = fakeMover(ultimaTecla);
            this.x = posicao[0];
            this.y = posicao[1];
        }
        
        app.image(this.imagem, this.x, this.y);
        
    }

    @Override
    public void estrategia() {
        
    }

    @Override
    public void desenha(App app) {
        
    }
    
    @Override
    // vai retornar a primeira opcao, senao puder usa a segunda
    public void calculaDirecao(int x, int y){
        
        HashMap<Long, Integer> distanciaTecla = new HashMap<>();
        ArrayList<Long> distancias = new ArrayList<>();
        
        long distancia;
        // 37 -> esquerda
        distancia = calculaQuadradoDistanciaEuclidiana(this.x - 16, this.y, x, y);
        distanciaTecla. put(distancia, 37);
        distancias.add(distancia);
        
        // 38 -> pra cima
        distancia = calculaQuadradoDistanciaEuclidiana(this.x, this.y - 16, x, y);
        distanciaTecla. put(distancia, 38);
        distancias.add(distancia);
        
        // 39 -> pra direita
        distancia = calculaQuadradoDistanciaEuclidiana(this.x + 16, this.y, x, y);
        distanciaTecla. put(distancia, 39);
        distancias.add(distancia);
        
        // 40 -> pra baixo
        distancia = calculaQuadradoDistanciaEuclidiana(this.x, this.y + 16, x, y);
        distanciaTecla. put(distancia, 40);
        distancias.add(distancia);
        
        Collections.sort(distancias);
        
        for(long dist : distancias){
            if(Math.pow(dist, 0.5) / 16 <= 1){
                this.ultimaTecla = 0;
                break;
            }
            
            int tecla = distanciaTecla.get(dist);
            
            if(movimentoValido(tecla)){
                this.ultimaTecla = tecla;
                break;
            }
            
        }
    }

}
