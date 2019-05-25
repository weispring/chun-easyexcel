/*     */ package com.alibaba.excel.analysis.v03;
/*     */ 
/*     */ import com.alibaba.excel.analysis.BaseSaxAnalyser;
/*     */ import com.alibaba.excel.context.AnalysisContext;
/*     */ import com.alibaba.excel.event.OneRowAnalysisFinishEvent;
/*     */ import com.alibaba.excel.exception.ExcelAnalysisException;
/*     */ import com.alibaba.excel.metadata.Sheet;
/*     */ import java.io.IOException;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import org.apache.poi.hssf.eventusermodel.EventWorkbookBuilder.SheetRecordCollectingListener;
/*     */ import org.apache.poi.hssf.eventusermodel.FormatTrackingHSSFListener;
/*     */ import org.apache.poi.hssf.eventusermodel.HSSFEventFactory;
/*     */ import org.apache.poi.hssf.eventusermodel.HSSFListener;
/*     */ import org.apache.poi.hssf.eventusermodel.HSSFRequest;
/*     */ import org.apache.poi.hssf.eventusermodel.MissingRecordAwareHSSFListener;
/*     */ import org.apache.poi.hssf.eventusermodel.dummyrecord.LastCellOfRowDummyRecord;
/*     */ import org.apache.poi.hssf.eventusermodel.dummyrecord.MissingCellDummyRecord;
/*     */ import org.apache.poi.hssf.model.HSSFFormulaParser;
/*     */ import org.apache.poi.hssf.record.BOFRecord;
/*     */ import org.apache.poi.hssf.record.BlankRecord;
/*     */ import org.apache.poi.hssf.record.BoolErrRecord;
/*     */ import org.apache.poi.hssf.record.BoundSheetRecord;
/*     */ import org.apache.poi.hssf.record.FormulaRecord;
/*     */ import org.apache.poi.hssf.record.LabelRecord;
/*     */ import org.apache.poi.hssf.record.LabelSSTRecord;
/*     */ import org.apache.poi.hssf.record.NoteRecord;
/*     */ import org.apache.poi.hssf.record.NumberRecord;
/*     */ import org.apache.poi.hssf.record.RKRecord;
/*     */ import org.apache.poi.hssf.record.Record;
/*     */ import org.apache.poi.hssf.record.SSTRecord;
/*     */ import org.apache.poi.hssf.record.StringRecord;
/*     */ import org.apache.poi.hssf.record.common.UnicodeString;
/*     */ import org.apache.poi.hssf.usermodel.HSSFWorkbook;
/*     */ import org.apache.poi.poifs.filesystem.POIFSFileSystem;
/*     */ 
/*     */ public class XlsSaxAnalyser extends BaseSaxAnalyser
/*     */   implements HSSFListener
/*     */ {
/*  30 */   private boolean analyAllSheet = false;
/*     */   private POIFSFileSystem fs;
/*     */   private int lastRowNumber;
/*     */   private int lastColumnNumber;
/* 105 */   private boolean outputFormulaValues = true;
/*     */   private EventWorkbookBuilder.SheetRecordCollectingListener workbookBuildingListener;
/*     */   private HSSFWorkbook stubWorkbook;
/*     */   private SSTRecord sstRecord;
/*     */   private FormatTrackingHSSFListener formatListener;
/*     */   private int nextRow;
/*     */   private int nextColumn;
/*     */   private boolean outputNextStringRecord;
/*     */   private int sheetIndex;
/*     */   private List<String> records;
/* 131 */   private boolean notAllEmpty = false;
/*     */   private BoundSheetRecord[] orderedBSRs;
/* 135 */   private List<BoundSheetRecord> boundSheetRecords = new ArrayList();
/*     */ 
/* 137 */   private List<Sheet> sheets = new ArrayList();
/*     */ 
/*     */   public XlsSaxAnalyser(AnalysisContext context)
/*     */     throws IOException
/*     */   {
/*  33 */     this.analysisContext = context;
/*  34 */     this.records = new ArrayList();
/*  35 */     if (this.analysisContext.getCurrentSheet() == null) {
/*  36 */       this.analyAllSheet = true;
/*     */     }
/*  38 */     context.setCurrentRowNum(Integer.valueOf(0));
/*  39 */     this.fs = new POIFSFileSystem(this.analysisContext.getInputStream());
/*     */   }
/*     */ 
/*     */   public List<Sheet> getSheets()
/*     */   {
/*  45 */     execute();
/*  46 */     return this.sheets;
/*     */   }
/*     */ 
/*     */   public void execute()
/*     */   {
/*  51 */     init();
/*  52 */     MissingRecordAwareHSSFListener listener = new MissingRecordAwareHSSFListener(this);
/*  53 */     this.formatListener = new FormatTrackingHSSFListener(listener);
/*     */ 
/*  55 */     HSSFEventFactory factory = new HSSFEventFactory();
/*  56 */     HSSFRequest request = new HSSFRequest();
/*     */ 
/*  58 */     if (this.outputFormulaValues) {
/*  59 */       request.addListenerForAllRecords(this.formatListener);
/*     */     } else {
/*  61 */       this.workbookBuildingListener = new EventWorkbookBuilder.SheetRecordCollectingListener(this.formatListener);
/*  62 */       request.addListenerForAllRecords(this.workbookBuildingListener);
/*     */     }
/*     */     try
/*     */     {
/*  66 */       factory.processWorkbookEvents(request, this.fs);
/*     */     } catch (IOException e) {
/*  68 */       throw new ExcelAnalysisException(e);
/*     */     }
/*     */   }
/*     */ 
/*     */   private void init() {
/*  73 */     this.lastRowNumber = 0;
/*  74 */     this.lastColumnNumber = 0;
/*  75 */     this.nextRow = 0;
/*     */ 
/*  77 */     this.nextColumn = 0;
/*     */ 
/*  79 */     this.sheetIndex = 0;
/*     */ 
/*  81 */     this.records = new ArrayList();
/*     */ 
/*  83 */     this.notAllEmpty = false;
/*     */ 
/*  85 */     this.orderedBSRs = null;
/*     */ 
/*  87 */     this.boundSheetRecords = new ArrayList();
/*     */ 
/*  89 */     this.sheets = new ArrayList();
/*  90 */     if (this.analysisContext.getCurrentSheet() == null)
/*  91 */       this.analyAllSheet = true;
/*     */     else
/*  93 */       this.analyAllSheet = false;
/*     */   }
/*     */ 
/*     */   public void processRecord(Record record)
/*     */   {
/* 140 */     int thisRow = -1;
/* 141 */     int thisColumn = -1;
/* 142 */     String thisStr = null;
/*     */ 
/* 144 */     switch (record.getSid()) {
/*     */     case 133:
/* 146 */       this.boundSheetRecords.add((BoundSheetRecord)record);
/* 147 */       break;
/*     */     case 2057:
/* 149 */       BOFRecord br = (BOFRecord)record;
/* 150 */       if (br.getType() != 16)
/*     */         break;
/* 152 */       if ((this.workbookBuildingListener != null) && (this.stubWorkbook == null)) {
/* 153 */         this.stubWorkbook = this.workbookBuildingListener.getStubHSSFWorkbook();
/*     */       }
/*     */ 
/* 156 */       if (this.orderedBSRs == null) {
/* 157 */         this.orderedBSRs = BoundSheetRecord.orderByBofPosition(this.boundSheetRecords);
/*     */       }
/* 159 */       this.sheetIndex += 1;
/*     */ 
/* 161 */       Sheet sheet = new Sheet(this.sheetIndex, 0);
/* 162 */       sheet.setSheetName(this.orderedBSRs[(this.sheetIndex - 1)].getSheetname());
/* 163 */       this.sheets.add(sheet);
/* 164 */       if (this.analyAllSheet) {
/* 165 */         this.analysisContext.setCurrentSheet(sheet);
/*     */       }
/* 167 */       break;
/*     */     case 252:
/* 171 */       this.sstRecord = ((SSTRecord)record);
/* 172 */       break;
/*     */     case 513:
/* 175 */       BlankRecord brec = (BlankRecord)record;
/*     */ 
/* 177 */       thisRow = brec.getRow();
/* 178 */       thisColumn = brec.getColumn();
/* 179 */       thisStr = "";
/* 180 */       break;
/*     */     case 517:
/* 182 */       BoolErrRecord berec = (BoolErrRecord)record;
/*     */ 
/* 184 */       thisRow = berec.getRow();
/* 185 */       thisColumn = berec.getColumn();
/* 186 */       thisStr = "";
/* 187 */       break;
/*     */     case 6:
/* 190 */       FormulaRecord frec = (FormulaRecord)record;
/*     */ 
/* 192 */       thisRow = frec.getRow();
/* 193 */       thisColumn = frec.getColumn();
/*     */ 
/* 195 */       if (this.outputFormulaValues) {
/* 196 */         if (Double.isNaN(frec.getValue()))
/*     */         {
/* 199 */           this.outputNextStringRecord = true;
/* 200 */           this.nextRow = frec.getRow();
/* 201 */           this.nextColumn = frec.getColumn();
/*     */         } else {
/* 203 */           thisStr = this.formatListener.formatNumberDateCell(frec);
/*     */         }
/*     */       }
/* 206 */       else thisStr = HSSFFormulaParser.toFormulaString(this.stubWorkbook, frec.getParsedExpression());
/*     */ 
/* 208 */       break;
/*     */     case 519:
/* 210 */       if (!this.outputNextStringRecord)
/*     */         break;
/* 212 */       StringRecord srec = (StringRecord)record;
/* 213 */       thisStr = srec.getString();
/* 214 */       thisRow = this.nextRow;
/* 215 */       thisColumn = this.nextColumn;
/* 216 */       this.outputNextStringRecord = false;
/* 217 */       break;
/*     */     case 516:
/* 221 */       LabelRecord lrec = (LabelRecord)record;
/*     */ 
/* 223 */       thisRow = lrec.getRow();
/* 224 */       thisColumn = lrec.getColumn();
/* 225 */       thisStr = lrec.getValue();
/* 226 */       break;
/*     */     case 253:
/* 228 */       LabelSSTRecord lsrec = (LabelSSTRecord)record;
/*     */ 
/* 230 */       thisRow = lsrec.getRow();
/* 231 */       thisColumn = lsrec.getColumn();
/* 232 */       if (this.sstRecord == null)
/* 233 */         thisStr = "";
/*     */       else {
/* 235 */         thisStr = this.sstRecord.getString(lsrec.getSSTIndex()).toString();
/*     */       }
/* 237 */       break;
/*     */     case 28:
/* 239 */       NoteRecord nrec = (NoteRecord)record;
/*     */ 
/* 241 */       thisRow = nrec.getRow();
/* 242 */       thisColumn = nrec.getColumn();
/*     */ 
/* 244 */       thisStr = "(TODO)";
/* 245 */       break;
/*     */     case 515:
/* 247 */       NumberRecord numrec = (NumberRecord)record;
/*     */ 
/* 249 */       thisRow = numrec.getRow();
/* 250 */       thisColumn = numrec.getColumn();
/*     */ 
/* 253 */       thisStr = this.formatListener.formatNumberDateCell(numrec);
/* 254 */       break;
/*     */     case 638:
/* 256 */       RKRecord rkrec = (RKRecord)record;
/*     */ 
/* 258 */       thisRow = rkrec.getRow();
/* 259 */       thisColumn = rkrec.getColumn();
/* 260 */       thisStr = "";
/* 261 */       break;
/*     */     }
/*     */ 
/* 267 */     if ((thisRow != -1) && (thisRow != this.lastRowNumber)) {
/* 268 */       this.lastColumnNumber = -1;
/*     */     }
/*     */ 
/* 272 */     if ((record instanceof MissingCellDummyRecord)) {
/* 273 */       MissingCellDummyRecord mc = (MissingCellDummyRecord)record;
/* 274 */       thisRow = mc.getRow();
/* 275 */       thisColumn = mc.getColumn();
/* 276 */       thisStr = "";
/*     */     }
/*     */ 
/* 280 */     if (thisStr != null)
/*     */     {
/* 282 */       if (this.analysisContext.trim()) {
/* 283 */         thisStr = thisStr.trim();
/*     */       }
/* 285 */       if (!"".equals(thisStr)) {
/* 286 */         this.notAllEmpty = true;
/*     */       }
/*     */ 
/* 289 */       this.records.add(thisStr);
/*     */     }
/*     */ 
/* 293 */     if (thisRow > -1) {
/* 294 */       this.lastRowNumber = thisRow;
/*     */     }
/* 296 */     if (thisColumn > -1) {
/* 297 */       this.lastColumnNumber = thisColumn;
/*     */     }
/*     */ 
/* 301 */     if ((record instanceof LastCellOfRowDummyRecord)) {
/* 302 */       thisRow = ((LastCellOfRowDummyRecord)record).getRow();
/*     */ 
/* 304 */       if (this.lastColumnNumber == -1) {
/* 305 */         this.lastColumnNumber = 0;
/*     */       }
/* 307 */       this.analysisContext.setCurrentRowNum(Integer.valueOf(thisRow));
/* 308 */       Sheet sheet = this.analysisContext.getCurrentSheet();
/*     */ 
/* 310 */       if (((sheet == null) || (sheet.getSheetNo() == this.sheetIndex)) && (this.notAllEmpty)) {
/* 311 */         notifyListeners(new OneRowAnalysisFinishEvent(this.records));
/*     */       }
/* 313 */       this.records.clear();
/* 314 */       this.lastColumnNumber = -1;
/* 315 */       this.notAllEmpty = false;
/*     */     }
/*     */   }
/*     */ }

/* Location:           C:\Users\Dell\Desktop\easyexcel-1.1.2-beta5.jar
 * Qualified Name:     com.alibaba.excel.analysis.v03.XlsSaxAnalyser
 * JD-Core Version:    0.6.0
 */