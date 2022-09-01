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
public class Elemento {

   protected final int ALTURA = 16, LARGURA = 16;
   private int x ,y;
   private PImage imagem;
   
   public Elemento(int x, int y, PImage imagem) {
        this.x = x;
        this.y = y;
        this.imagem = imagem;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public PImage getImagem() {
        return imagem;
    }

    public void setImagem(PImage imagem) {
        this.imagem = imagem;
    }

}
