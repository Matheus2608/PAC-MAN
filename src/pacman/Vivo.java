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
    public int ultimaTecla;
    protected int xInicial, yInicial, teclaAtual;
    protected ArrayList<Estatico> paredes, pastilhas, superPastilhas;
    protected ArrayList<Vivo> fantasmas;

    
    public Vivo(char idElemento, int x, int y, PImage imagem, App app){
        super(idElemento, x,y,imagem);
        xInicial = x;
        yInicial = y;
        this.app = app;
        this.ultimaTecla = 0;
        this.teclaAtual = 0;
        this.paredes = this.app.game.paredes;
        this.pastilhas = this.app.game.pastilhas;
        this.superPastilhas = this.app.game.superPastilhas;
        this.fantasmas = app.game.fantasmas;
    }
    
    public abstract void atualiza(); // atualiza a posição  
    
    public abstract boolean checaColisaoComPastilha(int coordEsq, int coordDir, int coordCima, int coordBaixo);
    
    public abstract boolean checaColisaoComSuperPastilha(int coordEsq, int coordDir, int coordCima, int coordBaixo);
    
    public boolean checaColisaoComParede(int coordEsq, int coordDir, int coordCima, int coordBaixo){
        // tratando como caixas
        for(Estatico parede : this.paredes){
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

    public int getTeclaAtual() {
        return teclaAtual;
    }

    public void setTeclaAtual(int teclaAtual) {
        this.teclaAtual = teclaAtual;
    }

    public ArrayList<Estatico> getParedes() {
        return paredes;
    }

    public void setParedes(ArrayList<Estatico> paredes) {
        this.paredes = paredes;
    }

    public ArrayList<Estatico> getPastilhas() {
        return pastilhas;
    }

    public void setPastilhas(ArrayList<Estatico> pastilhas) {
        this.pastilhas = pastilhas;
    }

    public ArrayList<Estatico> getSuperPastilhas() {
        return superPastilhas;
    }

    public void setSuperPastilhas(ArrayList<Estatico> superPastilhas) {
        this.superPastilhas = superPastilhas;
    }

    public ArrayList<Vivo> getFantasmas() {
        return fantasmas;
    }

    public void setFantasmas(ArrayList<Vivo> fantasmas) {
        this.fantasmas = fantasmas;
    }
    
}