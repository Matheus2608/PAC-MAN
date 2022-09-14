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
public abstract class Fantasma extends Vivo{
    protected int indModoAtual;
    protected int diffAcumuladaModos;
    
    protected PImage fantasmaAssustado;
    
    public Fantasma(char idElemento, int x, int y, PImage imagem, App app){
        super(idElemento, x,y,imagem, app);
        this.indModoAtual = 0;
        this.diffAcumuladaModos = 0;
        this.fantasmaAssustado = this.app.loadImage("src/imagens/fantasmas/frightened.png");
        
        System.out.println("numero de paredes na classe fantasma: " + this.paredes.size());
        
    }
    
    public boolean movimentoValido(int tecla){
        if(checaColisao(tecla)) return false;
            
        if( (tecla == 37 && this.ultimaTecla == 39) || (tecla == 39 && this.ultimaTecla == 37) ) 
            return false;
        if( (tecla == 38 && this.ultimaTecla == 40) || (tecla == 40 && this.ultimaTecla == 38) )
            return false;
        
        return true;
    }
    
    public long calculaQuadradoDistanciaEuclidiana(int x1, int y1, int x2, int y2){
        return ((x1 - x2) * (x1 - x2)) + ((y1- y2) * (y1 - y2));
    }
    
    
    
    public void moverFanstasmasPosInicial(){
        for(Vivo fantasma: this.fantasmas){
            fantasma.x = fantasma.xInicial;
            fantasma.y = fantasma.yInicial;
        }
    }
    
    
    @Override
    public boolean checaLidaComColisao(){return false;}
    public boolean checaColisao(int tecla){
        if(tecla == 0) return true;
        
        int[] posicao = fakeMover(tecla); 
        int x = posicao[0];
        int y = posicao[1];

        boolean foraEscopoX = x < 0 || x > 448;
        boolean foraEscopoY = y < 0 || y > 576;
        if (foraEscopoX || foraEscopoY) {
            return true;
        }

        int coordEsq = x;
        int coordDir = x + 16;
        int coordCima = y;
        int coordBaixo = y + 16;

        if(checaColisaoComParede(coordEsq, coordDir, coordCima, coordBaixo)){
            return true;
        }
        
        return false;
    }

    public abstract void estrategia();
    public abstract void desenha(App app);
    public abstract void calculaDirecao(int x, int y); // recebe a posicao do alvo
    
    
    public int[] fakeMover(int tecla){
        //System.out.println("coordenada antes: " + ((this.getY() / 16) + 1) + " " + ((this.getX() / 16) + 1));
        int x = this.x;
        int y = this.y;
        
        int velocidade = app.game.velocidade;
        switch (tecla) {
            case 37:
                x -=  velocidade;
                break;
            case 38:
                y -= velocidade;
                break;
            case 39:
                x += velocidade;
                break;
            case 40:
                y += velocidade;
                break;
            default:
                break;       
        }
        
        int[] res = new int[2];
        res[0] = x;
        res[1] = y;
        
        //System.out.println(y + " " + x);
        return res;
    }
    
    @Override
    public boolean checaColisaoComPastilha(int coordEsq, int coordDir, int coordCima, int coordBaixo){
        return false;
    }
    
    @Override
    public boolean checaColisaoComSuperPastilha(int coordEsq, int coordDir, int coordCima, int coordBaixo){
        return false;
    }

}