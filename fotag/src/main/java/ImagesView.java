import java.util.ArrayList;
import java.awt.*;
import javax.swing.*;

public class ImagesView extends JPanel implements Observer {

    private Model model;
    public ArrayList<ImageModel> imagesDisplayed;


    /**
     * Create a new View.
     */
    public ImagesView(Model model) {

        // Hook up this observer so that it will be notified when the model
        // changes.
        this.model = model;

        //TODO: Fix this
        this.setLayout(new GridLayout(0,this.model.columnCount()));

        this.imagesDisplayed = new ArrayList<>();

        this.setVisible(true);
        this.refreshImagesList();
        this.repaint();
    }

    public void refreshImagesList(){
        this.imagesDisplayed = new ArrayList<>();
        if (this.model.FilterEnabled) {
            for (ImageModel imageModel:this.model.Images) {
                if (imageModel.userRate >= model.FilterValue) {
                    this.imagesDisplayed.add(imageModel);
                }
            }
        }
        else {
            this.imagesDisplayed = this.model.Images;
        }

    }



    /**
     * Update with data from the model.
     */
    public void update(Object observable) {
        refreshImagesList();
        this.removeAll();
        for (ImageModel imgModel:this.imagesDisplayed) {
            imgModel.painted = false;
        }
        this.revalidate();
        this.repaint();
        //System.out.println("Images View changed!");
        /*for (ImageModel imgModel:this.imagesDisplayed) {
            imgModel.notifyObservers();
        }*/
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        this.setLayout(new GridLayout(0, this.model.columnCount()));
        for (ImageModel imgModel:this.imagesDisplayed) {
            if (imgModel.image != null && !imgModel.painted) {
                //clear
                imgModel.observers = new ArrayList<>();
                ImageView imageView = new ImageView(model, imgModel);

                this.add(imageView);
                imgModel.painted = true;
                imgModel.notifyObservers();

            }
        }
    }
}
