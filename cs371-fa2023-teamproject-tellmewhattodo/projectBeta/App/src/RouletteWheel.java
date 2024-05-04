import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;
import java.util.List;

public class RouletteWheel extends JFrame {

    
    private JPanel circlePanel;
    private JButton spinButton;
    private List<String> parts;
    private List<Color> partColors;
    private double currentRotation;

    public RouletteWheel() {
        setTitle("Randomized Circle");
        setSize(400, 400);
        //setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        //parts = new String[4];
        //partColors = new Color[4];

        parts = new ArrayList<>();
        partColors = new ArrayList<>();
        initializeParts();

        circlePanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                drawCircle(g);
            }
        };

        spinButton = new JButton("Spin");
        spinButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                rotateCircle();
            }
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(spinButton);

        setLayout(new BorderLayout());
        add(circlePanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    private void initializeParts() {
        int i = 1;
        while (true) {
            JTextField textField = new JTextField();
            Font font = new Font("Times New Roman", Font.PLAIN, 36);
            textField.setFont(font);
            textField.setPreferredSize(new Dimension(550, 100));

            JLabel label = new JLabel("Enter text for part " + i);
            Font labelFont = new Font("Times New Roman", Font.BOLD, 50);
            label.setFont(labelFont);

            JPanel panel = new JPanel();
            panel.add(label);
            panel.add(textField);

            int result = JOptionPane.showConfirmDialog(
                null,
                panel,
                "Enter Part Name",
                JOptionPane.OK_CANCEL_OPTION
            );

            if (result == JOptionPane.OK_OPTION) {
                String input = textField.getText();
                if (!input.isEmpty()) {
                    parts.add(input);
                } else {
                    parts.add("Part " + i);
                }
                partColors.add(getRandomColor());
                i++;
            } else {
                break;
            }
        }
    }


    private void drawCircle(Graphics g) {
        int centerX = circlePanel.getWidth() / 2;
        int centerY = circlePanel.getHeight() / 2;
        int radius = Math.min(centerX, centerY) - 20;
    
        Graphics2D g2d = (Graphics2D) g;
        AffineTransform oldTransform = g2d.getTransform();
        AffineTransform transform = new AffineTransform();
    
        int angleStep = 360 / parts.size();
    
        for (int i = 0; i < parts.size(); i++) {
            transform.setToIdentity();
            transform.translate(centerX, centerY);
            transform.rotate(Math.toRadians(currentRotation + i * angleStep));
            transform.translate(-centerX, -centerY);
    
            g2d.setTransform(transform);
    
            g2d.setColor(partColors.get(i));
            g2d.fillArc(centerX - radius, centerY - radius, 2 * radius, 2 * radius, 0, angleStep);
            g2d.setColor(Color.BLACK);
            g2d.drawString(parts.get(i), centerX, centerY - radius + 20);
        }
    
        g2d.setTransform(oldTransform);
    }

    private Color getRandomColor() {
        Random random = new Random();
        int r = random.nextInt(256);
        int g = random.nextInt(256);
        int b = random.nextInt(256);
        return new Color(r, g, b);
    }

    void rotateCircle() {
        final int totalSpins = 5;
        final int framesPerSpin = 20;
        final int delay = 15;  // Delay between frames (adjust for speed)

        double finalRotation = currentRotation + 360 * totalSpins + getRandomAngle();
        double angle = (finalRotation - currentRotation) / (totalSpins * framesPerSpin);

        Timer timer = new Timer(delay, new ActionListener() {
            int spins = 0;
            int frame = 0;

            @Override
            public void actionPerformed(ActionEvent e) {
                if (spins < totalSpins) {
                    currentRotation += angle;
                    circlePanel.repaint();
                    frame++;

                    if (frame >= framesPerSpin) {
                        spins++;
                        frame = 0;
                    }
                } else {
                    ((Timer) e.getSource()).stop();
                    int selectedPartIndex = (int) ((finalRotation % 360) / (360.0 / parts.size()));
                    String selectedPart = parts.get(selectedPartIndex);

                    JOptionPane.showMessageDialog(RouletteWheel.this, "Selected Part: " + selectedPart, "Result", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });

        timer.start();
    }

    private double getRandomAngle() {
        Random random = new Random();
        return random.nextDouble() * 360;
    }

    // public static void main(String[] args) {
    //     SwingUtilities.invokeLater(new Runnable() {
    //         @Override
    //         public void run() {
    //             RouletteWheel randomizedCircle = new RouletteWheel();
    //             randomizedCircle.setVisible(true);
    //         }
    //     });
    // }
}