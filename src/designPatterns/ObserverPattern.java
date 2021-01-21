package designPatterns;

import java.util.ArrayList;
import java.util.List;

public class ObserverPattern {
    public static void main(String[] args) {
        WeatherStation weatherStationA = new WeatherStation();
        WeatherStation weatherStationB = new WeatherStation();


        WeatherReporter reporterA = new WeatherReporter();
        reporterA.register(weatherStationA);
        reporterA.register(weatherStationB);

        WeatherReporter reporterB = new WeatherReporter();
        reporterB.register(weatherStationB);

        weatherStationA.updateWeather("It's raining.");
        weatherStationA.updateWeather("It's sunny.");
        weatherStationB.updateWeather("It's cloudy.");
    }
}

interface Subject {
    void registerObserver(Observer observer);

    void removeObserver(Observer observer);

    void notifyObserver(String message);
}

interface Observer {
    void update(String message);

    void register(Subject subject);
}

class WeatherStation implements Subject {
    List<Observer> observerList;

    public WeatherStation() {
        observerList = new ArrayList<Observer>();
    }

    @Override
    public void registerObserver(Observer observer) {
        if (!observerList.contains(observer)) {
            observerList.add(observer);
        }
    }

    @Override
    public void removeObserver(Observer observer) {
        if (observerList.contains(observer)) {
            observerList.remove(observer);
        } else {
            throw new RuntimeException("You are not in my observer list.");
        }
    }

    @Override
    public void notifyObserver(String message) {
        for (Observer observer : observerList) {
            observer.update(message);
        }
    }

    public void updateWeather(String message) {
        notifyObserver(message);
    }
}

class WeatherReporter implements Observer {
    @Override
    public void update(String message) {
        System.out.println("The newest weather message from weather station is: " + message);
    }

    @Override
    public void register(Subject subject) {
        subject.registerObserver(this);
    }
}