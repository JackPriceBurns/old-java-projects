package com.jack.chess;

import com.jack.chess.pieces.*;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.HashMap;
import java.util.HashSet;

public class Main extends Application {

    private HashSet<Piece> pieces;
    private int width = 8, height = 8;
    private boolean whiteTurn = true;
    private GridPane gridPane;
    private MouseListener mouseListener = new MouseListener(this);
    private Piece selected = null;
    private HashSet<Location> highlighted = new HashSet<Location>();
    private Location active;

    @Override
    public void start(Stage primaryStage) throws Exception{

        Group root = new Group();
        Scene scene = new Scene(root);
        scene.setFill(Color.WHITE);
        gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setVgap(0);
        gridPane.setHgap(0);

        pieces = new HashSet<Piece>();

        addTiles();
        newGame();

        root.getChildren().add(gridPane);

        scene.addEventHandler(MouseEvent.MOUSE_CLICKED, mouseListener);
        primaryStage.setTitle("Chess!");
        primaryStage.setScene(scene);
        primaryStage.setMaxHeight(828);
        primaryStage.setMaxWidth(804);
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    private void newGame() {
        setupPieces();
        drawPieces();
    }

    private void setupPieces() {
        String format = "RNBQKBNR/PPPPPPPP/////pppppppp/rnbqkbnr";
        String[] ranks = format.split("/");
        for(int y = 0; y < ranks.length; y++){
            for(int x = 0; x < ranks[y].split("").length; x++){
                addPiece(ranks[y].split("")[x], x, y);
            }
        }

    }

    private void addPiece(String s, int x, int y) {
        if(!(s.length() < 1)){
            Piece piece = null;
            if(s.equalsIgnoreCase("P")){
                if (s.toLowerCase().equals(s)) {
                    piece = new Pawn(true, new Location(x, y), this);
                } else {
                    piece = new Pawn(false, new Location(x, y), this);
                }
            }
            if(s.equalsIgnoreCase("B")){
                if (s.toLowerCase().equals(s)) {
                    piece = new Bishop(true, new Location(x, y), this);
                } else {
                    piece = new Bishop(false, new Location(x, y), this);
                }
            }
            if(s.equalsIgnoreCase("N")){
                if (s.toLowerCase().equals(s)) {
                    piece = new Knight(true, new Location(x, y), this);
                } else {
                    piece = new Knight(false, new Location(x, y), this);
                }
            }
            if(s.equalsIgnoreCase("R")){
                if (s.toLowerCase().equals(s)) {
                    piece = new Rook(true, new Location(x, y), this);
                } else {
                    piece = new Rook(false, new Location(x, y), this);
                }
            }
            if(s.equalsIgnoreCase("Q")){
                if (s.toLowerCase().equals(s)) {
                    piece = new Queen(true, new Location(x, y), this);
                } else {
                    piece = new Queen(false, new Location(x, y), this);
                }
            }
            if(s.equalsIgnoreCase("K")){
                if (s.toLowerCase().equals(s)) {
                    piece = new King(true, new Location(x, y), this);
                } else {
                    piece = new King(false, new Location(x, y), this);
                }
            }
            if(piece != null) {
                pieces.add(piece);
            } else {
                System.exit(-1);
            }
        }
    }

    private void addTiles() {

        for(int x = 0; x < width; x++){

            for(int y = 0; y < height; y++){

                if(!isOdd(x) && !isOdd(y) || (isOdd(x) && isOdd(y))){

                    ImageView imageView = new ImageView();
                    imageView.setImage(new Image("file:" + this.getClass().getResource("src/whiteTile.jpg").getPath()));
                    gridPane.add(imageView, x, y);

                } else {

                    ImageView imageView = new ImageView();
                    imageView.setImage(new Image("file:" + this.getClass().getResource("src/blackTile.jpg").getPath()));
                    gridPane.add(imageView, x, y);

                }

            }

        }

        for(Location location : highlighted){

            ImageView imageView = new ImageView();
            imageView.setImage(new Image(this.getClass().getResource("src/highlight.png").toExternalForm()));
            gridPane.add(imageView, location.getX(), location.getY());

        }

        if(active != null){

            ImageView imageView = new ImageView();
            imageView.setImage(new Image(this.getClass().getResource("src/active.png").toExternalForm()));
            gridPane.add(imageView, active.getX(), active.getY());

        }

    }

    private boolean isOdd(int x) {
        return (double) x / 2 > x / 2;
    }

    private void drawPieces() {
        pieces.forEach(this::drawPiece);
    }

    private void drawPiece(Piece piece){
        ImageView imageView = new ImageView();
        imageView.setFitWidth(100);
        imageView.setFitHeight(100);
        imageView.setImage(piece.getImage());
        gridPane.add(imageView, piece.getCurrentLoc().getX(), piece.getCurrentLoc().getY());
    }


    public static void main(String[] args) {
        launch(args);
    }

    public void clickAt(Location location) {

        Piece atLoc = getPiece(location);
        highlighted.clear();
        active = null;

        System.out.println("t1");

        if(selected != null) {

            System.out.println("t3");

            if (atLoc != null) {

                System.out.println("t4");

                if (selected.validMove(location) && ((whiteTurn && selected.isWhite()) || (!whiteTurn && !selected.isWhite())) && ((atLoc.isWhite() && !selected.isWhite()) || (!atLoc.isWhite() && selected.isWhite()))) {

                    System.out.println("t6");

                    pieces.remove(atLoc);
                    selected.setCurrentLoc(location);
                    selected.setHasMoved(true);

                    if (whiteTurn) {
                        whiteTurn = false;
                    } else {
                        whiteTurn = true;
                    }

                } else {

                    System.out.println("t7");

                    if (atLoc != null) {
                        active(location);
                        selected = atLoc;
                        if((atLoc.isWhite() && !selected.isWhite()) || (!atLoc.isWhite() && selected.isWhite())) {
                            highlight(selected.getValidMoves());
                        }
                    }

                }

            } else {

                System.out.println("t5");

                if (selected.validMove(location) && ((whiteTurn && selected.isWhite()) || (!whiteTurn && !selected.isWhite()))) {

                    selected.setCurrentLoc(location);
                    selected.setHasMoved(true);

                    if (whiteTurn) {
                        whiteTurn = false;
                    } else {
                        whiteTurn = true;
                    }

                }

            }

        } else {

            System.out.println("t2");

            if (atLoc != null) {

                highlight(atLoc.getValidMoves());
                active(location);
                selected = atLoc;

            } else {

                highlight(location);

            }

        }

        redrawBoard();

    }

    private void active(Location location) {

        active = location;

    }

    private void redrawBoard() {
        addTiles();
        drawPieces();
    }

    public Piece getPiece(Location location){
        Piece found = null;
        for(Piece piece : pieces){
            if(piece.getCurrentLoc().getX() == location.getX() && piece.getCurrentLoc().getY() == location.getY()){
                found = piece;
            }
        }

        if(found != null){
            return found;
        } else {
            return null;
        }
    }

    private void highlight(HashSet<Location> locations){
        locations.forEach(this::highlight);
    }

    private void highlight(Location location) {
        highlighted.add(location);
    }

    public boolean isWhiteTurn() {
        return whiteTurn;
    }
}
