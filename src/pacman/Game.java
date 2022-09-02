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
import processing.core.PImage;


public class Game {
    public App app;
    public ArrayList<ArrayList<Elemento>> mapa;
    public String nomeArquivo;
    public int vidas;
    public int velocidade;
    public int tempoAssustado;
    public HashMap<Character, String> mapElementos;
    //public ArrayList<Integer> tamanhoModos;
    public PacMan pacMan;
    public ArrayList<Vivo> fantasmas;
    public ArrayList<Estatico> paredes;
    public ArrayList<Estatico> frutas;
    public ArrayList<Estatico> pastilhas;
    public ArrayList<Estatico> superPastilhas;
    public boolean resetarGame;

    public Game(App app) {
        this.app = app;
        this.mapElementos = new HashMap<Character, String>();
        this.mapa = new ArrayList<ArrayList<Elemento>>();
        this.paredes = new ArrayList<Estatico>();
        this.frutas = new ArrayList<Estatico>();
        this.pastilhas = new ArrayList<Estatico>();
        this.superPastilhas = new ArrayList<Estatico>();
        this.fantasmas = new ArrayList<Vivo>();
        //this.tamanhoModos = new ArrayList<Integer>();
        this.pacMan = null;
        
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
    
    public boolean vitoritaOuDerrota(App app) {
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
        app.text("VITÓRIA!!!", 160, 260);
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
                    
                    if(id >= 0 && id <= 6){ // é uma parede
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
                        this.pacMan =  new PacMan(idElemento, x, y, imagem);
                        this.pacMan.app = this.app;
                    }

                    else if(idElemento == 'a'){
                        Rosa rosa = new Rosa(idElemento,x,y,imagem);
                        fantasmas.add(rosa);
                    }

                    else if(idElemento == 'c'){
                        Vermelho vermelho = new Vermelho(idElemento, x,y,imagem);
                        fantasmas.add(vermelho);
                    }

                    else if(idElemento == 'i'){
                        Laranja laranja = new Laranja(idElemento, x,y,imagem);
                        fantasmas.add(laranja);
                    }

                    else if(idElemento == 'w'){
                        Azul azul = new Azul(idElemento, x,y,imagem);
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
                //if (!("aciwgp".contains(Character.toString(elem.getIdElemento())))) {
                    app.image(elem.getImagem(), elem.getX(), elem.getY());
                //}
            }
        }
    }
}
        

