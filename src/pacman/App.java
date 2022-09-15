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
import processing.core.PFont;

public class App extends PApplet {
    
    public static final int LARGURA = 448;
    public static final int ALTURA = 576;
    public int tempo; // usado para verificar o tempo entre os estados dos fanstasmas
    public boolean debug; // se der tempo, fazer um debug em tempo real
    public Game game;
    public PFont font;

    
    /**
     * quando o jogo inciar automaticamente as funcoes setup e settings vao ser chamadas e as funcoes
     * parseJSON deve ler as caracteristicas atuais do jogo e a carregaJogo deve renderizar o jogo na tela
     * 
     */
    
   public App() {
        this.tempo = 0;
        this.debug = false;
        this.game = new Game(this);
   }
    
    
    @Override
    public void setup() {
        frameRate(60); // faz com que o jogo seja 60 fps
        this.game.leJSON(); // le as configuracoes inicias do jogo
        this.game.carregaJogo(this); // carrega o jogo
        
        // especifica a fonte do texto
        this.font = this.createFont("src/imagens/PressStart2P-Regular.ttf", 16f);
        textFont(this.font);
    }
    
   
    @Override
    public void settings() {
        // especifica o tamanho da tela
        size(LARGURA, ALTURA);
    }
    

    /*
    função que é chamada 60 vezes por segundo da classe PApplet
    */
    @Override
    public void draw() throws NullPointerException  { // Exceção que sera lançada caso não reconheça a imagem no PC
        // deixa a tela totalmente preta
        background(0, 0, 0);
        
        // checa se é para resetar o game
        if (this.game.resetarGame) {
            // reseta o jogo
            resetJogo();
        }
        
        
        
        
       // checa se o jogador ganhou ou perdeu
       if (this.game.vitoriaOuDerrota(this)) {
           
           // entao eh necessario resetar o jogo para o usuario jogar novamente
            this.game.resetarGame = true;
            return;
        }
       
       // desenha os elementos estaticos na interface
       try{
          this.game.desenhaMapa();
        }
        catch(NullPointerException e) //Caso ocorra erro no deseno do mapa, por falta de arquivos no PC ou algo do genero
        {
          System.out.println("Ocorreu um NullPointerException ao executar o metodo carregaMapElementos()");
          System.out.println("Tente ver se os arquivos necessarios estao no PC");
        }
       
        //Atualiza e desenha os elementos vivos
        atualizaElementosVivos();
        this.tempo += 1;
    }
    
    
    
    public void atualizaElementosVivos() {
        for(Vivo fantasma : game.fantasmas){
            fantasma.atualiza();
        }
        
        game.pacMan.atualiza();
    }
    
    public void resetJogo() {
        
        // tela preta por 5 segundos
        this.delay(5000);

        // colocando os atributos para seus valores iniciais
        this.tempo = 0;
        this.debug = false;

        this.game = new Game(this);
        
        // começando o jogo novamente
        setup();
    }
    
    // funcao de evento na qual eh chamada quando o usuario clica em alguma tecla
    @Override
    public void keyPressed(){
        // checa se é uma seta
        if (keyCode >= 37 && keyCode <= 40) {
            // atualiza o pacman sobre a ultima tecla pressionada
            this.game.pacMan.ultimaTecla = keyCode;
        }
    }
    
    public static void main(String[] args) {
        // inicializa o progarama
        PApplet.main("pacman.App");
    }
    
}