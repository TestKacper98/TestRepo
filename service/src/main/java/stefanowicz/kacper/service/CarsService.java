package stefanowicz.kacper.service;

import org.eclipse.collections.impl.collector.BigDecimalSummaryStatistics;
import org.eclipse.collections.impl.collector.Collectors2;
import stefanowicz.kacper.converter.CarsJsonConverter;
import stefanowicz.kacper.exception.AppException;
import stefanowicz.kacper.model.Car;
import stefanowicz.kacper.model.Statistics;
import stefanowicz.kacper.model.enums.CarBodyType;
import stefanowicz.kacper.model.enums.EngineType;
import stefanowicz.kacper.model.enums.TyreType;
import stefanowicz.kacper.service.enums.SortBy;
import stefanowicz.kacper.service.enums.StatisticsType;
import stefanowicz.kacper.validator.CarValidator;

import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CarsService {
    private final Set<Car> cars;

    public CarsService( String fileName ) {
        this.cars = getCarsFromJson(fileName);
    }

    private Set<Car> getCarsFromJson(String fileName){
        var carsConverter = new CarsJsonConverter(fileName);
        var carValidator = new CarValidator();
        var counter = new AtomicInteger(1);

        return carsConverter
                .fromJson()
                .orElseThrow(() -> new AppException("car service - json converter error"))
                .stream()
                .filter(car -> {
                    Map<String, String> errors = carValidator.validate(car);
                    if( carValidator.hasErrors() ){
                        System.out.println("----------------------------");
                        System.out.println("-- Validation error for car no. " + counter.get() + " --");
                        System.out.println("----------------------------");
                        errors.forEach((k, v) -> System.out.println(k + " -> " + v));
                    }
                    counter.incrementAndGet();
                    return !carValidator.hasErrors();
                }).collect(Collectors.toSet());
    }

    /**
     *
     * @param sortBy Argument to sort by
     * @param descOrder Sorting order
     * @return List of cars sorted by given sortBy argument and in order, dependent on descendingOrder argument.
     */
    public List<Car> sortCars(SortBy sortBy, boolean descOrder)
    {
        Stream<Car> carStream = switch (sortBy)
                {
                    case COMPONENTS_AMOUNT -> this.cars
                            .stream()
                            .sorted(Comparator.comparingInt(c -> c.getCarBody().getComponents().size()));

                    case ENGINE_POWER -> this.cars
                            .stream()
                            .sorted(Comparator.comparing(car -> car.getEngine().getPower()));

                    case TYRE_SIZE -> this.cars
                            .stream()
                            .sorted(Comparator.comparing(car -> car.getWheel().getSize()));
                };

        List<Car> sortedCars = carStream.collect(Collectors.toList());
        if(descOrder)
            Collections.reverse(sortedCars);
        return sortedCars;
    }

    /**
     *
     * @param carBodyType
     * @param fromPrice
     * @param toPrice
     * @return Set of cars with given body type and in given price range <fromPrice, toPrice>.
     */
    public Set<Car> getCarsByBodyType(CarBodyType carBodyType, BigDecimal fromPrice, BigDecimal toPrice)
    {
        if(fromPrice.compareTo(toPrice) > 0)
        {
            throw new IllegalArgumentException("ToPrice has to bo greater than or equal to fromPrice");
        }
        return this.cars
                .stream()
                .filter(car ->
                        car.getCarBody().getType().equals(carBodyType)
                                && car.getPrice().compareTo(fromPrice) >= 0
                                && car.getPrice().compareTo(toPrice) <= 0)
                .collect(Collectors.toSet());
    }

    /**
     *
     * @param engineType
     * @return Set of cars with given engineType, sorted in alphabetical order by model name.
     */
    public Set<Car> getCarsByEngine(EngineType engineType)
    {
        return this.cars
                .stream()
                .filter(car -> car.getEngine().getType().equals(engineType))
                .sorted(Comparator.comparing(Car::getModel))
                .collect(Collectors.toCollection(LinkedHashSet::new));
    }

    /**
     * Method prints out maximum, minimum and average value for given statistics type.
     * @param statisticsType
     */
    public Statistics getStatistics(StatisticsType statisticsType)
    {
        BigDecimalSummaryStatistics statistics = switch (statisticsType) {
            case ENGINE_POWER -> this.cars
                    .stream()
                    .collect(Collectors2.summarizingBigDecimal(car -> car.getEngine().getPower()));
            case MILEAGE -> this.cars
                    .stream()
                    .collect(Collectors2.summarizingBigDecimal(Car::getMileage));
            case PRICE -> this.cars
                    .stream()
                    .collect(Collectors2.summarizingBigDecimal(Car::getPrice));
        };

        return Statistics
                .builder()
                .average(statistics.getAverage())
                .maximum(statistics.getMax())
                .minimum(statistics.getMin())
                .build();
    }

    /**
     *
     * @return Map with Car object as key and its mileage as value, sorted by value in descending order.
     */
    public Map<Car, BigDecimal> getCarsMileage() {
        return this.cars
                .stream()
                .sorted(Comparator.comparing(Car::getMileage, Comparator.reverseOrder()))
                .collect(Collectors.toMap(
                        Function.identity(),
                        Car::getMileage,
                        (m1, m2) -> m1,
                        LinkedHashMap::new
                ));
    }

    /**
     *
     * @return Map with tyre type as key and list of cars with this tyre type as value, sorted by key size.
     */
    public Map<TyreType, List<Car>> getCarsByTyre() {
        Map<TyreType, List<Car>> resultMap = new LinkedHashMap<>();
        Arrays
                .stream(TyreType.values())
                .forEach(tyreType -> resultMap.put(
                        tyreType,
                        this.cars
                                .stream()
                                .filter(car -> car.getWheel().getType().equals(tyreType))
                                .collect(Collectors.toList())
                ));

        return resultMap
                .entrySet()
                .stream()
                .sorted((e1, e2) -> Integer.compare(e2.getValue().size(), e1.getValue().size()))
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (e1, e2) -> e1,
                        LinkedHashMap::new
                ));
    }

    /**
     *
     * @return Set of all available components.
     */
    public Set<String> getAllComponents(){
       return  this.cars
                .stream()
                .flatMap(car -> car.getCarBody().getComponents().stream())
                 .collect(Collectors.toSet());
    }

    /**
     * @param components
     * @return Set of cars which contains all of given components.
     */
    public Set<Car> getCarsContainingComponents(List<String> components) {
        return this.cars
                .stream()
                .filter(car -> car.getCarBody().getComponents().containsAll(components))
                .sorted(Comparator.comparing(Car::getModel))
                .collect(Collectors.toCollection(LinkedHashSet::new));
    }
}
