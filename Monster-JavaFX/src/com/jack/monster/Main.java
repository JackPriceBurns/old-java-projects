package com.jack.monster;

import com.jack.monster.entities.Monster;
import com.jack.monster.entities.Player;
import com.jack.monster.tiles.*;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.Random;


public class Main extends Application {

    private KeyListener keyListener;
    private Tile[][] grid;
    private int height = 19, width = 29;
    private Player player;
    private boolean loop = true;
    private PlayerTile playerTile = new PlayerTile();
    private GridPane gridPane;
    private Item[] inventory;
    private Random random;
    private int startX = 2;
    private int startY = 2;
    private Monster monster;

    @Override
    public void start(Stage primaryStage) throws Exception {

        grid = new Tile[width][height];

        random = new Random();

        new Generator(this);

        player = new Player(new Location(0, 0));
        monster = new Monster(new Location(width - 1, height - 1));

        keyListener = new KeyListener(this);

        Group root = new Group();
        Scene scene = new Scene(root);
        scene.setFill(Color.WHITE);
        gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setVgap(0);
        gridPane.setHgap(0);

        draw();

        root.getChildren().add(gridPane);

        primaryStage.setTitle("Monster Game");
        primaryStage.setScene(scene);
        primaryStage.addEventHandler(KeyEvent.ANY, keyListener);
        primaryStage.show();
    }

    public void setTile(Tile tile, int x, int y){
        ImageView imageView = tile.getImageView();
        if(tile.isShrouded()){
            ColorAdjust colorAdjust = new ColorAdjust();
            colorAdjust.setBrightness(0);
            imageView.setEffect(colorAdjust);
        }
        gridPane.add(imageView, x, y);
    }

    public void draw() {
        int gridX = 0;
        for(Tile[] x : grid) {
            int gridY = 0;
            for (Tile y : x) {
                ImageView imageView = new ImageView();
                if(player.getLocation().getX() == gridX && player.getLocation().getY() == gridY){
                    setTile(new PlayerTile(), gridX, gridY);
                } else if(monster.getLocation().getX() == gridX && monster.getLocation().getY() == gridY) {
                    setTile(new MonsterTile(), gridX, gridY);
                } else {
                    if (y instanceof Tile) {
                        if(y.getImageView() == null){
                            throw new NullPointerException();
                        }
                        setTile(y, gridX, gridY);
                    } else {
                    }
                }
                gridY++;
            }
            gridX++;
        }
    }

    private void print(String str) {
        System.out.println(str);
    }

    private void print(String str, boolean newLine) {
        if(newLine){
            System.out.println(str);
        } else {
            System.out.print(str);
        }
    }

    public KeyListener getKeyListener() {
        return keyListener;
    }

    public static void main(String[] args) {
        launch(args);
    }

    public Tile[][] getGrid(){
        return grid;
    }

    public Player getPlayer() {
        return player;
    }

    public GridPane getGridPane() {
        return gridPane;
    }

    public void messageBox(String title, String header,String msg){
        System.out.println(title + ":" + msg);

        Alert alert = new Alert(Alert.AlertType.INFORMATION);

        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(msg);

        alert.showAndWait();
    }

    public Monster getMonster() {
        return monster;
    }


    public int getHeight(){
        return height;
    }

    public int getWidth(){
        return width;
    }

    public void setGrid(Tile[][] grid) {
        this.grid = grid;
    }
}