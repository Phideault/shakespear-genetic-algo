import org.json.JSONArray;
import org.json.JSONObject;

import javax.swing.*;
import java.awt.event.ActionListener;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Collection;

public class Simulation {

    private final String target;
    private final double mutationRate;
    private final int maxPopulation;
    private final JSONObject finalData;
    private final Collection<JSONObject> dataGenerations = new ArrayList<>();

    public Simulation(String target, double mutationRate, int maxPopulation){
        this.target = target;
        this.mutationRate = mutationRate;
        this.maxPopulation = maxPopulation;

        this.finalData = new JSONObject();

        try {
            JSONObject settingsJson = new JSONObject();
            settingsJson.put("goal", this.target);
            settingsJson.put("maxPopulation", this.maxPopulation);
            settingsJson.put("mutationRate", this.mutationRate);
            finalData.put("settings", settingsJson);
        }catch (Exception ignored){

        }

        try {
            this.runSimulation();
        }catch (Exception ignored){

        }
    }

    private void runSimulation() {

        Population population = new Population(this.target, this.mutationRate, this.maxPopulation);

        this.addGenerationData(population);

        SimulationUI simulationUI = new SimulationUI(population);
        ActionListener taskPerformer = evt -> {
            population.naturalSelection();
            population.generate();
            population.calcFitness();
            population.evaluate();

            this.addGenerationData(population);

            simulationUI.update(population);
            if(population.isFinished()){
                try{
                    finalData.put("generations", new JSONArray(dataGenerations));
                    FileWriter file = new FileWriter("./output.json");
                    file.write(finalData.toString(2));
                    file.close();
                }catch (Exception ignored){

                }
                ((Timer)(evt.getSource())).stop();

            }
        };
        Timer timer = new Timer(100 ,taskPerformer);
        timer.setRepeats(true);
        timer.start();
    }

    private void addGenerationData(Population population) {
        try{
            JSONObject generationJson = new JSONObject();
            generationJson.put("id", population.generations);
            generationJson.put("best", population.getBest());
            JSONArray test = new JSONArray().put(population.getPopulation());
            test = (JSONArray) test.get(0);
            generationJson.put("population", test);
            dataGenerations.add(generationJson);
        }catch (Exception ignored) {

        }
    }


}
