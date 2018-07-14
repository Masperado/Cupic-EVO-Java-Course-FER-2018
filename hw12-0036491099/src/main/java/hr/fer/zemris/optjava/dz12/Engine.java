package hr.fer.zemris.optjava.dz12;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

public class Engine {

    private boolean[][] field;
    private boolean[][] initField;
    private int x;
    private int y;
    private int dir;
    private int kilaJanjetine;
    private int width;
    private int height;
    private StringBuilder sb;
    private int noOfAction;
    private int maxActions = 600;

    public Engine() {
        this.x = 0;
        this.y = 0;
        this.dir = 2;
        this.kilaJanjetine = 0;
        this.noOfAction = 0;
        this.sb = new StringBuilder();
    }

    public void reset() {
        this.field = Arrays.stream(this.initField).map(boolean[]::clone).toArray(boolean[][]::new);
        this.sb = new StringBuilder();
        this.x = 0;
        this.y = 0;
        this.dir = 2;
        this.kilaJanjetine = 0;
        this.noOfAction = 0;
    }

    public void parse(String path) throws IOException {

        List<String> lines = Files.readAllLines(Paths.get(path));

        String[] dimensions = lines.get(0).split("x");
        this.width = Integer.parseInt(dimensions[0]);
        this.height = Integer.parseInt(dimensions[1]);
        this.field = new boolean[width][height];


        if (lines.size() - 1 != height) {
            throw new IllegalArgumentException("Visina polja nije dobra!");
        }

        for (int i = 1; i < lines.size(); i++) {
            char[] food = lines.get(i).toCharArray();
            if (food.length != width) {
                throw new IllegalArgumentException("Širina polja nije dobra!");
            }

            for (int j = 0; j < width; j++) {
                if (food[j] == '1') {
                    field[i-1][j] = true;
                }
            }

        }

        this.initField = Arrays.stream(this.field).map(boolean[]::clone).toArray(boolean[][]::new);

    }

    public boolean imaliJanjetine(){
        boolean ima = false;

        switch (dir) {
            case 4:
                y--;
                if (y < 0) {
                    y = width - 1;
                }
                ima = field[x][y];
                y++;
                if (y > width - 1) {
                    y = 0;
                }
                break;
            case 3:
                x++;
                if (x > height - 1) {
                    x = 0;
                }
                ima = field[x][y];
                x--;
                if (x < 0) {
                    x = height - 1;
                }
                break;
            case 2:
                y++;
                if (y > width - 1) {
                    y = 0;
                }
                ima = field[x][y];
                y--;
                if (y < 0) {
                    y = width - 1;
                }
                break;
            case 1:
                x--;
                if (x < 0) {
                    x = height - 1;
                }
                ima = field[x][y];
                x++;
                if (x > height - 1) {
                    x = 0;
                }
                break;
        }

        return ima;
    }

    public void move() {
        noOfAction++;
        if (noOfAction>maxActions){
            return;
        }

        switch (dir) {
            case 4:
                y--;
                if (y < 0) {
                    y = width - 1;
                }
                break;
            case 3:
                x++;
                if (x > height - 1) {
                    x = 0;
                }
                break;
            case 2:
                y++;
                if (y > width - 1) {
                    y = 0;
                }
                break;
            case 1:
                x--;
                if (x < 0) {
                    x = height - 1;
                }
                break;

        }

        if (field[x][y]) {
            kilaJanjetine++;
            field[x][y] = false;

        }

        sb.append("MOVE|");
    }

    public void right() {
        noOfAction++;
        if (noOfAction>maxActions){
            return;
        }
        dir++;
        if (dir > 4) {
            dir = 1;
        }
        sb.append("RIGHT|");
    }


    public void left() {
        noOfAction++;
        if (noOfAction>maxActions){
            return;
        }
        dir--;
        if (dir < 1) {
            dir = 4;
        }
        sb.append("LEFT|");
    }

    public int getKilaJanjetine() {
        return kilaJanjetine;
    }

    public String getPath() {
        return sb.toString();
    }

    public int getNoOfAction() {
        return noOfAction;
    }

    public boolean moreActions(){
        return noOfAction<maxActions;
    }

    public boolean[][] getInitField() {
        return initField;
    }

    public int getX() {
        return x;
    }


    public int getY() {
        return y;
    }


    public int getDir() {
        return dir;
    }

}
