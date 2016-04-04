import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;


public class Menu {

    public static Scanner scanner = new Scanner(System.in);
    public static HashMap<String,Integer> inventoryList = new HashMap<>();

        public static void menu() {

            int i = 0;
                System.out.println("1. Add item to Inventory\n" +
                        "2. Remove item from Inventory\n" +
                        "3. Update an item's quantity");

                String choice = scanner.nextLine();
                System.out.println(">" + choice + "<");

            switch (choice) {
                case ("1"): {
                    System.out.println("1. What is the name of the item you would like to add?");
                    String item = scanner.nextLine();
                    Integer quantity = (++i);

                    inventoryList.put(item, quantity);
                    break;
                }
                case ("2"): {
                    System.out.println("2. Which item would you like to remove the Inventory?");
                    String item = scanner.nextLine();
                    int quantity = inventoryList.get(item);

                    inventoryList.remove(quantity -1);
                    System.out.println(item + " has been removed.");
                    if (quantity >=0){
                        inventoryList.remove(item);
                        break;
                    }
                }
                case ("3"): {
                    System.out.println("3. Which item would you like to update quantity for?");
                    String item = scanner.nextLine();
                    int quantity = inventoryList.get(item) -1;

                    System.out.println("Quanityt for" + item + "has been updated to " + quantity);
                    break;

                }
                default:
                    System.out.println("Didn't mean to select Inventory? No problem, we can " +
                            "can close out for you");
                    break;
            }
        }

}

