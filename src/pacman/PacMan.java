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
public class PacMan extends Vivo{
    // imagens dos estados do pacman e do simbolo de vida e vazio
    private PImage imCima, imBaixo, imEsq, imDir, imagemVazia, imVida, imBocaFechada;

    public PacMan(char idElemento, int x, int y, PImage imagem, App app){
        super(idElemento, x, y, imagem, app);
        
        this.imCima = this.app.loadImage("src/imagens/pacman//playerUp.png");
        this.imBaixo = this.app.loadImage("src/imagens/pacman/playerDown.png");
        this.imEsq = this.app.loadImage("src/imagens/pacman/playerLeft.png");
        this.imDir = this.app.loadImage("src/imagens/pacman/playerRight.png");
        this.imagemVazia = this.app.loadImage("src/imagens/empty.png");
        this.imVida = this.app.loadImage("src/imagens/pacman/playerRight.png");
        this.imBocaFechada = this.app.loadImage("src/imagens/pacman/playerClosed.png");
        System.out.println("numero de paredes na classe pacman: " + this.paredes.size());
    }
    
    
    // checa se houve colisao e lida
    @Override
    public boolean checaLidaComColisao(){
        
        // vao ser considerados verdadeiras colisoes se forem com uma parede ou com fantasma
        // para melhor funcionamento e legibilidade do codigo
        
        // posicao que estaria caso se movimentasse de acordo com a ultima tecla pressioanda
        int[] posicao = fakeMoverUltimaTecla(); 
        int y = posicao[0];
        int x = posicao[1];
        
        // esta fora do mapa?
        boolean foraEscopoX = x < 0 || x > 448;
        boolean foraEscopoY = y < 0 || y > 576;
        
        // coordenadas necessarias para utilizacao em funcoes de colisao
        int coordEsq = x;
        int coordDir = x + 16;
        int coordCima = y;
        int coordBaixo = y + 16;
        
        // se colide com fantasmas
        if(checaColisaoFantasmas(coordEsq, coordDir, coordCima, coordBaixo)){
            // move ambos para a posicao inicial
            lidaColisaoFantasma();
            return true;
        }
        
        // se  não colidir com uma parede ou estiver fora do mapa
        if (! ( (foraEscopoX || foraEscopoY) || checaColisaoComParede(coordEsq, coordDir, coordCima, coordBaixo) ) ) {
            // se nao tiver nenhuma colisao
            // atualiza a tecla atual para ultima tecla
            this.teclaAtual = this.ultimaTecla;
            
            // remove a pastilha se for colidir com ela para funcionar a funcao VitoriaOuDerrota
            checaColisaoComPastilha(coordEsq, coordDir, coordCima, coordBaixo);
            
            if(checaColisaoComPastilha(coordEsq, coordDir, coordCima, coordBaixo)){
                app.game.fantasmasAssustados = true;
            }
            
            // e move o pacman de acordo com as coordenadas da ultima tecla
            mover(y,x);
        }
        
        else{
            // se colidiu
            // os testes serao com a teclaAtual agora
           
            // posicao que estaria caso se movimentasse de acordo com a tecla atual
            posicao = fakeMoverTeclaAtual(); 
            y = posicao[0];
            x = posicao[1];

            foraEscopoX = x < 0 || x > 448;
            foraEscopoY = y < 0 || y > 576;
            
            // se estiver fora do mapa
             if (foraEscopoX || foraEscopoY) return true;
        
        
        coordEsq = x;
        coordDir = x + 16;
        coordCima = y;
        coordBaixo = y + 16;
        
        // se colidir com fantasmas
        if(checaColisaoFantasmas(coordEsq, coordDir, coordCima, coordBaixo)){
            // move os para a posicao inicial
            lidaColisaoFantasma();
            return true;
        }
        
        // se chocar com parede
        if(checaColisaoComParede(coordEsq, coordDir, coordCima, coordBaixo)){
            // nao move, continua na parede
            return true;
        }
        
        // se chocou(comeu) pastilha, remove ela do mapa e do classe jogo
        checaColisaoComPastilha(coordEsq, coordDir, coordCima, coordBaixo);
        mover(y, x);
        return false;
        }
        
     // so pq o netbeans pediu(msm q n colidiu)   
     return false;
    }
    
