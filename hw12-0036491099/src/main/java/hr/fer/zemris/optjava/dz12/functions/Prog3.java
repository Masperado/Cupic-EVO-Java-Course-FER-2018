package hr.fer.zemris.optjava.dz12.functions;

import java.util.ArrayList;
import java.util.List;

public class Prog3 implements IFunction {

    private IFunction a;
    private IFunction b;
    private IFunction c;
    private int depth;

    public void run() {
        a.run();
        b.run();
        c.run();
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

    public void setC(IFunction c) {
        this.c = c;
    }

    public IFunction copy() {
        IFunction aCopy = a.copy();
        IFunction bCopy = b.copy();
        IFunction cCopy = c.copy();

        Prog3 prog3Copy = new Prog3();
        prog3Copy.setA(aCopy);
        prog3Copy.setB(bCopy);
        prog3Copy.setC(cCopy);

        return prog3Copy;

    }

    public int getDepth() {
        return depth;
    }

    public void setDepth(int depth) {
        this.depth = depth;
        a.setDepth(depth + 1);
        b.setDepth(depth + 1);
        c.setDepth(depth + 1);
    }

    public int getBelow() {
        return 3 + a.getBelow() + b.getBelow() + c.getBelow();
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

        if (c instanceof Prog2 || c instanceof Prog3 || c instanceof IfFoodAhead) {
            functions.add(c);
            functions.addAll(c.functionsBelow());
        }

        return functions;
    }
}
