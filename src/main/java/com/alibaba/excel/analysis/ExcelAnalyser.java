package com.alibaba.excel.analysis;

import com.alibaba.excel.metadata.Sheet;
import java.util.List;

public abstract interface ExcelAnalyser
{
  public abstract void analysis(Sheet paramSheet);

  public abstract void analysis();

  public abstract List<Sheet> getSheets();
}

/* Location:           C:\Users\Dell\Desktop\easyexcel-1.1.2-vphonor-SNAPSHOT.jar
 * Qualified Name:     com.alibaba.excel.analysis.ExcelAnalyser
 * JD-Core Version:    0.6.0
 */