package structure;

import grafo.optilib.metaheuristics.Algorithm;
import grafo.optilib.results.Result;
import grafo.optilib.results.TableCreator;
import grafo.optilib.structure.Instance;
import grafo.optilib.structure.InstanceFactory;
import grafo.optilib.tools.RandomManager;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

public class Experiment<I extends Instance, IF extends InstanceFactory<I>> {
    private final long SEED = 1234L;
    private Algorithm<I> alg;
    private IF factory;

    public Experiment(Algorithm<I> alg, IF factory) {
        this.alg = alg;
        this.factory = factory;
    }

    private List<String> readFilesInFolder(String folder, String[] extensions) {
        List files = null;

        try {
            files = (List) Files.list(Paths.get(folder)).map(String::valueOf).filter((path) -> {
                String[] var2 = extensions;
                int var3 = extensions.length;

                for(int var4 = 0; var4 < var3; ++var4) {
                    String ext = var2[var4];
                    if (path.substring(Math.max(path.lastIndexOf("/"), path.lastIndexOf("\\")) + 1).startsWith(".")) {
                        return false;
                    }

                    if (path.endsWith(ext) || path.matches(ext)) {
                        return true;
                    }
                }

                return false;
            }).sorted().collect(Collectors.toList());
        } catch (IOException var5) {
        }

        return files;
    }

    public void launch(String inputFolder, String outputFile, String[] extensions) {
        List<String> files = this.readFilesInFolder(inputFolder, extensions);
        if (files == null) {
            System.err.println("The folder " + inputFolder + " does not contain any file matching the given extensions");
            System.exit(-1);
        }

        List<Result> results = new ArrayList();
        new ArrayList();
        Iterator var7 = files.iterator();

        while(var7.hasNext()) {
            String file = (String)var7.next();
            RandomManager.setSeed(1234L);
            I instance = this.factory.readInstance(file);
            Result res = this.alg.execute(instance);
            results.add(res);
        }

        TableCreator.createTable(outputFile, results);
    }

    public void launchIndividualInstance(String file, String outputFile) {
        List<Result> results = new ArrayList();
        RandomManager.setSeed(1234L);
        I instance = this.factory.readInstance(file);
        Result res = this.alg.execute(instance);
        //results.add(res);
        //TableCreator.createTable(outputFile, results);
    }
}
