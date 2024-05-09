// MainExplicit.java
public class MainExplicit {
    public static void main(String[] args) {
        String text = "Это еще одна строка для анализа.";
        int wordCount = WordCounter.countWords(text);
        System.out.println("Количество слов в строке: " + wordCount);
    }
}
