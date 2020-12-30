import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class BankCalculate {
    JFrame frame;
    JTextField inputCredit;
    JTextField inputPeriod;
    JTextField inputRate;
    JLabel monthlyPayment;
    JLabel overPayment;
    JLabel totalPayment;

    public void setUpGui() {
        frame = new JFrame();
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        createObjects();

        JButton buttonCalculate = new JButton("Подсчитать");
        buttonCalculate.addActionListener(new CalculateActionListener());
        JButton buttonClear = new JButton("Очистить");
        buttonClear.addActionListener(new ClearActionListener());

        JPanel inputPane = new JPanel();
        GridLayout inputGrid = new GridLayout(3, 2);
        inputGrid.setHgap(10);
        inputGrid.setVgap(10);
        inputPane.setLayout(inputGrid);
        inputPane.add(new JLabel("Сумма кредита"));
        inputPane.add(inputCredit);
        inputPane.add(new JLabel("Срок периода(месяцы)"));
        inputPane.add(inputPeriod);
        inputPane.add(new JLabel("Ставка, %"));
        inputPane.add(inputRate);


        JPanel outputPane = new JPanel();
        GridLayout outGrid = new GridLayout(3, 2);
        outGrid.setHgap(12);
        outGrid.setVgap(12);
        outputPane.setLayout(outGrid);

        outputPane.add(new JLabel("Ежемесячный платеж"));
        outputPane.add(monthlyPayment);
        outputPane.add(new JLabel("Переплата по кредиту"));
        outputPane.add(overPayment);
        outputPane.add(new JLabel("Общая выплата"));
        outputPane.add(totalPayment);

        JPanel buttonPanel = new JPanel();
        GridLayout buttonGrid = new GridLayout(1, 2);
        buttonGrid.setVgap(10);
        buttonGrid.setHgap(10);

        buttonPanel.setLayout(buttonGrid);
        buttonPanel.add(buttonCalculate);
        buttonPanel.add(buttonClear);

        FlowLayout layout = new FlowLayout();
        frame.setLayout(layout);
        frame.getContentPane().add(inputPane);
        frame.getContentPane().add(outputPane);
        frame.getContentPane().add(buttonPanel);
        frame.setSize(600, 400);
        frame.setVisible(true);
    }

    private void createObjects() {
        inputCredit = new JTextField(10);
        inputCredit.requestFocus();
        inputPeriod = new JTextField(5);
        inputPeriod.requestFocus();
        inputRate = new JTextField(5);
        inputRate.requestFocus();

        monthlyPayment = new JLabel("");
        overPayment = new JLabel("");
        totalPayment = new JLabel("");
    }

    private class CalculateActionListener implements ActionListener {
        float rate;
        int period;
        int credit;

        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                period = Integer.parseInt(inputPeriod.getText());
                rate = Float.parseFloat(inputRate.getText());
                credit = Integer.parseInt(inputCredit.getText());

            } catch (Exception ex) {
                System.out.println("Ошибка ввода");
                inputCredit.setText("");
                inputPeriod.setText("");
                inputRate.setText("");
                monthlyPayment.setText("");
                overPayment.setText("");
                totalPayment.setText("");
            }
            System.out.println(rate + " " + period + " " + credit);
            monthlyPayment.setText(Float.toString(Math.round(anuityCalculate(rate, period, credit))));
            overPayment.setText(Float.toString(Math.round(anuityCalculate(rate, period, credit) * period - credit)));
            totalPayment.setText(Float.toString(Math.round(anuityCalculate(rate, period, credit) * period)));
        }

        private float anuityCalculate(float r, int p, int c) {
            float tempPerc = r / 100 / 12;
            float newPerc = tempPerc + 1;
            float powPercent = 1;
            for (int i = 0; i < p; i++) {
                powPercent = powPercent * newPerc;
            }
            return c * (tempPerc + tempPerc / (powPercent - 1));
        }
    }

    private class ClearActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            inputCredit.setText("");
            inputPeriod.setText("");
            inputRate.setText("");
            monthlyPayment.setText("");
            overPayment.setText("");
            totalPayment.setText("");
        }
    }
}