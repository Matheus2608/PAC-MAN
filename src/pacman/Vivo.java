/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pacman;

/**
 *
 * @author matheus
 */
public abstract class Vivo extends Elemento {
    public App app;
    public int valocidade;
    public boolean vivo;
    private int xInicial, yInicial, ultimoMovimento;
    
    public abstract boolean checaColisao();

    public abstract void atualiza();
        
    public abstract void mover();

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

    public int getUltimoMovimento() {
        return ultimoMovimento;
    }

    public void setUltimoMovimento(int ultimoMovimento) {
        this.ultimoMovimento = ultimoMovimento;
    }
    
    
    
}
