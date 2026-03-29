import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.*;

public class Rover extends Actor
{
    private Display anzeige;
    
    public int mapY = 11;
    public int mapX = 15;
    
    /**CHATGPT**/

    private class Point {
        public int X;
        public int Y;
        
        public Point(int x, int y) {
            this.X = x;
            this.Y = y;
        }
    }
    
    public ArrayList<Point> punkte = new ArrayList<>();
    
    private boolean pointExist(int x, int y, ArrayList<Point> liste) {
            for (Point p : liste) {
                if (p.X == x && p.Y == y) {
                    return true;
                }
            }
            return false; // Kein Treffer nach der gesamten Liste
        }
    
    private void pointSave(int x, int y, ArrayList<Point> list) {
        if (!pointExist(x, y, list)) { 
            list.add(new Point(x, y));
            System.out.println("Punkt (" + x + "|" + y + ") wurde gespeichert.");
        } else {
            System.out.println("Punkt (" + x + "|" + y + ") ist schon bekannt!");
        }
    }
    
    public void pointShowAll(ArrayList<Point> x) {
        System.out.println("--- Besuchte Punkte ---");
        
        for (Point p : x) {
            // Wir greifen auf die X und Y Variablen des Punkt-Objekts zu
            System.out.println("Punkt bei X: " + p.X + ", Y: " + p.Y);
        }
        
        System.out.println("Gesamtanzahl: " + punkte.size());
    }
       
    /**CHATGPT ENDE**/    

    /**/
    public ArrayList<Point> getPointY(int Y){
        ArrayList<Point> x = new ArrayList<>();
        for (Point p : punkte) {
            if (p.Y == Y) {
                x.add(new Point(p.X, p.Y));
            }        
        }
        for (Point p : x) {
            System.out.println("("+p.X+"|"+p.Y+")");
        }
        return x;
    }

    public Point getMinXPointFromList(ArrayList<Point> x){   
        if (x == null || x.isEmpty()) {
            return null;
        }
        Point lowestPoint = x.get(0);

        for (Point p : x) {
            if (p.X < lowestPoint.X) {
                lowestPoint = p;
            }
        }
    
        return lowestPoint;
    }
    
    public ArrayList<Point> pointsOnLayer = new ArrayList<>();
    public boolean newLayer = false;
    public int currentSearchLayer;
    
    public void next_point_on_layer(int y) {
        if(newLayer){
            pointsOnLayer = getPointY(currentSearchLayer);
        }
        
    }
    
    public void act() 
    {
    } 

    public void toLeftGround()
    {
        toPoint(0, mapY);
    } 
    
    public void toPoint(int x, int y)
    {
        System.out.println("--- Move to Point ---");
        System.out.println("Point: (" + x + "|" + y + ")");
        toPointRotateY(y);
        toPointY(x, y, punkte);
        toPointRotateX(x);
        toPointX(x, y, punkte);
        System.out.println("--- Reached point! ---");
    }    
    
    /**Rotiere den Rover nach Oben oder Unten, je nachdem ob der Punkt über oder unter ihm liegt**/
    private void toPointRotateY(int y) {
        System.out.println("toPointRotateY(" + y + ")");
        if (getY()>y){
            if(getRotation()!=270){
                if(getRotation()==0){
                    drehe("links");
                }
                if(getRotation()==90){
                    drehe("links");
                    drehe("links");
                }
                if(getRotation()==180){
                    drehe("rechts");
                    }
            }
        }
        else if(getY() == y){}
        else {
            if(getRotation()!=90){
                if(getRotation()==0){
                    drehe("rechts");
                }
                if(getRotation()==270){
                    drehe("rechts");
                    drehe("rechts");
                }
                if(getRotation()==180){
                    drehe("links");
                }
            }
        }
    }
    
    private void toPointY(int x, int y, ArrayList<Point> list)
    {
        System.out.println("toPointY(" + x + "," + y + ")");
        boolean driveY = true;
        int rotation = 0;
        while(driveY == true) {
            if(!kannFahren()) {
                drehe("rechts");
                rotation = rotation + 1;
                while(rotation!=0){
                    if(kannFahren()) {
                    fahre();
                    pointSave(getX(), getY(), list);
                    drehe("links");
                    rotation--;
                    } else {
                        break;
                    }
                }
            }
            else if(kannFahren()) {
                fahre();
                pointSave(getX(), getY(), list);
            }
            if(y==getY()) {
                driveY = false;
            }
        }
    }
    
    /**Rotiere den Rover nach Links oder Rechts, je nachdem ob der Punkt links oder rechts von ihm liegt.**/
    private void toPointRotateX(int x) {
        System.out.println("toPointRotateX(" + x + ")");
        if (getX()>x){
            if(getRotation()!=180){
                if(getRotation()==270){
                    drehe("links");
                }
                if(getRotation()==0){
                    drehe("links");
                    drehe("links");
                }
                if(getRotation()==90){
                    drehe("rechts");
                }
            }
        }
        else if(getX() == x) {}
        else {
            if(getRotation()!=0){
                if(getRotation()==270){
                    drehe("rechts");
                }
                if(getRotation()==180){
                    drehe("rechts");
                    drehe("rechts");
                }
                if(getRotation()==90){
                    drehe("links");
                }
            }
        }
    }
    
