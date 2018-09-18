
import java.awt.image.BufferedImage;
import java.nio.file.Path;
import java.util.*;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.nio.file.Paths;
import java.time.LocalDate;

public class ImageModel implements java.io.Serializable {

    /** The observers that are watching this model for changes. */
    ArrayList<Observer> observers;
    public Model model;
    int userRate;
    public LocalDate uploadDate;
    public transient BufferedImage image;
    public String imagePath;
    public boolean painted;


    public ImageModel(Model model) {
        this.model = model;
        this.observers = new ArrayList<>();
        this.userRate = 0;
        painted = false;

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
        //System.out.println("No of observers for Image Model are: " + this.observers.size());
        for (Observer observer: this.observers) {
            observer.update(this);
        }
    }

    public String fileName() {

        return Paths.get(this.imagePath).getFileName().toString();
    }

}
