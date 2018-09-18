import java.util.ArrayList;
import java.io.File;
import java.io.*;
import javax.imageio.ImageIO;

public class Model implements java.io.Serializable {
    /** The observers that are watching this model for changes. */
    private ArrayList<Observer> observers;

    public ArrayList<ImageModel> Images;
    public int screenWidth;
    public transient int FilterValue;
    public transient boolean FilterEnabled;
    public boolean isGridView;



    /**
     * Create a new model.
     */
    public Model() {

        this.observers = new ArrayList<>();
        this.Images = new ArrayList<>();
        this.isGridView = true;
        this.FilterValue = 0;
        this.FilterEnabled = false;
        this.screenWidth = 800;
    }

    /**
     * Add an observer to be notified when this model changes.
     */
    public void addObserver(Observer observer) {

        this.observers.add(observer);
    }

    /**
     * Remove an observer from this model.
     */
    public void removeObserver(Observer observer) {

        this.observers.remove(observer);
    }

    /**
     * Notify all observers that the model has changed.
     */
    public void notifyObservers() {
        //System.out.println("No of observers for Model are: " + this.observers.size());
        for (Observer observer: this.observers) {
            observer.update(this);
        }/*
        for (ImageModel imgModel : Images) {
            imgModel.notifyObservers();
        }*/
    }

    public void addImage(File file){

        ImageModel imageModel = new ImageModel(this);
        imageModel.imagePath = file.getPath();

        try {
            imageModel.image = ImageIO.read(file);
            imageModel.uploadDate = java.time.LocalDate.now();
        }
        catch (IOException e) {
            System.out.println("Error reading data: " + e);
        }
        Images.add(imageModel);
    }

    public int imageCount() {
        int count = 0;
        if (this.FilterEnabled) {
            for (ImageModel im : this.Images) {
                if (im.userRate >= this.FilterValue) {
                    count++;
                }
            }
        } else {
            count = this.Images.size();
        }
        return count;
    }

    public int columnCount() {

        int column_count;
        if (!this.isGridView) {
           column_count = 1;
        } else {
            column_count =  Math.max(Math.min(this.screenWidth / Thumbnail.max_size.width, this.imageCount()), 1);
        }
        //System.out.println("Window width is: " + this.screenWidth);
        //System.out.println("Column count is: " + column_count);
        return  column_count;
    }

    public void load(Model model) {
        this.Images = model.Images;
        for (ImageModel imageModel: this.Images) {
            imageModel.observers = new ArrayList<>();
            try {
                imageModel.image = ImageIO.read(new File(imageModel.imagePath));
            }
            catch (IOException e) {
                System.out.println("Could not load in the saved session");
            }
        }

        this.notifyObservers();
    }


}
