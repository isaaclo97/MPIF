package structure;

import grafo.optilib.structure.Solution;
import grafo.optilib.tools.RandomManager;
import java.util.Random;
import java.util.*;
import java.util.concurrent.TimeUnit;

public class MPIFSolution implements Solution {

    private MPIFInstance instance;
    private int mark;
    private HashSet<Integer> S;
    private int remainingArray[];
    private HashSet<Integer> remaining;
    private ArrayList<Integer> curSol;
    private Queue<Integer> q = new LinkedList<>();;
    private HashMap<Integer,Integer> updateFO = new HashMap<>();
    private boolean visited[];
    private boolean update;
    private HashSet<Integer> remainingCheck = new HashSet<>();;
    private HashSet<Integer> remove = new HashSet<>();;
    private HashSet<Integer> remove2 = new HashSet<>();;
    private Random rand;

    public MPIFSolution(MPIFInstance instance) {
        S = new HashSet<>();
        remaining = new HashSet<>();
        for(int i=1; i<instance.getNodes();i++)
            remaining.add(i);
        this.instance = instance;
        remainingArray = new int[instance.getNodes()];
        visited = new boolean[instance.getFacilities()];
        curSol = new ArrayList<>();
        q = new LinkedList<>();
        this.update = true;
        rand = RandomManager.getRandom();
    }
    public MPIFSolution(MPIFSolution solution) {
        copy(solution);
    }

    public void copy(MPIFSolution solution){
        this.instance = solution.getInstance();
        this.S = new HashSet<>(solution.getS());
        this.curSol = new ArrayList<>(solution.getCurSol());
        this.visited = Arrays.copyOf(solution.visited, solution.visited.length);
        this.remaining = new HashSet<>(solution.getRemaining());
        this.remainingArray = Arrays.copyOf(solution.remainingArray, solution.remainingArray.length);
        this.rand = solution.getRand();
        this.update = false;
        this.mark = solution.getMark();
    }

    private int evaluateMediano() {
            int res = 0;
            for (Integer i: remaining) {
                int minDistance = 0x3f3f3f;
                for (int selected : S) {
                    minDistance = Math.min(minDistance, instance.getGraph()[i][selected]);
                }
                instance.setMapValues(i,minDistance);
                res += minDistance;
            }
        return this.mark = res;
    }

    public int getMark() {
        if(this.update){
            evaluateMediano();
           this.update = false;
        }
        return this.mark;
    }

    public MPIFInstance getInstance() {
        return instance;
    }

    public HashSet<Integer> getS() {
        return S;
    }

    public void addToSolution(int node){
        S.add(node); this.update=true;
        remaining.remove(node);
    }
    public void removeToSolution(int node){
        S.remove(node); this.update=true;
        remaining.add(node);
    }
    public boolean isInSolution(int node){
        return S.contains(node);
    }
    public int evaluateMedianoAddElementOptimized(int element) {
        getMark();
        int realRes = 0;
        for (int i=0; i<remaining.size();i++) {
            int elem = remainingArray[i];
            if (element==elem) continue;
            int valueElement = instance.getGraph()[elem][element];
            int mapValue = instance.getMapValues()[elem];
            if(mapValue>valueElement) realRes += valueElement;
            else realRes+=mapValue;
        }
        return realRes;
    }

    public int evaluateMedianoAddElement(int element) {
        int res = getMark();
        int realRes = 0;
        updateFO.clear();
        for (int i = 1; i < instance.getNodes(); i++) {
            if (S.contains(i) || element==i) continue;
            int valueElement = instance.getGraph()[i][element];
            int mapValue = instance.getMapValues()[i];
            if(mapValue>valueElement) {
                realRes+=valueElement;
                updateFO.put(i,valueElement);
            }
            else {
                realRes+=mapValue;
            }
        }
        return realRes;
    }

