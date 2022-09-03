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
    private PImage imCima, imBaixo, imEsq, imDir, imagemVazia, imVida, imBocaFechada;
    public PacMan(char idElemento, int x, int y, PImage imagem, App app){
        super(idElemento, x, y, imagem, app);
        super.xInicial = x;
        super.yInicial = y;
        
        this.imCima = this.app.loadImage("src/imagens/pacman//playerUp.png");
        this.imBaixo = this.app.loadImage("src/imagens/pacman/playerDown.png");
        this.imEsq = this.app.loadImage("src/imagens/pacman/playerLeft.png");
        this.imDir = this.app.loadImage("src/imagens/pacman/playerRight.png");
        this.imagemVazia = this.app.loadImage("src/imagens/empty.png");
        this.imVida = this.app.loadImage("src/imagens/pacman/playerRight.png");
        this.imBocaFechada = this.app.loadImage("src/imagens/pacman/playerClosed.png");
    }
    
    @Override
    public boolean checaColisao(){
        // vao ser considerados verdadeiras colisoes se forem com uma parede ou com fantasma
        // para melhor funcionamento e legibilidade do codigo
        
        int[] posicao = fakeMover(); 
        int y = posicao[0];
        int x = posicao[1];
        
        
        if(checaColisaoComPastilha(y, x)){
            System.out.println("colide com pastilha");
        }
        
        
        if(checaColisaoFantasmas(y, x)){
            System.out.println("colide com fantasma");
            mover();
            return true;
        }
        
        
        if(checaColisaoComParede(y, x)){
            System.out.println("colide com parede");
            return true;
        }
        
        
        if(checaColisaoComSuperPastilha(y, x)){
            System.out.println("colide com superpastilha");
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
    
    public boolean checaColisaoComPastilha(int y, int x){
        Estatico remover = null;
        ArrayList<Estatico> pastilhas = getApp().game.getPastilhas();
        System.out.println("tamanho de pastilhas" + " " + pastilhas.size());
        
        for(Estatico pastilha : pastilhas){
            if(pastilha.getX() == x && pastilha.getY() == y) {remover = pastilha; break;}
        }
        
        if(remover == null) return false;
        
        pastilhas.remove(remover);
        app.game.setPastilhas(pastilhas);
        return true;
    }
    
    public boolean checaColisaoComSuperPastilha(int y, int x){
        ArrayList<Estatico> superPastilhas = getApp().game.getSuperPastilhas();
        
        for(Estatico pastilha : superPastilhas){
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
        desenhaVidas();
        if(this.getUltimaTecla() >= 37 && this.getUltimaTecla() <= 40){          
            App app = this.getApp();
            Game game = app.game;
            //game.parseJSON();
            game.vitoriaOuDerrota(app);
            checaColisao();
            
        }   
    }
        
    
    @Override
    public void mover(int y, int x){
        //this.getApp().image(this.getImagem(), this.getX(), this.getY()); Forca diretamente
        ArrayList<ArrayList<Elemento>> mapa = this.getApp().game.getMapa();
        //App app = this.getApp();
        
        //atualiza a posicao onde ele estava para vazia
        
        Elemento elem = new Elemento('0', getX(), getY(), this.imagemVazia);
        
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
    // move o pacman para posicao inicial
    public void mover(){
        
        Elemento elem = new Elemento('0', this.getX(), this.getY(), this.imagemVazia);
        ArrayList<ArrayList<Elemento>> mapa = app.game.getMapa();
        //System.out.println("posicao que estou: " + this.getY() / 16 + " " + this.getX() / 16);
        mapa.get(this.getY() / 16).set(this.getX() / 16, elem);
        
        //System.out.println("posicao inicial: " + this.yInicial / 16 + " " + xInicial / 16);
        elem = new Elemento(this.getIdElemento(), xInicial, yInicial, this.getImagem());
        mapa.get(this.yInicial / 16).set(this.xInicial / 16, elem);
        
        app.game.setMapa(mapa);
        this.setX(xInicial);
        this.setY(yInicial);
        
        this.app.game.setVidas(this.app.game.getVidas() - 1);
    }

    
    
    public int[] fakeMover(){
        int tecla = this.getUltimaTecla();
        //System.out.println("coordenada antes: " + ((this.getY() / 16) + 1) + " " + ((this.getX() / 16) + 1));
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
        
        if (app.frameCount % 8 >= 0 && app.frameCount % 16 <= 8) {
            this.setImagem(this.imBocaFechada);
        }
        
        
        //System.out.println("coordenada depois: " + ((this.getY() / 16) + 1)  + " " + ((this.getX() / 16) + 1));
        int[] res = new int[2];
        res[0] = y;
        res[1] = x;
        
        return res;
        
    }
    
    public void desenhaVidas(){
        int x = 20;
        int y = 540;
        for(int i = 0; i < app.game.getVidas(); i++){
            app.image(imVida, x, y);
            x += 40;
        }
    }
}
