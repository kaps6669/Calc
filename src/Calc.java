import java.util.Scanner;
//hello
public class Calc {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите выражение (например, 2 + 3):");
        String input = scanner.nextLine();

        try {
            String result = calc(input);
            System.out.println("Результат: " + result);
        } catch (Exception e) {
            System.out.println("Ошибка: " + e.getMessage());
        }

        scanner.close();
    }

    private static String calc(String input) throws Exception {
        String[] parts = input.split(" ");
        if (parts.length != 3) {
            throw new IllegalArgumentException("Неправильный формат ввода");
        }

        String operand1 = parts[0];
        String operator = parts[1];
        String operand2 = parts[2];

        boolean isRoman1 = isRomanNumber(operand1);
        boolean isRoman2 = isRomanNumber(operand2);

        if ((isRoman1 && !isRoman2) || (!isRoman1 && isRoman2)) {
            throw new IllegalArgumentException("Нельзя использовать одновременно арабские и римские цифры");
        }

        int num1 = isRoman1 ? romanToArabic(operand1) : Integer.parseInt(operand1);
        int num2 = isRoman2 ? romanToArabic(operand2) : Integer.parseInt(operand2);

        if ((num1 < 1 || num1 > 10) || (num2 < 1 || num2 > 10)) {
            throw new IllegalArgumentException("Числа должны быть от 1 до 10 включительно");
        }

        int result;
        switch (operator) {
            case "+":
                result = num1 + num2;
                break;
            case "-":
                result = num1 - num2;
                break;
            case "*":
                result = num1 * num2;
                break;
            case "/":
                if (num2 == 0) {
                    throw new ArithmeticException("Деление на ноль");
                }
                result = num1 / num2;
                break;
            default:
                throw new IllegalArgumentException("Недопустимая операция: " + operator);
        }

        return isRoman1 ? arabicToRoman(result) : String.valueOf(result);
    }

    private static boolean isRomanNumber(String input) {
        for (char c : input.toCharArray()) {
            if (c != 'I' && c != 'V' && c != 'X' && c != 'L' && c != 'C' && c != 'D' && c != 'M') {
                return false;
            }
        }
        return true;
    }

    private static int romanToArabic(String input) {
        int[] values = {1, 5, 10, 50, 100, 500, 1000};
        char[] numerals = {'I', 'V', 'X', 'L', 'C', 'D', 'M'};

        int result = 0;
        int previousValue = 0;

        for (int i = input.length() - 1; i >= 0; i--) {
            int currentValue = 0;
            for (int j = 0; j < numerals.length; j++) {
                if (numerals[j] == input.charAt(i)) {
                    currentValue = values[j];
                    break;
                }
            }

            if (currentValue < previousValue) {
                result -= currentValue;
            } else {
                result += currentValue;
            }

            previousValue = currentValue;
        }

        return result;
    }

    private static String arabicToRoman(int number) {
        if (number < 1) {
            throw new IllegalArgumentException("Результат не может быть меньше единицы");
        }

        int[] values = {1000, 900, 500, 400, 100, 90, 50, 40, 10, 9, 5, 4, 1};
        String[] numerals = {"M", "CM", "D", "CD", "C", "XC", "L", "XL", "X", "IX", "V", "IV", "I"};

        StringBuilder result = new StringBuilder();
        int i = 0;

        while (number > 0) {
            if (number - values[i] >= 0) {
                result.append(numerals[i]);
                number -= values[i];
            } else {
                i++;
            }
        }

        return result.toString();
    }

}
