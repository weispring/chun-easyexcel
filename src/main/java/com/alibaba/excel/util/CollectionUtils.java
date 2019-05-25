/*     */ package com.alibaba.excel.util;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.Collection;
/*     */ import java.util.Enumeration;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Properties;
/*     */ 
/*     */ public abstract class CollectionUtils
/*     */ {
/*     */   public static boolean isEmpty(Collection<?> collection)
/*     */   {
/*  23 */     return (collection == null) || (collection.isEmpty());
/*     */   }
/*     */ 
/*     */   public static boolean isEmpty(Map<?, ?> map)
/*     */   {
/*  33 */     return (map == null) || (map.isEmpty());
/*     */   }
/*     */ 
/*     */   public static List arrayToList(Object source)
/*     */   {
/*  50 */     return Arrays.asList(ObjectUtils.toObjectArray(source));
/*     */   }
/*     */ 
/*     */   public static <E> void mergeArrayIntoCollection(Object array, Collection<E> collection)
/*     */   {
/*  60 */     if (collection == null) {
/*  61 */       throw new IllegalArgumentException("Collection must not be null");
/*     */     }
/*  63 */     Object[] arr = ObjectUtils.toObjectArray(array);
/*  64 */     for (Object elem : arr)
/*  65 */       collection.add(elem);
/*     */   }
/*     */ 
/*     */   public static <K, V> void mergePropertiesIntoMap(Properties props, Map<K, V> map)
/*     */   {
/*  79 */     if (map == null)
/*  80 */       throw new IllegalArgumentException("Map must not be null");
/*     */     Enumeration en;
/*  82 */     if (props != null)
/*  83 */       for (en = props.propertyNames(); en.hasMoreElements(); ) {
/*  84 */         String key = (String)en.nextElement();
/*  85 */         Object value = props.get(key);
/*  86 */         if (value == null)
/*     */         {
/*  88 */           value = props.getProperty(key);
/*     */         }
/*  90 */         map.put(key, value);
/*     */       }
/*     */   }
/*     */ 
/*     */   public static boolean contains(Iterator<?> iterator, Object element)
/*     */   {
/* 103 */     if (iterator != null) {
/* 104 */       while (iterator.hasNext()) {
/* 105 */         Object candidate = iterator.next();
/* 106 */         if (ObjectUtils.nullSafeEquals(candidate, element)) {
/* 107 */           return true;
/*     */         }
/*     */       }
/*     */     }
/* 111 */     return false;
/*     */   }
/*     */ 
/*     */   public static boolean contains(Enumeration<?> enumeration, Object element)
/*     */   {
/* 121 */     if (enumeration != null) {
/* 122 */       while (enumeration.hasMoreElements()) {
/* 123 */         Object candidate = enumeration.nextElement();
/* 124 */         if (ObjectUtils.nullSafeEquals(candidate, element)) {
/* 125 */           return true;
/*     */         }
/*     */       }
/*     */     }
/* 129 */     return false;
/*     */   }
/*     */ 
/*     */   public static boolean containsInstance(Collection<?> collection, Object element)
/*     */   {
/*     */     Iterator localIterator;
/* 141 */     if (collection != null) {
/* 142 */       for (localIterator = collection.iterator(); localIterator.hasNext(); ) { Object candidate = localIterator.next();
/* 143 */         if (candidate == element) {
/* 144 */           return true;
/*     */         }
/*     */       }
/*     */     }
/* 148 */     return false;
/*     */   }
/*     */ 
/*     */   public static boolean containsAny(Collection<?> source, Collection<?> candidates)
/*     */   {
/* 159 */     if ((isEmpty(source)) || (isEmpty(candidates))) {
/* 160 */       return false;
/*     */     }
/* 162 */     for (Iterator localIterator = candidates.iterator(); localIterator.hasNext(); ) { Object candidate = localIterator.next();
/* 163 */       if (source.contains(candidate)) {
/* 164 */         return true;
/*     */       }
/*     */     }
/* 167 */     return false;
/*     */   }
/*     */ 
/*     */   public static <E> E findFirstMatch(Collection<?> source, Collection<E> candidates)
/*     */   {
/* 181 */     if ((isEmpty(source)) || (isEmpty(candidates))) {
/* 182 */       return null;
/*     */     }
/* 184 */     for (Iterator localIterator = candidates.iterator(); localIterator.hasNext(); ) { Object candidate = localIterator.next();
/* 185 */       if (source.contains(candidate)) {
/* 186 */         return candidate;
/*     */       }
/*     */     }
/* 189 */     return null;
/*     */   }
/*     */ 
/*     */   public static <T> T findValueOfType(Collection<?> collection, Class<T> type)
/*     */   {
/* 201 */     if (isEmpty(collection)) {
/* 202 */       return null;
/*     */     }
/* 204 */     Object value = null;
/* 205 */     for (Iterator localIterator = collection.iterator(); localIterator.hasNext(); ) { Object element = localIterator.next();
/* 206 */       if ((type == null) || (type.isInstance(element))) {
/* 207 */         if (value != null)
/*     */         {
/* 209 */           return null;
/*     */         }
/* 211 */         value = element;
/*     */       }
/*     */     }
/* 214 */     return value;
/*     */   }
/*     */ 
/*     */   public static Object findValueOfType(Collection<?> collection, Class<?>[] types)
/*     */   {
/* 227 */     if ((isEmpty(collection)) || (ObjectUtils.isEmpty(types))) {
/* 228 */       return null;
/*     */     }
/* 230 */     for (Class type : types) {
/* 231 */       Object value = findValueOfType(collection, type);
/* 232 */       if (value != null) {
/* 233 */         return value;
/*     */       }
/*     */     }
/* 236 */     return null;
/*     */   }
/*     */ 
/*     */   public static boolean hasUniqueObject(Collection<?> collection)
/*     */   {
/* 246 */     if (isEmpty(collection)) {
/* 247 */       return false;
/*     */     }
/* 249 */     boolean hasCandidate = false;
/* 250 */     Object candidate = null;
/* 251 */     for (Iterator localIterator = collection.iterator(); localIterator.hasNext(); ) { Object elem = localIterator.next();
/* 252 */       if (!hasCandidate) {
/* 253 */         hasCandidate = true;
/* 254 */         candidate = elem;
/*     */       }
/* 256 */       else if (candidate != elem) {
/* 257 */         return false;
/*     */       }
/*     */     }
/* 260 */     return true;
/*     */   }
/*     */ 
/*     */   public static Class<?> findCommonElementType(Collection<?> collection)
/*     */   {
/* 270 */     if (isEmpty(collection)) {
/* 271 */       return null;
/*     */     }
/* 273 */     Class candidate = null;
/* 274 */     for (Iterator localIterator = collection.iterator(); localIterator.hasNext(); ) { Object val = localIterator.next();
/* 275 */       if (val != null) {
/* 276 */         if (candidate == null) {
/* 277 */           candidate = val.getClass();
/*     */         }
/* 279 */         else if (candidate != val.getClass()) {
/* 280 */           return null;
/*     */         }
/*     */       }
/*     */     }
/* 284 */     return candidate;
/*     */   }
/*     */ 
/*     */   public static <A, E extends A> A[] toArray(Enumeration<E> enumeration, A[] array)
/*     */   {
/* 293 */     ArrayList elements = new ArrayList();
/* 294 */     while (enumeration.hasMoreElements()) {
/* 295 */       elements.add(enumeration.nextElement());
/*     */     }
/* 297 */     return elements.toArray(array);
/*     */   }
/*     */ 
/*     */   public static <E> Iterator<E> toIterator(Enumeration<E> enumeration)
/*     */   {
/* 306 */     return new EnumerationIterator(enumeration);
/*     */   }
/*     */ 
/*     */   private static class EnumerationIterator<E>
/*     */     implements Iterator<E>
/*     */   {
/*     */     private final Enumeration<E> enumeration;
/*     */ 
/*     */     public EnumerationIterator(Enumeration<E> enumeration)
/*     */     {
/* 319 */       this.enumeration = enumeration;
/*     */     }
/*     */ 
/*     */     public boolean hasNext()
/*     */     {
/* 324 */       return this.enumeration.hasMoreElements();
/*     */     }
/*     */ 
/*     */     public E next()
/*     */     {
/* 329 */       return this.enumeration.nextElement();
/*     */     }
/*     */ 
/*     */     public void remove() throws UnsupportedOperationException
/*     */     {
/* 334 */       throw new UnsupportedOperationException("Not supported");
/*     */     }
/*     */   }
/*     */ }

/* Location:           C:\Users\Dell\Desktop\easyexcel-1.1.2-beta5.jar
 * Qualified Name:     com.alibaba.excel.util.CollectionUtils
 * JD-Core Version:    0.6.0
 */