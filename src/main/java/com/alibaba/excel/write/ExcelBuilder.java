package com.alibaba.excel.write;

import com.alibaba.excel.metadata.Sheet;
import com.alibaba.excel.metadata.Table;
import java.util.List;

public abstract interface ExcelBuilder
{
  public abstract void addContent(List paramList, int paramInt);

  public abstract void addContent(List paramList, Sheet paramSheet);

  public abstract void addContent(List paramList, Sheet paramSheet, Table paramTable);

  public abstract void merge(int paramInt1, int paramInt2, int paramInt3, int paramInt4);

  public abstract void finish();
}

/* Location:           C:\Users\Dell\Desktop\easyexcel-1.1.2-beta5.jar
 * Qualified Name:     com.alibaba.excel.write.ExcelBuilder
 * JD-Core Version:    0.6.0
 */