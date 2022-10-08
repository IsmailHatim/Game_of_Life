import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.TimerTask;

public class GameOfLife extends JPanel implements ActionListener, MouseListener, MouseMotionListener{
    /*Still need to fix a bug, when drawing the game is not totally working at the beginning, but it was really fun to code*/


    
    public static final int WINDOW_WIDTH = 800;
    public static final int WINDOW_HEIGHT = 800;
    public static final int MATRIX_SIZE = 100;

    public static int mouse_x = 0;
    public static int mouse_y = 0;

    public static boolean launched = false;
    public static boolean grid_displayed = false;
    public static boolean stopped = false;
    public static boolean iterated = false;
    public  static boolean dragged = false;
    public static JLabel iter_text;

    Matrix m;



    public GameOfLife(){
        super();
        this.m = new Matrix(MATRIX_SIZE);
        JButton launch = new JButton("Launch");
        JButton iterate = new JButton("Iterate");
        JButton display_grid = new JButton("Display Grid");
        JButton pause = new JButton("Pause");
        JButton stop = new JButton("Stop");
        iter_text = new JLabel("Iterations : " + m.iter);
        iter_text.setFont(new Font("Verdana",1,20));
        launch.addActionListener(this);
        iterate.addActionListener(this);
        display_grid.addActionListener(this);
        pause.addActionListener(this);
        stop.addActionListener(this);
        //
        launch.setBackground(Color.black);
        launch.setForeground(Color.white);
        iterate.setBackground(Color.black);
        iterate.setForeground(Color.white);
        display_grid.setBackground(Color.black);
        display_grid.setForeground(Color.white);
        pause.setBackground(Color.black);
        pause.setForeground(Color.white);
        stop.setBackground(Color.black);
        stop.setForeground(Color.white);
        //
        this.add(display_grid);
        this.add(launch);
        this.add(iterate);
        this.add(pause);
        this.add(stop);
        this.add(iter_text);
    }


    public static void main(String args[]){
        Window win = new Window("Game of Gyvenimas",WINDOW_WIDTH,WINDOW_HEIGHT);
        GameOfLife g = new GameOfLife();

        g.setBackground(Color.white);
        win.setLocation(200,200);
        win.add(g);
        win.addMouseListener(g);
        win.addMouseMotionListener(g);



        win.setVisible(true);
        g.m.add_life(20,20);
        g.m.add_life(20,21);
        g.m.add_life(20,22);
        Cell c = g.m.getCell(20,21);
        int nbb = Matrix.get_Cell_neighbours(c).size();
        grid_displayed = true;
        System.out.println(c.getNeighbours());
        begin(g);

    }


    public static void begin(GameOfLife g){
        java.util.Timer t = new java.util.Timer();
        t.schedule(new TimerTask() {
            @Override
            public void run() {
                //System.out.println(g.m.getSize()*(mouse_x/WINDOW_WIDTH) + "," + mouse_y);
                g.repaint();
                iter_text.setText("Iterations : " + g.m.iter);
                if (launched) {
                    g.m.update();
                    if (stopped) {
                        g.m.reset();
                        g.m.iter = 0;
                        launched = false;
                    }

                } else if (iterated) {
                    g.m.update();
                    System.out.println(g.m.getCell(20,21).getNeighbours());
                    iterated = false;
                }

                if (dragged) {
                    g.m.add_life((int) (g.m.getSize() * ((double) mouse_x / WINDOW_WIDTH)), (int) (g.m.getSize() * ((double) mouse_y / WINDOW_HEIGHT)));
                }

            }
        }, 0, 16);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        this.m.show(g);
        if (grid_displayed){
            this.m.displayGrid(g,1);
        }
    }




    @Override
    public void actionPerformed(ActionEvent e) {
        String cmd = e.getActionCommand();
        switch (cmd){
            case "Launch":
                stopped = false; // Not necessary, I think
                launched = true;

                break;
            case "Display Grid":
                if (grid_displayed){
                    grid_displayed = false;
                }
                else{
                    grid_displayed = true;
                }
            case "Pause":
                launched = false;
                break;
            case "Stop":
                launched = false;
                stopped = true;
                break;
            case "Iterate":
                iterated = true;
                break;
        }

    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        dragged = false;
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mouseDragged(MouseEvent e) {
        mouse_x = e.getX() - 12;
        mouse_y = e.getY() - 30;
        dragged = true;
    }

    @Override
    public void mouseMoved(MouseEvent e) {
    }
}
