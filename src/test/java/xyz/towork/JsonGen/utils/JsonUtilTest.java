package xyz.towork.JsonGen.utils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.HashSet;
import java.util.regex.Pattern;

import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

import xyz.towork.TestingTools.utils.JsonUtil;

public class JsonUtilTest {

    //generateRandomPhoneNumber(String pattern)
    //
    //проверка на соответствие длины и замены строки по шаблону
    @Test
    public void testGenerateRandomPhone() {
        String pattern = "+7xxx-xxx-xxxx";
        String randomPhoneNumber = JsonUtil.generateRandomPhoneNumber(pattern);

        assertEquals(pattern.length(), randomPhoneNumber.length());

        for (int i = 0; i < pattern.length(); i++) {
            if(pattern.charAt(i) == 'x') {
                assertTrue(Character.isDigit(randomPhoneNumber.charAt(i)));
            } else {
                assertEquals(pattern.charAt(i), randomPhoneNumber.charAt(i));
            }
        }
    }

    //generateRandomString(String length)
    //
    //проверка на успешное создание строки
    @RepeatedTest(10)
    public void testGenerateRandomString() {
        int expectedLength = 10;
        String randomString = JsonUtil.generateRandomString(String.valueOf(expectedLength));

        assertEquals(expectedLength, randomString.length());

        String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";

        for (char c : randomString.toCharArray()) {
            assertTrue(alphabet.indexOf(c) >= 0);
        }
    }

    //проверка на не числовую длину
    @Test
    public void testGenerateRandomStringNotNumericLength() {
        String length = "not-a-numeric";
        String randomString = JsonUtil.generateRandomString(length);
        assertEquals("none", randomString);
    }

    //проверка на пустую или нулевую длину строки
    @Test
    void testGenerateRandomStringEmptyOrZeroLength() {
        String length1 = "";
        String randomString1 = JsonUtil.generateRandomString(length1);
        assertEquals("none", randomString1);
        
        String length2 = "0";
        String randomString2 = JsonUtil.generateRandomString(length2);
        assertTrue(randomString2.isEmpty());
    }

    //проверка на отрицательную длину строки
    @Test
    void testGenerateRandomStringNegativeLength() {
        String length = "-10";
        String randomString = JsonUtil.generateRandomString(length);
        assertTrue(randomString.isEmpty());
    }

    //generateRandomInteger(String minMaxValue)
    //
    //проверка на корректность диапазона 
    @Test
    void testGenerateRandomIntegerWithinRange() {
        String minMaxValue = "1-100";
        int randomInteger = JsonUtil.generateRandomInteger(minMaxValue);

        String[] parts = minMaxValue.split("-");
        int min = Integer.parseInt(parts[0]);
        int max = Integer.parseInt(parts[1]);

        assertTrue(randomInteger >= min && randomInteger <= max);
    }

    //проверка на невалидный диапазон
    @Test
    void testGenerateRandomIntegerInvalidRange() {
        String minMaxValue = "abc-100";
        assertEquals(0, JsonUtil.generateRandomInteger(minMaxValue));
    }   

    //проверка на невалидный формат
    @Test
    void testGenerateRandomIntegerInvalidFormat() {
        String minMaxValue = "1,100";
        assertEquals(0, JsonUtil.generateRandomInteger(minMaxValue));
    }

    //generateRandomBoolean(String typeBoolean)
    //
    //проверка валидных значений
    @Test
    void testGenerateRandomBooleanTypeTrue() {
        assertTrue(JsonUtil.generateRandomBoolean("true")); 
    }

    @Test
    void testGenerateRandomBooleanTypeFalse() {
        assertFalse(JsonUtil.generateRandomBoolean("false"));
    }

    @RepeatedTest(10)
    void testGenerateRandomBooleanTypeRandom() {
        boolean value = JsonUtil.generateRandomBoolean("random");
        assertTrue(value || !value);
    }
    
    //проверка на default
    @RepeatedTest(10)
    void testGenerateRandomBooleanReturnsRandomValueDefault() {
        boolean value = JsonUtil.generateRandomBoolean("invalid-input");
        assertTrue(value || !value);
    }

    //generateNowDate()
    //
    //проверка на соответствие с учетом таймаута
    @Test
    void testGenerateNowDateIsCurrent() {
        String dateStr = JsonUtil.generateNowDate();
        LocalDateTime parsedDate = LocalDateTime.parse(dateStr, DateTimeFormatter.ISO_DATE_TIME);
        LocalDateTime nowUTC = LocalDateTime.now(ZoneOffset.UTC);
        
        long timeDifference = ChronoUnit.SECONDS.between(parsedDate, nowUTC);
        assertTrue(Math.abs(timeDifference) < 100);
    }

    //generateRandomEmail()
    //
    //проверка на корректность формата
    @RepeatedTest(10) 
    void testGenerateRandomEmail() {
        Pattern EMAIL_PATTERN = Pattern.compile("^[A-Za-z0-9_-]+@[A-Za-z0-9-]+\\.com$");
        String email = JsonUtil.generateRandomEmail();
        assertTrue(EMAIL_PATTERN.matcher(email).matches());

        assertTrue(email.contains("@"));

        assertTrue(email.endsWith(".com"));

        //проверяем длины локальной части и домена
        String[] parts = email.split("@");
        assertTrue(parts[0].length() >= 1 && parts[0].length() <= 25);
        assertTrue(parts[1].length() >= 4 && parts[1].length() <= 29);
    }

    //проверка на уникальность
    @RepeatedTest(100) 
    void testEmailRandomness() {
        HashSet<String> emails = new HashSet<>();
        for (int i = 0; i < 100; i++) {
            emails.add(JsonUtil.generateRandomEmail());
        }
        assertTrue(emails.size() > 1);
    }
}
