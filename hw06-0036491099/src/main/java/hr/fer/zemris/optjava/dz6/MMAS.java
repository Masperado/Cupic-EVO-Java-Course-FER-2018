package hr.fer.zemris.optjava.dz6;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import hr.fer.zemris.optjava.dz6.model.Ant;
import hr.fer.zemris.optjava.dz6.model.City;

public class MMAS {

	private int maxGenerations;
	private int maxAnts;
	private double alfa;
	private double beta;
	private List<City> cityList;
	private double a;
	private double ro;
	private double e;

	private List<Ant> ants;
	private Ant bestAnt;
	private Ant currentBestAnt;
	private double[][] tauArray;
	private double[][] miArray;
	private double tauMax;
	private double tauMin;

	public MMAS(int maxGenerations, int maxAnts, double alfa, double beta, List<City> cityList, double ro, double e) {
		this.maxGenerations = maxGenerations;
		this.maxAnts = maxAnts;
		this.alfa = alfa;
		this.beta = beta;
		this.cityList = cityList;
		this.ro = ro;
		this.e = e;
		this.ants = new ArrayList<>(maxAnts);
		generateBestAnt();
		generateTauArray();
		generateMiArray();
	}

	public void run() {
		for (int i = 0; i < maxGenerations; i++) {
			ants.clear();
			for (int j = 0; j < maxAnts; j++) {
				ants.add(createAnt());
			}
			getBestAnt();
			evaporateTauArray();
			if (i > (maxGenerations / 2)) {
				updateTauArray(bestAnt);
			} else {
				updateTauArray(currentBestAnt);
			}
		}
	}

	private void getBestAnt() {
		boolean changed = false;
		currentBestAnt = ants.get(0);
		for (Ant ant : ants) {
			if (ant.getDistanceTraveled() < bestAnt.getDistanceTraveled()) {
				bestAnt = ant;
				System.out.println(bestAnt);
				changed = true;
			}

			if (ant.getDistanceTraveled() < currentBestAnt.getDistanceTraveled()) {
				currentBestAnt = ant;
			}
		}
		if (changed) {
			tauMax = 1.0 / (ro * bestAnt.getDistanceTraveled());
			tauMin = tauMax / a;
		}

	}

	private Ant createAnt() {
		List<City> visited = new ArrayList<>();
		List<City> antCityList = copyCityList(cityList);
		Collections.shuffle(antCityList);
		City currentCity = antCityList.get(0);
		visited.add(currentCity);

		while (visited.size() != antCityList.size()) {

			double sumProbablities = 0;
			List<City> candidatesList = currentCity.getCandidates();
			for (City candidate : candidatesList) {
				if (!visited.contains(candidate))
					sumProbablities += Math.pow(tauArray[currentCity.getIndex()][candidate.getIndex()], alfa)
							* Math.pow(miArray[currentCity.getIndex()][candidate.getIndex()], beta);
			}

			if ((sumProbablities - 1E-20) < 0) {
				boolean added = false;
				List<City> listNew = copyCityList(antCityList);
				final City tempCurrentCity = currentCity;

				listNew.sort((city1, city2) -> Double.compare(tempCurrentCity.distance(city1),
						tempCurrentCity.distance(city2)));
				for (City candidate : listNew) {
					if (!visited.contains(candidate)) {
						visited.add(candidate);
						currentCity = candidate;
						added = true;
						break;
					}
				}
				if (added) {
					continue;
				} else {
					throw new RuntimeException();
				}
			}

			double hit = Math.random() * sumProbablities;
			int index = -1;
			while (hit > 0) {
				index++;
				City candidate = candidatesList.get(index);
				if (!visited.contains(candidate)) {
					hit -= Math.pow(tauArray[currentCity.getIndex()][candidate.getIndex()], alfa)
							* Math.pow(miArray[currentCity.getIndex()][candidate.getIndex()], beta);
				}
			}
			visited.add(candidatesList.get(index));
			currentCity = candidatesList.get(index);
		}

		return new Ant(visited);

	}

