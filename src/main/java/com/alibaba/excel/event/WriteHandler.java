package com.alibaba.excel.event;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

public abstract interface WriteHandler
{
  public abstract void sheet(int paramInt, Sheet paramSheet);

  public abstract void row(int paramInt, Row paramRow);

  public abstract void cell(int paramInt, Cell paramCell);
}

/* Location:           C:\Users\Dell\Desktop\easyexcel-1.1.2-beta5.jar
 * Qualified Name:     com.alibaba.excel.event.WriteHandler
 * JD-Core Version:    0.6.0
 */