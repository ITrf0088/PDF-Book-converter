import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;

public class Frame extends JFrame implements ActionListener {
    JFileChooser fileChooser = new JFileChooser();


    JPanel panel;

    public Frame(String title) {
        super(title);
        panel = new JPanel();
        panel.setBackground(new Color(60, 89, 92));
        this.setLocation(400, 200);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setSize(500, 250);
        this.setLayout(new BorderLayout());

        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        fileChooser.setMultiSelectionEnabled(false);
        fileChooser.setPreferredSize(new Dimension(1000, 600));
        fileChooser.setCurrentDirectory(fileChooser.getFileSystemView().getHomeDirectory());

        this.add(panel);
        setAllConfiguration(panel);
        this.setVisible(true);

    }

    JTextField textBaza;
    JTextField textPdf;
    JTextField textAz;
    JTextField textTo;

    private void setAllConfiguration(JPanel panel) {


        //-------------------------------------------------------------------------------------------------

        JLabel label1 = new JLabel("                                                                                                                                                                       ");
        panel.add(label1);
        Font font = new Font("Dialog", Font.PLAIN, 18);
        Color color = new Color(255, 255, 255);

        //-------------------------------------------------------------------------------------------------
        JLabel labelBaza = new JLabel("Базаи китоб -");
        labelBaza.setFont(font);
        labelBaza.setForeground(color);
        textBaza = new JTextField(25);
        JButton buttonBaza = new JButton(".db");
        panel.add(labelBaza);
        panel.add(textBaza);
        panel.add(buttonBaza);
        buttonBaza.addActionListener(this);
        //-------------------------------------------------------------------------------------------------

        JLabel labelPdf = new JLabel("     Pdf китоб -");
        labelPdf.setFont(font);
        labelPdf.setForeground(color);
        textPdf = new JTextField(25);
        JButton buttonPdf = new JButton(".pdf");
        panel.add(labelPdf);
        panel.add(textPdf);
        panel.add(buttonPdf);
        buttonPdf.addActionListener(this);
        //-------------------------------------------------------------------------------------------------

        JLabel label2 = new JLabel("                                                                                                                                                                       ");
        panel.add(label2);
        Font font2 = new Font("Dialog", Font.PLAIN, 15);
        //-------------------------------------------------------------------------------------------------

        JLabel labelAz = new JLabel("      АЗ саҳифаи");
        labelAz.setFont(font2);
        labelAz.setForeground(color);
        textAz = new JTextField(10);
        panel.add(labelAz);
        panel.add(textAz);
        //-------------------------------------------------------------------------------------------------

        JLabel label3 = new JLabel("                                                        ");
        panel.add(label3);
        //-------------------------------------------------------------------------------------------------

        JLabel labelTo = new JLabel("    ТО саҳифаи");
        labelTo.setFont(font2);
        labelTo.setForeground(color);
        textTo = new JTextField(10);
        panel.add(labelTo);
        panel.add(textTo);
        JLabel label4 = new JLabel("                                                        ");
        panel.add(label4);
        JLabel label5 = new JLabel("                                                                                                                                  ");
        panel.add(label5);
        JButton voridButton = new JButton("Ворид");
        voridButton.addActionListener(this);
        panel.add(voridButton);
        //-------------------------------------------------------------------------------------------------

    }


    @Override
    public void actionPerformed(ActionEvent e) {
        File selectedFile;

        if (e.getActionCommand().equals(".db")) {
            if ((fileChooser.showDialog(this, "Базаро интихоб кунед")) == 0) {
                selectedFile = fileChooser.getSelectedFile();
                if (!selectedFile.toString().endsWith(".db")) {
                    Toolkit.getDefaultToolkit().beep();
                    JOptionPane.showMessageDialog(this, new String[]{"Базаро интихоб кунед!!!"}, "!",
                            JOptionPane.ERROR_MESSAGE);
                    fileChooser.setSelectedFile(new File(""));
                    textBaza.setText("");

                } else {
                    textBaza.setText(fileChooser.getSelectedFile().getAbsolutePath());
                }
            }


        } else if (e.getActionCommand().equals(".pdf")) {
            if ((fileChooser.showDialog(this, "Китоби Pdf - ро интихоб кунед")) == 0) {
                selectedFile = fileChooser.getSelectedFile();
                if (!selectedFile.toString().endsWith(".pdf")) {
                    Toolkit.getDefaultToolkit().beep();
                    JOptionPane.showMessageDialog(this, new String[]{"Китоби Pdf - ро интихоб кунед!!!"}, "!",
                            JOptionPane.ERROR_MESSAGE);
                    fileChooser.setSelectedFile(new File(""));
                    textPdf.setText("");

                } else {
                    textPdf.setText(fileChooser.getSelectedFile().getAbsolutePath());
                }
            }

        } else if (e.getActionCommand().equals("Ворид")) {
            try {
                Integer.parseInt(textTo.getText().trim());
                Integer.parseInt(textAz.getText().trim());
            } catch (Exception exception) {
                JOptionPane.showMessageDialog(this, new String[]{"Ба қатори 'АЗ' ва 'ТО' рақам ворид кунед!!!"}, "!",
                        JOptionPane.ERROR_MESSAGE);
            }
            boolean isEmpty =
                    !textBaza.getText().isEmpty() &&
                            !textPdf.getText().isEmpty() &&
                            !textTo.getText().isEmpty() &&
                            !textAz.getText().isEmpty();

            if (isEmpty) {
                Thread thread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        int from = Integer.parseInt(textAz.getText().trim());
                        int to = Integer.parseInt(textTo.getText().trim());
                        SQLiteManager.setSqliteCon(textBaza.getText());
                        try {
                            SimplePdfReader.startWrite(Frame.this, textPdf.getText(), from, to);
                        } catch (IOException | SQLException ioException) {
                            JOptionPane.showMessageDialog(Frame.this, new String[]{ioException.getMessage()}, "!",
                                    JOptionPane.ERROR_MESSAGE);
                            setEnabled(true);
                            setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                        }
                    }
                });
                thread.start();


            }

        }

    }


    public static void main(String[] args) {

        new Frame("Ворид намудани сахифаҳои китоб, ба база.");
    }
}
