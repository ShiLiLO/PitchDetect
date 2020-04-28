import java.awt.*;
import java.awt.event.*;

import jm.JMC;
import javax.swing.*;
/**
 *  Klasa odpowiadająca głównemu widokowi aplikacji.
 */
public class GUI extends JFrame implements ActionListener, WindowListener, JMC{

    Avista parent;
    Task task;
    JTextField interval;
    JTextField scale;

    int[] MINOR_SCALE = new int[]{0, 2, 3, 5, 7, 8, 10};
    public void windowActivated(WindowEvent we) {};
    public void windowClosed(WindowEvent we) {};
    public void windowDeactivated(WindowEvent we) {};
    public void windowIconified(WindowEvent we) {};
    public void windowDeiconified(WindowEvent we) {};
    public void windowOpened(WindowEvent we) {};
    public void windowClosing(WindowEvent we) {
        System.exit(0);
    }

    public GUI(Avista parent) {
        super("Avista");
        this.parent = parent;
        task = new Task();
        JPanel pan;

        JLabel lblIcon, lblTitle;
        Icon logoImage;
        JButton atonalTask, customTask, retry, start, intervalTask, scaleTask;

        Font ft;

        pan = new JPanel();
        pan.setLayout(null);

        logoImage = new ImageIcon(getClass().getResource("ys.jpg"));
        lblIcon = new JLabel(logoImage);

        lblTitle = new JLabel("Wybierz zadanie!");
        ft = new Font("Arial", Font.BOLD, 15);

        interval = new JTextField(20);
        scale = new JTextField(20);

        interval.setToolTipText("Podaj interwał");
        scale.setToolTipText("Podaj skalę");

        atonalTask = new JButton("Ćwiczenie atonalne");
        customTask = new JButton("Ćwiczenie własne");
        retry = new JButton("Powtórz");
        start = new JButton("Start");
        intervalTask = new JButton("Ćwicz interwał: ");
        scaleTask = new JButton("Ćwiczenie skalę: ");

        atonalTask.setMnemonic(KeyEvent.VK_S);
        intervalTask.setMnemonic(KeyEvent.VK_S);
        scaleTask.setMnemonic(KeyEvent.VK_S);
        customTask.setMnemonic(KeyEvent.VK_S);
        retry.setMnemonic(KeyEvent.VK_S);
        start.setMnemonic(KeyEvent.VK_S);

        atonalTask.setRolloverEnabled(true);
        atonalTask.setMargin(new Insets(5, 5, 5, 5));

        pan.setBackground(new Color(158, 98, 44));

        atonalTask.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        customTask.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        lblIcon.setBounds(5, 5, 300, 381);

        lblTitle.setBounds(330, 20, 200, 20);
        lblTitle.setFont(ft);

        interval.setBounds(330,190,140,20);
        scale.setBounds(330,260,140,20);

        atonalTask.setBounds(330, 50, 140, 40);
        customTask.setBounds(330, 100, 140, 40);
        retry.setBounds(330, 340, 140, 40);
        start.setBounds(330, 290, 140, 40);
        intervalTask.setBounds(330, 150, 140, 40);
        intervalTask.setToolTipText("Możliwe opcje: 2, 2>, 3, 3>, 4, 5, 6, 6>, 7, 7<");
        scaleTask.setBounds(330, 220, 140, 40);
        scaleTask.setToolTipText("Możliwe opcje: MAJOR_SCALE, MINOR_SCALE, DORIAN_SCALE, BLUES_SCALE");

        atonalTask.addActionListener(this);
        atonalTask.setActionCommand("atonal");
        retry.addActionListener(this);
        retry.setActionCommand("reset");
        customTask.addActionListener(this);
        customTask.setActionCommand("custom");
        intervalTask.addActionListener(this);
        intervalTask.setActionCommand("interval");
        scaleTask.addActionListener(this);
        scaleTask.setActionCommand("scale");
        start.addActionListener(this);
        start.setActionCommand("start");

        pan.add(lblIcon);
        pan.add(lblTitle);
        pan.add(interval);
        pan.add(scale);
        pan.add(atonalTask);
        pan.add(customTask);
        pan.add(retry);
        pan.add(start);
        pan.add(intervalTask);
        pan.add(scaleTask);

        getContentPane().add(pan);

        setSize(530, 450);
        setLocation(230, 200);
        setVisible(true);

        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    /**
     *  Obsługa wciśnięcia przycisku
     */
    public void actionPerformed(ActionEvent ae) {
        if(parent.startDrawing)
            parent.startDrawing = false;

        if (ae.getActionCommand().equals("start")) {
            parent.startDrawing = true;
        } else if ((ae.getActionCommand().equals("atonal"))) {
            task.createRandomTask();
            parent.task = task;
        } else if ((ae.getActionCommand().equals("scale"))) {
            task.createTaskBasedOnScale(scale.getText());
            parent.task = task;
        } else if((ae.getActionCommand().equals("reset"))) {
            task.repeatNote(0);
            parent.task = task;
        } else if((ae.getActionCommand().equals("custom"))) {
            String input = JOptionPane.showInputDialog("Format midi - dwucyfrowy bez oddzielania spacjami np.:485052 odpowiada nutom C4D4E4 ");
            task.createCustomTask(input);
            parent.task = task;
        } else if ((ae.getActionCommand().equals("interval"))){
            task.createTaskBasedOnInterval(interval.getText());
            parent.task = task;
        }
    }

}