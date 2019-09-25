package stefanowicz.kacper.service;

import stefanowicz.kacper.exception.AppException;
import stefanowicz.kacper.model.enums.CarBodyType;
import stefanowicz.kacper.model.enums.EngineType;
import stefanowicz.kacper.service.enums.SortBy;
import stefanowicz.kacper.service.enums.StatisticsType;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public final class UserDataService {

    private UserDataService(){};

    private static Scanner scn = new Scanner(System.in);

    public static int getInt(String message){
        System.out.println(message);

        String text =  scn.nextLine();
        if( !text.matches("\\d+") ) {
            throw new AppException("This is not int value");
        }
        return Integer.parseInt(text);
    }

    public static SortBy getSortBy(){
        var counter  =  new AtomicInteger(0);

        Arrays
                .stream(SortBy.values())
                .forEach(val -> System.out.println(counter.incrementAndGet() +". " + val));
        int choice =  getInt("Choose sort type:");

        if(choice < 1 || choice > SortBy.values().length){
            throw new AppException("No sort type with given value!");
        }

        return SortBy.values()[choice - 1];
    }

    public static boolean getBoolean(String message) {
        System.out.println(message + " [ y / n ]");
        String text =  scn.nextLine();
        if( !text.toLowerCase().matches("[yn]")){
            throw new AppException("Invalid value! Permitted values are [y, n]");
        }
        return text.toLowerCase().equals("y");
    }

    public static CarBodyType getCarBodyType(){
        var counter =  new AtomicInteger(0);

        Arrays
                .stream(CarBodyType.values())
                .forEach(carBodyType -> System.out.println(counter.incrementAndGet() + ". " + carBodyType));
        int choice  =  getInt("Choose body type:");
        if(choice < 1 || choice > CarBodyType.values().length){
            throw new AppException("No body type with given value!");
        }

        return CarBodyType.values()[choice - 1];
    }

    public static double getDouble(String message) {
        System.out.println(message);
        String text =  scn.nextLine();
        if( !text.matches("(\\d+\\.)?\\d+")){
            throw new AppException("This is not double value");
        }
        return Double.parseDouble(text);
    }

    public static EngineType getEngineType(){
        var counter = new AtomicInteger(0);

        Arrays
                .stream(EngineType.values())
                .forEach(engineType -> System.out.println(counter.incrementAndGet() + ". " + engineType));
        int choice  =  getInt("Choose engine type:");
        if(choice < 1 || choice > EngineType.values().length){
            throw new AppException("No engine type with given value!");
        }

        return EngineType.values()[choice - 1];
    }

    public static StatisticsType getStatisticsType(){
        var counter  =  new AtomicInteger(0);

        Arrays
                .stream(StatisticsType.values())
                .forEach(statisticsType -> System.out.println(counter.incrementAndGet() + ". " + statisticsType));

        int choice =  getInt("Choose statistics type:");
        if(choice < 1 || choice > StatisticsType.values().length){
            throw new AppException("No statistics type with given value!");
        }

        return StatisticsType.values()[choice - 1];
    }

    public static List<String> getComponentsList(Set<String> components){
        if(components == null){
            throw new AppException("Components set is null");
        }
        List<String> chosenComponents = new ArrayList<>();
        List<String> listComp = new ArrayList<>(components);
        var counter =  new AtomicInteger(0);
        int choice;
        do{
            counter.set(0);
            listComp
                    .forEach(component -> System.out.println(counter.incrementAndGet() +  ". " + component));
            System.out.println("0. Done");
            choice = getInt("Choose component:");
            if(choice > listComp.size()){
                throw new AppException("There is no component with given value!");
            }

            if (choice == 0) {
                System.out.println("Chosen components:");
                chosenComponents.forEach(System.out::println);
                return chosenComponents;
            } else {
                chosenComponents.add(listComp.get(choice - 1));
                listComp.remove(listComp.get(choice - 1));
            }
        }while(chosenComponents.size() != components.size());

        System.out.println("Chosen components:");
        chosenComponents.forEach(System.out::println);
        return chosenComponents;
    }

    public static void close(){
        if( scn != null ){
            scn.close();
            scn = null;
        }
    }
}
