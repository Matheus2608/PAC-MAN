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
    public int xInicial;
    public int yInicial, ultimaTecla, teclaAtual;
    public ArrayList<Estatico> paredes, pastilhas, superPastilhas;
    public ArrayList<Vivo> fantasmas;
    
    public Vivo(char idElemento, int x, int y, PImage imagem, App app){
        super(idElemento, x,y,imagem);
        xInicial = x;
        yInicial = y;
        this.app = app;
        this.ultimaTecla = 0;
        this.teclaAtual = 0;
        this.paredes = app.game.paredes;
        this.pastilhas = app.game.pastilhas;
        this.superPastilhas = app.game.superPastilhas;
        this.fantasmas = app.game.fantasmas;
        System.out.println("numero de paredes no vivo: " + paredes.size());
    }
    
    public abstract boolean checaLidaComColisao();

    public abstract void atualiza();
        
    //public abstract void lidaColisaoFantasma();
    
    //public abstract void mover();
    
    //public abstract int[] fakeMover();
    
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
    
}
