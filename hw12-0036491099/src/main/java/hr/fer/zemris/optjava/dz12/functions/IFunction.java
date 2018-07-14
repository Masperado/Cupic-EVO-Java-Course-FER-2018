package hr.fer.zemris.optjava.dz12.functions;


import java.util.List;

public interface IFunction {

    void run();

    IFunction copy();

    void setDepth(int depth);

    int getDepth();

    int getBelow();

    List<IFunction> functionsBelow();

    IFunction getA();

    void setA(IFunction function);
}
