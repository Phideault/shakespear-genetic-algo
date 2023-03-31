import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Population {

    private final String target;
    public final double mutationRate;
    public final int maxPopulation;

    //Liste de la population
    private final List<DNA> population;
    //Liste de parents
    private List<DNA> matingPool;

    private final double perfectScore = 1;
    private String best;
    private double bestFitness = 0;

    //Nombre de génération
    public int generations = 0;
    //Fini d'évoluer la génération?
    private boolean finished;

    public Population(String target, double mutationRate, int maxPopulation) {

        this.target = target;
        this.mutationRate = mutationRate;
        this.best = "";
        this.maxPopulation = maxPopulation;

        this.population = new ArrayList<>();

        //Création de la population initiale
        for (int i = 0; i < this.maxPopulation; i++) {
            this.population.add(new DNA(target.length()));
        }

        this.matingPool = new ArrayList<>();

        this.finished = false;
        this.calcFitness();

    }

    public void calcFitness() {
        for (DNA dna : this.population) {
            dna.calculateFitness(target);
            if(dna.getFitness() > bestFitness){
                bestFitness = dna.getFitness();
            }
        }
    }

    //Génération du matingPool
    public void naturalSelection(){
        //Vide le matingPool
        this.matingPool = new ArrayList<>();

        double maxFitness = 0;

        //Détermine le max fitness dans la population
        for (DNA dna : this.population) {
            if (dna.getFitness() > maxFitness) {
                maxFitness = dna.getFitness();
            }
        }

        //Chaque organisme obtient la chance d'entrer dans le mating pool plus ou moin grand en dépend de leur fitness.
        //Fitness haut = +de chance
        //Fitness bas = -de chance
        //Description du loop: POUR CHAQUE MEMBRE DE LA POPULATION, ON PREND SON FITNESS, ON LE ROUND DOWN (=X) ET ON AJOUTE LE MEMBRE DE LA POPULATION DANS LE MATINGPOOL X FOIS
        for(DNA dna : this.population){
            double fitness = dna.getFitness();
            int roundedFitness = (int) Math.floor(fitness*100);
            for(int j = 0; j < roundedFitness + 1; j++){
                this.matingPool.add(dna);
            }
        }


    }

    public boolean isFinished() {
        return this.finished;
    }

    public void generate() {
        Random random = new Random();
        for(int i = 0; i < this.population.size(); i++){
            int a = random.nextInt(this.matingPool.size());
            int b = random.nextInt(this.matingPool.size());
            DNA partnerA = this.matingPool.get(a);
            DNA partnerB = this.matingPool.get(b);
            DNA child = partnerA.breed(partnerB);
            child.mutate(this.mutationRate);
            this.population.set(i, child);
        }
        this.generations++;
    }

    public void evaluate() {
        double worldRecord = 0;
        int index = 0;
        for(int i = 0; i < this.population.size(); i++){
            if(this.population.get(i).getFitness() > worldRecord){
                index = i;
                worldRecord = this.population.get(i).getFitness();
            }
        }

        this.best = this.population.get(index).getGenes();
        if(worldRecord == this.perfectScore){
            this.finished = true;
        }
    }

    public String getBest() {
        return best;
    }

    public int getGenerations() {
        return generations;
    }

    public List<DNA> getPopulation(){
        return population;
    }

    public double getBestFitness() {
        return bestFitness;
    }
}
