package hr.fer.zemris.optjava.dz6.model;

import java.util.ArrayList;
import java.util.List;

public class Ant {

	private List<City> cityList;
	private double distanceTraveled;

	public Ant(List<City> cityList) {
		this.cityList = generateList(cityList);
		checkCityList();
		calculateDistanceTraveled();
	}

	private List<City> generateList(List<City> cityList) {
		List<City> list = new ArrayList<>();
		City withIndex0 = null;
		for (City city : cityList) {
			if (city.getIndex() == 0) {
				withIndex0 = city;
				break;
			}
		}
		int indexOf0 = cityList.indexOf(withIndex0);
		for (int i = indexOf0; i < cityList.size(); i++) {
			list.add(cityList.get(i));
		}
		for (int i = 0; i < indexOf0; i++) {
			list.add(cityList.get(i));
		}
		return list;
	}

	private void checkCityList() {
		int indexSum = cityList.size() * (cityList.size() + 1) / 2;
		int currentSum = 0;
		for (City city : cityList) {
			currentSum += (city.getIndex() + 1);
		}
		if (currentSum != indexSum) {
			throw new RuntimeException("Wrong indexes!");
		}
	}

	private void calculateDistanceTraveled() {
		double distanceTraveledTemp = 0;
		City currentCity = cityList.get(0);

		for (int i = 1; i < cityList.size(); i++) {
			City nextCity = cityList.get(i);
			distanceTraveledTemp += currentCity.distance(nextCity);
			currentCity = nextCity;
		}
		distanceTraveledTemp += currentCity.distance(cityList.get(0));

		this.distanceTraveled = distanceTraveledTemp;
	}

	public double getDistanceTraveled() {
		return distanceTraveled;
	}

	public List<City> getCityList() {
		return this.cityList;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < cityList.size(); i++) {
			if (i == (cityList.size() - 1)) {
				sb.append(cityList.get(i).getIndex());
				break;
			}
			sb.append((cityList.get(i).getIndex() + 1) + "->");
		}
		sb.append("\n");
		sb.append("DISTANCE: " + distanceTraveled + "\n");
		return sb.toString();
	}

}
