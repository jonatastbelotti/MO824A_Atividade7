package problems.bpp.solvers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import metaheuristics.grasp.AbstractGRASP;
import problems.bpp.BPP;
import problems.bpp.Item;
import solutions.Solution;
import solutions.SolutionBPP;

/**
 *
 * @author Jônatas Trabuco Belotti [jonatas.t.belotti@hotmail.com]
 * @author Felipe de Carvalho Pereira [felipe.pereira@students.ic.unicamp.br]
 * @param <E>
 */
public class GRASP_BPP<E> extends AbstractGRASP<Item> {

    public int sampleGreedyP;
    public static int STANDARD = 1;
    public static int SAMPLED_GREEDY = 2;
    public int contructionMechanism;

    public GRASP_BPP(String nomeArquivo, Double alpha, Integer iterations, Integer time, Integer contructionMechanism) {
        super(new BPP(nomeArquivo), alpha, iterations, time);

        this.contructionMechanism = contructionMechanism;
        if (this.contructionMechanism == SAMPLED_GREEDY) {
            sampleGreedyP = (int) (0.05 * ObjFunction.getDomainSize());
        }
    }

    @Override
    public ArrayList<Item> makeCL() {
        return new ArrayList<Item>(Item.getItens());
    }

    @Override
    public ArrayList<Item> makeRCL() {
        return new ArrayList<Item>();
    }

    @Override
    public void updateCL() {
        if (this.CL.isEmpty() || this.incumbentSol.isEmpty()) {
            return;
        }

        this.CL.remove(this.incumbentSol.get(this.incumbentSol.size() - 1));
    }

    @Override
    public Solution<Item> createEmptySol() {
        Solution<Item> sol = new SolutionBPP();
        return sol;
    }

    @Override
    protected Solution<Item> createFullSol(Solution<Item> sol) {
        return new SolutionBPP(sol);
    }

    @SuppressWarnings("unchecked")
    private Solution<Item> sampleGreedyConstruction() {
        CL = makeCL();
        RCL = makeRCL();
        incumbentSol = createEmptySol();

        while (!CL.isEmpty()) {
            double minCost = Double.POSITIVE_INFINITY;
            Item bestCandidate = null;

            ArrayList<Item> sampledGreedyCL = (ArrayList<Item>) CL.clone();
            Collections.shuffle(sampledGreedyCL);

            int minimumP = Math.min(this.sampleGreedyP, CL.size());

            /* Get minimumP items for the rcl */
            for (int i = 0; i < minimumP; i++) {
                RCL.add(sampledGreedyCL.get(i));
            }

            /* Choose the best candidate from the RCL */
            for (Item c : RCL) {
                Double newCost = ObjFunction.evaluateInsertionCost(c, incumbentSol);
                if (newCost < minCost) {
                    minCost = newCost;
                    bestCandidate = c;
                }
            }

            // Insert best candidate in partial solution
            incumbentSol.add(bestCandidate);
            updateCL();
            RCL.clear();
        }

        ObjFunction.evaluate(incumbentSol);
        return incumbentSol;
    }

    @Override
    public Solution<Item> constructiveHeuristic() {
        CL = makeCL();
        RCL = makeRCL();
        incumbentSol = createEmptySol();
        Random random = new Random();

        while (!CL.isEmpty()) {
            double maxCost = Double.NEGATIVE_INFINITY, minCost = Double.POSITIVE_INFINITY;

            /*
            * Explore all candidate elements to enter the solution, saving the
            * highest and lowest cost variation achieved by the candidates.
             */
            for (Item c : CL) {
                Double deltaCost = ObjFunction.evaluateInsertionCost(c, incumbentSol);

                if (deltaCost < minCost) {
                    minCost = deltaCost;
                }
                if (deltaCost > maxCost) {
                    maxCost = deltaCost;
                }
            }

            /*
            * Among all candidates, insert into the RCL those with the highest
            * performance using parameter alpha as threshold.
             */
            for (Item c : CL) {
                Double deltaCost = ObjFunction.evaluateInsertionCost(c, incumbentSol);

                if (deltaCost >= minCost + alpha * (maxCost - minCost)) {
                    RCL.add(c);
                }
            }

            /* Choose a candidate randomly from the RCL */
            int rndIndex = random.nextInt(RCL.size());
            Item inCand = RCL.get(rndIndex);
            incumbentSol.add(inCand);

            updateCL();
            RCL.clear();
        }

        ObjFunction.evaluate(incumbentSol);

        return incumbentSol;
    }

