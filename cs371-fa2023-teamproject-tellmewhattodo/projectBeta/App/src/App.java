import javax.swing.*;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
//import java.util.List;

public class App extends JFrame implements ActionListener {

    private static JFrame simpleGUI;
    private int event = 2;
    private static WheelsContainer wheelsContainer; // Reference to WheelsContainer
    private static RouletteWheelInfo selectedWheelInfo;

    // Default constructor
    App() {
        wheelsContainer = new WheelsContainer(); // Create a WheelsContainer instance
    }

    public static JButton spinButton() {
        JButton spinButton = new JButton("Spin");
        spinButton.setBounds(100, 835, 250, 150);
        spinButton.setBackground(Color.RED);
        spinButton.setForeground(Color.WHITE);
        spinButton.setFont(new Font("Spin", Font.BOLD, 40));
        spinButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (selectedWheelInfo != null) {
                    // Use the selected wheel for spinning
                    RouletteWheel selectedWheel = selectedWheelInfo.getWheel();
                    selectedWheel.setVisible(true);
                    selectedWheelInfo.getWheel().rotateCircle();
                } 
            }
        });
        return spinButton;
    }

    // New class to handle the edit wheel dialog
    public class EditWheelDialog extends JDialog {
        private JTextField wheelNameField;

        public EditWheelDialog(JFrame parent) {
            super(parent, "Edit Wheel", true);
            setSize(400, 200);
            setLocationRelativeTo(parent);

            JPanel panel = new JPanel();
            getContentPane().add(panel);

            JLabel nameLabel = new JLabel("Wheel Name:");
            wheelNameField = new JTextField(20);
            JButton saveButton = new JButton("Save");

            panel.add(nameLabel);
            panel.add(wheelNameField);
            panel.add(saveButton);

            saveButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    // Handle the save action here (e.g., update the wheel name)
                    //String wheelName = wheelNameField.getText();
                    // You can perform any other editing you need here
                    dispose(); // Close the dialog when done
                }
            });
        }
    }

    public static JButton editWheelButton() {
        JButton editWheelButton = new JButton("EditWheel");
        editWheelButton.setBounds(700, 835, 250, 150);
        editWheelButton.setBackground(Color.BLUE);
        editWheelButton.setForeground(Color.WHITE);
        editWheelButton.setFont(new Font("EditWheel", 1, 40));

        editWheelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Create and show the edit wheel dialog when the EditWheel button is clicked
                EditWheelDialog editWheelDialog = new App().new EditWheelDialog(simpleGUI);
                editWheelDialog.setVisible(true);
            }
        });

        return editWheelButton;
    }

    public static JButton newWheelButton() {
        JButton newWheelButton = new JButton("NewWheel");
        
        // Set the size of the button
        newWheelButton.setBounds(1000, 835, 250, 150);
        newWheelButton.setBackground(Color.PINK);
        newWheelButton.setForeground(Color.WHITE);
        newWheelButton.setFont(new Font("NewWheel", Font.BOLD, 40));
    
        newWheelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Create a text field with a custom font and size
                JTextField textField = new JTextField();
                Font font = new Font("Arial", Font.PLAIN, 36); // Adjust the font and size
                textField.setFont(font);
                
                // Set the size of the text field
                textField.setPreferredSize(new Dimension(500, 100)); // Set the size of the text field
                
                // Create a custom option pane with the text field and a larger message font
                UIManager.put("OptionPane.messageFont", new Font("Arial", Font.PLAIN, 36)); // Set a larger message font
                
                JOptionPane optionPane = new JOptionPane(
                    textField,
                    JOptionPane.PLAIN_MESSAGE,
                    JOptionPane.OK_CANCEL_OPTION
                );
                
                JDialog dialog = optionPane.createDialog(simpleGUI, "Enter a name for the new wheel:");
                dialog.setVisible(true);
                
                // Reset the UIManager font settings after the input dialog
                UIManager.put("OptionPane.messageFont", UIManager.getDefaults().get("OptionPane.messageFont"));
    
                int result = (int) optionPane.getValue();
                
                if (result == JOptionPane.OK_OPTION) {
                    String wheelName = textField.getText();
                    if (!wheelName.isEmpty()) {
                        // Add the new wheel with the provided name to the WheelsContainer
                        wheelsContainer.addWheel(wheelName);
                    }
                }
            }
        });
    
        return newWheelButton;
    }

    public static JButton wheelsButton() {
        JButton wheelsButton = new JButton("Wheels");
        wheelsButton.setBounds(400, 835, 250, 150);
        wheelsButton.setBackground(Color.GREEN);
        wheelsButton.setForeground(Color.WHITE);
        wheelsButton.setFont(new Font("Wheels", 1, 40));

        wheelsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                selectedWheelInfo = wheelsContainer.selectWheel();
            }
        });

        return wheelsButton;
    }

    public static JButton deleteWheelButton() {
        JButton deleteWheelButton = new JButton("Delete Wheel");
        deleteWheelButton.setBounds(1300, 835, 250, 150);
        deleteWheelButton.setBackground(Color.ORANGE);
        deleteWheelButton.setForeground(Color.WHITE);
        deleteWheelButton.setFont(new Font("Delete Wheel", 1, 35));
    
        deleteWheelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Check if a wheel is selected
                if (selectedWheelInfo != null) {
                    int confirm = JOptionPane.showConfirmDialog(simpleGUI, "Are you sure you want to delete the selected wheel?",
                            "Confirm Deletion", JOptionPane.YES_NO_OPTION);
    
                    if (confirm == JOptionPane.YES_OPTION) {
                        // Remove the selected wheel from the list of saved wheels
                        wheelsContainer.getSavedWheels().remove(selectedWheelInfo);
                        selectedWheelInfo = null; // Reset the selected wheel
                    }
                }
            }
        });
    
        return deleteWheelButton;
    }

    public static JButton optionsButton() {
        JButton optionsButton = new JButton("Settings");
        optionsButton.setBounds(1600, 835, 250, 150);
        optionsButton.setBackground(Color.DARK_GRAY);
        optionsButton.setForeground(Color.WHITE);
        optionsButton.setFont(new Font("Settings", 1, 40));

        optionsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Create a new window when the button is clicked
                JFrame popupFrame = new JFrame("Settings");
                popupFrame.setSize(400, 300);
                popupFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

                // Add JLabel components for "option 1," "option 2," "option 3," and "option 4"
                JLabel optionLabel1 = new JLabel("Option 1");
                JLabel optionLabel2 = new JLabel("Option 2");
                JLabel optionLabel3 = new JLabel("Option 3");
                JLabel optionLabel4 = new JLabel("Option 4");

                optionLabel1.setBounds(50, 30, 300, 30);
                optionLabel2.setBounds(50, 70, 300, 30);
                optionLabel3.setBounds(50, 110, 300, 30);
                optionLabel4.setBounds(50, 150, 300, 30);

                popupFrame.add(optionLabel1);
                popupFrame.add(optionLabel2);
                popupFrame.add(optionLabel3);
                popupFrame.add(optionLabel4);

                popupFrame.setLayout(null);
                popupFrame.setVisible(true);
            }
        });

        return optionsButton;
    }

    public static JButton lightModeButton() {
        JButton lightModeButton = new JButton("Light Mode");
        lightModeButton.setBounds(1600, 100, 250, 150);
        lightModeButton.setBackground(Color.CYAN);
        lightModeButton.setForeground(Color.WHITE);
        lightModeButton.setFont(new Font("Light Mode", 1, 35));
        App lmb = new App();
        lightModeButton.addActionListener(lmb);
        return lightModeButton;
    }

    public static void main(String[] args) throws Exception {
        simpleGUI = new JFrame("TellMeWhatToDo");
        simpleGUI.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        simpleGUI.setSize(1930, 1930);
        simpleGUI.getContentPane().setBackground(Color.BLACK);



        simpleGUI.add(spinButton());
        simpleGUI.add(editWheelButton());
        simpleGUI.add(newWheelButton());
        simpleGUI.add(optionsButton());
        simpleGUI.add(wheelsButton());
        simpleGUI.add(deleteWheelButton());
        simpleGUI.add(lightModeButton());

        simpleGUI.setLayout(null);
        simpleGUI.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String s = e.getActionCommand();
        if (s.equals("Light Mode")) {
            if (event % 2 == 0) {
                simpleGUI.getContentPane().setBackground(Color.WHITE);
                event++;
            } else {
                simpleGUI.getContentPane().setBackground(Color.BLACK);
                event++;
            }
        }
    }
}
