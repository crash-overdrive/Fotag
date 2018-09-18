import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.io.File;
import java.io.*;

public class Main {
    /*
    static FileOutputStream fileOut;
    static ObjectOutputStream objectOut;

    static FileInputStream fileIn;
    static ObjectInputStream objectIn;*/


    public static void main(String[] args)  {

        Model model = new Model();
        ToolbarView toolbarView = new ToolbarView(model);
        ImagesView imagesView = new ImagesView(model);
        loadState(model, new File("./savefile"));

        model.addObserver(toolbarView);
        model.addObserver(imagesView);

        JFrame frame = new JFrame("Fotag!! - Created with <3 by Shashwat Pratap");
        frame.setPreferredSize(new Dimension(800,600));
        //frame.setMinimumSize(new Dimension(128,128));
        frame.setMinimumSize(new Dimension(360,360));

        JScrollPane scrollBar = new JScrollPane(imagesView, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scrollBar.setPreferredSize(new Dimension(600, 600));
        frame.setLayout(new BorderLayout());
        frame.add(toolbarView, BorderLayout.PAGE_START);
        frame.add(scrollBar, BorderLayout.CENTER);

        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        frame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {



                try {
                    FileOutputStream fileOutputStream = new FileOutputStream(new File("./savefile"));
                    ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
                    objectOutputStream.writeObject(model);
                    objectOutputStream.close();
                    fileOutputStream.close();
                } catch (Exception exception) {
                    System.out.println("Couldn't save state: " + exception);
                }
            }
        });

        frame.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                super.componentResized(e);
                model.screenWidth = scrollBar.getViewport().getWidth();
                //System.out.println("No of columns are " + model.columnCount());
                model.notifyObservers();
            }
        });

    }

    public static void loadState(Model mainModel, File file) {
        FileInputStream fileInputStream;
        ObjectInputStream objectInputStream;
        try {
            fileInputStream = new FileInputStream(file);
            objectInputStream = new ObjectInputStream(fileInputStream);
            Model deserializedModel = (Model) objectInputStream.readObject();
            fileInputStream.close();
            objectInputStream.close();
            mainModel.load(deserializedModel);
        }
        catch (Exception exception) {
            System.out.println("Couldn't load state");
        }
    }

}
