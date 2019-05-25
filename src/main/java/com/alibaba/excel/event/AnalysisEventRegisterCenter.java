package com.alibaba.excel.event;

public abstract interface AnalysisEventRegisterCenter
{
  public abstract void appendLister(String paramString, AnalysisEventListener paramAnalysisEventListener);

  public abstract void notifyListeners(OneRowAnalysisFinishEvent paramOneRowAnalysisFinishEvent);

  public abstract void cleanAllListeners();
}

/* Location:           C:\Users\Dell\Desktop\easyexcel-1.1.2-beta5.jar
 * Qualified Name:     com.alibaba.excel.event.AnalysisEventRegisterCenter
 * JD-Core Version:    0.6.0
 */