package localSearch;

import grafo.optilib.metaheuristics.Improvement;
import structure.MPIFSolution;

import java.util.concurrent.TimeUnit;

//SWAPS
public class LS implements Improvement<MPIFSolution> {

    private int TIME=1000;

    public LS(){
    }
    public void improve(MPIFSolution solution) {
        boolean improveResult;
        MPIFSolution newSolution = new MPIFSolution(solution);
        int S_array[] = new int[solution.getInstance().getFacilities()];
        int bestMark = newSolution.getMark();
        improveResult = true;
        int cnt = 0;
        for (Integer i : newSolution.getS()) {
            S_array[cnt] = i;
            cnt++;
        }
        final long startTime = solution.getInstance().getStartTime();
        while (improveResult) {
            improveResult = false;
            for (int i=0; i<newSolution.getS().size();i++) {
                if (S_array[i] == 0) continue;
                newSolution.destroySolution(S_array[i]);
                int newS[] = newSolution.constructSolution(1);
                newSolution.setUpdate(false);
                int curMark = newS[1];
                long curTime = TimeUnit.MILLISECONDS.convert(System.nanoTime() - startTime, TimeUnit.NANOSECONDS);
                if (curMark == 0 || curTime / 1000 >= TIME) return;
                if (bestMark > curMark && curMark!=1061109567) {
                    solution.copy(newSolution);
                    bestMark = curMark;
                    S_array[i]=newS[0];
                    improveResult = true;
                    if (bestMark == 0){
                        return;
                    }
                    break;
                }
                newSolution.copy(solution);
            }
        }
    }
}
