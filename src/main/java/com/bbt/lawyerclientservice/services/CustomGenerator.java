package com.bbt.lawyerclientservice.services;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
@Component
public class CustomGenerator {
    private static final String[] CITIES = { "Алма-Ата", "Астана", "Шымкент", "Актобе", "Караганда", "Тараз", "Усть-Каменогорск", "Павлодар", "Атырау",
            "Семей", "Кызылорда", "Актау", "Костанай", "Уральск", "Туркестан", "Петропавловск", "Кокшетау", "Темиртау",
            "Талдыкорган", "Экибастуз", "Рудный", "Жезказган", "Каскелен", "Жанаозен", "Кентау", "Балхаш", "Сатпаев",
            "Кульсары", "Талгар", "Сарыагаш", "Конаев", "Косшы", "Жаркент", "Алатау", "Арыс", "Аксу", "Степногорск",
            "Щучинск", "Шу", "Риддер", "Жетысай", "Аягоз", "Есик", "Шахтинск", "Аральск", "Аксай", "Алтай", "Кандыагаш",
            "Житикара", "Сарань", "Байконур", "Ленгер", "Шардара", "Лисаковск", "Атбасар", "Хромтау", "Текели", "Абай",
            "Тобыл", "Каратау", "Аркалык", "Шалкар", "Жанатас", "Алга", "Ушарал", "Уштобе", "Зайсан", "Шемонаиха",
            "Макинск", "Сарканд", "Акколь", "Тайынша", "Эмба", "Ерейментау", "Есиль", "Приозёрск", "Курчатов",
            "Каркаралинск", "Форт-Шевченко", "Булаево", "Каражал", "Сергеевка", "Казалинск", "Серебрянск", "Мамлютка",
            "Державинск", "Шар", "Степняк"
    };

    private static String[] REGION_CODES = {"700", "701", "702", "703", "704", "705", "706", "707", "708", "709",
            "747", "750", "751", "760", "761", "763", "764", "771", "775", "776",
            "777", "778"
    };
    public String generatePhoneNumber(){
        Random random = new Random();

        String regionCode = REGION_CODES[random.nextInt(REGION_CODES.length)];


        StringBuilder phoneNumber = new StringBuilder();
        phoneNumber.append("+7(").append(regionCode).append(")");
        for (int i = 0; i < 7; i++) {
            phoneNumber.append(random.nextInt(10)); // Генерируем случайную цифру от 0 до 9
            if (i == 2 || i == 4 || i == 6) {
                phoneNumber.append(" "); // Добавляем пробел после каждых двух цифр
            }
        }
        return phoneNumber.toString();
    }

    public String getRandomCity(){
        Random random = new Random();
        int randomCityIndex = random.nextInt(CITIES.length);
        return CITIES[randomCityIndex];
    }

    public String generateEmail(String firstName, String lastName) {
        String englishFirstName = transliterate(firstName);
        String englishLastName = transliterate(lastName);

        String email = englishFirstName.toLowerCase() + "." + englishLastName.toLowerCase() + "@gmail.com";

        return email;
    }

    private static final Map<Character, String> cyrToLatMap;
    static {
        cyrToLatMap = new HashMap<>();
        cyrToLatMap.put(' ', " ");
        cyrToLatMap.put('а', "a");
        cyrToLatMap.put('б', "b");
        cyrToLatMap.put('в', "v");
        cyrToLatMap.put('г', "g");
        cyrToLatMap.put('д', "d");
        cyrToLatMap.put('е', "e");
        cyrToLatMap.put('ё', "e");
        cyrToLatMap.put('ж', "zh");
        cyrToLatMap.put('з', "z");
        cyrToLatMap.put('и', "i");
        cyrToLatMap.put('й', "y");
        cyrToLatMap.put('к', "k");
        cyrToLatMap.put('л', "l");
        cyrToLatMap.put('м', "m");
        cyrToLatMap.put('н', "n");
        cyrToLatMap.put('о', "o");
        cyrToLatMap.put('п', "p");
        cyrToLatMap.put('р', "r");
        cyrToLatMap.put('с', "s");
        cyrToLatMap.put('т', "t");
        cyrToLatMap.put('у', "u");
        cyrToLatMap.put('ф', "f");
        cyrToLatMap.put('х', "h");
        cyrToLatMap.put('ц', "ts");
        cyrToLatMap.put('ч', "ch");
        cyrToLatMap.put('ш', "sh");
        cyrToLatMap.put('щ', "sch");
        cyrToLatMap.put('ъ', "");
        cyrToLatMap.put('ы', "i");
        cyrToLatMap.put('ь', "");
        cyrToLatMap.put('э', "e");
        cyrToLatMap.put('ю', "ju");
        cyrToLatMap.put('я', "ja");
        cyrToLatMap.put('А', "A");
        cyrToLatMap.put('Б', "B");
        cyrToLatMap.put('В', "V");
        cyrToLatMap.put('Г', "G");
        cyrToLatMap.put('Д', "D");
        cyrToLatMap.put('Е', "E");
        cyrToLatMap.put('Ё', "E");
        cyrToLatMap.put('Ж', "Zh");
        cyrToLatMap.put('З', "Z");
        cyrToLatMap.put('И', "I");
        cyrToLatMap.put('Й', "Y");
        cyrToLatMap.put('К', "K");
        cyrToLatMap.put('Л', "L");
        cyrToLatMap.put('М', "M");
        cyrToLatMap.put('Н', "N");
        cyrToLatMap.put('О', "O");
        cyrToLatMap.put('П', "P");
        cyrToLatMap.put('Р', "R");
        cyrToLatMap.put('С', "S");
        cyrToLatMap.put('Т', "T");
        cyrToLatMap.put('У', "U");
        cyrToLatMap.put('Ф', "F");
        cyrToLatMap.put('Х', "H");
        cyrToLatMap.put('Ц', "Ts");
        cyrToLatMap.put('Ч', "Ch");
        cyrToLatMap.put('Ш', "Sh");
        cyrToLatMap.put('Щ', "Sch");
        cyrToLatMap.put('Ъ', "");
        cyrToLatMap.put('Ы', "I");
        cyrToLatMap.put('Ь', "");
        cyrToLatMap.put('Э', "E");
        cyrToLatMap.put('Ю', "Ju");
        cyrToLatMap.put('Я', "Ja");
    }


    public static String transliterate(String message){
        StringBuilder builder = new StringBuilder();
        for (var ch : message.toCharArray()) {
            builder.append(cyrToLatMap.getOrDefault(ch,"?"));
        }
        return builder.toString();
    }

    public int generateExperience(int age){
        int experience = age - 24;
        return experience >= 0? experience : 0;
    }
}
