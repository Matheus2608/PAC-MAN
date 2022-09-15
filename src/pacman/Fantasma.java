/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pacman;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import processing.core.PImage;

/**
 *
 * @author matheus
 */
public abstract class Fantasma extends Vivo{
    protected int indModoAtual;
    protected int diffAcumuladaModos; 
    protected PImage fantasmaAssustado;
    
    // construtor
    public Fantasma(char idElemento, int x, int y, PImage imagem, App app){
        super(idElemento, x,y,imagem, app);
        this.indModoAtual = 0;
        this.diffAcumuladaModos = 0;
        this.fantasmaAssustado = this.app.loadImage("src/imagens/fantasmas/frightened.png");       
    }
    
    // função para ver se o proximo movimento nao tera colisao com parede
    public boolean movimentoValido(int tecla){
        if(checaColisao(tecla)) return false;
            
        if( (tecla == 37 && this.ultimaTecla == 39) || (tecla == 39 && this.ultimaTecla == 37) ) 
            return false;
        if( (tecla == 38 && this.ultimaTecla == 40) || (tecla == 40 && this.ultimaTecla == 38) )
            return false;
        
        return true;
    }
    
    // função necessaria para a logica do comportamento dos fantasmas
    public long calculaQuadradoDistanciaEuclidiana(int x1, int y1, int x2, int y2){
        return ((x1 - x2) * (x1 - x2)) + ((y1- y2) * (y1 - y2));
    }   
    
    // 
    public void moverFanstasmasPosInicial(){
        for(Vivo fantasma: this.fantasmas){
            fantasma.x = fantasma.xInicial;
            fantasma.y = fantasma.yInicial;
        }
    }
    
    
    
    public boolean checaColisao(int tecla){
        if(tecla == 0) return true;
        
        int[] posicao = fakeMover(tecla); 
        int x = posicao[0];
        int y = posicao[1];

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
            return true;
        }
        
        return false;
    }
    
    public int[] fakeMover(int tecla){
        //System.out.println("coordenada antes: " + ((this.getY() / 16) + 1) + " " + ((this.getX() / 16) + 1));
        int x = this.x;
        int y = this.y;
        
        int velocidade = app.game.velocidade;
        switch (tecla) {
            case 37:
                x -=  velocidade;
                break;
            case 38:
                y -= velocidade;
                break;
            case 39:
                x += velocidade;
                break;
            case 40:
                y += velocidade;
                break;
            default:
                break;       
        }
        
        int[] res = new int[2];
        res[0] = x;
        res[1] = y;
        
        //System.out.println(y + " " + x);
        return res;
    }
    
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
            if(Math.pow(dist, 0.5) / 16 <= 1 && !(app.game.perseguindo)){
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
    
    public void mover(){
        if(this.ultimaTecla >= 37 && this.ultimaTecla <= 40){
            int[] posicao = fakeMover(ultimaTecla);
            this.x = posicao[0];
            this.y = posicao[1];
        }
        
        app.image(this.imagem, this.x, this.y);
        
    }
    
    @Override
    public boolean checaColisaoComPastilha(int coordEsq, int coordDir, int coordCima, int coordBaixo){
        return false;
    }
    
    @Override
    public boolean checaColisaoComSuperPastilha(int coordEsq, int coordDir, int coordCima, int coordBaixo){
        return false;
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