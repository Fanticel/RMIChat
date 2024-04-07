package net.model;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;

public class LogSaver extends Thread{
  String fName;
  String fullFileName;
  BufferedWriter out;
  File file;
  public LogSaver(){
    fName = "LogFrom-" + LocalDate.now();
    fileChange(fName);
  }
  private void fileChange(String fName){
    this.fName = fName;
    fullFileName = "Server\\src\\net\\model\\Logs\\"+fName + ".txt";
    file = new File(fullFileName);
    if (file.exists()){
      try {
        out = new BufferedWriter(new FileWriter(fullFileName, true));
      }
      catch (IOException e) {
        throw new RuntimeException(e);
      }
    }
    else {
      try {
        file.createNewFile();
        out = new BufferedWriter(new FileWriter(fullFileName, true));
      }
      catch (IOException e) {
        throw new RuntimeException(e);
      }
    }
  }
  public void addToLog(String message) throws IOException {
    System.out.println("saving" + message + "to: " + fullFileName);
    out.write(message);
    out.flush();
  }
  public void closeFile() throws IOException {
    out.close();
  }
  @Override public void run() {
    while (true) {
      try {
        Thread.sleep(10000);
        String newName = "LogFrom-" + LocalDate.now();
        if (!newName.equals(fName)){
          fileChange(newName);
        }
      }
      catch (InterruptedException e) {
        throw new RuntimeException(e);
      }
    }
  }
}
