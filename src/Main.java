import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    Scanner in = new Scanner(System.in);
    private static final ArrayList<String> list = new ArrayList<>();
    public static void printList() {
        if (list.isEmpty()) {
            System.out.println("There is nothing here");
        } else {
            System.out.println("Current List:");
            for (int x = 0; x < list.size(); x++) {
                System.out.println((x + 1) + ". " + list.get(x));
            }
        }
    }
    public static void addItem(String item) {
        list.add(item);
        System.out.println( item +" added");
    }
    public static int getListSize() {
        return list.size();
    }
    public static void deleteItem(int content) {
        if (content >= 0 && content < list.size()) {
            String delItem = list.remove(content);
            System.out.println(delItem + " deleted: " );
        } else {
            System.out.println("Item not on list.");
        }
    }
    public static void insertItem(int content, String item) {
        if (content >= 0 && content <= list.size()) {
            list.add(content, item);
            System.out.println("Item inserted at position " + content + ": " + item);
        } else {
            System.out.println("Item not on list");
        }
    }


    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        String cmd;
        boolean running = true;

        while (running) {
            Main.printList();
            System.out.println("\nMenu:");
            System.out.println("A - Add an item");
            System.out.println("D - Delete an item");
            System.out.println("I - Insert an item");
            System.out.println("P - Print the list");
            System.out.println("Q - Quit");

            cmd = SafeInput.getRegExString(in, "Please enter a command", "[AaDdIiPpQq]");

            switch (cmd.toUpperCase()) {
                case "A":
                    String newItem = SafeInput.getRegExString(in, "Enter the item to add", ".*");
                    Main.addItem(newItem);
                    break;
                case "D":
                    int deleteIndex = SafeInput.getRangedInt(in,"Enter the number of the item to delete", 1, Main.getListSize()) - 1;
                    Main.deleteItem(deleteIndex);
                    break;
                case "I":
                    String insertItem = SafeInput.getRegExString(in,"Enter the item to insert", ".*");
                    int insertIndex = SafeInput.getRangedInt(in,"Enter the position to insert the item", 1, Main.getListSize()) - 1;
                    Main.insertItem(insertIndex, insertItem);
                    break;
                case "P":
                    Main.printList();
                    break;
                case "Q":
                    if (SafeInput.getYNConfirm(in,"Are you sure you want to quit (Y/N)")) {
                        running = false;
                        System.out.println("GET OUT!!");
                    }
                    break;
            }
        }
        in.close();
    }
}