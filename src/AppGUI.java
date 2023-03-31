import javax.swing.*;

public class AppGUI extends JFrame{
    private JPanel panelMain;
    private JButton runSimulationButton;
    private JTextField objectiveTextField;
    private JSlider mutationChanceSlider;
    private JTextField maxPopTextField;
    private JLabel mutationChancePercent;

    public AppGUI(){
        openUI();
        mutationChanceSlider.addChangeListener(e -> mutationChancePercent.setText(mutationChanceSlider.getValue() + "%"));

        runSimulationButton.addActionListener(e -> {
            String target = objectiveTextField.getText();
            double mutationRate = (double) mutationChanceSlider.getValue() /100;
            int maxPopulation = Integer.parseInt(maxPopTextField.getText());
            Simulation simulation = new Simulation(target, mutationRate, maxPopulation);
        });
    }

    private void openUI(){
        setTitle("Genetic Algorithm Settings");
        getContentPane().add(panelMain);
        setLocation(100,100);
        pack();
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
