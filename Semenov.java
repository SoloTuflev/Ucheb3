package calc1;
import java.util.Scanner;

public class Semenov {
    
    public static void main(String[] args) {
        System.out.println("Enter the operation");
        System.out.println("1. Addition");
        System.out.println("2. Subtraction");
        System.out.println("3. Multiplication");
        System.out.println("4. Division");
        //Семеёнов Даниил Сергеевич
        Scanner scanner = new Scanner(System.in);
        int operation = scanner.nextInt();
        
         System.out.println("Enter the first number: ");
        int x = scanner.nextInt();
          System.out.println("Enter the second number: ");
        int y = scanner.nextInt();
         
        int res = 0;
         
        if (operation == 1)
             res = x + y;
        else if (operation == 2)
            res = x - y; 
        else if (operation == 3)
            res = x * y;
        else if (operation == 4)
            res = x / y;
        System.out.println("Result = " + res);
        System.out.println("Semenov");
    }
}

