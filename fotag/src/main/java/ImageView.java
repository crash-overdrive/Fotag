import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ImageView extends JPanel implements Observer{

    Model model;
    ImageModel imageModel;

    public JPanel mainBox;
    public JLabel imageName;
    public JLabel uploadDate;
    public JButton resetRateButton;
    public Thumbnail image;
    public RateBar rateBar;




    public ImageView(Model model, ImageModel imageModel) {
        this.model = model;
        this.imageModel = imageModel;

        this.imageModel.addObserver(this);

        this.mainBox = new JPanel();
        this.mainBox.setLayout(new BoxLayout(this.mainBox, BoxLayout.Y_AXIS));

        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.setBorder(BorderFactory.createLineBorder(Color.lightGray));

        this.add(Box.createHorizontalGlue());
        this.add(Box.createVerticalGlue());

        this.image = new Thumbnail(this.imageModel.image, this.imageModel.fileName());
        this.add(this.image);
        this.image.setAlignmentY(Component.CENTER_ALIGNMENT);

        this.imageName = new JLabel("File Name: " + this.imageModel.fileName());
        this.imageName.setAlignmentX(Component.CENTER_ALIGNMENT);
        this.mainBox.add(this.imageName);

        this.uploadDate = new JLabel("Upload Date: " + this.imageModel.uploadDate);
        this.uploadDate.setAlignmentX(Component.CENTER_ALIGNMENT);
        this.mainBox.add(this.uploadDate);

        this.rateBar = new RateBar(model, imageModel);
        this.rateBar.setAlignmentX(Component.CENTER_ALIGNMENT);
        this.mainBox.add(this.rateBar);
        //this.imageModel.addObserver(this.rateBar);

        this.resetRateButton = new JButton("Reset rating");
        this.resetRateButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        this.resetRateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                imageModel.userRate = 0;
                imageModel.notifyObservers();
                model.notifyObservers();
            }
        });
        this.mainBox.add(this.resetRateButton);
        this.add(this.mainBox);

        this.add(Box.createHorizontalGlue());
        this.add(Box.createVerticalGlue());
        this.setMinimumSize(new Dimension(Thumbnail.max_size.width, image.getHeight()));

    }

    public void update(Object observable) {
        if (this.model.isGridView) {
            this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

            //this.model.notifyObservers();
            //this.imageModel.notifyObservers();
        } else {
            this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
            //this.model.notifyObservers();
            //this.imageModel.notifyObservers();
        }
        this.revalidate();
        this.repaint();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        this.revalidate();
    }

}
