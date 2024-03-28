package net;

public class LogSingleton
{
  private static LogSingleton instance;
  private String log;
  private int threadCount;

  private LogSingleton()
  {
    log = "";
    threadCount = 0;
  }

  public static synchronized LogSingleton getInstance()
  {
    if (instance == null)
    {
      instance = new LogSingleton();

    }

    return instance;

  }

  public String getLog()
  {
    return log;
  }

  public void addLog(String addString){
    log += addString;
  }
  public void increaseThreadCount(){
    threadCount ++;
  }
  public void decreaseThreadCount(){
    threadCount --;
  }
  public int getThreadCount(){
    return threadCount;
  }

}