    // funcoes de checagem de colisao usam as coordenadas dos elementos e os tratam como caixas para verem se
    // estao colidindo, se forem somente com numeros das coordenadas(ja testados varias vezes) nao funciona
    
    // a unica diferenca dessa eh q ela remove a pastilha
    @Override
    public boolean checaColisaoComPastilha(int coordEsq, int coordDir, int coordCima, int coordBaixo){
        Estatico remover = null;
        ArrayList<Estatico> pastilhas = this.pastilhas;

        for(Estatico pastilha : pastilhas){
            int pastilhaEsq = pastilha.getX();
            int pastilhaDir = pastilhaEsq + 16;
            int pastilhaCima = pastilha.getY();
            int pastilhaBaixo = pastilhaCima + 16;
            if(coordEsq < pastilhaDir && coordDir > pastilhaEsq && coordCima < pastilhaBaixo && coordBaixo > pastilhaCima) {remover = pastilha; break;}
        }
        
        if(remover == null) return false;
        
        pastilhas.remove(remover);
        app.game.pastilhas = pastilhas;
        return true;
    }
    
    // funcoes de checagem de colisao usam as coordenadas dos elementos e os tratam como caixas para verem se
    // estao colidindo, se forem somente com numeros das coordenadas(ja testados varias vezes) nao funciona
    @Override
    public boolean checaColisaoComSuperPastilha(int coordEsq, int coordDir, int coordCima, int coordBaixo){
        ArrayList<Estatico> superPastilhas = this.superPastilhas;
        
        for(Estatico superPastilha : superPastilhas){
            int superPastilhaEsq = superPastilha.getX();
            int superPastilhaDir = superPastilhaEsq + 16;
            int superPastilhaCima = superPastilha.getY();
            int superPastilhaBaixo = superPastilhaCima + 16;
            
            if(coordEsq < superPastilhaDir && coordDir > superPastilhaEsq && coordCima < superPastilhaBaixo && coordBaixo > superPastilhaCima) return true;
        }
        
        return false;
    }
    
    // funcoes de checagem de colisao usam as coordenadas dos elementos e os tratam como caixas para verem se
    // estao colidindo, se forem somente com numeros das coordenadas(ja testados varias vezes) nao funciona
    
    // unica diferenca eh q atualiza os fantasmas
    public boolean checaColisaoFantasmas(int coordEsq, int coordDir, int coordCima, int coordBaixo){
        ArrayList<Vivo> fantasmas = this.fantasmas;
        
        for(Vivo fantasma : fantasmas){
            int fantasmaEsq = fantasma.getX();
            int fantasmaDir = fantasmaEsq + 16;
            int fantasmaCima = fantasma.getY();
            int fantasmaBaixo = fantasmaCima + 16;
            if(coordEsq < fantasmaDir && coordDir > fantasmaEsq && coordCima < fantasmaBaixo && coordBaixo > fantasmaCima){
                // transforma o elemento vivo em fantasma(pois eh um fantasma) para poder usar a funcao de move
                // los para posicao inicial
                Fantasma fColide = (Fantasma) fantasma;
                if(app.game.fantasmasAssustados){
                    
                }
                // move os fantasmas para a posicao inicial
                fColide.moverFanstasmasPosInicial();
                return true;
            }
        }
        
        return false;
    }
    
