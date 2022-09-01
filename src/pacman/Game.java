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
import processing.core.PImage;


public class Game {
    public App app;
    public ArrayList<ArrayList<Elemento>> mapa;
    public String nomeArquivo;
    public int vidas;
    public int velocidade;
    public int tempoAssustado;
    public String filename;
    public HashMap<Character, String> mapElementos;
    public ArrayList<Integer> tamanhoModos;
    //public pacMan PacMan;
    //public ArrayList<Fanstasma> fantasmas;
    //public ArrayList<Elemento> paredes;
    //public ArrayList<Elemento> frutas;
    public int frutas;
    public boolean resetarGame;

    public Game(App app) {
        this.app = app;
        this.mapElementos = new HashMap<Character, String>();
        this.mapa = new ArrayList<ArrayList<Elemento>>();
        //this.paredes = new ArrayList<GameObject>();
        //this.fantasmas = new ArrayList<Fantasma>();
        this.tamanhoModos = new ArrayList<Integer>();
        //this.pacMan = null;
        this.nomeArquivo = "mapa.txt";
        
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
    
    
    
    public void parseJSON(App app) {
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
    
    public boolean checkWinOrLose(App app) {
        if (this.frutas == 0) {
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

                    // acha o caminho da imagem no hashmap
                    String caminhoImagem = mapElementos.get(idElemento);
                    // carrega a imagem
                    PImage imagem = app.loadImage(caminhoImagem);
                    Elemento elem = new Elemento(x, y, imagem);
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
                if(elem != null){
                    app.image(elem.getImagem(), elem.getX(), elem.getY());
                }
            }
        }
        
    }
}
        

