package MayBeNeedful;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class GallowsGame {
    private String word;
    private int attempts;
    private int wordLength;
    public void setAttempts(int attempts){
        this.attempts = attempts;
    }
    public void setWordLength(int length){
        this.wordLength = length;
    }
    public void startGame() throws IOException {
        Random random = new Random();
        Scanner scan = new Scanner(System.in);

        String tmp = "";
        int data = 0;
        String symbol;
        ArrayList<String> words5 = new ArrayList<>();
        ArrayList<String> words8 = new ArrayList<>();
        ArrayList<String> words10 = new ArrayList<>();
        ArrayList<Character> key = new ArrayList<>();
        ArrayList<Character> lettersFails = new ArrayList<>();

        // Заполнение массива букв
        for (int i = 1072; i < 1104; i++) lettersFails.add((char) i);
        lettersFails.add((char) 1105);

        // Выбор случаного слова из базы данных
        switch(this.wordLength){
            case 5:
                FileReader reader5 = new FileReader("C:/Users/Lenovo/IdeaProjects/Main/src/resource/5 letters words.txt");

                while (data != -1){
                    data = reader5.read();
                    if (data == '\n') {
                        tmp = tmp.toLowerCase();
                        words5.add(tmp);
                        tmp = "";
                    }
                    else tmp += (char) data;
                }
                reader5.close();

                this.word = words5.get(random.nextInt(0, words5.size()-1));
                break;
            case 8:
                FileReader reader8 = new FileReader("C:/Users/Lenovo/IdeaProjects/Main/src/resource/8 letters words.txt");

                while (data != -1){
                    data = reader8.read();
                    if (data == '\n' || data == ' ') {
                        tmp = tmp.toLowerCase();
                        words8.add(tmp);
                        tmp = "";
                    }
                    else tmp += (char) data;
                }
                reader8.close();

                this.word = words8.get(random.nextInt(0, words8.size()-1));
                break;
            case 10:
                FileReader reader10 = new FileReader("C:/Users/Lenovo/IdeaProjects/Main/src/resource/10 letters words.txt");

                while (data != -1){
                    data = reader10.read();
                    if (data == '\n' || data == ' ') {
                        tmp = tmp.toLowerCase();
                        words10.add(tmp);
                        tmp = "";
                    }
                    else tmp += (char) data;
                }
                reader10.close();

                this.word = words10.get(random.nextInt(0, words10.size()-1));
                break;
        }

//        System.out.println(this.word);

        for (int i = 0; i < this.wordLength; i++) key.add('*');

//        System.out.println(this.word);

        while (this.attempts != 0){
            if (!key.contains('*')) break;
            while(true) {
                System.out.print("\nСЛОВО: ");
                for (char i : key) System.out.print(i);

                System.out.println("\nУ Вас осталось " + this.attempts + " прав на ошибку");
                System.out.print("\nВы еще не использовали следующие буквы: ");
                for (char i : lettersFails) System.out.print(i + " ");

                System.out.print("\nВведите букву: ");
                symbol = scan.nextLine();
                if (!lettersFails.contains(symbol.charAt(0))) System.out.println("\nЭта буква уже была введена, попробуйте еще раз");
                else if (symbol.length() == 1) break;
                else System.out.println("\nВы ввели не букву, попробуйте еще раз");
            }
            if (this.word.contains(symbol)){
                key.remove(this.word.indexOf(symbol));
                key.add(this.word.indexOf(symbol), symbol.charAt(0));
                System.out.println("\nПОЗДРАВЛЯЮ! Буква угадана!\n");
                lettersFails.remove(lettersFails.indexOf(symbol.charAt(0)));
            }
            else {
                System.out.println("Такой буквы в слове нет!");
                this.attempts -= 1;
                lettersFails.remove(lettersFails.indexOf(symbol.charAt(0)));
            }
        }
        if (this.attempts == 0 && key.contains('*')) System.out.println("\nВаши попытки закончились, Вас повесили!\n");
        else System.out.println("\nПоздравляем, Вы выиграли!!!!");

//        scan.close();
    }

    public static void menu() throws IOException {
        Scanner scan = new Scanner(System.in);
        GallowsGame gallow = new GallowsGame();

        int operation;
        while (true) {
            while (true) {
                System.out.print("\tВИСЕЛИЦА\n\n1) Легкий:\n5 букв и 25 ошибок\n\n2) Средний:\n8 букв и 20 ошибок\n\n3) Сложный:\n10 букв и 15 ошибок\n\nВыберите уровень сложности: ");
                operation = scan.nextInt();
                if (operation == 1 || operation == 2 || operation == 3) break;
                else System.out.println("\nВы ввели неверную операцию, попробуйте еще раз\n");
            }
            switch (operation) {
                case 1:
                    gallow.setWordLength(5);
                    gallow.setAttempts(25);
                    break;
                case 2:
                    gallow.setWordLength(8);
                    gallow.setAttempts(20);
                    break;
                case 3:
                    gallow.setWordLength(10);
                    gallow.setAttempts(15);
                    break;
            }

            gallow.startGame();

            System.out.print("\n1) Да\n0) Нет\nХотите сыграть еще раз? ");
            if (scan.nextInt() == 0) break;
        }

        scan.close();
    }
}
