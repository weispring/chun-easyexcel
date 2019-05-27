package com.alibaba.excel.context;

import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.excel.metadata.BaseRowModel;
import com.alibaba.excel.metadata.ExcelHeadProperty;
import com.alibaba.excel.metadata.Sheet;
import com.alibaba.excel.support.ExcelTypeEnum;
import java.io.InputStream;
import java.util.List;

public abstract interface AnalysisContext
{
  public abstract Object getCustom();

  public abstract Sheet getCurrentSheet();

  public abstract void setCurrentSheet(Sheet paramSheet);

  public abstract ExcelTypeEnum getExcelType();

  public abstract InputStream getInputStream();

  public abstract AnalysisEventListener getEventListener();

  public abstract Integer getCurrentRowNum();

  public abstract void setCurrentRowNum(Integer paramInteger);

  @Deprecated
  public abstract Integer getTotalCount();

  public abstract void setTotalCount(Integer paramInteger);

  public abstract ExcelHeadProperty getExcelHeadProperty();

  public abstract void buildExcelHeadProperty(Class<? extends BaseRowModel> paramClass, List<String> paramList);

  public abstract boolean trim();

  public abstract void setCurrentRowAnalysisResult(Object paramObject);

  public abstract Object getCurrentRowAnalysisResult();

  public abstract void interrupt();

  public abstract boolean use1904WindowDate();

  public abstract void setUse1904WindowDate(boolean paramBoolean);
}

/* Location:           C:\Users\Dell\Desktop\easyexcel-1.1.2-vphonor-SNAPSHOT.jar
 * Qualified Name:     com.alibaba.excel.context.AnalysisContext
 * JD-Core Version:    0.6.0
 */