    @Override
    public Solution<Item> localSearch() {
        Double deltaAnterior, deltaAtual, minDelta;
        Item item1 = null, item2 = null;

        deltaAnterior = Double.MIN_VALUE;
        deltaAtual = Double.MAX_VALUE;

        while (!deltaAnterior.equals(deltaAtual)) {

            minDelta = 1D;

            for (Item i1 : incumbentSol) {
                for (Item i2 : incumbentSol) {
                    if (i1.equals(i2)) {
                        continue;
                    }

                    double deltaCost = ObjFunction.evaluateChangeCost(i1, i2, incumbentSol);

                    if (deltaCost < minDelta) {
                        minDelta = deltaCost;
                        item1 = i1;
                        item2 = i2;
                    }
                }
            }

            if (minDelta < 1D) {
                int ind1 = incumbentSol.indexOf(item1);
                int ind2 = incumbentSol.indexOf(item2);

                incumbentSol.set(ind1, item2);
                incumbentSol.set(ind2, item1);

                ObjFunction.evaluate(incumbentSol);
            }

            deltaAnterior = deltaAtual;
            deltaAtual = minDelta;
        }

        return null;
    }

    @Override
    public Solution<Item> solve() {
        long tempoInicial = System.currentTimeMillis();

        bestSol = createEmptySol();
        for (int i = 0; i < maxIterations && ((System.currentTimeMillis() - tempoInicial) / 1000D) < this.maxTime; i++) {
            if (this.contructionMechanism == STANDARD) {
                constructiveHeuristic();
            } else if (this.contructionMechanism == SAMPLED_GREEDY) {
                sampleGreedyConstruction();
            }

            localSearch();

            if (incumbentSol.cost < bestSol.cost) {
                bestSol = createFullSol(incumbentSol);

                if (verbose) {
                    System.out.println("(Iter. " + i + ") (Tim. " + ((System.currentTimeMillis() - tempoInicial) / 1000D) + "s) BestSol = " + bestSol);
                }
            }
        }
        return bestSol;
    }

    public static void main(String[] args) {
        String arquivo;
        Double ALPHA = 0.10D;
        Integer NUM_ITERACOES = 1000000;
        Integer TEMPO_SEGUNDOS = 60 * 10;

        arquivo = "./bpp_instances/instance0.bpp";
        arquivo = "./bpp_instances/instance1.bpp";
        arquivo = "./bpp_instances/instance2.bpp";
        arquivo = "./bpp_instances/instance3.bpp";
        arquivo = "./bpp_instances/instance4.bpp";
        arquivo = "./bpp_instances/instance5.bpp";
        arquivo = "./bpp_instances/instance6.bpp";
        arquivo = "./bpp_instances/instance7.bpp";
        arquivo = "./bpp_instances/instance8.bpp";
        arquivo = "./bpp_instances/instance9.bpp";

        System.out.println("Algoritmo GRASP para a instancia " + arquivo);

//        System.out.println("Conctruction mechanism: STANDARD");
//        GRASP_BPP grasp = new GRASP_BPP(arquivo, ALPHA, NUM_ITERACOES, TEMPO_SEGUNDOS, STANDARD);
        System.out.println("Conctruction mechanism: SAMPLED GREEDY");
        GRASP_BPP grasp = new GRASP_BPP(arquivo, ALPHA, NUM_ITERACOES, TEMPO_SEGUNDOS, SAMPLED_GREEDY);

        long startTime = System.currentTimeMillis();
        Solution<Item> bestSol = grasp.solve();
        long endtTime = System.currentTimeMillis();

        System.out.println("maxVal = " + bestSol.descCompleta());

        System.out.println("Tempo de execução: " + ((endtTime - startTime) / 1000D) + " segundos");

    }

}
