package xyz.towork.TestingTools.utils;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JsonUtil {
    private static final String ALPHABET = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
    private static final String LOCAL_CHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789-_";
    private static final String DOMAIN_CHARS = "abcdefghijklmnopqrstuvwxyz01234567890123456789-";

    private static final Logger logger = LoggerFactory.getLogger(JsonUtil.class);

    //функция для генерации случайного номера телефона по маске
    public static String generateRandomPhoneNumber(String pattern) {
        Random random = new Random();
        StringBuilder phoneNumber = new StringBuilder();

        for (int i = 0; i < pattern.length(); i++) {
            char c = pattern.charAt(i);
            if (c == 'x') {
                phoneNumber.append(random.nextInt(10));
            } else {
                phoneNumber.append(c);
            }
        }
        return phoneNumber.toString();
    }

    //функция генерации случайной строки по заданной длине
    public static String generateRandomString(String length) {
        StringBuilder generatedString = new StringBuilder();
        Random random = new Random();
        try {
            if (Integer.parseInt(length) < 0) {
                length = "0";
            }
            for (int i = 0; i < Integer.parseInt(length); i++) {
                int index = random.nextInt(ALPHABET.length());
                generatedString.append(ALPHABET.charAt(index));
            }
            return generatedString.toString();
        } catch (NumberFormatException ex) {
            logger.error(length, ex);
            logger.info("The length value is not an integer");
            return "none";
        } catch (Exception ex) {
            logger.error(length, ex);
            logger.info("Hmm...");
            return "none";
        }
    }

    //функция генерации случайного числа в заданном диапозоне
    public static int generateRandomInteger(String minMaxValue) {
            try {
                String[] values = minMaxValue.split(":");
                int result;
                logger.info("Min value = " + values[0]);
                logger.info("Max value = ", values[1]);

                int min = Integer.parseInt(values[0]);
                int max = Integer.parseInt(values[1]);

                result = (int) Math.floor(Math.random() * (max - min + 1)) + min;

                return result;

            } catch (NumberFormatException ex) {
                logger.error(minMaxValue, ex);
                logger.info("Incorrect integer value");
                return 0;
            } catch (ArrayIndexOutOfBoundsException ex) {
                logger.error(minMaxValue, ex);
                logger.info("Incorrect separator format");
                return 0;
            } catch (Exception ex) {
                logger.error(minMaxValue, ex);
                logger.info("Hmm...");
                return 0;
            }   
    }

    //функция генерации случайного булева значения
    public static boolean generateRandomBoolean(String typeBoolean) {
        typeBoolean = typeBoolean.toLowerCase();
        switch (typeBoolean) {
            case "random":
                return Math.random() >= 0.5;
            case "true":
                return true;
            case "false":
                return false;
            default:
                return Math.random() >= 0.5;
        }
    }

    //функция возвращающий дату в UTC
    public static String generateNowDate() {
        return LocalDateTime.now(ZoneOffset.UTC).toString();
    }

    //функция генерации случайного email
    public static String generateRandomEmail() {
        Random random = new Random();
        int localLength = random.nextInt(25) + 1;
        StringBuilder generatedEmail = new StringBuilder();
        //генерируем локальное имя
        generatedEmail.append(LOCAL_CHARS.charAt(random.nextInt(LOCAL_CHARS.length() - 2))); 

        for (int i = 1; i < localLength - 1; i++) {
            int index = random.nextInt(LOCAL_CHARS.length());
            generatedEmail.append(LOCAL_CHARS.charAt(index));
        }

        if (localLength > 1) {
            generatedEmail.append(LOCAL_CHARS.charAt(random.nextInt(LOCAL_CHARS.length() - 2)));
        }

        generatedEmail.append("@");
        //генерируем доменное имя
        int domainLength = random.nextInt(21) + 1;
        generatedEmail.append(DOMAIN_CHARS.charAt(random.nextInt(DOMAIN_CHARS.length() - 1)));

        for (int i = 1; i < domainLength - 1; i++) {
            int index = random.nextInt(DOMAIN_CHARS.length());
            generatedEmail.append(DOMAIN_CHARS.charAt(index));
        }

        if (domainLength > 1) {
            generatedEmail.append(DOMAIN_CHARS.charAt(random.nextInt(DOMAIN_CHARS.length() - 1)));
        }

        generatedEmail.append(".com");

        return generatedEmail.toString();
    }

}
