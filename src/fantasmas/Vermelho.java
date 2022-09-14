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
public class Vermelho extends Fantasma{
    
    public Vermelho(char IdElemento, int x, int y, PImage imagem, App app){
        super(IdElemento, x,y,imagem, app);   
    }
    
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
            this.calculaDirecao(paredeSuperiorEsquerda.getX(), paredeSuperiorEsquerda.getY());
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
