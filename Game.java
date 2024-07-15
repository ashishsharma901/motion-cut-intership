import java.util.Scanner;

public class Game {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Story story = new Story();
        
        System.out.println("Welcome to the Text Adventure Game!");
        
        story.start(scanner);
        
        System.out.println("Congratulations! You have won the game!");
        scanner.close();
    }
}

class Story {

    public void start(Scanner scanner) {
        System.out.println("You are in a dark forest. Do you want to: \n 1. Go left \n 2. Go right");
        int choice = getInput(scanner, 2);
        
        if (choice == 1) {
            goLeft(scanner);
        } else {
            goRight(scanner);
        }
    }
    
    private void goLeft(Scanner scanner) {
        System.out.println("You encounter a river. Do you want to:\n1. Swim across\n2. Build a raft");
        int choice = getInput(scanner, 2);
        
        if (choice == 1) {
            System.out.println("You try to swim across but get caught in a current and drown.");
            endGame();
        } else {
            System.out.println("You build a raft and successfully cross the river.");
            afterRiver(scanner);
        }
    }
    
    private void goRight(Scanner scanner) {
        System.out.println("You find a cave. Do you want to:\n1. Enter\n2. Walk past");
        int choice = getInput(scanner, 2);
        
        if (choice == 1) {
            System.out.println("You enter the cave and find treasure.");
            afterCave(scanner);
        } else {
            System.out.println("You walk past the cave and get lost in the forest.");
            endGame();
        }
    }
    
    private void afterRiver(Scanner scanner) {
        System.out.println("After crossing the river, you find a village. Do you want to:\n1. Enter the village\n2. Walk around it");
        int choice = getInput(scanner, 2);
        
        if (choice == 1) {
            System.out.println("You enter the village and find a helpful guide who leads you to safety.");
        } else {
            System.out.println("You walk around the village and get lost in the woods.");
            endGame();
        }
    }
    
    private void afterCave(Scanner scanner) {
        System.out.println("After finding the treasure, you encounter a mysterious figure. Do you want to:\n1. Talk to the figure\n2. Run away");
        int choice = getInput(scanner, 2);
        
        if (choice == 1) {
            System.out.println("The figure reveals themselves to be a friendly wizard who helps you find your way home.");
        } else {
            System.out.println("You run away and get lost in the cave.");
            endGame();
        }
    }
    
    private void endGame() {
        System.out.println("Game Over. Try again.");
        System.exit(0);
    }

    private int getInput(Scanner scanner, int options) {
        int choice = -1;
        while (choice < 1 || choice > options) {
            System.out.println("Enter a number between 1 and " + options + ":");
            if (scanner.hasNextInt()) {
                choice = scanner.nextInt();
                if (choice < 1 || choice > options) {
                    System.out.println("Invalid choice. Please try again.");
                }
            } else {
                System.out.println("Invalid input. Please enter a number.");
                scanner.next(); // Clear invalid input
            }
        }
        scanner.nextLine(); // Clear the buffer after the correct integer input
        return choice;
    }
}