    public int[] constructSolution(double factor){
        int totalToAdd = this.getInstance().getFacilities() - this.getS().size();
        int selectedNode = -1;
        int curValue = 0x3f3f3f3f;
        remainingCheck.clear();
        remove.clear();
        remove2.clear();
        int cnt = 0;
        for(Integer j: remaining) {
            if (isPossible(factor, j)) remainingCheck.add(j);
            else remove.add(j);
            remainingArray[cnt]=j;
            cnt++;
        }
        for(int i=0; i<totalToAdd;i++) {
            curValue = 0x3f3f3f3f;
            selectedNode = -1;
            for(Integer j: remainingCheck){
                int valueAux =  this.evaluateMedianoAddElementOptimized(j);
                if(curValue>valueAux || (curValue == valueAux && rand.nextInt(2) == 0)){
                    curValue = valueAux;
                    selectedNode = j;
                }
            }
            if(selectedNode==-1) {
                break;
            }
            evaluateMedianoAddElement(selectedNode);
            remainingCheck.remove(selectedNode);
            addNodeAndUpdateDistance(updateFO, curValue, selectedNode);
            for(Integer j: remove){
                if (isPossible(factor, j)) {
                    remainingCheck.add(j);
                    remove2.add(j);
                }
            }
            for(Integer j:remove2){
                remove.remove(j);
            }
        }
        this.setUpdate(true);
        int res[] = new int[2];
        res[0]=selectedNode;
        res[1]=this.getMark();
        return res;
    }

    private void addNodeAndUpdateDistance(HashMap<Integer, Integer> updateFO, int curValue, int selectedNode) {
        this.addToSolution(selectedNode);
        for(Integer key: updateFO.keySet()) {
            instance.setMapValues(key, updateFO.get(key));
        }
        this.mark = curValue;
        this.update = false;
    }

    private boolean isPossible(double factor, int j) {
        if(this.getS().contains(j))
            return false;
        for (int s : this.getS()) {
            if (this.getInstance().getGraph()[s][j] <= (this.getInstance().getDistance() * factor)) {
                return true;
            }
        }
        return false;
    }

    public void destroySolution(int nodeToDelete){
        curSol.clear();
        for(int s: this.getS()){
            if(s==0 || s==nodeToDelete) continue;
            curSol.add(s);
        }
        this.removeToSolution(nodeToDelete);
        removeElementsDisconnected();
    }

    private void removeElementsDisconnected() {
        q.clear();
        q.add(0);
        Arrays.fill(visited,false);
        while(!q.isEmpty()){
            int curNode = q.poll();
            for(int i=0; i<curSol.size(); i++){
                if(visited[i]) continue;
                if(this.getInstance().getGraph()[curNode][curSol.get(i)]<=this.getInstance().getDistance()){
                    visited[i]=true;
                    q.add(curSol.get(i));
                }
            }
        }
        for(int i=0; i<curSol.size();i++){
            if(!visited[i]) this.removeToSolution(curSol.get(i));
        }
    }
    public void destroySolutionRealRandom(double K){
        int totalToDelete = (int)K;
        curSol.clear();
        for(int s: this.getS()){
            if(s==0) continue;
            curSol.add(s);
        }
        for(int i=0; i<totalToDelete && curSol.size()>0;i++) {
            int deleteFacility = rand.nextInt(curSol.size());
            int remove = curSol.get(deleteFacility);
            while(remove==0){
                remove = curSol.get(deleteFacility);
                deleteFacility = rand.nextInt(curSol.size());
            }

            if(deleteFacility==0) continue;
            int addNode = rand.nextInt(this.getInstance().getNodes());
            //iterations++;
            int iters = 0;
            while(this.isInSolution(addNode) || !this.isFeasibleSwap(addNode,remove) || addNode==0) {
                addNode = rand.nextInt(this.getInstance().getNodes());
                iters++;
                if(iters==100) break;
            }
            if(iters==100) continue;
            this.addToSolution((Integer)addNode);
            this.removeToSolution(remove);
            curSol.remove((Integer)remove);
        }

    }

    public ArrayList<Integer> getCurSol() {
        return curSol;
    }

    public void printSelectedNodes(){
        ArrayList<Integer> arr = new ArrayList<>();
        for(Integer s:S){
            arr.add(s);
        }
        Collections.sort(arr);
        for(Integer s:arr){
            System.out.print(s + " - ");
        }
        System.out.println();
    }
    public HashSet<Integer> getRemaining() {
        return remaining;
    }

    public boolean isFeasibleSwap(int add, int remove) {
        this.removeToSolution(remove);
        int dist = this.getInstance().getDistance();
        for(Integer i: S) {
            if (this.getInstance().getGraph()[remove][i] <= dist) {
                if(this.getInstance().getGraph()[add][i] > dist) {
                    this.addToSolution(remove);
                    return false;
                }
            }
        }
        this.addToSolution(remove);
        return true;
    }

    public void setUpdate(boolean update){
        this.update = update;
    }
    public Random getRand() {
        return rand;
    }
}
