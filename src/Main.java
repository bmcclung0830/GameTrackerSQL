import com.sun.tools.doclets.formats.html.SourceToHTMLConverter;

public class Main {

    public static void main(String[] args) {
        //System.out.println("Hello World!");

        Car car = new Car();

        car.carMake = "Nissan";
        car.year = 1991;
        car.isTurbo = true;
        car.carColor = "Grey";

        System.out.println("This car is a " + car.carMake + " and the year it was manufactured was " +
                car.year + ". The color of this car is " + car.carColor + ". This car is a turbo car: " +
                car.isTurbo + ".");


    }
}

class Car{
    String carMake;
    int year;
    boolean isTurbo;
    String carColor;
}
