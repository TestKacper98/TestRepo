package stefanowicz.kacper.main;

import stefanowicz.kacper.menu.MenuService;

public class App {
    public static void main( String[] args ) {
        final String FILE_NAME = "cars.json";
        var menuService =  new MenuService(FILE_NAME);
        menuService.mainMenu();
    }
}