    private void toPointX(int x, int y, ArrayList<Point> list)
    {
        System.out.println("toPointX(" + x + "," + y + ")");
        boolean left = true;
        int rotation = 0;
        while(left == true) {
            if(!kannFahren()) {
                drehe("rechts");
                rotation = rotation + 1;
                while(rotation!=0){
                    if(kannFahren()) {
                    fahre();
                    pointSave(getX(), getY(), list);
                    drehe("links");
                    rotation--;
                    } else {
                        break;
                    }
                    if(y==getY() && !huegelZwischenPunkt(x, y)){
                        toPointRotateX(x);
                        break;
                    }
                    else if(x==getX()&& !huegelZwischenPunkt(x, y)){
                        toPointRotateY(y);
                        break;
                    }
                }
            }
            else if (y==getY() && !huegelZwischenPunkt(x, y)){
                toPointRotateX(x);
            }
            else if(x==getX() && !huegelZwischenPunkt(x, y)){
                toPointRotateY(y);       
            }
            if(kannFahren()) {
                fahre();
                pointSave(getX(), getY(), list);
            }
            if(y==getY() && getX() == x){
                left = false;
            }
        }
    }
    
    /**CHATGPT**/
    /**
     * Der Rover bewegt sich ein Feld in Fahrtrichtung weiter.
     * Sollte sich in Fahrtrichtung ein Objekt der Klasse Huegel befinden oder er sich an der Grenze der Welt befinden,
     * dann erscheint eine entsprechende Meldung auf dem Display.
     */
    private void fahre()
    {
        if(!kannFahren())
        {
            System.out.println(warumNichtFahren());
            return;
        }
    
        move(1);
        Greenfoot.delay(1);
    }

    private boolean kannFahren()
    {
        // Hindernis direkt vor dem Rover
        if(huegelVorhanden("vorne"))
        {
            return false;
        }
    
        // Oben am Rand (Rotation 270 = nach oben)
        if(getRotation() == 270 && getY() == 0)
        {
            return false;
        }
    
        // Unten
        if(getRotation() == 90 && getY() == getWorld().getHeight() - 1)
        {
            return false;
        }
    
        // Rechts
        if(getRotation() == 0 && getX() == getWorld().getWidth() - 1)
        {
            return false;
        }
    
        // Links
        if(getRotation() == 180 && getX() == 0)
        {
            return false;
        }
    
        return true;
    }
    
    private String warumNichtFahren()
    {
        if(huegelVorhanden("vorne"))
            return "Zu steil!";
    
        if(getRotation() == 270 && getY() == 0)
            return "Oben ist Ende!";
    
        if(getRotation() == 90 && getY() == getWorld().getHeight() - 1)
            return "Unten ist Ende!";
    
        if(getRotation() == 0 && getX() == getWorld().getWidth() - 1)
            return "Rechts ist Ende!";
    
        if(getRotation() == 180 && getX() == 0)
            return "Links ist Ende!";
    
        return "Unbekannter Fehler";
    }
    
    public boolean huegelZwischenPunkt(int zielX, int zielY) {
        int aktX = getX();
        int aktY = getY();
    
        // 1. Schrittweite auf der X- und Y-Achse bestimmen (-1, 0 oder 1)
        int dx = 0;
        if (zielX > aktX) dx = 1;
        if (zielX < aktX) dx = -1;
    
        int dy = 0;
        if (zielY > aktY) dy = 1;
        if (zielY < aktY) dy = -1;
    
        // 2. Berechnen, wie viele Felder wir insgesamt prüfen müssen
        int maxDistanz = Math.max(Math.abs(zielX - aktX), Math.abs(zielY - aktY));
    
        // 3. Feld für Feld den Weg abschreiten (von 1 bis kurz vor das Ziel)
        for (int i = 1; i < maxDistanz; i++) {
            // Offset für den aktuellen Prüf-Schritt berechnen
            int offsetX = i * dx;
            int offsetY = i * dy;
    
            // Prüfen, ob an diesem Offset ein Hügel liegt
            Huegel h = (Huegel) getOneObjectAtOffset(offsetX, offsetY, Huegel.class);
    
            // Wenn ein Hügel gefunden wurde und die Steigung > 30 ist -> Weg blockiert
            if (h != null && h.getSteigung() > 30) {
                return true;
            }
        }
    
        // Die Schleife ist durchgelaufen und hat keinen Hügel gefunden
        return false;
    }
    
    /**CHATGPT ENDE**/
    
    /**
     * Der Rover dreht sich um 90 Grad in die Richtung, die mit richtung (ï¿½linksï¿½ oder ï¿½rechtsï¿½) ï¿½bergeben wurde.
     * Sollte ein anderer Text (String) als "rechts" oder "links" ï¿½bergeben werden, dann erscheint eine entsprechende Meldung auf dem Display.
     */
    private void drehe(String richtung)
    {
        if(richtung=="rechts")
        {
            setRotation(getRotation()+90);
        }
        else if (richtung=="links")
        {
            setRotation(getRotation()-90);
        }
        else
        {
            System.out.println("Befehl nicht korrekt!");
        }
    }

