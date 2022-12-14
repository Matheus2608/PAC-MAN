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
    protected int x ,y;
    protected char idElemento;
    protected PImage imagem;

    
   // Construtores, sets e gets de posição e imagem dos elementos
    public Elemento(char idElemento, int x, int y, PImage imagem) {
        this.x = x;
        this.y = y;
        this.imagem = imagem;
        this.idElemento = idElemento;
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
    
    public char getIdElemento() {
        return idElemento;
    }

    public void setIdElemento(char id) {
        this.idElemento = id;
    }
}