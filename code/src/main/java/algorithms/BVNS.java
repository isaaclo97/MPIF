package algorithms;

import grafo.optilib.metaheuristics.Algorithm;
import grafo.optilib.metaheuristics.Constructive;
import grafo.optilib.metaheuristics.Improvement;
import grafo.optilib.results.Result;
import grafo.optilib.structure.Solution;
import grafo.optilib.tools.RandomManager;
import structure.MPIFInstance;
import structure.MPIFSolution;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class BVNS implements Algorithm<MPIFInstance> {

	final Constructive<MPIFInstance, MPIFSolution> constructive;

	// The output directory path, used for testing purposes only
	private Double K;
	private int iterations,TIME=1000; //80
	private final Improvement<MPIFSolution> improvement;
	private long timeToBest = 0;
	private int iterationsBVNS = 0;
	private int shakeFailers = 0;
	private int shakeTotal = 0;
	private int mejora = 0;
	private int constructiveVal,lsVal=-1;
	private ArrayList<Integer> mejoraITER = new ArrayList<>();

	public BVNS(Constructive<MPIFInstance, MPIFSolution> constructive, Improvement<MPIFSolution> improve, Integer iterations, double K){
		this.constructive = constructive;
		this.iterations = iterations;
		this.improvement = improve;
		this.K = K;
	}

	@Override
	public Result execute(MPIFInstance instance) {
		final long startTime = instance.getStartTime();
		timeToBest = 0;
		iterationsBVNS = 0;
		mejora = 0;
		shakeTotal = 0;
		shakeFailers = 0;
		lsVal = -1;
		mejoraITER.clear();
		System.out.println(instance.getName());
		Result r = new Result(instance.getName());
		MPIFSolution sol = constructive.constructSolution(instance);
		constructiveVal = sol.getMark();
		System.out.println("Curbest: " + constructiveVal);
		timeToBest = getTimeToSolution(startTime);
		MPIFSolution bestSol = new MPIFSolution(sol);
		if(sol.getMark()!=0) {
			improvement.improve(sol);
			if(lsVal==-1)
				lsVal=sol.getMark();
			if(sol.getMark()!=0) {
				timeToBest = getTimeToSolution(startTime);
				bestSol = new MPIFSolution(sol);
				System.out.println("Curbest LS: " + bestSol.getMark());
				MPIFSolution mdccVNS = new MPIFSolution(bestSol);
				for (int i = 0; i < iterations; i++) {
					iterationsBVNS++;
					long curTime = getTimeToSolution(startTime);
					if (!(bestSol.getMark() == 0 || curTime / 1000 >= TIME)) {
						mdccVNS.copy(bestSol);
						//BVNS
						double realKMax = Math.max(1, (int) (K * (double) bestSol.getInstance().getFacilities()));
						int k = 1;
						while (k <= realKMax) {
							shakeRealRandomSwap(mdccVNS, k);
							improvement.improve(mdccVNS);
							k = neighborhoodChange(mdccVNS, k, bestSol,startTime);
							curTime = getTimeToSolution(startTime);
							if (bestSol.getMark() == 0 || curTime / 1000 >= TIME) break;
						}
					}
					if (i == 100 || i == 200 || i == 300 || i == 400 || i == 500) {
						r.add("# Iteration " + i, bestSol.getMark());
						r.add("# Time Iteration " + i, getTimeToSolution(startTime));
						r.add("# Time to best " + i, timeToBest);
						r.add("# BVNS iterations_"+i, iterationsBVNS);
						r.add("# Shake Failures_"+i, shakeFailers);
						r.add("# Shake Total_"+i, shakeTotal);
						r.add("# Mejora_"+i, mejora);
					}
				}
			}
		}
		System.out.println("Final mark -> " + bestSol.getMark());
		long timeToSolution = getTimeToSolution(startTime);
		System.out.println("Final time -> " + timeToSolution);
		r.add("Time (s)", timeToSolution);
		r.add("# Constructions", iterations);
		r.add("# P", instance.getFacilities());
		r.add("Distance (s)", instance.getDistance());
		r.add("# Constructive", constructiveVal);
		r.add("# Constructive + LS", lsVal);
		r.add("# Global F.O Value", bestSol.getMark());
		r.add("# Time to best", timeToBest);
		r.add("# BVNS iterations", iterationsBVNS);
		r.add("# Shake Failures", shakeFailers);
		r.add("# Shake Total", shakeTotal);
		r.add("# Mejora_", mejora);
		for(Integer e: mejoraITER)
			r.add("# Mejora_Iter", e);

		System.out.println("--");
		bestSol.printSelectedNodes();

		return r;
	}

	private void shakeRealRandomSwap(MPIFSolution bcVNS, int k) {
		bcVNS.destroySolutionRealRandom(k);
		shakeTotal++;
	}

	private int neighborhoodChange(MPIFSolution bcVNSImprove, int k, MPIFSolution bestSol,long time) {
		if(bcVNSImprove.getMark()<bestSol.getMark()){
			bestSol.copy(bcVNSImprove);
			mejoraITER.add(iterationsBVNS);
			System.out.println("Curbest BVNS: " + bcVNSImprove.getMark());
			mejora++;
			k=1;
			timeToBest = getTimeToSolution(time);
		}
		else {
			bcVNSImprove.copy(bestSol);
			k++;
		}
		return k;
	}

	private long getTimeToSolution(long startTime) {
		long timeToSolution;
		timeToSolution = TimeUnit.MILLISECONDS.convert(System.nanoTime() - startTime, TimeUnit.NANOSECONDS);
		return timeToSolution;
	}

	@Override
	public Solution getBestSolution() {
		return null;
	}

	@Override
	public String toString(){
		return this.getClass().getSimpleName() + "(" + constructive + ")";
	}
}
