import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class DNA {

    private double fitness;
    private final List<Character> genes;

    public DNA(int length) {
        this.fitness = 0;
        this.genes = new ArrayList<>();
        for(int i = 0; i < length; i++){
            this.genes.add(newCharacter());
            if(i == 0){
                this.genes.set(i, this.genes.get(i).toString().toUpperCase().charAt(0));
            }
        }
    }

    private char newCharacter() {
        Random random = new Random();
        String possibleGenes = "abcdefghijklmnopqrstuvwxyz .";
        return possibleGenes.charAt(random.nextInt(possibleGenes.length()));
    }

    @Override
    public String toString() {
        StringBuilder currentGeneList = new StringBuilder();
        for(int i = 0; i < genes.size() - 1; i++){
            currentGeneList.append(genes.get(i));
        }
        return currentGeneList.toString();
    }

    public String getGenes() {
        StringBuilder genes = new StringBuilder();
        for (Character gene : this.genes) {
            genes.append(gene);
        }
        return genes.toString();
    }

    public void calculateFitness(String target){
        int score = 0;
        for(int i = 0; i < this.genes.size(); i++){
            if(target.charAt(i) == this.genes.get(i)){
                score++;
            }
        }
        this.fitness = (double) score / target.length();
    }

    public double getFitness(){
        return this.fitness;
    }

    public DNA breed(DNA partnerB) {
        Random random = new Random();

        DNA child = new DNA(this.genes.size());
        for(int i = 0; i < this.genes.size(); i++){
            int selectedGene = random.nextInt(2);
            if(selectedGene == 0){
                child.genes.set(i, this.genes.get(i));
            }else{
                child.genes.set(i, partnerB.genes.get(i));
            }
        }

        return child;
    }

//    @Override
//    public DNA plus(DNA partnerB){
//        Random random = new Random();
//        DNA child = new DNA(this.genes.size());
//        for(int i = 0; i < this.genes.size(); i++){
//            int selectedGene = random.nextInt(2);
//            if(selectedGene == 0){
//                child.genes.set(i, this.genes.get(i));
//            }else{
//                child.genes.set(i, partnerB.genes.get(i));
//            }
//        }
//
//        return child;
//    }

    public void mutate(double mutationRate) {
        Random random = new Random();
        for(int i = 0; i < this.genes.size(); i++){
            if(random.nextDouble(1) < mutationRate){
                char newChar = newCharacter();
                if(i == 0) newChar = Character.toUpperCase(newChar);
                this.genes.set(i, newChar);
            }
        }
    }
}
