package fr.insa.gravityrocket;

import fr.insa.gravityrocket.model.entity.FuelTank;
import fr.insa.gravityrocket.model.entity.Reactor;
import fr.insa.gravityrocket.model.entity.Rocket;
import fr.insa.gravityrocket.view.GravityRocketWindow;

import javax.swing.*;


public class GravityRocket
{

    public static void main(String[] args)
    {
        GravityRocketWindow window = new GravityRocketWindow();

        Level level1 = new Level();
        FuelTank basicTank = new FuelTank(100);
        Reactor basicReactor = new Reactor(0.005, 1000);
        Rocket rocket = new Rocket(basicTank, basicReactor);
        level1.setRocket(rocket);

        window.getLevelPanel().setLevel(level1);

        Timer timer = new Timer(16, e -> window.repaint());
        timer.start();
    }

}
