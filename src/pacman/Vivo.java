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
public abstract class Vivo extends Elemento {
    protected App app;
    protected int valocidade;
    protected boolean vivo;
    protected int xInicial, yInicial, ultimaTecla;
    
    public Vivo(char idElemento, int x, int y, PImage imagem, App app){
        super(idElemento, x,y,imagem);
        this.app = app;
    }
    
    public abstract boolean checaColisao();

    public abstract void atualiza();
        
    public abstract void mover();
    
    public abstract int[] fakeMover();
    
    public boolean checaColisaoFantasmas(int coordEsq, int coordDir, int coordCima, int coordBaixo){
        ArrayList<Vivo> fantasmas = getApp().game.getFantasmas();
        
        for(Vivo fantasma : fantasmas){
            int fantasmaEsq = fantasma.getX();
            int fantasmaDir = fantasmaEsq + 16;
            int fantasmaCima = fantasma.getY();
            int fantasmaBaixo = fantasmaCima + 16;
            if(coordEsq < fantasmaDir && coordDir > fantasmaEsq && coordCima < fantasmaBaixo && coordBaixo > fantasmaCima)return true;
        }
        
        return false;
        
    }
    
    public abstract boolean checaColisaoComPastilha(int coordEsq, int coordDir, int coordCima, int coordBaixo);
    
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
    
    public boolean checaColisaoComParede(int coordEsq, int coordDir, int coordCima, int coordBaixo){
        ArrayList<Estatico> paredes = getApp().game.getParedes();
        
        
        
        // tratando como caixas
        
        
        for(Estatico parede :  paredes){
            int paredeEsq = parede.getX();
            int paredeDir = paredeEsq + 16;
            int paredeCima = parede.getY();
            int paredeBaixo = paredeCima + 16;
            
            if(coordEsq < paredeDir && coordDir > paredeEsq && coordCima < paredeBaixo && coordBaixo > paredeCima) return true;
        }
        
        return false;
    }

    public App getApp() {
        return app;
    }

    public void setApp(App app) {
        this.app = app;
    }

    public int getValocidade() {
        return valocidade;
    }

    public void setValocidade(int valocidade) {
        this.valocidade = valocidade;
    }

    public boolean isVivo() {
        return vivo;
    }

    public void setVivo(boolean vivo) {
        this.vivo = vivo;
    }

    public int getxInicial() {
        return xInicial;
    }

    public void setxInicial(int xInicial) {
        this.xInicial = xInicial;
    }

    public int getyInicial() {
        return yInicial;
    }

    public void setyInicial(int yInicial) {
        this.yInicial = yInicial;
    }

    public int getUltimaTecla() {
        return ultimaTecla;
    }

    public void setUltimaTecla(int ultimaTecla) {
        this.ultimaTecla = ultimaTecla;
    }
    
    
    
}
