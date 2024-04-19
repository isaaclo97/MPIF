package constructives;

import grafo.optilib.metaheuristics.Constructive;
import structure.MPIFInstance;
import structure.MPIFSolution;

public class GreedyConstructive implements Constructive<MPIFInstance, MPIFSolution> {
    public MPIFSolution constructSolution(MPIFInstance instance) {
        MPIFSolution sol = new MPIFSolution(instance);
        sol.addToSolution(0);
        sol.constructSolution(1);
        sol.setUpdate(true);
        sol.getMark();
        return sol;
    }
}
