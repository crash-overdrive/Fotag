import javax.swing.*;
import java.awt.*;
import javax.swing.border.Border;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class ToolbarView extends JToolBar implements Observer {

    public JButton gridButton;
    public JButton listButton;
    public JPanel leftPanel;
    public JLabel title;
    public JPanel rightPanel;
    public JButton uploadButton;
    public JCheckBox applyFilterCheckbox;
    public Border BtnNotClickedBorder;
    public Border BtnClickedBorder;
    public FilterBar filterBar;

    Model model;

    public ToolbarView(Model model) {
        this.model = model;

        this.uploadButton = new JButton();
        this.uploadButton.setIcon(new ImageIcon("Resources/images/uploadButton.png"));
        this.uploadButton.setPreferredSize(new Dimension(36, 31));
        this.uploadButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //System.out.println("Upload Button Clicked");
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setMultiSelectionEnabled(true);
                int result = fileChooser.showOpenDialog(uploadButton);
                if (result == JFileChooser.APPROVE_OPTION) {
                    File[] files = fileChooser.getSelectedFiles();
                    for (File file:files) {
                        model.addImage(file);
                    }
                }
                model.notifyObservers();
            }
        });

        this.setLayout(new BorderLayout());
        this.setFloatable(false);


        this.leftPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        this.rightPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));

        this.applyFilterCheckbox = new JCheckBox("Apply filters?");

        this.title = new JLabel("Fotag!");

        this.filterBar = new FilterBar(model);
        this.model.addObserver(filterBar);

        this.gridButton = new JButton();
        this.gridButton.setIcon(new ImageIcon("Resources/images/grid.png"));
        this.gridButton.setPreferredSize(new Dimension(36,36));

        this.listButton = new JButton();
        this.listButton.setIcon(new ImageIcon("Resources/images/list.png"));
        this.listButton.setPreferredSize(new Dimension(36,36));

        this.BtnNotClickedBorder = UIManager.getBorder("Button.border");
        this.BtnClickedBorder = BorderFactory.createMatteBorder(2,2,2,2,Color.black);

        gridButton.setBorder(BtnClickedBorder);
        this.leftPanel.add(gridButton);
        gridButton.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                model.isGridView = true;
                model.notifyObservers();
            }
        });

        this.leftPanel.add(listButton);
        listButton.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                model.isGridView = false;
                model.notifyObservers();
            }
        });

        this.add(this.leftPanel, BorderLayout.WEST);

        title.setFont(new Font("Comic Sans MS", Font.PLAIN, 36));
        title.setHorizontalAlignment(JLabel.CENTER);
        this.add(title, BorderLayout.CENTER);

        this.rightPanel.add(this.uploadButton);
        this.rightPanel.add(this.applyFilterCheckbox);
        this.rightPanel.add(filterBar);


        this.applyFilterCheckbox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                model.FilterEnabled = !model.FilterEnabled;

                model.notifyObservers();
            }
        });

        this.add(this.rightPanel, BorderLayout.EAST);
    }

    public void update(Object observable) {

                if (this.model.isGridView) {
                    this.gridButton.setBorder(BtnClickedBorder);
                    this.listButton.setBorder(BtnNotClickedBorder);
                }
                else {
                    this.listButton.setBorder(BtnClickedBorder);
                    this.gridButton.setBorder(BtnNotClickedBorder);
                }

        //System.out.println("Toolbar View Changed!!");
        revalidate();
        repaint();
    }
}

