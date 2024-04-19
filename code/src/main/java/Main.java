import algorithms.*;
import constructives.*;
import grafo.optilib.metaheuristics.Algorithm;
import grafo.optilib.metaheuristics.Improvement;
import localSearch.*;
import structure.Experiment;
import structure.MPIFInstance;
import structure.MPIFInstanceFactory;
import structure.MPIFSolution;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;

public class Main {
    public static void main(String[] args){

        Calendar cal = Calendar.getInstance();
        int day = cal.get(Calendar.DAY_OF_MONTH);
        int month = cal.get(Calendar.MONTH) + 1;
        int year = cal.get(Calendar.YEAR);
        int hour = cal.get(Calendar.HOUR_OF_DAY);
        int minute = cal.get(Calendar.MINUTE);
        String date = String.format("%04d-%02d-%02d T%02d-%02d", year, month, day, hour, minute);

        String dir = "../instances/pmed";
        int iterations = 100;
        double kmax = 0.2;
        if(args.length == 1){
            dir = args[0];
        }else if(args.length == 2){
            dir = args[0];
            iterations = Integer.parseInt(args[1]);
        }else if(args.length == 3){
            dir = args[0];
            iterations = Integer.parseInt(args[1]);
            kmax = Double.parseDouble(args[2]);
        }

        MPIFInstanceFactory factory = new MPIFInstanceFactory();

        String outDir = "experiments/" + date;
        File outDirCreator = new File(outDir);
        outDirCreator.mkdirs();
        String[] extensions = new String[]{".txt"};

        Algorithm<MPIFInstance>[] execution = new Algorithm[]{
                new BVNS(new GreedyConstructive(), new LS(), iterations, kmax),
        };

        for (int i = 0; i < execution.length; i++) {
            System.out.println(i);
            String outputFile = outDir + "/" + execution[i].toString() + "_" + i + ".xlsx";
            Experiment<MPIFInstance, MPIFInstanceFactory> experiment = new Experiment<>(execution[i], factory);
            experiment.launch(dir, outputFile,extensions);
        }
    }
}