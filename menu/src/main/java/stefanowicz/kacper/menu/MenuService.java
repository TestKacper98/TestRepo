package stefanowicz.kacper.menu;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import stefanowicz.kacper.exception.AppException;
import stefanowicz.kacper.model.Car;
import stefanowicz.kacper.model.Statistics;
import stefanowicz.kacper.model.enums.CarBodyType;
import stefanowicz.kacper.model.enums.EngineType;
import stefanowicz.kacper.model.enums.TyreType;
import stefanowicz.kacper.service.CarsService;
import stefanowicz.kacper.service.UserDataService;
import stefanowicz.kacper.service.enums.SortBy;
import stefanowicz.kacper.service.enums.StatisticsType;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class MenuService {
    private final CarsService carsService;

    public MenuService(String fileName) { this.carsService = new CarsService(fileName); }

    public void mainMenu() {
        int option;
        do{
            try{
                option = printMenu();
                switch (option) {
                    case 1 -> option1();
                    case 2 -> option2();
                    case 3 -> option3();
                    case 4 -> option4();
                    case 5 -> option5();
                    case 6 -> option6();
                    case 7 -> option7();
                    case 0 -> {
                        UserDataService.close();
                        System.out.println("See you soon!");
                        return;
                    }
                }
            }
            catch (AppException e){
                System.out.println("-----------------------------");
                System.out.println("--------- EXCEPTION ---------");
                System.out.println(e.getMessage());
                System.out.println("-----------------------------");
            }
        }while(true);
    }

    private int printMenu(){
        System.out.println("1. Sort cars");
        System.out.println("2. Cars with given body type");
        System.out.println("3. Cars with given engine type");
        System.out.println("4. Statistics for given type");
        System.out.println("5. Cars with its mileage");
        System.out.println("6. Cars grouped by tyre type");
        System.out.println("7. Cars with chosen components");
        System.out.println("0. Exit");
        return UserDataService.getInt("Choose an option:");
    }

    private void option1(){
        SortBy sortBy =  UserDataService.getSortBy();
        boolean descending =  UserDataService.getBoolean("Descending order");
        List<Car> sortedCars = carsService.sortCars(sortBy, descending);
        System.out.println(toJson(sortedCars));
    }

    private void option2(){
        CarBodyType carBodyType = UserDataService.getCarBodyType();
        BigDecimal fromPrice =  new BigDecimal(UserDataService.getDouble("From price: "));
        BigDecimal toPrice =  new BigDecimal(UserDataService.getDouble("To price: "));

        Set<Car> carsWithBodyType = carsService.getCarsByBodyType(carBodyType, fromPrice, toPrice);

       if(carsWithBodyType.size() == 0 ){
           System.out.println("-----------------------------------------------");
           System.out.println("--- There are no cars with given arguments! ---");
           System.out.println("-----------------------------------------------");
       }
       else{
           System.out.println(toJson(carsWithBodyType));
       }
    }

    private void option3(){
        EngineType engineType = UserDataService.getEngineType();
        Set<Car> carsByEnginge = carsService.getCarsByEngine(engineType);
        System.out.println(toJson(carsByEnginge));
    }

    private void option4(){
        StatisticsType statisticsType =  UserDataService.getStatisticsType();
        Statistics statistics = carsService.getStatistics(statisticsType);
        System.out.println(toJson(statistics));
    }

    private void option5() {
        Map<Car, BigDecimal> carBigDecimalMap = carsService.getCarsMileage();
        carBigDecimalMap.forEach((k, v) -> {
            System.out.println(toJson(k) + " -> " + v);
        });
    }

    private void option6() {
        Map<TyreType, List<Car>> carsByTyreType = carsService.getCarsByTyre();
        System.out.println(toJson(carsByTyreType));
    }
    private void option7(){
        List<String> chosenComp = UserDataService.getComponentsList(carsService.getAllComponents());
        Set<Car> carsWithComponents  = carsService.getCarsContainingComponents(chosenComp);
        if(carsWithComponents.size() == 0){
            System.out.println("------------------------------------------------");
            System.out.println("--- There are no cars with chosen components ---");
            System.out.println("------------------------------------------------");
        }
        else{
            System.out.println(toJson(carsWithComponents));
        }
    }

    private static <T> String toJson(T t){
        try{
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            return gson.toJson(t);
        }
        catch(Exception e){
            throw new AppException("to json conversion exception in menu service");
        }
    }
}
