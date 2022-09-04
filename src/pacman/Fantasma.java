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
        
    public Fantasma(char idElemento, int x, int y, PImage imagem, App app){
        super(idElemento, x,y,imagem, app);
        this.indModoAtual = 0;
    }
    
    public abstract void atualiza();
    public abstract void estrategia();
    public abstract void draw(App app);

    public boolean estaPerseguindo() {
        // se esta no ultimo segundo do modo
        if(app.tempo == this.app.game.tamanhoModos.get(indModoAtual)){
            // segue para o proximo modo e muda o estado
            this.indModoAtual++;
            this.app.game.setPerseguindo(!(this.app.game.isPerseguindo()));
        }
        
        // se ja tiver iterado por todos os modos reinicia e faz denovo
        if(indModoAtual == this.app.game.getTamanhoModos().size()){
            indModoAtual = 0;
            this.app.game.setPerseguindo(false);
        }
        
        // retorna se esta perseguindo
        return this.app.game.isPerseguindo();
    }
    
}
