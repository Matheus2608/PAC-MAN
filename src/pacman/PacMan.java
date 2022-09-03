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
public class PacMan extends Vivo{
    //private boolean checarX, checarY;
    private PImage imCima, imBaixo, imEsq, imDir;
    public PacMan(char idElemento, int x, int y, PImage imagem, App app){
        super(idElemento, x, y, imagem, app);
        
        this.imCima = this.app.loadImage("src/imagens/pacman//playerUp.png");
        this.imBaixo = this.app.loadImage("src/imagens/pacman/playerDown.png");
        this.imEsq = this.app.loadImage("src/imagens/pacman/playerLeft.png");
        this.imDir = this.app.loadImage("src/imagens/pacman/playerRight.png");
        
    }
    
    @Override
    public boolean checaColisao(){
        // vao ser considerados verdadeiras colisoes se forem com uma parede ou com fantasma
        // para melhor funcionamento e legibilidade do codigo
        
        int[] posicao = fakeMover(); 
        int y = posicao[0];
        int x = posicao[1];
        
        if(checaColisaoFantasmas(y, x)){
            //System.out.println("colide com fantasma");
            return true;
        }
        
        
        if(checaColisaoComParede(y, x)){
            //System.out.println("colide com parede");
            return true;
        }
        
        
        if(checaColisaoComSuperPastilha(y, x)){
            //System.out.println("colide com superpastilha");
            mover(y,x);
            return false;
        }
        
        // se nao chocou com nenhum dos anterioes, chocou com uma pastilha, q nao vamos considerar com uma colisao
        //System.out.println("nao colide e retonar falso");
        mover(y,x);
        return false;
    }
    
    public boolean checaColisaoFantasmas(int y, int x){
        ArrayList<Vivo> fantasmas = getApp().game.getFantasmas();

        for(Vivo fantasma : fantasmas){
            if(fantasma.getX() == x && fantasma.getY() == y) return true;
        }
        
        return false;
        
    }
    public boolean checaColisaoComSuperPastilha(int y, int x){
        ArrayList<Estatico> superPastilha = getApp().game.getSuperPastilhas();
        
        for(Estatico pastilha : superPastilha){
            if(pastilha.getX() == x && pastilha.getY() == y) return true;
        }
        
        return false;
    }
    
    public boolean checaColisaoComParede(int y, int x){
        ArrayList<Estatico> paredes = getApp().game.getParedes();
        
        for(Estatico parede :  paredes){
            if(parede.getX() == x && parede.getY() == y) return true;
        }
        
        return false;
    }

    @Override
    public void atualiza(){
        if(this.getUltimaTecla() >= 37 && this.getUltimaTecla() <= 40){          
//            System.out.println("ultima tecla: " + this.getUltimaTecla());
            App app = this.getApp();
            Game game = app.game;
            game.parseJSON();
            if(game.vitoritaOuDerrota(app)) app.resetGame();
            checaColisao();

        }
        
    }
        
    
    @Override
    public void mover(int y, int x){
        //this.getApp().image(this.getImagem(), this.getX(), this.getY()); Forca diretamente
        ArrayList<ArrayList<Elemento>> mapa = this.getApp().game.getMapa();
        App app = this.getApp();
        
        //atualiza a posicao onde ele estava para vazia
        PImage imagemVazia = app.loadImage("src/imagens/empty.png");
        Elemento elem = new Elemento('0', getX(), getY(), imagemVazia);
        
        mapa.get(getY() / 16).set(getX() / 16, elem);
        app.game.setMapa(mapa);
        
        int indX = x / 16;
        int indY = y / 16;
        //System.out.println(indY + " " + indX);
        elem = new Elemento(this.getIdElemento(), x, y, this.getImagem());
        
        
        mapa.get(indY).set(indX, elem);
        app.game.setMapa(mapa);
        
        app.game.desenhaMapa();
        // atualiza a posicao(move)
        this.setX(x);
        this.setY(y);
        
        
    }
    
    @Override
    // vazia pois nao vai ser implementada.
    public void mover(){}

    
    
    public int[] fakeMover(){
        int tecla = this.getUltimaTecla();
        System.out.println("entrei no fakemover");
        System.out.println("coordenada antes: " + ((this.getY() / 16) + 1) + " " + ((this.getX() / 16) + 1));
        int x = this.getX(), y = this.getY();
        switch (tecla) {
            case 37:
                x = this.getX() - 16;
                this.setImagem(imEsq);
                break;
            case 38:
                y = this.getY() - 16;
                this.setImagem(imCima);
                break;
            case 39:
                x = this.getX() + 16;
                this.setImagem(imDir);
                break;
            case 40:
                y = this.getY() + 16;
                this.setImagem(imBaixo);
                break;
            default:
                break;       
        }
        
        System.out.println("coordenada depois: " + ((this.getY() / 16) + 1)  + " " + ((this.getX() / 16) + 1));
        int[] res = new int[2];
        res[0] = y;
        res[1] = x;
        
        return res;
        
    }
    
}
