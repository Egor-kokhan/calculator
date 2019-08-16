package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import sample.database.DataBaseHandler;

import java.util.List;

public class Controller {

    /**
     * Поле, где выводится промежуточные данные и результат выражений
     */
    @FXML
    private TextField output;

    /**
     * Отвечает за список выражений на панели
     */
    @FXML
    private TextArea log;


    private long num1 = 0;
    private long num2 = 0;
    private String operator = "";
    private boolean start;

    private Model model = new Model();
    private DataBaseHandler db = new DataBaseHandler();

    /**
     * Отвечающий за ввод цифр метод. С помощью флага start происходит стирание
     * с панели предыдущих данных, так же как и стирание "нуля по умолчанию"
     * Так же добавляет цифры в поле промежуточных данных, чтобы составить число
     *
     * @param event с помощью этого параметра выясняем, какую именно клавишу нажал пользователь
     */
    @FXML
    public void processNumpad(ActionEvent event){
        if(start){
            output.setText("");
            start = false;
        }
        if(output.getText().equals("0")) {
            output.setText("");
        }
        String value = ((Button)event.getSource()).getText();

        output.setText(output.getText() + value);
    }

    /**
     * Метод отвечает за основную логику калькулятора
     * Если введен оператор вычисления (+,-,*,/), то запоминаем оператор и число,
     * которое было введено до.
     * Если же введен оператор результата (=), запоминаем число введенное до, передаем
     * все необходимые для вычисления данные, выводим результат и полное выражение на соотв панели
     * Обнуляем оператор и флаг, указывая что далее новое выражение.
     *
     * @param event с помощью этого параметра выясняем, на какой именно оператор нажал пользователь
     */
    @FXML
    public void processOperator(ActionEvent event){
        String value = ((Button)event.getSource()).getText();
        if (!"=".equals(value)){
            if(!operator.isEmpty())return;

            operator = value;
            num1 = Long.parseLong(output.getText());
            output.setText("");
        }else{
            if(operator.isEmpty())return;

            num2 = Long.parseLong(output.getText());
            long result = model.calculate(num1,num2,operator);
            output.setText(String.valueOf(result));
            log.appendText((num1+" "+ operator + " " + num2 + " = "+result+"\n"));
            operator = "";
            start = true;
        }
    }

    /**
     * Очистка результирующей панели
     */
    public void clear(ActionEvent event){
        output.setText("0");
    }

    /**
     * Заносит все результаты данной сессии в базу данных
     */
    public void saveInDataBase(ActionEvent event){
        List<String> list = model.getList();
        for (int i = 0; i < list.size() ; i++) {
            db.addLine(list.get(i));
        }
        list.clear();
        System.out.println("saved successfully");
    }

    /**
     * Выводит 10 выражений из базы данных
     */
    public void viewInPanel(ActionEvent event){
        log.setText("");
        List<String> list = db.getTenLines();
        for (int i = 0; i < list.size(); i++) {
            log.appendText(list.get(i)+"\n");
        }
    }
}
