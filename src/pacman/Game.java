/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pacman;
/**
 *
 * @author matheus
 */
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

import fantasmas.*;
import org.json.simple.JSONArray;
import processing.core.PImage;


public class Game {
    private App app;
    private ArrayList<ArrayList<Elemento>> mapa;
    private String nomeArquivo;
    private int vidas;
    private int velocidade;
    private int tempoAssustado;
    private HashMap<Character, String> mapElementos;
    public ArrayList<Integer> tamanhoModos;
    private PacMan pacMan;
    private ArrayList<Vivo> fantasmas;
    private ArrayList<Estatico> paredes;
    private ArrayList<Estatico> frutas;
    private ArrayList<Estatico> pastilhas;
    private ArrayList<Estatico> superPastilhas;
    private boolean resetarGame;
    private boolean perseguindo; 

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
        carregaMapElementos();
    }
    
    
    public void carregaMapElementos() {
        this.mapElementos.put('0', "src/imagens/empty.png");
        this.mapElementos.put('1', "src/imagens/paredes/horizontal.png");
        this.mapElementos.put('2', "src/imagens/paredes/vertical.png");
        this.mapElementos.put('3', "src/imagens/paredes/upLeft.png");
        this.mapElementos.put('4', "src/imagens/paredes/upRight.png");
        this.mapElementos.put('5', "src/imagens/paredes/downLeft.png");
        this.mapElementos.put('6', "src/imagens/paredes/downRight.png");
        this.mapElementos.put('7', "src/imagens/fruit.png");
        this.mapElementos.put('8', "src/imagens/superFruit.png");
        this.mapElementos.put('p', "src/imagens/pacman/playerClosed.png");
        this.mapElementos.put('g', "src/imagens/fantasmas/ghost.png");
        this.mapElementos.put('a', "src/imagens/fantasmas/ambusher.png");
        this.mapElementos.put('c', "src/imagens/fantasmas/chaser.png");
        this.mapElementos.put('i', "src/imagens/fantasmas/ignorant.png");
        this.mapElementos.put('w', "src/imagens/fantasmas/whim.png");
        this.mapElementos.put('s', "src/imagens/sodaCan.png");
    }
    
    
    
    public void parseJSON() {
        // Create a JSONParser object
        JSONParser jsonParser = new JSONParser();
        try {
            // Parse the config file
            FileReader fReader = new FileReader("config.json");
            JSONObject jsonObject = (JSONObject) jsonParser.parse(fReader);

            // Store the values into instance attributes
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
        if (this.pastilhas.isEmpty()) {
            desenhaVitoria(app);
            return true;
        } else if (this.vidas == 0) {
            desenhaDerrota(app);
            return true;
        }
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
        ArrayList<ArrayList<Elemento>> matrizJogo = new ArrayList<ArrayList<Elemento>>();

        try {
            // Read into the file
            File arquivo = new File("mapa.txt");
            Scanner scan = new Scanner(arquivo);

            int y = 0;
            while (scan.hasNextLine()) {
                String linha = scan.nextLine();
                // Create an array for the current row
                ArrayList<Elemento> linhaAtual = new ArrayList<Elemento>();
              
                int x = 0;
                // Iterate through the line
                for (char idElemento : linha.toCharArray()) {

                    int id = Character.getNumericValue(idElemento);
                    // acha o caminho da imagem no hashmap
                    String caminhoImagem = mapElementos.get(idElemento);
                    // carrega a imagem
                    PImage imagem = app.loadImage(caminhoImagem);
                    Elemento elem = new Elemento(idElemento, x, y, imagem);
                    
                    Estatico estatico = estatico = new Estatico(idElemento, x, y, imagem);
                    
                    if(id >= 1 && id <= 6){ // é uma parede
                        estatico.setParede(true);
                        paredes.add(estatico);
                    }

                    else if(id == 7){
                        estatico.setPastilha(true);
                        pastilhas.add(estatico);
                    }

                    else if(id == 8){
                        estatico.setSuperPastilha(true);
                        superPastilhas.add(estatico);
                    }

                    else if(idElemento == 'p') {
                        this.pacMan =  new PacMan(idElemento, x, y, imagem, app);
                    }

                    else if(idElemento == 'a'){
                        Rosa rosa = new Rosa(idElemento,x,y,imagem, app);
                        fantasmas.add(rosa);
                    }

                    else if(idElemento == 'c'){
                        Vermelho vermelho = new Vermelho(idElemento, x,y,imagem,app);
                        fantasmas.add(vermelho);
                    }

                    else if(idElemento == 'i'){
                        Laranja laranja = new Laranja(idElemento, x,y,imagem, app);
                        fantasmas.add(laranja);
                    }

                    else if(idElemento == 'w'){
                        Azul azul = new Azul(idElemento, x,y,imagem, app);
                        fantasmas.add(azul);
                    }
                    
                    linhaAtual.add(elem);
                    x += 16;
                }
                
                matrizJogo.add(linhaAtual);
                y += 16;
            }
            
            this.mapa = matrizJogo;           
        }
        
        catch (FileNotFoundException fnfe) {
            fnfe.printStackTrace();
        }
        
        
    }
    
    public void desenhaMapa(){
        for(ArrayList<Elemento> linha : this.mapa){
            for(Elemento elem : linha){
                
                if (!("aciwg".contains(Character.toString(elem.getIdElemento())))) {
                    app.image(elem.getImagem(), elem.getX(), elem.getY());
                }
            }
        }
    }

    public App getApp() {
        return app;
    }

    public void setApp(App app) {
        this.app = app;
    }

    public ArrayList<ArrayList<Elemento>> getMapa() {
        return mapa;
    }

    public void setMapa(ArrayList<ArrayList<Elemento>> mapa) {
        this.mapa = mapa;
    }

    public String getNomeArquivo() {
        return nomeArquivo;
    }

    public void setNomeArquivo(String nomeArquivo) {
        this.nomeArquivo = nomeArquivo;
    }

    public int getVidas() {
        return vidas;
    }

    public void setVidas(int vidas) {
        this.vidas = vidas;
    }

    public int getVelocidade() {
        return velocidade;
    }

    public void setVelocidade(int velocidade) {
        this.velocidade = velocidade;
    }

    public int getTempoAssustado() {
        return tempoAssustado;
    }

    public void setTempoAssustado(int tempoAssustado) {
        this.tempoAssustado = tempoAssustado;
    }

    public HashMap<Character, String> getMapElementos() {
        return mapElementos;
    }

    public void setMapElementos(HashMap<Character, String> mapElementos) {
        this.mapElementos = mapElementos;
    }

    public PacMan getPacMan() {
        return pacMan;
    }

    public void setPacMan(PacMan pacMan) {
        this.pacMan = pacMan;
    }

    public ArrayList<Vivo> getFantasmas() {
        return fantasmas;
    }

    public void setFantasmas(ArrayList<Vivo> fantasmas) {
        this.fantasmas = fantasmas;
    }

    public ArrayList<Estatico> getParedes() {
        return paredes;
    }

    public void setParedes(ArrayList<Estatico> paredes) {
        this.paredes = paredes;
    }

    public ArrayList<Estatico> getFrutas() {
        return frutas;
    }

    public void setFrutas(ArrayList<Estatico> frutas) {
        this.frutas = frutas;
    }

    public ArrayList<Estatico> getPastilhas() {
        return pastilhas;
    }

    public void setPastilhas(ArrayList<Estatico> pastilhas) {
        this.pastilhas = pastilhas;
    }

    public ArrayList<Estatico> getSuperPastilhas() {
        return superPastilhas;
    }

    public void setSuperPastilhas(ArrayList<Estatico> superPastilhas) {
        this.superPastilhas = superPastilhas;
    }

    public boolean isResetarGame() {
        return resetarGame;
    }

    public void setResetarGame(boolean resetarGame) {
        this.resetarGame = resetarGame;
    }

    public ArrayList<Integer> getTamanhoModos() {
        return tamanhoModos;
    }

    public void setTamanhoModos(ArrayList<Integer> tamanhoModos) {
        this.tamanhoModos = tamanhoModos;
    }

    public boolean isPerseguindo() {
        return perseguindo;
    }

    public void setPerseguindo(boolean perseguindo) {
        this.perseguindo = perseguindo;
    }
    
    
}
        

