import java.awt.*;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class Matrix {
    private static ArrayList<ArrayList<Cell>> state = new ArrayList<>();
    private static ArrayList<ArrayList<Cell>> next_state = new ArrayList<>();
    int iter;
    private static int size;


    public Matrix(int size){
        this.size = size;
        this.iter = 0;
        for (int i = 0; i < size; i++){
            ArrayList<Cell> line = new ArrayList<>();
            for (int j = 0; j < size; j++){
                Cell c = new Cell(i,j, true);
                line.add(c);

            }
            state.add(line);
        }
        set_all_neighbours();

    }

    public void add_life(int x, int y){
        Cell c = state.get(x).get(y);
        c.setDead(false);
        c.setNeighbours(get_Cell_neighbours(c));
        for (int i = 0; i < c.neighbours.size(); i++){
            c.neighbours.get(i).setNeighbours(get_Cell_neighbours(c.neighbours.get(i)));
        }
    }

    public void show(Graphics g){
        for (int i = 0; i < size; i++){
            for (int j = 0; j < size; j++) {
                Cell c = state.get(i).get(j);
                if (!c.dead) {
                    c.show(g,Color.black,iter);
                }
            }
        }
    }

    public void update(){
        for (int i = 0; i < size; i++){
            ArrayList<Cell> line = new ArrayList<>(size);
            for (int j = 0; j < size; j++){
                Cell c = state.get(i).get(j);
                c.update();
                line.add(new Cell(c.getX(),c.getY(),c.getDead_next()));
            }
            next_state.add(line);
        }
        state = next_state;
        set_all_neighbours();
        next_state = new ArrayList<>();
        this.iter++;
    }

    public void set_all_neighbours(){
        for (int i = 0; i < size; i++){
            for (int j = 0; j < size; j++){
                Cell c = state.get(i).get(j);
                c.setNeighbours(get_Cell_neighbours(c));
            }
        }
    }
    
    public void reset(){
        for (int i = 0; i < size; i++){
            for (int j = 0; j < size; j++){
                Cell c = state.get(i).get(j);
                c.setDead(true);
            }
        }
        for (int i = 0; i < size; i++){
            for (int j = 0; j < size; j++){
                Cell c = state.get(i).get(j);
                c.setNeighbours(get_Cell_neighbours(c));
            }
        }
    }

    public void displayGrid(Graphics g, int stroke){
        Graphics2D g2 = (Graphics2D) g;
        g2.setColor(Color.black);
        g2.setStroke(new BasicStroke(stroke));
        for (int i = 1; i <= size; i++){
            int horizontal_pavage = i*(GameOfLife.WINDOW_WIDTH/GameOfLife.MATRIX_SIZE);
            g2.draw(new Line2D.Double(horizontal_pavage,0,horizontal_pavage,GameOfLife.WINDOW_HEIGHT));
        }
        for (int j = 1; j <= size; j++){
            int vertical_pavage = j*(GameOfLife.WINDOW_HEIGHT/GameOfLife.MATRIX_SIZE);
            g2.draw(new Line2D.Double(0,vertical_pavage,GameOfLife.WINDOW_WIDTH,vertical_pavage));
        }
    }

    public static ArrayList<Cell> get_Cell_neighbours(Cell c){
        ArrayList<Cell> nb = new ArrayList<>();
        if (c.x > 0){
            nb.add(state.get(c.x - 1).get(c.y));
            if (c.y > 0){
                nb.add(state.get(c.x - 1).get(c.y - 1));
            }
            if (c.y < size - 1){
                nb.add(state.get(c.x - 1).get(c.y + 1));
            }
        }

        if (c.x < size - 1){
            nb.add(state.get(c.x + 1).get(c.y));
            if (c.y > 0){
                nb.add(state.get(c.x + 1).get(c.y - 1));
            }
            if (c.y < size - 1){
                nb.add(state.get(c.x + 1).get(c.y + 1));
            }
        }
        if (c.y > 0){
            nb.add(state.get(c.x).get(c.y - 1));
        }
        if (c.y < size - 1){
            nb.add(state.get(c.x).get(c.y + 1));
        }


        return nb;
    }



    @Override
    public String toString(){

        return "Matrix :\n" + state;
    }

    public int getSize(){return this.size;}
    public Cell getCell(int x, int y){return state.get(x).get(y);}

}
