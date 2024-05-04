import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class WheelsContainer extends JPanel {

    private List<RouletteWheelInfo> savedWheels;

    public WheelsContainer() {
        setLayout(new FlowLayout());
        savedWheels = new ArrayList<>();
    }

    public void addWheel(String name) {//adds a wheel to the container, takes in the wheels name
        RouletteWheelInfo wheelInfo = new RouletteWheelInfo(name, null);
        savedWheels.add(wheelInfo);
    }

    public RouletteWheelInfo selectWheel() {
        String[] wheelNames = savedWheels.stream()
                .map(RouletteWheelInfo::getName)
                .toArray(String[]::new);
    
        JComboBox<String> comboBox = new JComboBox<>(wheelNames);
        
        // Create a custom JOptionPane with a larger panel
        JOptionPane optionPane = new JOptionPane(
            comboBox,
            JOptionPane.QUESTION_MESSAGE,
            JOptionPane.OK_CANCEL_OPTION
        );
        
        // Set a larger font for the JComboBox items
        comboBox.setFont(new Font("Arial", Font.PLAIN, 24));
        
        // Set a larger font for the buttons in the optionPane
        for (Component component : optionPane.getComponents()) {
            if (component instanceof JButton) {
                ((JButton) component).setFont(new Font("Arial", Font.PLAIN, 24));
            }
        }
        
        JDialog dialog = optionPane.createDialog(null, "Select Wheel");
        
        // Set the size of the dialog
        dialog.setSize(1000, 400);
        
        dialog.setVisible(true);
        
        if (optionPane.getValue() != null) {
            String selectedName = (String) comboBox.getSelectedItem();
            return savedWheels.stream()
                .filter(wheelInfo -> wheelInfo.getName().equals(selectedName))
                .findFirst()
                .orElse(null);
        }
        
        return null;
    }

    public List<RouletteWheelInfo> getSavedWheels() {
        return savedWheels;
    }
}