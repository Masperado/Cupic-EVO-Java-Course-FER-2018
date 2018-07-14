package hr.fer.zemris.optjava.dz12;

import hr.fer.zemris.optjava.dz12.functions.IFunction;

public class Solution {
    private int kilaJanjetine;
    private String put;
    private IFunction root;

    public Solution(IFunction root) {
        this.root = root;
        this.root.setDepth(0);
    }

    public void run() {
        while(EngineSingleton.getEngine().moreActions()){
            this.root.run();
        }
        this.kilaJanjetine = EngineSingleton.getEngine().getKilaJanjetine();
        this.put = EngineSingleton.getEngine().getPath();
        EngineSingleton.getEngine().reset();
    }

    public int getKilaJanjetine() {
        return kilaJanjetine;
    }

    public String getPut() {
        return put;
    }

    public IFunction rootCopy(){
        return root.copy();
    }

    public void punish(){
        this.kilaJanjetine *= 0.9;
    }

    public IFunction getRoot() {
        return root;
    }
}