	private void updateTauArray(Ant currentBestAnt) {
		List<City> bestAntList = currentBestAnt.getCityList();
		double delta = 1.0 / bestAnt.getDistanceTraveled();
		City currentCity = bestAntList.get(0);
		for (int i = 1; i < bestAntList.size(); i++) {
			City nextCity = bestAntList.get(i);
			tauArray[currentCity.getIndex()][nextCity.getIndex()] += e * delta;

			if (tauArray[currentCity.getIndex()][nextCity.getIndex()] > tauMax) {
				tauArray[currentCity.getIndex()][nextCity.getIndex()] = tauMax;
			}

			tauArray[nextCity.getIndex()][currentCity.getIndex()] = tauArray[currentCity.getIndex()][nextCity
					.getIndex()];

			currentCity = nextCity;
		}
		tauArray[currentCity.getIndex()][bestAntList.get(0).getIndex()] += e * delta;

		if (tauArray[currentCity.getIndex()][bestAntList.get(0).getIndex()] > tauMax) {
			tauArray[currentCity.getIndex()][bestAntList.get(0).getIndex()] = tauMax;
		}

		tauArray[bestAntList.get(0).getIndex()][currentCity.getIndex()] = tauArray[currentCity.getIndex()][bestAntList
				.get(0).getIndex()];
	}

	private void evaporateTauArray() {
		for (int i = 0; i < cityList.size(); i++) {
			for (int j = 0; j < cityList.size(); j++) {
				tauArray[i][j] *= (1 - ro);
				if (tauArray[i][j] < tauMin) {
					tauArray[i][j] = tauMin;
				}
			}
		}
	}

	private void generateMiArray() {
		miArray = new double[cityList.size()][cityList.size()];
		for (int i = 0; i < cityList.size(); i++) {
			for (int j = 0; j < cityList.size(); j++) {
				miArray[i][j] = 1.0 / cityList.get(i).distance(cityList.get(j));
			}
		}

	}

	private void generateBestAnt() {
		List<City> visited = new ArrayList<>();
		List<City> antCityList = copyCityList(cityList);
		Collections.shuffle(antCityList);
		City currentCity = antCityList.get(0);
		visited.add(currentCity);

		while (visited.size() != antCityList.size()) {
			boolean added = false;
			for (City candidate : currentCity.getCandidates()) {
				if (!visited.contains(candidate)) {
					visited.add(candidate);
					currentCity = candidate;
					added = true;
					break;
				}
			}
			if (!added) {
				List<City> listNew = copyCityList(antCityList);
				final City tempCurrentCity = currentCity;

				listNew.sort((city1, city2) -> Double.compare(tempCurrentCity.distance(city1),
						tempCurrentCity.distance(city2)));
				for (City candidate : listNew) {

					if (!(visited.contains(candidate))) {
						visited.add(candidate);
						currentCity = candidate;
						added = true;
						break;
					}
				}
			}
			if (!added) {
				throw new RuntimeException();
			}
		}
		bestAnt = new Ant(visited);
		System.out.println(bestAnt);
	}

	private void generateTauArray() {
		tauArray = new double[cityList.size()][cityList.size()];
		double bestAntDistance = bestAnt.getDistanceTraveled();
		tauMax = 1.0 / (ro * bestAntDistance);
		for (int i = 0; i < cityList.size(); i++) {
			for (int j = 0; j < cityList.size(); j++) {
				tauArray[i][j] = tauMax;
			}
		}
		double n = cityList.size();

		a = n * ((n - 1) / (n * (Math.pow(0.9, (-1.0 / n)) - 1)));

		tauMin = tauMax / a;
	}

	private List<City> copyCityList(List<City> listToBeCopied) {
		return listToBeCopied.stream().collect(Collectors.toList());
	}

}
