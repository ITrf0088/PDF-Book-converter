import javax.swing.*;
import java.awt.Frame;
import java.awt.*;

public class StateLoad extends JDialog {
    int from;
    int lenOfFiles;
    int nowLen;

    int prozentLen;
    int widthRect;


    public void computeState(int nowLen){
        this.nowLen = nowLen;
        try {
            prozentLen = (nowLen * 100) / lenOfFiles;
        }catch (Exception e){

        }

        widthRect = prozentLen*4;
        this.repaint();

    }

    JFrame frame;
    JPanel north = new JPanel(new BorderLayout());
    JPanel center = new JPanel(new BorderLayout());

    JComponent start =new JComponent() {
        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2D = ((Graphics2D) g);

            g2D.setPaint(new Color(60, 89, 92));
            g2D.fillRect(0,0,widthRect,200);
            g2D.setPaint(new Color(221, 238, 246));
            g2D.drawString(prozentLen+"%",widthRect-30,20);
        }
    };

    JComponent down = new JComponent() {
        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2D = ((Graphics2D) g);
            g2D.setColor(new Color(221, 238, 246));
            g2D.setFont(new Font("Impact", Font.PLAIN, 25));
            g2D.drawString("Сахифаи : "+(from+nowLen)+"/"+(lenOfFiles+from),90,26);
        }
    };



    public StateLoad(Frame owner, int lengthOfFiles, int from) {
        super(owner,"Интизор шавед китоб дохил шуда истодааст!");
        this.lenOfFiles = lengthOfFiles;
        this.from = from;
        this.frame = (JFrame) owner;
        setLocation(500,250);

        north.add(down);
        north.setBackground(new Color(60, 89, 92));
        north.setPreferredSize(new Dimension(400,30));

        center.add(start);
        center.setBackground(new Color(221, 238, 246));
        center.setPreferredSize(new Dimension(200,90));
        this.setResizable(false);
        this.add(north,BorderLayout.PAGE_END);
        this.add(center,BorderLayout.CENTER);
        this.setVisible(true);
        this.pack();
        north.revalidate();
        north.repaint();
        center.revalidate();
        center.repaint();
        this.repaint();

    }


 }


