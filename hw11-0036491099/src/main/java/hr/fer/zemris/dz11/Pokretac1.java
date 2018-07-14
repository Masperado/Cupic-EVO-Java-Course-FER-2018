package hr.fer.zemris.dz11;

import hr.fer.zemris.art.GrayScaleImage;
import hr.fer.zemris.generic.ga.*;
import hr.fer.zemris.generic.ga.api.ICrossing;
import hr.fer.zemris.generic.ga.api.IMutation;
import hr.fer.zemris.generic.ga.api.ISelection;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

public class Pokretac1 {

    public static void main(String[] args) throws IOException {
        if (args.length != 7) {
            System.out.println("Neispravan broj argumenata!");
            System.exit(1);
        }

        GrayScaleImage image = GrayScaleImage.load(new File(args[0]));
        int rectNumber = Integer.parseInt(args[1]);
        int populationSize = Integer.parseInt(args[2]);
        int maxIter = Integer.parseInt(args[3]);
        double minFit = Double.parseDouble(args[4]);

        Path pathForParam = Paths.get(args[5]);


        File solutionImage = new File(args[6]);

        ISelection<Solution> selection = new TournamentSelection(10);
//        ISelection<Solution> selection = new RouletteWheel(rectNumber, image);
        ICrossing<Solution> crossing = new OnePointCrossing();
        IMutation<Solution> mutation = new SigmaMutation(20);
        Evaluator evaluator = new Evaluator(image);

        EvaluateGA ga = new EvaluateGA(selection, crossing, mutation, minFit,
                maxIter, rectNumber, image, populationSize);

        Solution.play();

        Solution best = ga.run();

        GrayScaleImage imageBest = new GrayScaleImage(image.getWidth(), image.getHeight());
        imageBest = evaluator.draw(best, imageBest);
        imageBest.save(solutionImage);

        Files.write(pathForParam, best.toString().getBytes(StandardCharsets.UTF_8), StandardOpenOption.CREATE);


    }



}