    // funcao que e chamada 60 vezes por segundo para atualizar a posicao do pacman
    @Override
    public void atualiza(){
        
        desenhaVidas();
        
        // se a tecla for uma seta
        if(this.ultimaTecla >= 37 && this.ultimaTecla <= 40){
            // checa se acabou ou nao o jogo
            app.game.vitoriaOuDerrota(app);
            
            // chama a principal funcao para lidar com o movimento do pacman
            checaLidaComColisao();
            
        }   
    }
        
    
    public void mover(int y, int x){
        
        ArrayList<ArrayList<Elemento>> mapa = this.app.game.mapa;
        
        //atualiza a posicao onde ele estava para vazia
        
        // inicializa um elemento como sendo vazio
        Elemento elem = new Elemento('0', getX(), getY(), this.imagemVazia);
        
        // no lugar onde o pacman estava, coloca o elemento vazio
        mapa.get(getY() / 16).set(getX() / 16, elem);
        
        // posicao do pacman conforme a tecla
        int indX = x / 16;
        int indY = y / 16;
        
        // inicializa o pacman nessa posicao
        elem = new Elemento(this.getIdElemento(), x, y, this.getImagem());
        
        // atualiza o mapa com o pacman nessa posicao
        mapa.get(indY).set(indX, elem);
        
        //atualiza o mapa na classe jogo
        app.game.mapa = mapa;
        
        // atualiza a posicao do pacman nessa classe
        this.x = x;
        this.y = y;
        
        
    }
    
    
    // move o pacman para posicao inicial
    public void lidaColisaoFantasma(){
        
        Elemento elem = new Elemento('0', this.getX(), this.getY(), this.imagemVazia);
        ArrayList<ArrayList<Elemento>> mapa = app.game.mapa;
        //System.out.println("posicao que estou: " + this.getY() / 16 + " " + this.getX() / 16);
        mapa.get(this.getY() / 16).set(this.getX() / 16, elem);
        
        //System.out.println("posicao inicial: " + this.yInicial / 16 + " " + xInicial / 16);
        elem = new Elemento(this.idElemento, xInicial, yInicial, this.getImagem());
        mapa.get(this.yInicial / 16).set(this.xInicial / 16, elem);
        
        app.game.mapa = mapa;
        this.x = xInicial;
        this.y = yInicial;
        
        this.app.game.vidas -= 1;
    }

    

    
    public int[] fakeMoverUltimaTecla(){
        int ultimaTecla = this.ultimaTecla;
        
        //System.out.println("coordenada antes: " + ((this.getY() / 16) + 1) + " " + ((this.getX() / 16) + 1));
        int x = this.getX(), y = this.getY();
        int velocidade = app.game.velocidade;
        switch (ultimaTecla) {
            case 37:
                x -= velocidade;
                this.setImagem(imEsq);
                break;
            case 38:
                y -= velocidade;
                this.setImagem(imCima);
                break;
            case 39:
                x += velocidade;
                this.setImagem(imDir);
                break;
            case 40:
                y += velocidade;
                this.setImagem(imBaixo);
                break;
            default:
                break;       
        }
        
        if (app.frameCount % 8 >= 0 && app.frameCount % 16 <= 8) {
            this.setImagem(this.imBocaFechada);
        }
        
        
        //System.out.println("coordenada depois: " + ((this.getY() / 16) + 1)  + " " + ((this.getX() / 16) + 1));
        int[] res = new int[2];
        res[0] = y;
        res[1] = x;
        
        return res;
        
    }
    
    public int[] fakeMoverTeclaAtual(){
        int teclaAtual = this.teclaAtual, velocidade = app.game.velocidade;
        int x = this.x, y = this.y;
        switch (teclaAtual) {
            case 37:
                x -= velocidade;
                this.setImagem(imEsq);
                break;
            case 38:
                y -= velocidade;
                this.setImagem(imCima);
                break;
            case 39:
                x += velocidade;
                this.setImagem(imDir);
                break;
            case 40:
                y += velocidade;
                this.setImagem(imBaixo);
                break;
            default:
                break;       
        }
        
        if (app.frameCount % 8 >= 0 && app.frameCount % 16 <= 8) {
            this.setImagem(this.imBocaFechada);
        }
        
        
        //System.out.println("coordenada depois: " + ((this.getY() / 16) + 1)  + " " + ((this.getX() / 16) + 1));
        int[] res = new int[2];
        res[0] = y;
        res[1] = x;
        
        return res;
        
    }
    
    public void desenhaVidas(){
        int x = 20;
        int y = 540;
        for(int i = 0; i < app.game.vidas; i++){
            app.image(imVida, x, y);
            x += 40;
        }
    }
}
