/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fantasmas;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import pacman.App;
import pacman.Estatico;
import pacman.Fantasma;
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
        
        //System.out.println(paredeSuperiorEsquerda.getY() / 16 + " " + paredeSuperiorEsquerda.getX() / 16);
        
    }
    
    public long calculaQuadradoDistanciaEuclidiana(int x1, int y1, int x2, int y2){
        return ((x1 - x2) * (x1 - x2)) + ((y1- y2) * (y1 - y2));
    }

    @Override
    public void atualiza() {
        
        // seu alvo é o pacman
        if(estaPerseguindo()){ 
            System.out.println("perseguindo");
            this.calculaDirecao(this.app.game.getPacMan().getX(), this.app.game.getPacMan().getY());
        }
        // seu alvo é o canto superior esquerdo mais proximo
        else{
            System.out.println("disperso");
            this.calculaDirecao(paredeSuperiorEsquerda.getX(), paredeSuperiorEsquerda.getY());
        }
        
        mover(); 
    }

    
    
    
    @Override
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
            if(Math.pow(dist, 0.5) / 16 <= 1 && !(estaPerseguindo())){
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
