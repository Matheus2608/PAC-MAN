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
    protected int xInicial, yInicial, ultimaTecla, teclaAtual;
    
    public Vivo(char idElemento, int x, int y, PImage imagem, App app){
        super(idElemento, x,y,imagem);
        xInicial = x;
        yInicial = y;
        this.app = app;
        this.ultimaTecla = 0;
        this.teclaAtual = 0;
    }
    
    public abstract boolean checaColisao();

    public abstract void atualiza();
        
    public abstract void mover();
    
    public abstract int[] fakeMover();
    
    public abstract boolean checaColisaoComPastilha(int coordEsq, int coordDir, int coordCima, int coordBaixo);
    
    public abstract boolean checaColisaoComSuperPastilha(int coordEsq, int coordDir, int coordCima, int coordBaixo);
    
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
