package hr.fer.zemris.optjava.dz12;

public class EngineSingleton {

    private static Engine engine;

    public static Engine getEngine() {
        if (engine == null){
            engine = new Engine();
        }

        return engine;
    }
}
