import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class FilterBar extends JPanel implements Observer{
    Model model;
    Star[] stars;

    public FilterBar(Model model) {
        //System.out.println("Filter Bar initialisation");
        this.model = model;
        this.setBorder(BorderFactory.createLineBorder(Color.lightGray));
        stars = new Star[5];
        this.setLayout(new FlowLayout(FlowLayout.LEFT));
        for (int i = 0; i < 5; ++i) {
            if (i == 0) {
                stars[i] = new Star(i+1, true);
            }
            else {
                stars[i] = new Star(i+1, false);
            }
            stars[i].addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    int filtervalue = ((Star)e.getSource()).starNumber;
                    model.FilterValue = filtervalue;
                    model.notifyObservers();
                }
            });
            this.add(stars[i]);

        }

    }



    public void update(Object observer) {
        //System.out.println("Update to Filter Bar called");

        for (int i = 0; i < this.model.FilterValue; ++i) {
            stars[i].isColored = true;
        }
        for (int i = this.model.FilterValue; i < 5; ++i) {
            stars[i].isColored = false;
        }

        this.revalidate();
        this.repaint();
    }
}
