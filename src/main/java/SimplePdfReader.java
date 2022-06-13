import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;

public class SimplePdfReader {

    public static void startWrite(JFrame frame,String pdfFile, int from, int to ) throws IOException, SQLException {
        //Мактаби Камол
        //Мачмуаи маколот
        frame.setCursor(new Cursor(Cursor.WAIT_CURSOR));
        frame.setEnabled(false);
        File file = new File(pdfFile);
        PDFManager pdfManager = new PDFManager();
        pdfManager.setFilePath(file.toString());

        StateLoad stateLoad = new StateLoad(frame,to-from,from);
        //loop for pdf pages
        int counter = 0;
        for (int i = from; i <= to; i++) {
            String str = pdfManager.toText(i);

            try (BufferedReader pageTxtBuffer = new BufferedReader(new CharArrayReader(toCorrectWords(str).toCharArray()))){

                StringBuilder buildTextForDataBase = new StringBuilder();

                int numberOfPage = 3;
                try {
                    numberOfPage = Integer.parseInt(pageTxtBuffer.readLine().trim());
                }catch (Exception exception){

                }
                String line;

                //loop for buffered Page Text
                while ((line = pageTxtBuffer.readLine()) != null) {

                    if (line.endsWith("-")) {
                        line = line.substring(0, line.length() - 1);
                    }

                    buildTextForDataBase.append(line);
                    if(line.length()<47){
                        buildTextForDataBase.append("\n\t");
                    }


                }//end while
                SQLiteManager.executeInsert(++counter,numberOfPage,buildTextForDataBase.toString());
                stateLoad.computeState((i-from));
            }
        }
        stateLoad.dispose();
        frame.setEnabled(true);
        frame.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
        Toolkit.getDefaultToolkit().beep();
        JOptionPane.showMessageDialog(frame, new String[]{"Саҳифаҳои китоб бомуваффақият ба ҷадвали kitob_sahifa ворид шуд!"},
                "!",JOptionPane.INFORMATION_MESSAGE);

    }

    private static String toCorrectWords(String text) {
        text = text
                .replace("Љ","Ҷ")
                .replace("љ","ҷ")
                .replace("њ","ҳ")
                .replace("Њ","Ҳ")
                .replace("ї","ӣ")
                .replace("Ї","Ӣ")
                .replace("ѓ","ғ")
                .replace("Ѓ","Ғ")
                .replace("ќ","қ")
                .replace("Ќ","Қ")
                .replace("ў","ӯ")
                .replace("Ў","Ӯ");
        return text;
    }
}
