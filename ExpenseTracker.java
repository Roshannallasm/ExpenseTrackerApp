import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ExpenseTracker {
    private List<Expense> expenses = new ArrayList<>();
    private String currentUser;

    public ExpenseTracker(String currentUser) {
        this.currentUser = currentUser;
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
        Object object = ois.readObject();
        if (object instanceof List<?>) {
            List<?> rawList = (List<?>) object;
            
            // Create a new ArrayList and add elements from rawList
            expenses = new ArrayList<>(rawList.size());
            for (Object element : rawList) {
                if (element instanceof Expense) {
                    expenses.add((Expense) element);
                } else {
                    // Handle unexpected elements if necessary
                    System.out.println("Unexpected element type found in the list.");
                }
            }
            
            System.out.println("Expense data loaded successfully!");
        } else {
            System.out.println("Invalid data format in the file.");
        }
    } catch (IOException | ClassNotFoundException e) {
        System.out.println("No saved data found.");
    }
}
}
