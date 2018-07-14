package hr.fer.zemris.optjava.dz2;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import Jama.Matrix;

public class Jednostavno {

	public static void main(String[] args) throws IOException {
		
		if (args.length!=2 && args.length!=4) {
			System.out.println("Neispravan broj argumenata!");
			System.exit(1);
		}
		
		Point point = null;
		
		if (args.length==4) {
			point = new Point(Double.valueOf(args[2]), Double.valueOf(args[3]));
		}
		int maxIterations = Integer.valueOf(args[1]);
		List<Matrix> solutions = null;
		
		switch(args[0]) {
		case "1a":
			solutions=NumOptAlgorithms.getMinimumGrad(new Function1(), maxIterations, point);
			break;
		case "1b":
			solutions=NumOptAlgorithms.getMinimumNewt(new Function1(), maxIterations, point);
			break;
		case "2a":
			solutions=NumOptAlgorithms.getMinimumGrad(new Function2(), maxIterations, point);
			break;
		case "2b":
			solutions=NumOptAlgorithms.getMinimumNewt(new Function2(), maxIterations, point);
			break;
		default:
			System.out.println("Nepznata naredba");
			System.exit(1);
		}
		

		
		final XYSeries data = new XYSeries("iteration");
		for (int i = 0; i < solutions.size(); i++) {
			double[] temp = solutions.get(i).getRowPackedCopy();
			data.add(temp[0], temp[1]);
			System.out.println("Iteracija: " + (i+1) + ", rješenje: " + temp[0] + ", " + temp[1]);
		}

		final XYSeriesCollection dataset = new XYSeriesCollection();
		dataset.addSeries(data);

		JFreeChart xylineChart = ChartFactory.createXYLineChart("Iteration graph", "x1", "x2", dataset,
				PlotOrientation.VERTICAL, true, true, false);

		int width = 640; /* Width of the image */
		int height = 480; /* Height of the image */
		File XYChart = new File("XYLineChart.jpeg");
		ChartUtilities.saveChartAsJPEG(XYChart, xylineChart, width, height);
	}

}
