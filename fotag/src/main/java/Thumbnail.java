import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Thumbnail extends JPanel {
    transient BufferedImage image;
    String filename;
    static Dimension max_size = new Dimension(280,280);

    public Thumbnail(BufferedImage image, String filename) {
        this.image = image;
        this.filename = filename;
        Dimension imageDimension = scaleDimension(max_size);
        this.setMaximumSize(imageDimension);
        this.setMinimumSize(imageDimension);
        this.setPreferredSize(imageDimension);

        this.addMouseListener(new MouseAdapter() {

            public void mouseClicked(MouseEvent e) {
                Dimension dimensions = scaleDimension(new Dimension(800, 600));
                ImageIcon icon = new ImageIcon(image.getScaledInstance(dimensions.width, dimensions.height, Image.SCALE_SMOOTH));
                JOptionPane.showMessageDialog(Thumbnail.this, new JLabel(icon), Thumbnail.this.filename, JOptionPane.PLAIN_MESSAGE);
            }
        });
    }



    public double scaleFactor(Dimension dimemsionLimit) {
        return Math.min(dimemsionLimit.getWidth() / image.getWidth(), dimemsionLimit.getHeight() / image.getHeight());
    }

    public Dimension scaleDimension(Dimension dimensionLimit) {
        double factor = scaleFactor(dimensionLimit);
        return new Dimension((int) (factor * image.getWidth()), (int) (factor * image.getHeight()));
    }

    public void paintComponent(Graphics g) {
        Graphics2D g2D = (Graphics2D) g;
        g2D.drawImage(image, 0, 0, getWidth(), getHeight(), this);
        this.revalidate();
    }
}
