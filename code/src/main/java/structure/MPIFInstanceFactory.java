package structure;

import grafo.optilib.structure.InstanceFactory;

public class MPIFInstanceFactory extends InstanceFactory<MPIFInstance> {
    @Override
    public MPIFInstance readInstance(String s) {
        return new MPIFInstance(s);
    }
}
