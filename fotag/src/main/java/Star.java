import javax.swing.*;
import java.awt.*;
public class Star extends JButton {
    public Boolean isColored;
    public int starNumber;

    public Star(int starNumber, Boolean isColored) {
        this.setPreferredSize(new Dimension(36, 36));
        this.isColored = isColored;
        this.starNumber = starNumber;
        if (isColored) {
            this.setIcon(new ImageIcon("Resources/images/filled-star.png"));
        }
        else {
            this.setIcon(new ImageIcon("Resources/images/empty-star.png"));
        }
    }

    public void paintComponent(Graphics g) {
        //System.out.println("paintComponent of Stars Called!");
        super.paintComponent(g);
        if (this.isColored) {
            this.setIcon(new ImageIcon("Resources/images/filled-star.png"));
        }
        else {
            this.setIcon(new ImageIcon("Resources/images/empty-star.png"));
        }
        this.revalidate();
    }
}
