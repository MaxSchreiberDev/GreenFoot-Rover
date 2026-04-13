import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Test1 here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Test1 extends Planet
{

    /**
     * Constructor for objects of class Test1.
     * 
     */
    public Test1()
    {
        prepare();
    }
    
    /**
     * Prepare the world for the start of the program.
     * That is: create the initial objects and add them to the world.
     */
    private void prepare()
    {
        Huegel huegel = new Huegel();
        addObject(huegel,7,4);
        Huegel huegel2 = new Huegel();
        addObject(huegel2,7,6);
        Huegel huegel3 = new Huegel();
        addObject(huegel3,7,5);
        Huegel huegel4 = new Huegel();
        addObject(huegel4,7,7);
        Huegel huegel5 = new Huegel();
        addObject(huegel5,7,8);
        Huegel huegel6 = new Huegel();
        addObject(huegel6,7,9);
        Marke marke = new Marke();
        addObject(marke,11,6);
        removeObject(huegel6);
        Rover rover = new Rover();
        addObject(rover,3,6);
        Gestein gestein = new Gestein();
        addObject(gestein,8,4);
        gestein.setLocation(8,4);
        Gestein gestein2 = new Gestein();
        addObject(gestein2,8,4);
        Gestein gestein3 = new Gestein();
        addObject(gestein3,8,2);
        Gestein gestein4 = new Gestein();
        addObject(gestein4,8,3);
        gestein.setLocation(8,4);
        gestein.setLocation(8,4);
        removeObject(gestein2);
        removeObject(gestein);
        removeObject(gestein4);
        removeObject(gestein3);
    }
}
