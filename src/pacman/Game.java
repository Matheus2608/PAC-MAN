/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pacman;
/**
 *
 * @author matheus
 */
import fantasmas.Azul;
import fantasmas.Laranja;
import fantasmas.Rosa;
import fantasmas.Vermelho;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import org.json.simple.JSONArray;
import processing.core.PImage;


public class Game {
    public App app;
    public ArrayList<ArrayList<Elemento>> mapa;
    public String nomeArquivo;
    public int vidas;
    public int velocidade;
    public int tempoAssustado;
    public HashMap<Character, String> mapElementos;
    public ArrayList<Integer> tamanhoModos;
    public PacMan pacMan;
    public ArrayList<Vivo> fantasmas;
    public ArrayList<Estatico> paredes;
    public ArrayList<Estatico> frutas;
    public ArrayList<Estatico> pastilhas;
    public ArrayList<Estatico> superPastilhas;
    public boolean resetarGame;
    public boolean perseguindo;
    public boolean fantasmasAssustados;

    public Game(App app) {
        this.app = app;
        this.mapElementos = new HashMap<Character, String>();
        this.mapa = new ArrayList<ArrayList<Elemento>>();
        this.paredes = new ArrayList<Estatico>();
        this.frutas = new ArrayList<Estatico>();
        this.pastilhas = new ArrayList<Estatico>();
        this.superPastilhas = new ArrayList<Estatico>();
        this.fantasmas = new ArrayList<Vivo>();
        this.tamanhoModos = new ArrayList<Integer>();
        this.pacMan = null;
        this.resetarGame = false;
        this.perseguindo = false;
        this.fantasmasAssustados = false;
        carregaMapElementos();
    }
    
    // preenche o hashmap com imagens necessarias dos seus respectivos elementos
    public void carregaMapElementos() {
        this.mapElementos.put('0', "src/imagens/empty.png"); // imagem vazia
        this.mapElementos.put('1', "src/imagens/paredes/horizontal.png"); // parede horizontal
        this.mapElementos.put('2', "src/imagens/paredes/vertical.png"); // parede vertical
        this.mapElementos.put('3', "src/imagens/paredes/upLeft.png"); // parede superiorEsquerda
        this.mapElementos.put('4', "src/imagens/paredes/upRight.png"); // parede superiorDireita
        this.mapElementos.put('5', "src/imagens/paredes/downLeft.png"); // parede inferiorEsquerda
        this.mapElementos.put('6', "src/imagens/paredes/downRight.png"); // parede inferiorDireita
        this.mapElementos.put('7', "src/imagens/fruit.png"); // pastilha
        this.mapElementos.put('8', "src/imagens/superFruit.png"); // superPastilha
        this.mapElementos.put('p', "src/imagens/pacman/playerClosed.png"); //pacMan fechado
        this.mapElementos.put('g', "src/imagens/fantasmas/ghost.png"); // fantasma generico
        this.mapElementos.put('a', "src/imagens/fantasmas/ambusher.png"); // fantasma rosa
        this.mapElementos.put('c', "src/imagens/fantasmas/chaser.png"); // fantasma vermelho
        this.mapElementos.put('i', "src/imagens/fantasmas/ignorant.png"); // fantasma laranja
        this.mapElementos.put('w', "src/imagens/fantasmas/whim.png"); // fantasma azul
        this.mapElementos.put('s', "src/imagens/sodaCan.png"); // refrigerante
    }
    
    
    // naveja pelo arquivo config.json onde ha as informacoes iniciais e importantes para o funcionamento do jogo
    public void parseJSON() {
        
        // Cria um objeto JSONParser a fim de analisar o arquivo
        JSONParser jsonParser = new JSONParser();
        try {
            // le a analisa o arquivo
            FileReader fReader = new FileReader("config.json");
            JSONObject jsonObject = (JSONObject) jsonParser.parse(fReader);

            // armazena os valores em atributos da classe Jogo para nao precissar iterar mais uma vez
            this.nomeArquivo = jsonObject.get("mapa").toString();
            this.vidas = Integer.parseInt(jsonObject.get("vidas").toString());
            this.velocidade = Integer.parseInt(jsonObject.get("velocidade").toString());
            String tempoAssustado = jsonObject.get("tempoAssustado").toString();
            this.tempoAssustado = Integer.parseInt(tempoAssustado);
            
            JSONArray vetorJson = (JSONArray) jsonObject.get("tamanhoModos");
            for (Object obj : vetorJson) {
                int modo = Integer.parseInt(obj.toString());
                this.tamanhoModos.add(modo);
            }
            
        // se tiver algum erro no processo    
        } catch (FileNotFoundException fnfe) {
            fnfe.printStackTrace();
            return;
        } catch (IOException ioe) {
            ioe.printStackTrace();
            return;
        } catch (ParseException pe) {
            pe.printStackTrace();
            return;
        }
    }
    
