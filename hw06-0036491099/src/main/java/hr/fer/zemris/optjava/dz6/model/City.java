package hr.fer.zemris.optjava.dz6.model;

import java.util.List;
import java.util.stream.Collectors;

public class City {

	private double x;
	private double y;
	private int index;
	private List<City> candidates;

	public City(double x, double y, int index) {
		this.x = x;
		this.y = y;
		this.index = index;
	}

	public void generateCandidatesList(List<City> allCities, int k) {

		List<City> sortedList = allCities.stream().collect(Collectors.toList());
		sortedList.sort((city1, city2) -> Double.compare(distance(city1), distance(city2)));
		this.candidates = sortedList.stream().limit(k).collect(Collectors.toList());
	}

	public double distance(City city) {
		if (city == this) {
			return Double.MAX_VALUE;
		}

		return Math.sqrt(Math.pow(x - city.getX(), 2) + Math.pow(y - city.getY(), 2));
	}

	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}

	public List<City> getCandidates() {
		return candidates;
	}

	public int getIndex() {
		return index;
	}

}
