/*     */ package com.alibaba.excel.util;
/*     */ 
/*     */ import com.alibaba.excel.metadata.ExcelColumnProperty;
/*     */ import com.alibaba.excel.metadata.ExcelHeadProperty;
/*     */ import java.lang.reflect.Field;
/*     */ import java.math.BigDecimal;
/*     */ import java.text.ParseException;
/*     */ import java.text.SimpleDateFormat;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Date;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.regex.Matcher;
/*     */ import java.util.regex.Pattern;
/*     */ import net.sf.cglib.beans.BeanMap;
/*     */ import org.apache.poi.hssf.usermodel.HSSFDateUtil;
/*     */ 
/*     */ public class TypeUtil
/*     */ {
/*  21 */   private static List<String> DATE_FORMAT_LIST = new ArrayList(4);
/*     */   public static final Pattern pattern;
/*     */ 
/*     */   private static int getCountOfChar(String value, char c)
/*     */   {
/*  30 */     int n = 0;
/*  31 */     if (value == null) {
/*  32 */       return 0;
/*     */     }
/*  34 */     char[] chars = value.toCharArray();
/*  35 */     for (char cc : chars) {
/*  36 */       if (cc == c) {
/*  37 */         n++;
/*     */       }
/*     */     }
/*  40 */     return n;
/*     */   }
/*     */ 
/*     */   public static Object convert(String value, Field field, String format, boolean us) {
/*  44 */     if (!StringUtils.isEmpty(value)) {
/*  45 */       if (Float.class.equals(field.getType())) {
/*  46 */         return Float.valueOf(Float.parseFloat(value));
/*     */       }
/*  48 */       if ((Integer.class.equals(field.getType())) || (Integer.TYPE.equals(field.getType()))) {
/*  49 */         return Integer.valueOf(Integer.parseInt(value));
/*     */       }
/*  51 */       if ((Double.class.equals(field.getType())) || (Double.TYPE.equals(field.getType()))) {
/*  52 */         if ((null != format) && (!"".equals(format))) {
/*  53 */           int n = getCountOfChar(value, '0');
/*  54 */           return Double.valueOf(Double.parseDouble(formatFloat0(value, n)));
/*     */         }
/*  56 */         return Double.valueOf(Double.parseDouble(formatFloat(value)));
/*     */       }
/*     */ 
/*  59 */       if ((Boolean.class.equals(field.getType())) || (Boolean.TYPE.equals(field.getType()))) {
/*  60 */         String valueLower = value.toLowerCase();
/*  61 */         if ((valueLower.equals("true")) || (valueLower.equals("false"))) {
/*  62 */           return Boolean.valueOf(Boolean.parseBoolean(value.toLowerCase()));
/*     */         }
/*  64 */         Integer integer = Integer.valueOf(Integer.parseInt(value));
/*  65 */         if (integer.intValue() == 0) {
/*  66 */           return Boolean.valueOf(false);
/*     */         }
/*  68 */         return Boolean.valueOf(true);
/*     */       }
/*     */ 
/*  71 */       if ((Long.class.equals(field.getType())) || (Long.TYPE.equals(field.getType()))) {
/*  72 */         return Long.valueOf(Long.parseLong(value));
/*     */       }
/*  74 */       if (Date.class.equals(field.getType())) {
/*  75 */         if ((value.contains("-")) || (value.contains("/")) || (value.contains(":"))) {
/*  76 */           return getSimpleDateFormatDate(value, format);
/*     */         }
/*  78 */         Double d = Double.valueOf(Double.parseDouble(value));
/*  79 */         return HSSFDateUtil.getJavaDate(d.doubleValue(), us);
/*     */       }
/*     */ 
/*  82 */       if (BigDecimal.class.equals(field.getType())) {
/*  83 */         return new BigDecimal(value);
/*     */       }
/*  85 */       if (String.class.equals(field.getType())) {
/*  86 */         return formatFloat(value);
/*     */       }
/*     */     }
/*     */ 
/*  90 */     return null;
/*     */   }
/*     */ 
/*     */   public static Boolean isNum(Field field) {
/*  94 */     if (field == null) {
/*  95 */       return Boolean.valueOf(false);
/*     */     }
/*  97 */     if ((Integer.class.equals(field.getType())) || (Integer.TYPE.equals(field.getType()))) {
/*  98 */       return Boolean.valueOf(true);
/*     */     }
/* 100 */     if ((Double.class.equals(field.getType())) || (Double.TYPE.equals(field.getType()))) {
/* 101 */       return Boolean.valueOf(true);
/*     */     }
/*     */ 
/* 104 */     if ((Long.class.equals(field.getType())) || (Long.TYPE.equals(field.getType()))) {
/* 105 */       return Boolean.valueOf(true);
/*     */     }
/*     */ 
/* 108 */     if (BigDecimal.class.equals(field.getType())) {
/* 109 */       return Boolean.valueOf(true);
/*     */     }
/* 111 */     return Boolean.valueOf(false);
/*     */   }
/*     */ 
/*     */   public static Boolean isNum(Object cellValue) {
/* 115 */     if (((cellValue instanceof Integer)) || ((cellValue instanceof Double)) || ((cellValue instanceof Short)) || ((cellValue instanceof Long)) || ((cellValue instanceof Float)) || ((cellValue instanceof BigDecimal)))
/*     */     {
/* 121 */       return Boolean.valueOf(true);
/*     */     }
/* 123 */     return Boolean.valueOf(false);
/*     */   }
/*     */ 
/*     */   public static String getDefaultDateString(Date date) {
/* 127 */     SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
/* 128 */     return simpleDateFormat.format(date);
/*     */   }
/*     */ 
/*     */   public static Date getSimpleDateFormatDate(String value, String format)
/*     */   {
/* 133 */     if (!StringUtils.isEmpty(value)) {
/* 134 */       Date date = null;
/*     */       SimpleDateFormat simpleDateFormat;
/* 135 */       if (!StringUtils.isEmpty(format)) {
/* 136 */         simpleDateFormat = new SimpleDateFormat(format);
/*     */         try {
/* 138 */           date = simpleDateFormat.parse(value);
/* 139 */           return date;
/*     */         } catch (ParseException localParseException) {
/*     */         }
/*     */       }
/* 143 */       for (String dateFormat : DATE_FORMAT_LIST) {
/*     */         try {
/* 145 */           simpleDateFormat = new SimpleDateFormat(dateFormat);
/* 146 */           date = simpleDateFormat.parse(value);
/*     */         } catch (ParseException localParseException1) {
/*     */         }
/* 149 */         if (date != null)
/*     */         {
/*     */           break;
/*     */         }
/*     */       }
/* 154 */       return date;
/*     */     }
/*     */ 
/* 157 */     return null;
/*     */   }
/*     */ 
/*     */   public static String formatFloat(String value)
/*     */   {
/* 163 */     if ((null != value) && (value.contains(".")) && 
/* 164 */       (isNumeric(value)))
/*     */       try {
/* 166 */         BigDecimal decimal = new BigDecimal(value);
/* 167 */         BigDecimal setScale = decimal.setScale(10, 5).stripTrailingZeros();
/* 168 */         return setScale.toPlainString();
/*     */       }
/*     */       catch (Exception localException)
/*     */       {
/*     */       }
/* 173 */     return value;
/*     */   }
/*     */ 
/*     */   public static String formatFloat0(String value, int n) {
/* 177 */     if ((null != value) && (value.contains(".")) && 
/* 178 */       (isNumeric(value)))
/*     */       try {
/* 180 */         BigDecimal decimal = new BigDecimal(value);
/* 181 */         BigDecimal setScale = decimal.setScale(n, 5);
/* 182 */         return setScale.toPlainString();
/*     */       }
/*     */       catch (Exception localException)
/*     */       {
/*     */       }
/* 187 */     return value;
/*     */   }
/*     */ 
/*     */   private static boolean isNumeric(String str)
/*     */   {
/* 193 */     Matcher isNum = pattern.matcher(str);
/*     */ 
/* 195 */     return isNum.matches();
/*     */   }
/*     */ 
/*     */   public static String formatDate(Date cellValue, String format)
/*     */   {
/*     */     SimpleDateFormat simpleDateFormat;
/* 202 */     if (!StringUtils.isEmpty(format))
/* 203 */       simpleDateFormat = new SimpleDateFormat(format);
/*     */     else {
/* 205 */       simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
/*     */     }
/* 207 */     return simpleDateFormat.format(cellValue);
/*     */   }
/*     */ 
/*     */   public static String getFieldStringValue(BeanMap beanMap, String fieldName, String format) {
/* 211 */     String cellValue = null;
/* 212 */     Object value = beanMap.get(fieldName);
/* 213 */     if (value != null) {
/* 214 */       if ((value instanceof Date))
/* 215 */         cellValue = formatDate((Date)value, format);
/*     */       else {
/* 217 */         cellValue = value.toString();
/*     */       }
/*     */     }
/* 220 */     return cellValue;
/*     */   }
/*     */ 
/*     */   public static Map getFieldValues(List<String> stringList, ExcelHeadProperty excelHeadProperty, Boolean use1904WindowDate) {
/* 224 */     Map map = new HashMap();
/* 225 */     for (int i = 0; i < stringList.size(); i++) {
/* 226 */       ExcelColumnProperty columnProperty = excelHeadProperty.getExcelColumnProperty(i);
/* 227 */       if (columnProperty != null) {
/* 228 */         Object value = convert((String)stringList.get(i), columnProperty.getField(), columnProperty
/* 229 */           .getFormat(), use1904WindowDate.booleanValue());
/* 230 */         if (value != null) {
/* 231 */           map.put(columnProperty.getField().getName(), value);
/*     */         }
/*     */       }
/*     */     }
/* 235 */     return map;
/*     */   }
/*     */ 
/*     */   static
/*     */   {
/*  24 */     DATE_FORMAT_LIST.add("yyyy/MM/dd HH:mm:ss");
/*  25 */     DATE_FORMAT_LIST.add("yyyy-MM-dd HH:mm:ss");
/*  26 */     DATE_FORMAT_LIST.add("yyyyMMdd HH:mm:ss");
/*     */ 
/* 190 */     pattern = Pattern.compile("[\\+\\-]?[\\d]+([\\.][\\d]*)?([Ee][+-]?[\\d]+)?$");
/*     */   }
/*     */ }

/* Location:           C:\Users\Dell\Desktop\easyexcel-1.1.2-beta5.jar
 * Qualified Name:     com.alibaba.excel.util.TypeUtil
 * JD-Core Version:    0.6.0
 */