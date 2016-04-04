public class Main {

    public static void main(String[] args) {
        Nissan nissan = new Nissan();
            nissan.carModel = "300zx";
            nissan.carYear = 1991;
            nissan.engineSize = 3.0;
            nissan.transType = "manual";

        System.out.println("Type of car: " + nissan.carModel);
        System.out.println("Year car was manufactured: " + nissan.carYear);
        System.out.println("This car has a " + nissan.engineSize + " liter engine and a " + nissan.transType +
                            " transmission.");


        G37 g37 = new G37();
            g37.carMake = "Infiniti";
            g37.crankHorsePower = 350;
            g37.isCarCleaned = false;
            g37.sizeOfEngine = 3.7;

        System.out.println("The car I currently drive is manufactured by " + g37.carMake);
        System.out.println("This car has a " + g37.crankHorsePower + " " + g37.sizeOfEngine + "liter engine.");
        System.out.println("My car is clean right now: " + g37.isCarCleaned);


        Mollys mollys = new Mollys();
            mollys.fishColor = "black";
            mollys.howManyInchesLong = 3;
            mollys.isPregnant = false;

        System.out.println("The molly that this is about is " + mollys.fishColor);
        System.out.println("My " + mollys.fishColor + " molly is " + mollys.howManyInchesLong + " inches long.");
        System.out.println("This molly is pregnant: " + mollys.isPregnant);


        Guppy guppy = new Guppy();
            guppy.gallonsOfFishTank = 10 ;
            guppy.isPregnant = true;
            guppy.tailFinColor = "blue";

        System.out.println("My" + guppy.tailFinColor + " tail guppy is kept in a " + guppy.gallonsOfFishTank +
                            " gallon tank.");
        System.out.println("This particular fish is pregnant:" + guppy.isPregnant);


        Stella stella = new Stella();
            stella.ageOfDogInWeeks = 12;
            stella.colorsOfDog = "brown and white";
            stella.typeOfDog = "Jack Russell Terrier";

        System.out.println("I nought my daughter a " + stella.colorsOfDog + " " + stella.typeOfDog +
                            " as a surprise.");
        System.out.println("Her name is Stella and she is now " + stella.ageOfDogInWeeks + " weeks old.");
    }
}
