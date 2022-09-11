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
import pacman.Estatico;
import pacman.Fantasma;
import pacman.PacMan;
import processing.core.PImage;

/**
 *
 * @author matheus
 */
public class Laranja extends Fantasma{
    
    private Estatico paredeInferiorEsquerda;
    
    public Laranja(char idElemento, int x, int y, PImage imagem, App app){
        super(idElemento, x,y,imagem, app);
   
        ArrayList<Estatico> paredes = app.game.getParedes();
        paredeInferiorEsquerda = paredes.get(0);
        for(Estatico parede: paredes){
            //System.out.println("parede y: " + parede.getY());
            if(parede.getY() > paredeInferiorEsquerda.getY()){
                paredeInferiorEsquerda = parede;
            }
            
            else if(parede.getY() == paredeInferiorEsquerda.getY()){
                if(parede.getX() < paredeInferiorEsquerda.getX()){
                    paredeInferiorEsquerda = parede;
                }
            }

        }

        paredeInferiorEsquerda.setY(528);
        paredeInferiorEsquerda.setX(432);
        
    }
    
    public int distanciaRetilineaAtePacMan(){
        int diffX = abs(this.x - this.app.game.getPacMan().getX());
        int diffY = abs(this.y - this.app.game.getPacMan().getY());
        
        return diffX + diffY;
    }
    
    public int[] posicaoAlvo(){
        PacMan pacMan = this.app.game.getPacMan();
        
        if(this.app.game.isPerseguindo()){
            if(distanciaRetilineaAtePacMan() / 16 > 8){
                int[] resposta = {pacMan.getX(), pacMan.getY()};
                return resposta;
            }
            else{
                int[] resposta = {paredeInferiorEsquerda.getX(), paredeInferiorEsquerda.getY()};
                return resposta;
            }
            
            
        }
        
        else{
            int[] resposta = {paredeInferiorEsquerda.getX(), paredeInferiorEsquerda.getY()};
            return resposta;
        } 
    }

    @Override
    public void atualiza() {
        int[] posicaoAlvo = posicaoAlvo();
        this.calculaDirecao(posicaoAlvo[0], posicaoAlvo[1]);

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
