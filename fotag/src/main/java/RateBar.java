import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
public class RateBar extends JPanel implements Observer{
    ImageModel imgModel;
    Star[] stars;

    public RateBar(Model model, ImageModel imgModel) {
        this.imgModel = imgModel;
        this.imgModel.addObserver(this);

        this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));

        this.stars = new Star[5];

        for (int i = 0; i < 5; ++i) {
            stars[i] = new Star(i+1, false);
            stars[i].setAlignmentX(Box.CENTER_ALIGNMENT);
            stars[i].addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    int newRating = ((Star)(e.getSource())).starNumber;
                    imgModel.userRate = newRating;
                    //System.out.println("New user rating for picture is: " + newRating);

                    imgModel.notifyObservers();
                    model.notifyObservers();
                }
            });
            this.add(stars[i]);
        }

    }

    public void update(Object observer) {

        //System.out.println("User Rating for the picture is: " + this.imgModel.userRate);
        if (this.imgModel.userRate > 0) {
            for(int i=0; i<this.imgModel.userRate; ++i) {
                stars[i].isColored = true;
            }
            for(int i=this.imgModel.userRate; i<5; ++i) {
                stars[i].isColored = false;
            }
        }
        else {
            for(int i=0; i<5; ++i) {
                stars[i].isColored = false;

            }
        }


        this.revalidate();
        this.repaint();
    }
}
