package battleship;

/**
 * @author Mack_TB
 * @version 1.0.7
 * @since 03/26/2021
 */

public enum Ship {
    AIRCRAFT_CARRIER("Aircraft Carrier", 5),
    BATTLESHIP("Battleship", 4),
    SUBMARINE("Submarine", 3),
    CRUISER("Cruiser", 3),
    DESTROYER("Destroyer", 2);

    private final String name;
    private final int numberCells;
    private String[] coordinates;

    Ship(String name, int numberCells) {
        this.name = name;
        this.numberCells = numberCells;
    }

    Ship(String name, int numberCells, String[] coordinates) {
        this(name, numberCells);
        this.coordinates = coordinates;
    }

    public String getName() {
        return name;
    }

    public int getNumberCells() {
        return numberCells;
    }

    public String[] getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(String[] coordinates) {
        this.coordinates = coordinates;
    }
}
