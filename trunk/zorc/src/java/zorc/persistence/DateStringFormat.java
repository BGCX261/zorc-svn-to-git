package zorc.persistence;

/**
 * Класс для преобразования строкового представления даты.
 *
 * @author Genocide
 */
public class DateStringFormat {

    /**
     * Конвертация строкового представления даты dd.MM.yyyy -> yyyy-MM-dd.
     *
     * @param date Логическое представление даты в виде строки (dd.mm.yyyy).
     * @return Строка с датой вида yyyy-MM-dd.
     */
    public static String logicalToDataBase(String date) {
        String arrStr[] = date.split("\\.");
        return arrStr[2] + "-" + arrStr[1] + "-" + arrStr[0];

    }
}
