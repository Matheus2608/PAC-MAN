/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pacman;

/**
 *
 * @author matheus
 */
import processing.core.PApplet;
public class App extends PApplet {
    
    // tamanho da tela
    public static final int WIDTH = 448;
    public static final int HEIGHT = 576;
    public int tempo; // usado para verificar o tempo entre os estados dos fanstasmas
    public boolean debug;
    public Game game;

    
    /**
     * quando o jogo inciar automaticamente as funcoes setup e settings vao ser chamadas e as funcoes
     * parseJSON deve ler as caracteristicas atuais do jogo e a loadGame deve renderizar o jogo na tela
     * frameRate faz com que o jogo seja 60 fps
     */
    
   public App() {
        this.tempo = 0;
        this.debug = false;
        this.game = new Game(this);
   }
    
    
    @Override
    public void setup() {
        frameRate(60);
        this.game.parseJSON(this);
        this.game.carregaJogo(this);
    }
    
   
    @Override
    public void settings() {
        size(WIDTH, HEIGHT);
    }
    


    @Override
    public void draw() {
        // deixa a tela totalmente preta
        background(0, 0, 0);

        //Atualiza os elementos
        atualizaElementos();

        // Draws the game grid and the entities
        this.game.desenhaMapa();

        // Draws the ghost and waka entities to the screen
        // drawEntities();
    }
    
    public void atualizaElementos() {
        for(Vivo fantasma : game.fantasmas){
            fantasma.atualiza();
        }
        
        game.pacMan.atualiza();
    }
    
        public void resetGame() {
            // colocando os atributos para seus valores iniciais
            this.tempo = 0;
            this.debug = false;
            
            this.game = new Game(this);
            setup();
        }
//    
    @Override
    public void keyPressed(){
        // 37 é esquerda, 38 pra cima, 39 direita, 40 pra baixo
        if (keyCode >= 37 && keyCode <= 40) { // é uma das setas
            this.game.pacMan.setUltimaTecla(keyCode);
        }
    }
    
    public static void main(String[] args) {
        PApplet.main("pacman.App");
    }
    
}

//    public void atualizaDinamicos() {
//        for(Fantasma fantasma : this.game.fanstasmas) {
//            fantasma.atualiza(this);
//        }
//        this.game.pacMan.atualiza(this);
//    }
//
//    public void desenhaDinamicos() {
//        for(Ghost ghost : this.game.ghosts) {
//            ghost.draw(this);
//        }
//        this.game.waka.draw(this);
//    }