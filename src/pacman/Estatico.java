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
public class Estatico extends Elemento{
    private boolean fruta, parede, pastilha, superPastilha, vazio;

    Estatico(char idElemento, int x, int y, PImage imagem) {
        super(idElemento, x, y, imagem);
    }
    
    public boolean isFruta() {
        return fruta;
    }

    public void setFruta(boolean fruta) {
        this.fruta = fruta;
    }

    public boolean isParede() {
        return parede;
    }

    public void setParede(boolean parede) {
        this.parede = parede;
    }

    public boolean isPastilha() {
        return pastilha;
    }

    public void setPastilha(boolean pastilha) {
        this.pastilha = pastilha;
    }

    public boolean isSuperPastilha() {
        return superPastilha;
    }

    public void setSuperPastilha(boolean superPastilha) {
        this.superPastilha = superPastilha;
    }
   
}
