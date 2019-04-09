package fr.insa.gravityrocket.logic.entity.rocket;

/**
 * Classe décrivant un réservoir de fusée avec une capacité maximale propre et un contenu dynamique
 */
public class FuelTank
{

    /**
     * La densité du carburant
     */
    public static final double FUEL_DENSITY = 4;

    /**
     * La capacité maximale du réservoir
     */
    private final double capacity;
    /**
     * Le volume de carburant dans le réservoir
     */
    private       double fuelVolume;

    public FuelTank(double capacity) {
        this.capacity = capacity;
        this.fuelVolume = this.capacity;
    }

    /**
     * Retire un volume donné de carburant du réservoir
     *
     * @param volume La quantité de carburant à retirer du réservoir
     */
    public void removeFuel(double volume) {
        setFuelVolume(getFuelVolume() - volume);
    }

    /**
     * Ajoute un volume donné de carburant au réservoir
     *
     * @param volume La quantité de carburant à ajouter au réservoir
     */
    public void addFuel(double volume) {
        setFuelVolume(getFuelVolume() + volume);
    }

    /**
     * Remplit le réservoir
     */
    public void fill() {
        setFuelVolume(getCapacity());
    }

    /**
     * Change le volume de carburant dans le réservoir tout en gardant ce volume dans l'intervalle [0; capacity]
     *
     * @param fuelVolume Le volume de carburant dans le réservoir
     */
    public void setFuelVolume(double fuelVolume) {
        this.fuelVolume = Math.max(0, Math.min(fuelVolume, getCapacity()));
    }

    public double getFuelVolume() {
        return fuelVolume;
    }

    public double getCapacity() {
        return capacity;
    }

    public boolean isEmpty() {
        return this.fuelVolume == 0;
    }

    public double getMass() {
        return 1000 + this.fuelVolume * FUEL_DENSITY;
    }

}