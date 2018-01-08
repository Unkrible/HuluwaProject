package Log;

import java.io.*;

public class HuluwaLogger {
    private static HuluwaLogger huluwaLogger = new HuluwaLogger();
    private static int recordNum = 0;

    public static HuluwaLogger getInstance() {
        return huluwaLogger;
    }

    private StringBuilder strBuilder;
    private HuluwaLogger() {
        strBuilder = new StringBuilder();
    }

    public synchronized void append(String str) {
        strBuilder.append(str);
    }

    public void writeFile() {
        synchronized (HuluwaLogger.class) {
            if(strBuilder.length()==0)
                return;
            BufferedWriter bufferedWriter = null;
            try {
                bufferedWriter = new BufferedWriter(new FileWriter(new File("battleRecord"+(recordNum++) + ".record")));
                bufferedWriter.write(strBuilder.toString());
                strBuilder.delete(0, strBuilder.length());
                System.out.println("Record completely!");
            } catch (IOException ioException) {
                ioException.printStackTrace();
            } finally {
                closeWriter(bufferedWriter);
            }
        }
    }

    public void readFile(String filePath) {
        if(filePath == null)
            return;
        if(!filePath.endsWith(".record"))
            return;
        BufferedReader bufferedReader = null;
        synchronized (HuluwaLogger.class) {
            try{
                bufferedReader = new BufferedReader(new FileReader(new File(filePath)));
                String record = null;
                strBuilder.delete(0, strBuilder.length());
                while((record=bufferedReader.readLine())!=null){
                    strBuilder.append(record+"\n");
                }
            } catch (IOException exception){
                exception.printStackTrace();
            } finally {
                closeReader(bufferedReader);
            }
        }
    }

    public String getStr(){
        return strBuilder.toString();
    }

    private void closeWriter(Writer writer){
        if(writer == null)
            return;
        try {
            writer.close();
        } catch (IOException exception){
            exception.printStackTrace();
        }
    }
    private void closeReader(Reader reader){
        if(reader == null)
            return;
        try {
            reader.close();
        } catch (IOException exception){
            exception.printStackTrace();
        }
    }

}