    /**
     * Der Rover gibt durch einen Wahrheitswert (true oder false )zurï¿½ck, ob sich auf seiner Position ein Objekt der Klasse Gestein befindet.
     * Eine entsprechende Meldung erscheint auch auf dem Display.
     */
    private boolean gesteinVorhanden()
    {
        if(getOneIntersectingObject(Gestein.class)!=null)
        {
            System.out.println("Gestein gefunden!");
            return true;

        }

        return false;
    }

    /**
     * Der Rover ï¿½berprï¿½ft, ob sich in richtung ("rechts", "links", oder "vorne") ein Objekt der Klasse Huegel befindet.
     * Das Ergebnis wird auf dem Display angezeigt.
     * Sollte ein anderer Text (String) als "rechts", "links" oder "vorne" ï¿½bergeben werden, dann erscheint eine entsprechende Meldung auf dem Display.
     */
    private boolean huegelVorhanden(String richtung)
    {
        int rot = getRotation();

        if (richtung=="vorne" && rot==0 || richtung=="rechts" && rot==270 || richtung=="links" && rot==90)
        {
            if(getOneObjectAtOffset(1,0,Huegel.class)!=null && ((Huegel)getOneObjectAtOffset(1,0,Huegel.class)).getSteigung() >30)
            {
                return true;
            }
        }

        if (richtung=="vorne" && rot==180 || richtung=="rechts" && rot==90 || richtung=="links" && rot==270)
        {
            if(getOneObjectAtOffset(-1,0,Huegel.class)!=null && ((Huegel)getOneObjectAtOffset(-1,0,Huegel.class)).getSteigung() >30)
            {
                return true;
            }
        }

        if (richtung=="vorne" && rot==90 || richtung=="rechts" && rot==0 || richtung=="links" && rot==180)
        {
            if(getOneObjectAtOffset(0,1,Huegel.class)!=null && ((Huegel)getOneObjectAtOffset(0,1,Huegel.class)).getSteigung() >30)
            {
                return true;
            }

        }

        if (richtung=="vorne" && rot==270 || richtung=="rechts" && rot==180 || richtung=="links" && rot==0)
        {
            if(getOneObjectAtOffset(0,-1,Huegel.class)!=null && ((Huegel)getOneObjectAtOffset(0,-1,Huegel.class)).getSteigung() >30)
            {
                return true;
            }

        }

        if(richtung!="vorne" && richtung!="links" && richtung!="rechts")
        {
            System.out.println("Befehl nicht korrekt!");
        }

        return false;
    }
    
    /**
     * Der Rover ermittelt den Wassergehalt des Gesteins auf seiner Position und gibt diesen auf dem Display aus.
     * Sollte kein Objekt der Klasse Gestein vorhanden sein, dann erscheint eine entsprechende Meldung auf dem Display.
     */
    public void analysiereGestein()
    {
        if(gesteinVorhanden())
        {
            System.out.println("Gestein untersucht! Wassergehalt ist " + ((Gestein)getOneIntersectingObject(Gestein.class)).getWassergehalt()+"%.");
            Greenfoot.delay(1);
            removeTouching(Gestein.class);
        }
        else 
        {
            System.out.println("Hier ist kein Gestein");
        }
    }

    /**
     * Der Rover erzeugt ein Objekt der Klasse ï¿½Markierungï¿½ auf seiner Position.
     */
    private void setzeMarke()
    {
        getWorld().addObject(new Marke(), getX(), getY());
    }

    /**
     * *Der Rover gibt durch einen Wahrheitswert (true oder false )zurï¿½ck, ob sich auf seiner Position ein Objekt der Marke befindet.
     * Eine entsprechende Meldung erscheint auch auf dem Display.
     */
    public boolean markeVorhanden()
    {
        if(getOneIntersectingObject(Marke.class)!=null)
        {
            return true;
        }

        return false;
    }

    public void showChords() {
        System.out.println("Aktuelle Position: (" + getX() + "|" + getY() + ")");
    }
    
    
    public void showRotation() {
        System.out.println("Aktuelle Rotation: (" + getRotation() + ")");
    }
    
    
    private void entferneMarke()
    {
        if(markeVorhanden())
        {
            removeTouching(Marke.class);
        }
    }
    
    private void displayAusschalten()
    {
        getWorld().removeObject(anzeige);

    }

    protected void addedToWorld(World world)
    {
        setImage("images/rover.png");
        // Das Holen der Welt und Erstellen der Anzeige wurde entfernt
        System.out.println("initializing Robot");
    }

    class Display extends Actor
    {
        GreenfootImage bild; 

        public Display()
        {
          bild = getImage();
        }

        public void act() 
        {

        }  

        public void anzeigen(String pText)
        {
           loeschen();
           getImage().drawImage(new GreenfootImage(pText, 25, Color.BLACK, new Color(0, 0, 0, 0)),10,10);

        }

        public void loeschen()
        {
            getImage().clear();
            setImage("images/nachricht.png");
        }

    }
}
