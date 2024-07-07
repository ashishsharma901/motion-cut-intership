import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Map;
import java.util.Scanner;

class Expense implements Serializable {
    String description;
    double amount;
    String category;

    public Expense(String description, double amount, String category) {
        this.description = description;
        this.amount = amount;
        this.category = category;
    }
}

class ExpenseTracker {
    private ArrayList<Expense> expenses;

    public ExpenseTracker() {
        this.expenses = new ArrayList<>();
    }

    public void addExpense(String description, double amount, String category) {
        Expense expense = new Expense(description, amount, category);
        expenses.add(expense);
        System.out.println("Expense added successfully.");
    }

    public void viewExpenses() {
        if (expenses.isEmpty()) {
            System.out.println("No expenses recorded yet.");
        } else {
            System.out.println("Expense List:");
            for (Expense expense : expenses) {
                System.out.println("Description: " + expense.description +
                        ", Amount: " + expense.amount +
                        ", Category: " + expense.category);
            }
        }
    }

    public void viewExpenseSummaries() {
        if (expenses.isEmpty()) {
            System.out.println("No expenses recorded yet.");
        } else {
            double totalExpense = 0;
            Map<String, Double> categoryExpenses = new HashMap<>();

            for (Expense expense : expenses) {
                totalExpense += expense.amount;
                categoryExpenses.put(expense.category, categoryExpenses.getOrDefault(expense.category, 0.0) + expense.amount);
            }

            System.out.println("Total Expense: " + totalExpense);
            for (Map.Entry<String, Double> entry : categoryExpenses.entrySet()) {
                System.out.println("Category: " + entry.getKey() + ", Amount: " + entry.getValue());
            }
        }
    }

    public void saveExpensesToFile(String filename) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filename))) {
            oos.writeObject(expenses);
            System.out.println("Expenses saved to file: " + filename);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("unchecked")
    public void loadExpensesFromFile(String filename) {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filename))) {
            expenses = (ArrayList<Expense>) ois.readObject();
            System.out.println("Expenses loaded from file: " + filename);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}

public class ExpenseTrackerApp {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ExpenseTracker expenseTracker = new ExpenseTracker();

        try {
            while (true) {
                System.out.println("\nExpense Tracker Menu:");
                System.out.println("1. Add Expense");
                System.out.println("2. View Expenses");
                System.out.println("3. View Expense Summaries");
                System.out.println("4. Save Expenses to File");
                System.out.println("5. Load Expenses from File");
                System.out.println("6. Exit");

                System.out.print("Enter your choice (1-6): ");
                int choice = scanner.nextInt();
                scanner.nextLine(); // Consume the newline character

                switch (choice) {
                    case 1:
                        System.out.print("Enter expense description: ");
                        String description = scanner.nextLine();

                        double amount = 0;
                        boolean validAmount = false;
                        while (!validAmount) {
                            try {
                                System.out.print("Enter expense amount: ");
                                amount = scanner.nextDouble();
                                validAmount = true;
                            } catch (InputMismatchException e) {
                                System.out.println("Invalid input. Please enter a valid number for amount.");
                                scanner.nextLine(); // Consume the invalid input
                            }
                        }

                        scanner.nextLine(); // Consume the newline character

                        System.out.print("Enter expense category: ");
                        String category = scanner.nextLine();

                        expenseTracker.addExpense(description, amount, category);
                        break;

                    case 2:
                        expenseTracker.viewExpenses();
                        break;

                    case 3:
                        expenseTracker.viewExpenseSummaries();
                        break;

                    case 4:
                        System.out.print("Enter filename to save expenses: ");
                        String saveFilename = scanner.nextLine();
                        expenseTracker.saveExpensesToFile(saveFilename);
                        break;

                    case 5:
                        System.out.print("Enter filename to load expenses from: ");
                        String loadFilename = scanner.nextLine();

                        File file = new File(loadFilename);
                        if (file.exists()) {
                            expenseTracker.loadExpensesFromFile(loadFilename);
                        } else {
                            System.out.println("File does not exist: " + loadFilename);
                        }
                        break;

                    case 6:
                        System.out.println("Exiting Expense Tracker. Goodbye!");
                        System.exit(0);
                        break;

                    default:
                        System.out.println("Invalid choice. Please enter a number between 1 and 6.");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            scanner.close();
        }
    }
}
