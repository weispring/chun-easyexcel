/*    */ package com.alibaba.excel.analysis.v07;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import java.io.InputStream;
/*    */ import javax.xml.parsers.ParserConfigurationException;
/*    */ import javax.xml.parsers.SAXParser;
/*    */ import javax.xml.parsers.SAXParserFactory;
/*    */ import org.xml.sax.ContentHandler;
/*    */ import org.xml.sax.InputSource;
/*    */ import org.xml.sax.SAXException;
/*    */ import org.xml.sax.XMLReader;
/*    */ 
/*    */ public class XmlParserFactory
/*    */ {
/*    */   public static void parse(InputStream inputStream, ContentHandler contentHandler)
/*    */     throws ParserConfigurationException, SAXException, IOException
/*    */   {
/* 16 */     InputSource sheetSource = new InputSource(inputStream);
/* 17 */     SAXParserFactory saxFactory = SAXParserFactory.newInstance();
/* 18 */     SAXParser saxParser = saxFactory.newSAXParser();
/* 19 */     XMLReader xmlReader = saxParser.getXMLReader();
/* 20 */     xmlReader.setContentHandler(contentHandler);
/* 21 */     xmlReader.parse(sheetSource);
/*    */   }
/*    */ }

/* Location:           C:\Users\Dell\Desktop\easyexcel-1.1.2-vphonor-SNAPSHOT.jar
 * Qualified Name:     com.alibaba.excel.analysis.v07.XmlParserFactory
 * JD-Core Version:    0.6.0
 */