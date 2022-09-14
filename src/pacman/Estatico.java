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
    public boolean fruta, parede, pastilha, superPastilha;
    
    // Elementos que nao se mexem, fazem parte do mapa.
    Estatico(char idElemento, int x, int y, PImage imagem) {
        super(idElemento, x, y, imagem);
    }
   
}
