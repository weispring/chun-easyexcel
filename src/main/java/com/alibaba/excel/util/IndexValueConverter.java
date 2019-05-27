/*    */ package com.alibaba.excel.util;
/*    */ 
/*    */ import com.alibaba.excel.metadata.IndexValue;
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
/*    */ import java.util.Stack;
/*    */ 
/*    */ public class IndexValueConverter
/*    */ {
/*    */   public static List<String> converter(List<IndexValue> i_list)
/*    */   {
/* 16 */     List tem = new ArrayList();
/*    */ 
/* 18 */     char[] start = { '@' };
/* 19 */     int j = 0;
/* 20 */     for (; j < i_list.size(); j++) {
/* 21 */       IndexValue currentIndexValue = (IndexValue)i_list.get(j);
/* 22 */       char[] currentIndex = currentIndexValue.getV_index().replaceAll("[0-9]", "").toCharArray();
/* 23 */       if (j > 0) {
/* 24 */         start = ((IndexValue)i_list.get(j - 1)).getV_index().replaceAll("[0-9]", "").toCharArray();
/*    */       }
/* 26 */       int deep = subtraction26(currentIndex, start);
/* 27 */       int k = 0;
/* 28 */       for (; k < deep - 1; k++) {
/* 29 */         tem.add(null);
/*    */       }
/* 31 */       tem.add(currentIndexValue.getV_value());
/*    */     }
/* 33 */     return tem;
/*    */   }
/*    */ 
/*    */   private static int subtraction26(char[] currentIndex, char[] beforeIndex) {
/* 37 */     int result = 0;
/*    */ 
/* 39 */     Stack currentStack = new Stack();
/* 40 */     Stack berforStack = new Stack();
/*    */ 
/* 42 */     for (int i = 0; i < currentIndex.length; i++) {
/* 43 */       currentStack.push(Character.valueOf(currentIndex[i]));
/*    */     }
/* 45 */     for (int i = 0; i < beforeIndex.length; i++) {
/* 46 */       berforStack.push(Character.valueOf(beforeIndex[i]));
/*    */     }
/* 48 */     int i = 0;
/* 49 */     char beforechar = '@';
/* 50 */     while (!currentStack.isEmpty()) {
/* 51 */       char currentChar = ((Character)currentStack.pop()).charValue();
/* 52 */       if (!berforStack.isEmpty()) {
/* 53 */         beforechar = ((Character)berforStack.pop()).charValue();
/*    */       }
/* 55 */       int n = currentChar - beforechar;
/* 56 */       if (n < 0) {
/* 57 */         n += 26;
/* 58 */         if (!currentStack.isEmpty()) {
/* 59 */           char borrow = ((Character)currentStack.pop()).charValue();
/* 60 */           char newBorrow = (char)(borrow - '\001');
/* 61 */           currentStack.push(Character.valueOf(newBorrow));
/*    */         }
/*    */ 
/*    */       }
/*    */ 
/* 66 */       result = (int)(result + n * Math.pow(26.0D, i));
/* 67 */       i++;
/* 68 */       beforechar = '@';
/*    */     }
/*    */ 
/* 71 */     return result;
/*    */   }
/*    */ }

/* Location:           C:\Users\Dell\Desktop\easyexcel-1.1.2-vphonor-SNAPSHOT.jar
 * Qualified Name:     com.alibaba.excel.util.IndexValueConverter
 * JD-Core Version:    0.6.0
 */