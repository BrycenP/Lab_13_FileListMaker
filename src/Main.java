import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;
public class Main {
    Scanner in = new Scanner(System.in);
    private static final ArrayList<String> list = new ArrayList<>();
    private static String cFile = "";
    private static boolean save = false;
    public static void viewList() {
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
    public static void moveItem(int fromIndex, int toIndex){
        if (fromIndex >= 0 && fromIndex < list.size() && toIndex >= 0 && toIndex <=list.size()){
        String item = list.remove(fromIndex);
            list.add(toIndex, item);
            save = true;
            System.out.println("Moved item from position " + (fromIndex + 1) + " to position " + (toIndex + 1) + ".");
        }else{
            System.out.println("Cannot complete that move.");
        }
    }
    public static void clearList() {
        list.clear();
        save = true;
        System.out.println("List cleared.");
    }
    public static boolean loadList(String filename) {
        File file = new File(filename);
        if (file.exists()) {
            try (Scanner scanner = new Scanner(file)) {
                list.clear(); // Clear the current list
                while (scanner.hasNextLine()) {
                    list.add(scanner.nextLine());
                }
                cFile = filename;
                save = false;
                System.out.println("List loaded from " + filename);
                return true;
            } catch (IOException e) {
                System.out.println("Error reading file.");
            }
        } else {
            System.out.println("File does not exist.");
        }
        return false;
    }
    public static void saveList(String filename) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(filename))) {
            for (String item : list) {
                writer.println(item);
            }
            cFile = filename;
            save = false;
            System.out.println("List saved to " + filename);
        } catch (FileNotFoundException e) {
            System.out.println("Error: The file '" + filename + "' could not be found or created.");
        } catch (IOException e) {
            System.out.println("Error saving file.");
        }
    }
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        String cmd;
        boolean running = true;

        while (running) {
            Main.viewList();
            System.out.println("\nMenu:");
            System.out.println("A - Add an item");
            System.out.println("D - Delete an item");
            System.out.println("I - Insert an item");
            System.out.println("V - View the list");
            System.out.println("M – Move an item");
            System.out.println("O – Open a list file from disk");
            System.out.println("S – Save the current list file to disk");
            System.out.println("C – Clear removes all the elements from the current list");
            System.out.println("Q - Quit");
            cmd = SafeInput.getRegExString(in, "Please enter a command ", "[AaDdIiVvMmOoSsCcQq]");
            switch (cmd.toUpperCase()) {
                case "A":
                    String newItem = SafeInput.getRegExString(in, "Enter the item to add ", ".*");
                    Main.addItem(newItem);
                    break;
                case "D":
                    int deleteIndex = SafeInput.getRangedInt(in, "Enter the number of the item to delete ", 1, Main.getListSize()) - 1;
                    Main.deleteItem(deleteIndex);
                    in.nextLine();
                    break;
                case "I":
                    String insertItem = SafeInput.getRegExString(in, "Enter the item to insert ", ".*");
                    int insertIndex = SafeInput.getRangedInt(in, "Enter the position to insert the item ", 1, Main.getListSize()) - 1;
                    Main.insertItem(insertIndex, insertItem);
                    in.nextLine();
                    break;
                case "V":
                    Main.viewList();
                    break;
                case "Q":
                    if (SafeInput.getYNConfirm(in, "Are you sure you want to quit (y or n)")) {
                        if (SafeInput.getYNConfirm(in, "Save current list? (y or n)")) {
                            saveList(cFile.isEmpty() ? SafeInput.getRegExString(in, "What would you like to save this list as?", ".*") : cFile);
                        }
                        running = false;
                        System.out.println("GET OUT!!");
                    }
                    break;
                case "M":
                    int fromIndex = SafeInput.getRangedInt(in, "Enter the number of the item to move ", 1, getListSize()) - 1;
                    int toIndex = SafeInput.getRangedInt(in, "Enter the position to move the item to ", 1, getListSize()) - 1;
                    moveItem(fromIndex, toIndex);
                    break;
                case "O":
                    if (!save) {
                        if (SafeInput.getYNConfirm(in, "Save current list? (y or n)")) {
                            saveList(cFile.isEmpty() ? SafeInput.getRegExString(in, "What would you like to save this list as? ", ".*") : cFile);
                        }
                    }
                    String filenameToLoad = SafeInput.getRegExString(in, "What list would you like to open? ", ".*\\.txt");
                    loadList(filenameToLoad);
                    break;
                case "S":
                    if (!cFile.isEmpty()) {
                        saveList(cFile);
                    } else {
                        String saveFilename = SafeInput.getRegExString(in, "What do you want to save this list as? ", ".*\\.txt");
                        saveList(saveFilename);
                    }
                    break;
                case "C":
                    if (SafeInput.getYNConfirm(in, "Are you sure you want to clear the list (y or n) ")) {
                        clearList();
                    }
                    break;
            }

        }
        in.close();
    }
}