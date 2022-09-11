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
        
        this.imCima = this.app.loadImage("src/imagens/pacman//playerUp.png");
        this.imBaixo = this.app.loadImage("src/imagens/pacman/playerDown.png");
        this.imEsq = this.app.loadImage("src/imagens/pacman/playerLeft.png");
        this.imDir = this.app.loadImage("src/imagens/pacman/playerRight.png");
        this.imagemVazia = this.app.loadImage("src/imagens/empty.png");
        this.imVida = this.app.loadImage("src/imagens/pacman/playerRight.png");
        this.imBocaFechada = this.app.loadImage("src/imagens/pacman/playerClosed.png");
    }
    
    @Override 
    public int[] fakeMover() {return null;}
    
    @Override
    public boolean checaColisao(){
         // vao ser considerados verdadeiras colisoes se forem com uma parede ou com fantasma
        // para melhor funcionamento e legibilidade do codigo
        
        int[] posicao = fakeMoverUltimaTecla(); 
        int y = posicao[0];
        int x = posicao[1];
        
        boolean foraEscopoX = x < 0 || x > 448;
        boolean foraEscopoY = y < 0 || y > 576;
        
        int coordEsq = x;
        int coordDir = x + 16;
        int coordCima = y;
        int coordBaixo = y + 16;
        

        if(checaColisaoFantasmas(coordEsq, coordDir, coordCima, coordBaixo)){
            //System.out.println("colide com fantasma");
            mover();
            return true;
        }
        
        if (! ( (foraEscopoX || foraEscopoY) || checaColisaoComParede(coordEsq, coordDir, coordCima, coordBaixo) ) ) {
            // se nao tiver nenhuma colisao
            // atualiza a tecla atual para ultima tecla
            this.teclaAtual = this.ultimaTecla;
            
            // remove a pastilha se for colidir com ela para funcionar a funcao VitoriaOuDerrota
            checaColisaoComPastilha(coordEsq, coordDir, coordCima, coordBaixo);
            
            // e move o pacman de acordo com as coordenadas da ultima tecla
            mover(y,x);
        }
        
        else{
            // se chocou
            posicao = fakeMoverTeclaAtual(); 
            y = posicao[0];
            x = posicao[1];

            foraEscopoX = x < 0 || x > 448;
            foraEscopoY = y < 0 || y > 576;
            
             if (foraEscopoX || foraEscopoY) {
            return true;
        }
        
        coordEsq = x;
        coordDir = x + 16;
        coordCima = y;
        coordBaixo = y + 16;
        
        if(checaColisaoFantasmas(coordEsq, coordDir, coordCima, coordBaixo)){
            //System.out.println("colide com fantasma");
            mover();
            return true;
        }
        
        
        if(checaColisaoComParede(coordEsq, coordDir, coordCima, coordBaixo)){
            //System.out.println("colide com parede");
            return true;
        }
        
        checaColisaoComPastilha(coordEsq, coordDir, coordCima, coordBaixo);
        mover(y, x);
        return false;
        }
     return false;
    }
    
    @Override
    public boolean checaColisaoComPastilha(int coordEsq, int coordDir, int coordCima, int coordBaixo){
        Estatico remover = null;
        ArrayList<Estatico> pastilhas = getApp().game.getPastilhas();

        for(Estatico pastilha : pastilhas){
            int pastilhaEsq = pastilha.getX();
            int pastilhaDir = pastilhaEsq + 16;
            int pastilhaCima = pastilha.getY();
            int pastilhaBaixo = pastilhaCima + 16;
            if(coordEsq < pastilhaDir && coordDir > pastilhaEsq && coordCima < pastilhaBaixo && coordBaixo > pastilhaCima) {remover = pastilha; break;}
        }
        
        if(remover == null) return false;
        
        pastilhas.remove(remover);
        app.game.setPastilhas(pastilhas);
        return true;
    }
    
    @Override
    public boolean checaColisaoComSuperPastilha(int coordEsq, int coordDir, int coordCima, int coordBaixo){
        ArrayList<Estatico> superPastilhas = getApp().game.getSuperPastilhas();
        
        for(Estatico superPastilha : superPastilhas){
            int superPastilhaEsq = superPastilha.getX();
            int superPastilhaDir = superPastilhaEsq + 16;
            int superPastilhaCima = superPastilha.getY();
            int superPastilhaBaixo = superPastilhaCima + 16;
            
            if(coordEsq < superPastilhaDir && coordDir > superPastilhaEsq && coordCima < superPastilhaBaixo && coordBaixo > superPastilhaCima) return true;
        }
        
        return false;
    }
    
    public boolean checaColisaoFantasmas(int coordEsq, int coordDir, int coordCima, int coordBaixo){
        ArrayList<Vivo> fantasmas = getApp().game.getFantasmas();
        
        for(Vivo fantasma : fantasmas){
            int fantasmaEsq = fantasma.getX();
            int fantasmaDir = fantasmaEsq + 16;
            int fantasmaCima = fantasma.getY();
            int fantasmaBaixo = fantasmaCima + 16;
            if(coordEsq < fantasmaDir && coordDir > fantasmaEsq && coordCima < fantasmaBaixo && coordBaixo > fantasmaCima){
                Fantasma fColide = (Fantasma) fantasma;
                fColide.moverFanstasmasPosInicial();
                return true;
            }
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

    

    
    public int[] fakeMoverUltimaTecla(){
        int ultimaTecla = this.getUltimaTecla();
        
        //System.out.println("coordenada antes: " + ((this.getY() / 16) + 1) + " " + ((this.getX() / 16) + 1));
        int x = this.getX(), y = this.getY();
        int velocidade = app.game.getVelocidade();
        switch (ultimaTecla) {
            case 37:
                x -= velocidade;
                this.setImagem(imEsq);
                break;
            case 38:
                y -= velocidade;
                this.setImagem(imCima);
                break;
            case 39:
                x += velocidade;
                this.setImagem(imDir);
                break;
            case 40:
                y += velocidade;
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
    
    public int[] fakeMoverTeclaAtual(){
        int teclaAtual = this.teclaAtual, velocidade = app.game.getVelocidade();
        int x = this.x, y = this.y;
        switch (teclaAtual) {
            case 37:
                x -= velocidade;
                this.setImagem(imEsq);
                break;
            case 38:
                y -= velocidade;
                this.setImagem(imCima);
                break;
            case 39:
                x += velocidade;
                this.setImagem(imDir);
                break;
            case 40:
                y += velocidade;
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
