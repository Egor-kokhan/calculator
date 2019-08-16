package sample;

import java.util.ArrayList;
import java.util.List;

public class Model {

    /**
     * Список всех выражений. Именно этот список
     * при необходимости записывается в базу данных
     */
    private List<String> list = new ArrayList<>();

    /**
     * Метод отвечает за вычисления выражений и запись в "список всех выражений"
     *
     * @param num1 Первый параметр выражения
     * @param num2 Второй параметр выражения
     * @param operator Оператор выражения
     * @return результат вычисления
     */
    public long calculate(long num1, long num2, String operator){
        long result = 0;
        switch (operator){
            case "+":
                result = num1 + num2;
                break;
            case "-":
                result = num1 - num2;
                break;
            case "/":
                if(num2 == 0){
                    result = 0;
                    break;
                }
                result = num1 / num2;
                break;
            case "*":
                result = num1 * num2;
                break;
        }
        list.add(num1+" "+ operator + " " + num2 + " = " + result);
        return result;
    }

    public List<String> getList() {
        return list;
    }
}
