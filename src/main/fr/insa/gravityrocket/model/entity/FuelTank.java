package fr.insa.gravityrocket.model.entity;

public class FuelTank
{
    public static final double FUEL_DENSITY = 4;

    /**
     * La capacité maximale du réservoir
     */
    private final double capacity;
    /**
     * Le volume de carburant dans le réservoir
     */
    private double fuelVolume;

    public FuelTank(double capacity)
    {
        this.capacity = capacity;
        this.fuelVolume = this.capacity;
    }

    public void removeFuel(double volume)
    {
        this.fuelVolume = Math.max(0, this.fuelVolume - volume);
    }

    public void addFuel(double volume)
    {
        this.fuelVolume = Math.min(this.fuelVolume + volume, this.capacity);
    }

    public double getCapacity()
    {
        return capacity;
    }

    public double getFuelVolume()
    {
        return fuelVolume;
    }

    public boolean isEmpty()
    {
        return this.fuelVolume == 0;
    }

    public double getMass()
    {
        return 1000 + this.fuelVolume * FUEL_DENSITY;
    }

}