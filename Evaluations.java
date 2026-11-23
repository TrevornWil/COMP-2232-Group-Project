import java.util.ArrayList;

public class Evaluations {

    public double evaluate(String expression) {
        expression = expression.replace("x", "*");
        expression = expression.replace("÷", "/");
        expression = expression.replace("-", "-");

        ArrayList<String> element = splitIntoElement(expression);
        return solveWithBrackets(element);
    }

    private ArrayList<String> splitIntoElement(String expression) {
        ArrayList<String> element = new ArrayList<String>();
        String currentNumber = "";

        for (int i = 0; i < expression.length(); i++) {
            char currentChar = expression.charAt(i);

            boolean isNegativeSign = false;
            if (currentChar == '-' && currentNumber.equals("")) {
                if (element.size() == 0) {
                    isNegativeSign = true;
                } else {
                    String previousToken = element.get(element.size() - 1);
                    if (isOperator(previousToken) || previousToken.equals("(")) {
                        isNegativeSign = true;
                    }
                }
            }

            if (currentChar == '-' && currentNumber.matches("[0-9.]+[eE]")) {
                isNegativeSign = true;
            }
            if (currentChar == '+' && currentNumber.matches("[0-9.]+[eE]")) {
                isNegativeSign = true;
            }

            boolean isExponent = (currentChar == 'e' || currentChar == 'E')
                && currentNumber.matches("[0-9.]+");

            if (Character.isDigit(currentChar) || currentChar == '.' ||
                isExponent || isNegativeSign) {
                currentNumber = currentNumber + currentChar;
            } else {
                if (!currentNumber.equals("")) {
                    element.add(currentNumber);
                    currentNumber = "";
                }
                element.add(String.valueOf(currentChar));
            }
        }

        if (!currentNumber.equals("")) {
            element.add(currentNumber);
        }
        return element;
    }

    private boolean isOperator(String token) {
        return token.equals("+") || token.equals("-") || token.equals("*") || token.equals("/");
    }

    private double solveWithBrackets(ArrayList<String> element) {
        while (element.contains("(")) {
            int openIndex = element.lastIndexOf("(");

            int closeIndex = -1;
            for (int i = openIndex; i < element.size(); i++) {
                if (element.get(i).equals(")")) {
                    closeIndex = i;
                    break;
                }
            }
            if (closeIndex == -1) {
                throw new IllegalArgumentException("Missing closing bracket");
            }

            ArrayList<String> insideBrackets = new ArrayList<String>(element.subList(openIndex + 1, closeIndex));
            double bracketAnswer = solveWithoutBrackets(insideBrackets);

            for (int i = closeIndex; i >= openIndex; i--) {
                element.remove(i);
            }
            element.add(openIndex, String.valueOf(bracketAnswer));
        }
        return solveWithoutBrackets(element);
    }

    private double solveWithoutBrackets(ArrayList<String> element) {
        if (element.size() == 0) {
            throw new IllegalArgumentException("Empty expression");
        }

        int position = 0;
        while (position < element.size()) {
            String token = element.get(position);

            if (token.equals("*") || token.equals("/")) {
                if (position == 0 || position == element.size() - 1) {
                    throw new IllegalArgumentException("Incomplete expression");
                }
                double leftNumber = Double.parseDouble(element.get(position - 1));
                double rightNumber = Double.parseDouble(element.get(position + 1));
                double answer;

                if (token.equals("*")) {
                    answer = leftNumber * rightNumber;
                } else {
                    if (rightNumber == 0) {
                        throw new ArithmeticException("Division by zero");
                    }
                    answer = leftNumber / rightNumber;
                }

                element.set(position - 1, String.valueOf(answer));
                element.remove(position + 1);
                element.remove(position);
            } else {
                position++;
            }
        }

        double total = Double.parseDouble(element.get(0));
        int index = 1;
        while (index < element.size()) {
            if (index + 1 >= element.size()) {
                throw new IllegalArgumentException("Incomplete expression");
            }
            String operator = element.get(index);
            double nextNumber = Double.parseDouble(element.get(index + 1));

            if (operator.equals("+")) {
                total = total + nextNumber;
            } else if (operator.equals("-")) {
                total = total - nextNumber;
            } else {
                throw new IllegalArgumentException("Invalid expression");
            }
            index = index + 2;
        }
        return total;
    }
}