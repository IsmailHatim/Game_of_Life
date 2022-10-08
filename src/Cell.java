import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

public class Cell {
    int x;
    int y;
    boolean dead;
    boolean dead_next;
    int alive_nb;


    ArrayList<Cell> neighbours = new ArrayList<>();
    private Rectangle2D box;

    public Cell(int x, int y, boolean dead) {
        this.x = x;
        this.y = y;
        this.dead = dead;
        this.dead_next = dead;
        setBox(GameOfLife.WINDOW_WIDTH / GameOfLife.MATRIX_SIZE);
    }


    public void update() {

        alive_nb = 0;
        for (int i = 0; i < neighbours.size(); i++) {
            if (!neighbours.get(i).dead) {
                alive_nb++;
            }
        }
        if (this.dead && alive_nb == 3) {
            setDead_next(false);
        }
        if (!this.dead && alive_nb != 2 && alive_nb != 3) {
            setDead_next(true);
        }
    }

    public void show(Graphics g, Color color, int iter) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setColor(color);
        g2.fill(box);
    }

    public void setBox(int size) {
        this.box = new Rectangle(x * size, y * size, size, size);
    }

    public void setBox(int width, int height) {
        this.box = new Rectangle(x * width, y * height, width, height);
    }


    @Override
    public String toString(){
        return "Cell : (" + x + "," + y + ")" + ", alive : " + !dead + ", neighbours alive :" + alive_nb;
    }


    public void setNeighbours(ArrayList<Cell> neighbours){this.neighbours = neighbours;}
    public void setDead(boolean dead){this.dead = dead;}
    public void setDead_next(boolean dead_next){this.dead_next = dead_next;}
    public void setAlive_nb(int alive_nb){this.alive_nb = alive_nb;}
    public int getX(){return this.x;}
    public int getY(){return this.y;}
    public boolean getDead(){return this.dead;}
    public boolean getDead_next(){return this.dead_next;}
    public ArrayList<Cell> getNeighbours(){return this.neighbours;}
    public int getAlive_nb(){return this.alive_nb;}
    

}
