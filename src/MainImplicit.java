// MainImplicit.java
public class MainImplicit {
    public static void main(String[] args) {
        String text = "Это строка, которую мы будем анализировать.";
        int wordCount = WordCounter.countWords(text);
        System.out.println("Количество слов в строке: " + wordCount);
    }
}
