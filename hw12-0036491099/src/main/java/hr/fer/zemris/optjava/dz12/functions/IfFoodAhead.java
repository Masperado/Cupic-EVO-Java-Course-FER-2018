package hr.fer.zemris.optjava.dz12.functions;

import hr.fer.zemris.optjava.dz12.EngineSingleton;

import java.util.ArrayList;
import java.util.List;

public class IfFoodAhead implements IFunction {

    private IFunction a;
    private IFunction b;
    private int depth;

    public void run() {
        if (EngineSingleton.getEngine().imaliJanjetine()) {
            a.run();
        } else {
            b.run();
        }
    }

    public IFunction getA() {
        return a;
    }

    public void setA(IFunction a) {
        this.a = a;
    }

    public void setB(IFunction b) {
        this.b = b;
    }

    public IFunction copy() {
        IFunction aCopy = a.copy();
        IFunction bCopy = b.copy();

        IfFoodAhead ifCopy = new IfFoodAhead();
        ifCopy.setA(aCopy);
        ifCopy.setB(bCopy);

        return ifCopy;

    }

    public int getDepth() {
        return depth;
    }

    public void setDepth(int depth) {
        this.depth = depth;
        a.setDepth(depth + 1);
        b.setDepth(depth + 1);
    }

    public int getBelow() {
        return 2 + a.getBelow() + b.getBelow();
    }

    public List<IFunction> functionsBelow() {
        List<IFunction> functions = new ArrayList<>();

        if (a instanceof Prog2 || a instanceof Prog3 || a instanceof IfFoodAhead) {
            functions.add(a);
            functions.addAll(a.functionsBelow());
        }

        if (b instanceof Prog2 || b instanceof Prog3 || b instanceof IfFoodAhead) {
            functions.add(b);
            functions.addAll(b.functionsBelow());
        }
        return functions;
    }
}
