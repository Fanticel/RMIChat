package net;

public class LogSingleton
{
  private static LogSingleton instance;
  private String log;

  private LogSingleton()
  {
    log = "";

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

}
