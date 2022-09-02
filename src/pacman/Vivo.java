/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pacman;
import processing.core.PImage;
/**
 *
 * @author matheus
 */
public abstract class Vivo extends Elemento {
    public App app;
    public int valocidade;
    public boolean vivo;
    private int xInicial, yInicial, ultimaTecla;
    
    public Vivo(char idElemento, int x, int y, PImage imagem){
        super(idElemento, x,y,imagem);
    }
    
    public abstract boolean checaColisao();

    public abstract void atualiza();
        
    public abstract void mover();
    
    public abstract void mover(int y, int x);

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
