package hr.fer.zemris.optjava.dz12.functions;


import hr.fer.zemris.optjava.dz12.EngineSingleton;

import java.util.ArrayList;
import java.util.List;

public class Left implements IFunction{

    private int depth;

    public void run() {
        EngineSingleton.getEngine().left();
    }

    public IFunction copy(){
        return new Left();
    }

    public void setDepth(int depth){
        this.depth = depth;
    }

    public int getDepth() {
        return depth;
    }

    public int getBelow(){
        return 0;
    }

    public List<IFunction> functionsBelow() {
        return new ArrayList<>();
    }

    public IFunction getA() {
        throw new RuntimeException("Nikad ne ajde u terminalni znak na križanju staze.");
    }

    public void setA(IFunction function) {
        throw new RuntimeException("Nikad ne ajde u terminalni znak na križanju staze.");
    }
}
