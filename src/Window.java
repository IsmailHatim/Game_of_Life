import javax.swing.*;

public class Window extends JFrame {


    public Window(String title, int width, int height){
        super(title);
        this.setSize(width,height);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
