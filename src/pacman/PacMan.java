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
    
    public PacMan(char idElemento, int x, int y, PImage imagem){
        super(idElemento, x, y, imagem);
    }
    
    @Override
    public boolean checaColisao(){
        // vao ser considerados verdadeiras colisoes se forem com uma parede ou com fantasma
        // para melhor funcionamento e legibilidade do codigo
        
        int[] posicao = fakeMover(); 
        int y = posicao[0];
        int x = posicao[1];
        
        if(checaColisaoFantasmas(y, x)){
            System.out.println("colide com fantasma");
            return true;
        }
        
        
        if(checaColisaoComParede(y, x)){
            System.out.println("colide com parede");
            return true;
        }
        
        
        if(checaColisaoComSuperPastilha(y, x)){
            System.out.println("colide com superpastilha");
            mover(y,x);
            return false;
        }
        
        // se nao chocou com nenhum dos anterioes, chocou com uma pastilha, q nao vamos considerar com uma colisao
        System.out.println("nao colide e retonar falso");
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
//            else if(!checaColisao()){
//                System.out.println("movi");
//                mover(posicao[0], posicao[1]);
//            }
            //desenhar();
        }
        
    }
        
    
    @Override
    public void mover(int y, int x){
        System.out.println("y = " + y + " x = " + x);
        
        int indX = x / 16;
        int indY = y / 16;
        //System.out.println(indY + " " + indX);
        Elemento elem = new Elemento(this.getIdElemento(), indX, indY, this.getImagem());
        //this.getApp().image(this.getImagem(), this.getX(), this.getY()); Forca diretamente
        ArrayList<ArrayList<Elemento>> mapa = this.getApp().game.getMapa();
        mapa.get(indY).set(indX, elem);
        this.getApp().game.setMapa(mapa);
        
        //atualiza a posicao onde ele estava para vazia
        PImage imagemVazia = getApp().loadImage("src/imagens/empty.png");
        elem = new Elemento('0', getX(), getY(), imagemVazia);
        //this.getApp().image(this.getImagem(), this.getX(), this.getY()); Forca diretamente
        
        mapa.get(getY() / 16).set(getX() / 16, elem);
        this.getApp().game.setMapa(mapa);
        
        
        
        
        
        // atualiza a posicao(move)
        this.setX(x);
        this.setY(y);
        
        
    }
    
    @Override
    // vazia pois nao vai ser implementada.
    public void mover(){}
//        int tecla = this.getUltimaTecla();
//        System.out.println("entrei no mover");
//        System.out.println("coordenada antes: " + this.getX() / 16 + " " + this.getY() / 16);
//        switch (tecla) {
//            case 37:
//                this.setX(this.getX() - 16);
//                break;
//            case 38:
//                this.setY(this.getY() - 16);
//                break;
//            case 39:
//                this.setX(this.getX() + 16);
//                break;
//            default:
//                this.setY(this.getY() + 16);
//                break;
//        }
//        
//        System.out.println("coordenada depois: " + (this.getY() / 16) + 1  + " " + (this.getX() / 16) + 1);
    
    
    public int[] fakeMover(){
        int tecla = this.getUltimaTecla();
        System.out.println("entrei no fakemover");
        System.out.println("coordenada antes: " + ((this.getY() / 16) + 1) + " " + ((this.getX() / 16) + 1));
        int x = this.getX(), y = this.getY();
        switch (tecla) {
            case 37:
                x = this.getX() - 16;
                break;
            case 38:
                y = this.getY() - 16;
                break;
            case 39:
                x = this.getX() + 16;
                break;
            case 40:
                y = this.getY() + 16;
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
    
    public void desenhar() {
        
    }
}
