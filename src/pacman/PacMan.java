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
    private boolean checarX, checarY;
    
    public PacMan(char idElemento, int x, int y, PImage imagem){
        super(idElemento, x, y, imagem);
    }
    
    @Override
    public boolean checaColisao(){
        // vao ser considerados verdadeiras colisoes se forem com uma parede ou com fantasma
        // para melhor funcionamento e legibilidade do codigo
        if(checaColisaoFantasmas()){
            System.out.println("colide com fantasma");
            return true;
        }
        
        
        if(checaColisaoComParede()){
            System.out.println("colide com parede");
            return true;
        }
        
        System.out.println("nao colide e retonar falso");
//        if(checaColisaoComSuperPastilha()){ 
//            return false;
//        }
        
        // se nao chocou com nenhum dos anterioes, chocou com uma pastilha, q nao vamos considerar com uma colisao
        
        return false;
    }
    
    public boolean checaColisaoFantasmas(){
        ArrayList<Vivo> fantasmas = getApp().game.getFantasmas();
        
        int tecla = getUltimaTecla();
        if(tecla == 37 || tecla == 39){
            this.checarX = true;
            this.checarY = false;
        }
        else{
            this.checarX = false;
            this.checarY = true;
        }
        
        for(Vivo fanstasma : fantasmas){
            if(checarX){
                if(tecla == 37 && this.getX() - 16 == fanstasma.getX()) return true;
                if(tecla == 39 && this.getX() + 16 == fanstasma.getX()) return true;
            }
            
            if(checarY){
                if(tecla == 38 && this.getY() - 16 == fanstasma.getY()) return true;
                if(tecla == 40 && this.getY() + 16 == fanstasma.getY()) return true;
            }
        }
        
        return false;
        
    }
    public boolean checaColisaoComSuperPastilha(){
        ArrayList<Estatico> superPastilha = getApp().game.getSuperPastilhas();
        
        int tecla = getUltimaTecla();
        if(tecla == 37 || tecla == 39){
            this.checarX = true;
            this.checarY = false;
        }
        else{
            this.checarX = false;
            this.checarY = true;
        }
        
        for(Estatico pastilha : superPastilha){
            if(checarX){
                if(tecla == 37 && this.getX() - 16 == pastilha.getX()) return true;
                if(tecla == 39 && this.getX() + 16 == pastilha.getX()) return true;
            }
            
            if(checarY){
                if(tecla == 38 && this.getY() - 16 == pastilha.getY()) return true;
                if(tecla == 40 && this.getY() + 16 == pastilha.getY()) return true;
            }
        }
        
        return false;
    }
    
    public boolean checaColisaoComParede(){
        ArrayList<Estatico> paredes = getApp().game.getParedes();
        int tecla = getUltimaTecla();
        if(tecla == 37 || tecla == 39){
            this.checarX = true;
            this.checarY = false;
        }
        else{
            this.checarX = false;
            this.checarY = true;
        }
        
        for(Estatico parede :  paredes){
            if(checarX){
                if(tecla == 37 && this.getX() - 16 == parede.getX()) return true;
                if(tecla == 39 && this.getX() + 16 == parede.getX()) return true;
            }
            
            if(checarY){
                if(tecla == 38 && this.getY() - 16 == parede.getY()) return true;
                if(tecla == 40 && this.getY() + 16 == parede.getY()) return true;
            }
        }
        
        return false;
    }

    @Override
    public void atualiza(){
        if(this.getUltimaTecla() >= 37 && this.getUltimaTecla() <= 40){
            int[] posicao = fakeMover();
            mover(posicao[0], posicao[1]);
//            System.out.println("ultima tecla: " + this.getUltimaTecla());
//            App app = this.getApp();
//            Game game = app.game;
//            game.parseJSON();
//            if(game.vitoritaOuDerrota(app)) app.resetGame();
//            else if(!checaColisao()){
//                System.out.println("movi");
//                mover();
//            }
            desenhar();
        }
        
    }
        
    
    @Override
    public void mover(int y, int x){
        
        //atualiza a posicao onde ele estava para vazia
        PImage imagemVazia = getApp().loadImage("src/imagens/empty.png");
        Elemento elem = new Elemento('0', getX(), getY(), imagemVazia);
        //this.getApp().image(this.getImagem(), this.getX(), this.getY()); Forca diretamente
        ArrayList<ArrayList<Elemento>> mapa = this.getApp().game.getMapa();
        mapa.get(getY()).set(getX(), elem);
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
        switch (tecla) {
            case 37:
                this.setX(this.getX() - 16);
                break;
            case 38:
                this.setY(this.getY() - 16);
                break;
            case 39:
                this.setX(this.getX() + 16);
                break;
            case 40:
                this.setY(this.getY() + 16);
                break;
            default:
                break;       
        }
        
        System.out.println("coordenada depois: " + ((this.getY() / 16) + 1)  + " " + ((this.getX() / 16) + 1));
        int[] res = new int[2];
        res[0] = this.getY();
        res[1] = this.getX();
        
        return res;
        
    }
    
    public void desenhar() {
        int indX = this.getX() / 16;
        int indY = this.getY() / 16;
        //System.out.println(indY + " " + indX);
        Elemento elem = new Elemento(this.getIdElemento(), indX, indY, this.getImagem());
        //this.getApp().image(this.getImagem(), this.getX(), this.getY()); Forca diretamente
        ArrayList<ArrayList<Elemento>> mapa = this.getApp().game.getMapa();
        mapa.get(indY).set(indX, elem);
        this.getApp().game.setMapa(mapa);
    }
}