    public boolean vitoriaOuDerrota(App app) {
        // checa se nao tem pastilhas = vitoria
        if (this.pastilhas.isEmpty()) {
            desenhaVitoria(app);
            return true;
        // checa se nao possui vidas = derrota
        } else if (this.vidas == 0) {
            desenhaDerrota(app);
            return true;
        }
        
        // caso nao tenha ganhado nem perdido, o jogo nao acabou
        return false;
    }

    
    public void desenhaVitoria(App app) {
        app.background(0, 0, 0);
        app.text("VITORIA!!!", 160, 260);
    }
    
    public void desenhaDerrota(App app) {
        app.background(0, 0, 0);
        app.text("GAME OVER", 160, 260);
    }
    
    public void carregaJogo(App app){
        // inicializa a matriz do jogo vazia inicialmente
        ArrayList<ArrayList<Elemento>> matrizJogo = new ArrayList<ArrayList<Elemento>>();

        try {
            // le o arquivo do mapa
            File arquivo = new File("mapa.txt");
            Scanner scan = new Scanner(arquivo);
            
            // cada elemento vai ter 16 por 16 pixels, logo ha necessidade de guardar os valores das coordenadas
            // dos pixels nos elementos
            
            // y são as linhas e x são as colunas
            int y = 0;
            
            // enquanto houver linhas a serem lidas no arquivo
            while (scan.hasNextLine()) {
                String linha = scan.nextLine();
                
                // Inicializa um array para cada linha
                ArrayList<Elemento> linhaAtual = new ArrayList<Elemento>();
              
                int x = 0;
                // Para cada elemento(id nesse caso) contido na linha
                for (char idElemento : linha.toCharArray()) {

                    int id = Character.getNumericValue(idElemento);
                    
                    // acha o caminho da imagem no hashmap
                    String caminhoImagem = mapElementos.get(idElemento);
                    // carrega a imagem
                    PImage imagem = app.loadImage(caminhoImagem);
                    
                    //cria o elemento
                    Elemento elem = new Elemento(idElemento, x, y, imagem);
                    
                    // inicializa um elemento estatico para posterior utilizacao se for um elemento estatico
                    Estatico estatico = estatico = new Estatico(idElemento, x, y, imagem);
                    
                    // checa se e uma parede
                    if(id  >= 1 && id <= 6){ 
                        estatico.parede = true;
                        // adiciona no array de paredes
                        paredes.add(estatico);
                    }
                    
                    // checa se e uma pastilha
                    else if(id == 7){
                        estatico.pastilha = true;
                        pastilhas.add(estatico);
                    }
                    
                    // checa se e uma super pastilha
                    else if(id == 8){
                        estatico.superPastilha = true;
                        superPastilhas.add(estatico);
                    }
                    
                    // checa se e o pacman
                    else if(idElemento == 'p') {
                        this.pacMan =  new PacMan(idElemento, x, y, imagem, app);
                    }
                    
                    // checa se e o fantasma rosa
                    else if(idElemento == 'a'){
                        Rosa rosa = new Rosa(idElemento,x,y,imagem, app);
                        fantasmas.add(rosa);
                    }
                    
                    // checa se e o fantasma vermelho
                    else if(idElemento == 'c'){
                        Vermelho vermelho = new Vermelho(idElemento, x,y,imagem,app);
                        fantasmas.add(vermelho);
                    }
                    
                    // checa se e o fantasma laranja
                    else if(idElemento == 'i'){
                        Laranja laranja = new Laranja(idElemento, x,y,imagem, app);
                        fantasmas.add(laranja);
                    }
                    
                    // checa se e o fantasma azul
                    else if(idElemento == 'w'){
                        Azul azul = new Azul(idElemento, x,y,imagem, app);
                        fantasmas.add(azul);
                    }
                    
                    // adiciona o elemento na linha
                    linhaAtual.add(elem);
                    // muda de coluna
                    x += 16;
                }
                
                // adiciona a linha na matriz do jogo
                matrizJogo.add(linhaAtual);
                // muda de linha
                y += 16;
            }
            
            // referencia a matriz como sendo o mapa do jogo
            this.mapa = matrizJogo;           
        }
        
        catch (FileNotFoundException fnfe) {
            fnfe.printStackTrace();
        }
        
        
        System.out.println("numero de paredes no game: " + this.paredes.size());
        
        
    }
    
    public void desenhaMapa(){
        // para cada elemento contido no mapa imprime na tela somente os que não são fantasmas
        // pois esses vao se auto imprimir quando forem atualizados
        for(ArrayList<Elemento> linha : this.mapa){
            for(Elemento elem : linha){
                if (!("aciwg".contains(Character.toString(elem.getIdElemento())))) {
                    app.image(elem.getImagem(), elem.getX(), elem.getY());
                }
            }
        }
    }
}
        

