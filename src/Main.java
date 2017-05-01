import java.io.File;
import java.io.FileWriter;
import java.util.Scanner;
import java.util.Stack;

public class Main {
    public static int size(int[] array, int i) {
        int result = 1;
        for (int j = 0; j < array.length; j++) {
            if (i == array[j]) {
                result += size(array, j + 1);
            }
        }
        return result;
    }

    public static int iteraction = 0;

    public static int findMaxIndex(int[] array, int root, int[] number) {
        int maxLength = -1;
        int index = -1;
        for (int i = 0; i < array.length; i++) {
            if (array[i] == root) {
                if (number[i] > maxLength) {
                    maxLength = number[i];
                    index = i;
                }
            }
        }
        return index;
    }

    public static int[] numirizm(int[] array, int[] number, int root, int[] result) {
        while (true) {
            int index = findMaxIndex(array, root, number);
            if (index == -1) {
                result[iteraction] = root;
                iteraction++;
                array[root - 1] = -1;
                number[root - 1] = -1;
                return result;
            }
            result = numirizm(array, number, index + 1, result);
            if (array[root - 1] == -1) {
                result[iteraction] = root;
                break;
            }
        }
        return result;
    }

    public static int payment(int[] array, int[] length) {
        Stack<Integer> stack = new Stack<>();
        int result = 0;
        stack.push(length[0]);
        result += stack.size();
        for (int i = 1; i < length.length - 1; i++) {
            if (stack.peek() != 0) {
                if (length[i] != array[stack.peek() - 1]) {
                    stack.push(length[i]);
                    result += stack.size();
                } else {
                    while (length[i] == array[stack.peek() - 1]) {
                        stack.pop();
                        if (stack.empty()) {
                            break;
                        }
                    }
                    stack.push(length[i]);
                    result += stack.size();
                }
            } else {
                break;
            }
        }
        return result;
    }

    private static int countElementsNotEqualsZero(int[] array) {
        int count = 0;
        for (int i = 0; i < array.length; i++) {
            if (array[i] != 0) {
                count++;
            }
        }
        return count;
    }

    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(new File("input.txt"));
        int N = sc.nextInt();
        int number = sc.nextInt();
        int[] array = new int[N];
        int[] arrayTemp = new int[N];
        int[] numberOfElements = new int[N];
        int[] result = new int[N];
        for (int i = 0; i < N; i++) {
            numberOfElements[i] = 0;
        }
        sc.nextLine();
        for (int i = 0; i < N; i++) {
            String str = sc.nextLine();
            String[] elements = str.split(":");
            for (int j = 0; j < Integer.valueOf(elements[1]); j++) {
                array[Integer.valueOf(elements[j + 2]) - 1] = Integer.valueOf(elements[0]);
                arrayTemp[Integer.valueOf(elements[j + 2]) - 1] = Integer.valueOf(elements[0]);
            }
        }
        for (int i = 0; i < N; i++) {
            numberOfElements[i] = size(array, i + 1);
        }
        result = numirizm(array, numberOfElements, number, result);
        int[] finalResult = new int[countElementsNotEqualsZero(result)];
        for (int i = 0; i < finalResult.length; i++) {
            finalResult[i] = result[i];
        }
        for (int i = 0; i < finalResult.length; i++) {
            System.out.print(finalResult[i] + " ");
        }
        System.out.println();
        FileWriter wr = new FileWriter("output.txt");
        wr.write(payment(arrayTemp, finalResult) + "\n");
        for (int i = 0; i < finalResult.length - 1; i++) {
            wr.write(finalResult[i] + " ");
        }
        wr.write(finalResult[finalResult.length - 1] + "");
        wr.close();
    }
}