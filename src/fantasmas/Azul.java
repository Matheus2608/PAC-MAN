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
public class Azul extends Fantasma{

    public Azul(char IdElemento, int x, int y, PImage imagem, App app){
        super(IdElemento, x,y,imagem, app);
    }

    @Override
    public boolean checaLidaComColisao() {
        return false;
    }
    
    
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
            this.calculaDirecao(paredeInferiorDireita.getX(), paredeInferiorDireita.getY());
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
    
    @Override
    public void estrategia() {
        
    }

    @Override
    public void desenha(App app) {
        
    }
    
    
    @Override
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
