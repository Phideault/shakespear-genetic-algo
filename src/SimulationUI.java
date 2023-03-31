import javax.swing.*;

public class SimulationUI extends JFrame{
    private JPanel panelMain;
    public JLabel bestLabel;
    public JLabel totalGenLabel;
    public JLabel totalPopLabel;
    public JLabel mutationRateLabel;
    private JButton closeButton;
    private JProgressBar progressBar1;

    public SimulationUI(Population population){
        update(population);
        openUI();
        closeButton.addActionListener(e -> dispose());
    }

    private void openUI(){
        setTitle("Genetic Algorithm Simulation");
        getContentPane().add(panelMain);
        setLocation(100,100);
        pack();
        setVisible(true);
    }

    public void update(Population population){
        bestLabel.setText("Best: " + population.getBest());
        totalGenLabel.setText("Total generations: " + population.getGenerations());
        totalPopLabel.setText("Total Population: " + population.maxPopulation);
        mutationRateLabel.setText("Mutation rate: " + (int) (population.mutationRate * 100) + "%");
        progressBar1.setValue((int) Math.floor(population.getBestFitness()*100));
    }
}
