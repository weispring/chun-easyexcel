package com.alibaba.excel.event;

import com.alibaba.excel.context.AnalysisContext;

public abstract class AnalysisEventListener<T>
{
  public abstract void invoke(T paramT, AnalysisContext paramAnalysisContext);

  public abstract void doAfterAllAnalysed(AnalysisContext paramAnalysisContext);
}

/* Location:           C:\Users\Dell\Desktop\easyexcel-1.1.2-beta5.jar
 * Qualified Name:     com.alibaba.excel.event.AnalysisEventListener
 * JD-Core Version:    0.6.0
 */