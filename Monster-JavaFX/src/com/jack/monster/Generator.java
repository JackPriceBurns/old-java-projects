package com.jack.monster;

import com.jack.monster.tiles.GroundTile;
import com.jack.monster.tiles.WallTile;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class Generator {

    private Tile[][] maze;
    private int height;
    private int width;
    private Set<Location> cells;
    private Set<Location> usedCells = new HashSet<Location>();
    private Set<Location> notDeadCells = new HashSet<Location>();
    private Random random = new Random();
    private Main main;

    public Generator(Main main){
        maze = main.getGrid();
        height = main.getHeight() - 1;
        width = main.getWidth() - 1;

        this.main = main;

        getCells();

        generateMaze();
    }

    private void generateGrid(){
        main.setGrid(maze);
    }

    public void getCells() {
        int tileX = 0;
        Set<Location> cells = new HashSet<Location>();

        for(Tile[] x : maze){
            int tileY = 0;
            for(Tile y : x) {
                if(tileX % 2 == 0 && tileY % 2 == 0) {
                    cells.add(new Location(tileX, tileY));
                }
                tileY++;
            }
            tileX++;
        }
        this.cells = cells;
    }

    private Set<Location> getAdjacentCells(Location loc){
        Set<Location> result = new HashSet<Location>();

        if(loc.getX() - 2 >= 0){
            result.add(new Location(loc.getX() - 2, loc.getY()));
        }
        if(loc.getX() + 2 <= width){
            result.add(new Location(loc.getX() + 2, loc.getY()));
        }
        if(loc.getY() - 2 >= 0){
            result.add(new Location(loc.getX(), loc.getY() - 2));
        }
        if(loc.getY() + 2 <= height){
            result.add(new Location(loc.getX(), loc.getY() + 2));
        }

        return result;
    }

    private Set<Location> getNotUsedAdjacentCells(Location loc){
        Set<Location> adjacentCells = getAdjacentCells(loc);
        System.out.println("AdjCells: " + adjacentCells.size());
        Set<Location> notUsedAdjacentCells = new HashSet<Location>();
        for(Location adjLoc : adjacentCells){
            if(!containsLocation(usedCells, adjLoc)){
                notUsedAdjacentCells.add(adjLoc);
            }
        }
        return notUsedAdjacentCells;
    }

    private <T> T getRandObjInSet(Set<T> set){
        int size = set.size();
        int randItem = random.nextInt(size);
        int i = 0;
        for(T t : set){
            if(i == randItem){
                return t;
            }
            i++;
        }
        return null;
    }

    private void makePath(Location startingCell){
        Set<Location> nextCells = getNotUsedAdjacentCells(startingCell);
        System.out.println("Size: " + nextCells.size());
        while(nextCells.size() > 0){
            Location nextCell = getRandObjInSet(nextCells);
            int removeX = (startingCell.getX() + nextCell.getX()) / 2;
            int removeY = (startingCell.getY() + nextCell.getY()) / 2;
            maze[startingCell.getX()][startingCell.getY()] = new GroundTile();
            maze[removeX][removeY] = new GroundTile();
            System.out.println("RemoveX: " + removeX + " RemoveY: " + removeY);
            startingCell = nextCell;
            nextCells = getNotUsedAdjacentCells(startingCell);
            usedCells.add(startingCell);
        }
    }

    private boolean containsLocation(Set<Location> locations, Location location){
        for(Location loc : locations){
            if(loc.getX() == location.getX() && loc.getY() == location.getY()){
                return true;
            }
        }
        return false;
    }

    private void generateMaze(){
        int tileX = 0;
        for(Tile[] x : maze){
            int tileY = 0;
            for(Tile y: x){
                if(Math.floorMod(tileX - 1, 2) == 0 || Math.floorMod(tileY - 1, 2) == 0){
                    maze[tileX][tileY] = new WallTile();
                } else {
                    maze[tileX][tileY] = new GroundTile();
                }
                tileY++;
            }
            tileX++;
        }

        int tries = 0;

        while(usedCells.size() < cells.size()){
            if(usedCells.size() > 0){
                notDeadCells.clear();
                for(Location loc : usedCells){
                    if(getAdjacentCells(loc).size() > 0){
                        notDeadCells.add(loc);
                    }
                }
            }

            if(usedCells.size() == 0){
                Location newLoc = new Location(0, 0);
                usedCells.add(newLoc);
                makePath(newLoc);
            } else {
                makePath(getRandObjInSet(notDeadCells));
            }

            if(tries > 200){
                break;
            }
            tries++;
        }

        generateGrid();
    }

    private double mod(int x, int y){
        return ((x / y) - (int) (x / y));
    }
}
