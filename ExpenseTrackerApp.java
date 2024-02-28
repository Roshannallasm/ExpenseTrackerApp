import java.io.*;
import java.util.*;

class Expense {
    String date;
    String category;
    double amount;

    public Expense(String date, String category, double amount) {
        this.date = date;
        this.category = category;
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "Date: " + date + ", Category: " + category + ", Amount: $" + amount;
    }
}

class ExpenseTracker {
    private List<Expense> expenses;
    private String currentUser;

    public ExpenseTracker(String currentUser) {
        this.currentUser = currentUser;
        this.expenses = new ArrayList<>();
    }

    public void addExpense(String date, String category, double amount) {
        Expense newExpense = new Expense(date, category, amount);
        expenses.add(newExpense);
        System.out.println("Expense added successfully!");
    }

    public void displayExpenses() {
        if (expenses.isEmpty()) {
            System.out.println("No expenses found.");
        } else {
            System.out.println("Expense List for " + currentUser + ":");
            for (Expense expense : expenses) {
                System.out.println(expense);
            }
        }
    }

    public double calculateTotalExpense(String category) {
        double totalExpense = 0;
        for (Expense expense : expenses) {
            if (expense.category.equalsIgnoreCase(category)) {
                totalExpense += expense.amount;
            }
        }
        return totalExpense;
    }

    public void saveToFile() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(currentUser + "_expenses.ser"))) {
            oos.writeObject(expenses);
            System.out.println("Expense data saved successfully!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadFromFile() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(currentUser + "_expenses.ser"))) {
            expenses = (List<Expense>) ois.readObject();
            System.out.println("Expense data loaded successfully!");
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("No saved data found.");
        }
    }
}

public class ExpenseTrackerApp {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Welcome to Expense Tracker!");

        System.out.print("Enter your username: ");
        String username = scanner.nextLine();

        ExpenseTracker expenseTracker = new ExpenseTracker(username);

        int choice;
        do {
            System.out.println("\n1. Add Expense");
            System.out.println("2. Display Expenses");
            System.out.println("3. Calculate Total Expense for a Category");
            System.out.println("4. Save Expenses to File");
            System.out.println("5. Load Expenses from File");
            System.out.println("0. Exit");
            System.out.print("Enter your choice: ");
            choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    System.out.print("Enter date (MM/DD/YYYY): ");
                    String date = scanner.next();
                    System.out.print("Enter category: ");
                    String category = scanner.next();
                    System.out.print("Enter amount: $");
                    double amount = scanner.nextDouble();
                    expenseTracker.addExpense(date, category, amount);
                    break;
                case 2:
                    expenseTracker.displayExpenses();
                    break;
                case 3:
                    System.out.print("Enter category: ");
                    String categoryToCalculate = scanner.next();
                    double totalExpense = expenseTracker.calculateTotalExpense(categoryToCalculate);
                    System.out.println("Total Expense for category " + categoryToCalculate + ": $" + totalExpense);
                    break;
                case 4:
                    expenseTracker.saveToFile();
                    break;
                case 5:
                    expenseTracker.loadFromFile();
                    break;
            }

        } while (choice != 0);
    }
}
