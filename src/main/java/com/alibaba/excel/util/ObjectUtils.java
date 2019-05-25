/*     */ package com.alibaba.excel.util;
/*     */ 
/*     */ import java.lang.reflect.Array;
/*     */ import java.util.Arrays;
/*     */ import java.util.Collection;
/*     */ import java.util.Map;
/*     */ 
/*     */ public abstract class ObjectUtils
/*     */ {
/*     */   private static final int INITIAL_HASH = 7;
/*     */   private static final int MULTIPLIER = 31;
/*     */   private static final String EMPTY_STRING = "";
/*     */   private static final String NULL_STRING = "null";
/*     */   private static final String ARRAY_START = "{";
/*     */   private static final String ARRAY_END = "}";
/*     */   private static final String EMPTY_ARRAY = "{}";
/*     */   private static final String ARRAY_ELEMENT_SEPARATOR = ", ";
/*     */ 
/*     */   public static boolean isCheckedException(Throwable ex)
/*     */   {
/*  64 */     return (!(ex instanceof RuntimeException)) && (!(ex instanceof Error));
/*     */   }
/*     */ 
/*     */   public static boolean isCompatibleWithThrowsClause(Throwable ex, Class<?>[] declaredExceptions)
/*     */   {
/*  75 */     if (!isCheckedException(ex)) {
/*  76 */       return true;
/*     */     }
/*  78 */     if (declaredExceptions != null) {
/*  79 */       for (Class declaredException : declaredExceptions) {
/*  80 */         if (declaredException.isInstance(ex)) {
/*  81 */           return true;
/*     */         }
/*     */       }
/*     */     }
/*  85 */     return false;
/*     */   }
/*     */ 
/*     */   public static boolean isArray(Object obj)
/*     */   {
/*  94 */     return (obj != null) && (obj.getClass().isArray());
/*     */   }
/*     */ 
/*     */   public static boolean isEmpty(Object[] array)
/*     */   {
/* 104 */     return (array == null) || (array.length == 0);
/*     */   }
/*     */ 
/*     */   public static boolean isEmpty(Object obj)
/*     */   {
/* 129 */     if (obj == null) {
/* 130 */       return true;
/*     */     }
/*     */ 
/* 133 */     if ((obj instanceof CharSequence)) {
/* 134 */       return ((CharSequence)obj).length() == 0;
/*     */     }
/* 136 */     if (obj.getClass().isArray()) {
/* 137 */       return Array.getLength(obj) == 0;
/*     */     }
/* 139 */     if ((obj instanceof Collection)) {
/* 140 */       return ((Collection)obj).isEmpty();
/*     */     }
/* 142 */     if ((obj instanceof Map)) {
/* 143 */       return ((Map)obj).isEmpty();
/*     */     }
/*     */ 
/* 147 */     return false;
/*     */   }
/*     */ 
/*     */   public static boolean containsElement(Object[] array, Object element)
/*     */   {
/* 158 */     if (array == null) {
/* 159 */       return false;
/*     */     }
/* 161 */     for (Object arrayEle : array) {
/* 162 */       if (nullSafeEquals(arrayEle, element)) {
/* 163 */         return true;
/*     */       }
/*     */     }
/* 166 */     return false;
/*     */   }
/*     */ 
/*     */   public static boolean containsConstant(Enum<?>[] enumValues, String constant)
/*     */   {
/* 177 */     return containsConstant(enumValues, constant, false);
/*     */   }
/*     */ 
/*     */   public static boolean containsConstant(Enum<?>[] enumValues, String constant, boolean caseSensitive)
/*     */   {
/* 188 */     for (Enum candidate : enumValues) {
/* 189 */       if (caseSensitive ? candidate
/* 190 */         .toString().equals(constant) : candidate
/* 191 */         .toString().equalsIgnoreCase(constant)) {
/* 192 */         return true;
/*     */       }
/*     */     }
/* 195 */     return false;
/*     */   }
/*     */ 
/*     */   public static <E extends Enum<?>> E caseInsensitiveValueOf(E[] enumValues, String constant)
/*     */   {
/* 207 */     for (Enum candidate : enumValues) {
/* 208 */       if (candidate.toString().equalsIgnoreCase(constant)) {
/* 209 */         return candidate;
/*     */       }
/*     */     }
/*     */ 
/* 213 */     throw new IllegalArgumentException(String.format("constant [%s] does not exist in enum type %s", new Object[] { constant, enumValues
/* 214 */       .getClass().getComponentType().getName() }));
/*     */   }
/*     */ 
/*     */   public static <A, O extends A> A[] addObjectToArray(A[] array, O obj)
/*     */   {
/* 225 */     Class compType = Object.class;
/* 226 */     if (array != null) {
/* 227 */       compType = array.getClass().getComponentType();
/*     */     }
/* 229 */     else if (obj != null) {
/* 230 */       compType = obj.getClass();
/*     */     }
/* 232 */     int newArrLength = array != null ? array.length + 1 : 1;
/*     */ 
/* 234 */     Object[] newArr = (Object[])(Object[])Array.newInstance(compType, newArrLength);
/* 235 */     if (array != null) {
/* 236 */       System.arraycopy(array, 0, newArr, 0, array.length);
/*     */     }
/* 238 */     newArr[(newArr.length - 1)] = obj;
/* 239 */     return newArr;
/*     */   }
/*     */ 
/*     */   public static Object[] toObjectArray(Object source)
/*     */   {
/* 252 */     if ((source instanceof Object[])) {
/* 253 */       return (Object[])(Object[])source;
/*     */     }
/* 255 */     if (source == null) {
/* 256 */       return new Object[0];
/*     */     }
/* 258 */     if (!source.getClass().isArray()) {
/* 259 */       throw new IllegalArgumentException(new StringBuilder().append("Source is not an array: ").append(source).toString());
/*     */     }
/* 261 */     int length = Array.getLength(source);
/* 262 */     if (length == 0) {
/* 263 */       return new Object[0];
/*     */     }
/* 265 */     Class wrapperType = Array.get(source, 0).getClass();
/* 266 */     Object[] newArray = (Object[])(Object[])Array.newInstance(wrapperType, length);
/* 267 */     for (int i = 0; i < length; i++) {
/* 268 */       newArray[i] = Array.get(source, i);
/*     */     }
/* 270 */     return newArray;
/*     */   }
/*     */ 
/*     */   public static boolean nullSafeEquals(Object o1, Object o2)
/*     */   {
/* 290 */     if (o1 == o2) {
/* 291 */       return true;
/*     */     }
/* 293 */     if ((o1 == null) || (o2 == null)) {
/* 294 */       return false;
/*     */     }
/* 296 */     if (o1.equals(o2)) {
/* 297 */       return true;
/*     */     }
/* 299 */     if ((o1.getClass().isArray()) && (o2.getClass().isArray())) {
/* 300 */       return arrayEquals(o1, o2);
/*     */     }
/* 302 */     return false;
/*     */   }
/*     */ 
/*     */   private static boolean arrayEquals(Object o1, Object o2)
/*     */   {
/* 315 */     if (((o1 instanceof Object[])) && ((o2 instanceof Object[]))) {
/* 316 */       return Arrays.equals((Object[])(Object[])o1, (Object[])(Object[])o2);
/*     */     }
/* 318 */     if (((o1 instanceof boolean[])) && ((o2 instanceof boolean[]))) {
/* 319 */       return Arrays.equals((boolean[])(boolean[])o1, (boolean[])(boolean[])o2);
/*     */     }
/* 321 */     if (((o1 instanceof byte[])) && ((o2 instanceof byte[]))) {
/* 322 */       return Arrays.equals((byte[])(byte[])o1, (byte[])(byte[])o2);
/*     */     }
/* 324 */     if (((o1 instanceof char[])) && ((o2 instanceof char[]))) {
/* 325 */       return Arrays.equals((char[])(char[])o1, (char[])(char[])o2);
/*     */     }
/* 327 */     if (((o1 instanceof double[])) && ((o2 instanceof double[]))) {
/* 328 */       return Arrays.equals((double[])(double[])o1, (double[])(double[])o2);
/*     */     }
/* 330 */     if (((o1 instanceof float[])) && ((o2 instanceof float[]))) {
/* 331 */       return Arrays.equals((float[])(float[])o1, (float[])(float[])o2);
/*     */     }
/* 333 */     if (((o1 instanceof int[])) && ((o2 instanceof int[]))) {
/* 334 */       return Arrays.equals((int[])(int[])o1, (int[])(int[])o2);
/*     */     }
/* 336 */     if (((o1 instanceof long[])) && ((o2 instanceof long[]))) {
/* 337 */       return Arrays.equals((long[])(long[])o1, (long[])(long[])o2);
/*     */     }
/* 339 */     if (((o1 instanceof short[])) && ((o2 instanceof short[]))) {
/* 340 */       return Arrays.equals((short[])(short[])o1, (short[])(short[])o2);
/*     */     }
/* 342 */     return false;
/*     */   }
/*     */ 
/*     */   public static int nullSafeHashCode(Object obj)
/*     */   {
/* 363 */     if (obj == null) {
/* 364 */       return 0;
/*     */     }
/* 366 */     if (obj.getClass().isArray()) {
/* 367 */       if ((obj instanceof Object[])) {
/* 368 */         return nullSafeHashCode((Object[])(Object[])obj);
/*     */       }
/* 370 */       if ((obj instanceof boolean[])) {
/* 371 */         return nullSafeHashCode((boolean[])(boolean[])obj);
/*     */       }
/* 373 */       if ((obj instanceof byte[])) {
/* 374 */         return nullSafeHashCode((byte[])(byte[])obj);
/*     */       }
/* 376 */       if ((obj instanceof char[])) {
/* 377 */         return nullSafeHashCode((char[])(char[])obj);
/*     */       }
/* 379 */       if ((obj instanceof double[])) {
/* 380 */         return nullSafeHashCode((double[])(double[])obj);
/*     */       }
/* 382 */       if ((obj instanceof float[])) {
/* 383 */         return nullSafeHashCode((float[])(float[])obj);
/*     */       }
/* 385 */       if ((obj instanceof int[])) {
/* 386 */         return nullSafeHashCode((int[])(int[])obj);
/*     */       }
/* 388 */       if ((obj instanceof long[])) {
/* 389 */         return nullSafeHashCode((long[])(long[])obj);
/*     */       }
/* 391 */       if ((obj instanceof short[])) {
/* 392 */         return nullSafeHashCode((short[])(short[])obj);
/*     */       }
/*     */     }
/* 395 */     return obj.hashCode();
/*     */   }
/*     */ 
/*     */   public static int nullSafeHashCode(Object[] array)
/*     */   {
/* 403 */     if (array == null) {
/* 404 */       return 0;
/*     */     }
/* 406 */     int hash = 7;
/* 407 */     for (Object element : array) {
/* 408 */       hash = 31 * hash + nullSafeHashCode(element);
/*     */     }
/* 410 */     return hash;
/*     */   }
/*     */ 
/*     */   public static int nullSafeHashCode(boolean[] array)
/*     */   {
/* 418 */     if (array == null) {
/* 419 */       return 0;
/*     */     }
/* 421 */     int hash = 7;
/* 422 */     for (boolean element : array) {
/* 423 */       hash = 31 * hash + hashCode(element);
/*     */     }
/* 425 */     return hash;
/*     */   }
/*     */ 
/*     */   public static int nullSafeHashCode(byte[] array)
/*     */   {
/* 433 */     if (array == null) {
/* 434 */       return 0;
/*     */     }
/* 436 */     int hash = 7;
/* 437 */     for (byte element : array) {
/* 438 */       hash = 31 * hash + element;
/*     */     }
/* 440 */     return hash;
/*     */   }
/*     */ 
/*     */   public static int nullSafeHashCode(char[] array)
/*     */   {
/* 448 */     if (array == null) {
/* 449 */       return 0;
/*     */     }
/* 451 */     int hash = 7;
/* 452 */     for (char element : array) {
/* 453 */       hash = 31 * hash + element;
/*     */     }
/* 455 */     return hash;
/*     */   }
/*     */ 
/*     */   public static int nullSafeHashCode(double[] array)
/*     */   {
/* 463 */     if (array == null) {
/* 464 */       return 0;
/*     */     }
/* 466 */     int hash = 7;
/* 467 */     for (double element : array) {
/* 468 */       hash = 31 * hash + hashCode(element);
/*     */     }
/* 470 */     return hash;
/*     */   }
/*     */ 
/*     */   public static int nullSafeHashCode(float[] array)
/*     */   {
/* 478 */     if (array == null) {
/* 479 */       return 0;
/*     */     }
/* 481 */     int hash = 7;
/* 482 */     for (float element : array) {
/* 483 */       hash = 31 * hash + hashCode(element);
/*     */     }
/* 485 */     return hash;
/*     */   }
/*     */ 
/*     */   public static int nullSafeHashCode(int[] array)
/*     */   {
/* 493 */     if (array == null) {
/* 494 */       return 0;
/*     */     }
/* 496 */     int hash = 7;
/* 497 */     for (int element : array) {
/* 498 */       hash = 31 * hash + element;
/*     */     }
/* 500 */     return hash;
/*     */   }
/*     */ 
/*     */   public static int nullSafeHashCode(long[] array)
/*     */   {
/* 508 */     if (array == null) {
/* 509 */       return 0;
/*     */     }
/* 511 */     int hash = 7;
/* 512 */     for (long element : array) {
/* 513 */       hash = 31 * hash + hashCode(element);
/*     */     }
/* 515 */     return hash;
/*     */   }
/*     */ 
/*     */   public static int nullSafeHashCode(short[] array)
/*     */   {
/* 523 */     if (array == null) {
/* 524 */       return 0;
/*     */     }
/* 526 */     int hash = 7;
/* 527 */     for (short element : array) {
/* 528 */       hash = 31 * hash + element;
/*     */     }
/* 530 */     return hash;
/*     */   }
/*     */ 
/*     */   public static int hashCode(boolean bool)
/*     */   {
/* 538 */     return bool ? 1231 : 1237;
/*     */   }
/*     */ 
/*     */   public static int hashCode(double dbl)
/*     */   {
/* 546 */     return hashCode(Double.doubleToLongBits(dbl));
/*     */   }
/*     */ 
/*     */   public static int hashCode(float flt)
/*     */   {
/* 554 */     return Float.floatToIntBits(flt);
/*     */   }
/*     */ 
/*     */   public static int hashCode(long lng)
/*     */   {
/* 562 */     return (int)(lng ^ lng >>> 32);
/*     */   }
/*     */ 
/*     */   public static String identityToString(Object obj)
/*     */   {
/* 577 */     if (obj == null) {
/* 578 */       return "";
/*     */     }
/* 580 */     return new StringBuilder().append(obj.getClass().getName()).append("@").append(getIdentityHexString(obj)).toString();
/*     */   }
/*     */ 
/*     */   public static String getIdentityHexString(Object obj)
/*     */   {
/* 589 */     return Integer.toHexString(System.identityHashCode(obj));
/*     */   }
/*     */ 
/*     */   public static String getDisplayString(Object obj)
/*     */   {
/* 602 */     if (obj == null) {
/* 603 */       return "";
/*     */     }
/* 605 */     return nullSafeToString(obj);
/*     */   }
/*     */ 
/*     */   public static String nullSafeClassName(Object obj)
/*     */   {
/* 615 */     return obj != null ? obj.getClass().getName() : "null";
/*     */   }
/*     */ 
/*     */   public static String nullSafeToString(Object obj)
/*     */   {
/* 626 */     if (obj == null) {
/* 627 */       return "null";
/*     */     }
/* 629 */     if ((obj instanceof String)) {
/* 630 */       return (String)obj;
/*     */     }
/* 632 */     if ((obj instanceof Object[])) {
/* 633 */       return nullSafeToString((Object[])(Object[])obj);
/*     */     }
/* 635 */     if ((obj instanceof boolean[])) {
/* 636 */       return nullSafeToString((boolean[])(boolean[])obj);
/*     */     }
/* 638 */     if ((obj instanceof byte[])) {
/* 639 */       return nullSafeToString((byte[])(byte[])obj);
/*     */     }
/* 641 */     if ((obj instanceof char[])) {
/* 642 */       return nullSafeToString((char[])(char[])obj);
/*     */     }
/* 644 */     if ((obj instanceof double[])) {
/* 645 */       return nullSafeToString((double[])(double[])obj);
/*     */     }
/* 647 */     if ((obj instanceof float[])) {
/* 648 */       return nullSafeToString((float[])(float[])obj);
/*     */     }
/* 650 */     if ((obj instanceof int[])) {
/* 651 */       return nullSafeToString((int[])(int[])obj);
/*     */     }
/* 653 */     if ((obj instanceof long[])) {
/* 654 */       return nullSafeToString((long[])(long[])obj);
/*     */     }
/* 656 */     if ((obj instanceof short[])) {
/* 657 */       return nullSafeToString((short[])(short[])obj);
/*     */     }
/* 659 */     String str = obj.toString();
/* 660 */     return str != null ? str : "";
/*     */   }
/*     */ 
/*     */   public static String nullSafeToString(Object[] array)
/*     */   {
/* 673 */     if (array == null) {
/* 674 */       return "null";
/*     */     }
/* 676 */     int length = array.length;
/* 677 */     if (length == 0) {
/* 678 */       return "{}";
/*     */     }
/* 680 */     StringBuilder sb = new StringBuilder();
/* 681 */     for (int i = 0; i < length; i++) {
/* 682 */       if (i == 0) {
/* 683 */         sb.append("{");
/*     */       }
/*     */       else {
/* 686 */         sb.append(", ");
/*     */       }
/* 688 */       sb.append(String.valueOf(array[i]));
/*     */     }
/* 690 */     sb.append("}");
/* 691 */     return sb.toString();
/*     */   }
/*     */ 
/*     */   public static String nullSafeToString(boolean[] array)
/*     */   {
/* 704 */     if (array == null) {
/* 705 */       return "null";
/*     */     }
/* 707 */     int length = array.length;
/* 708 */     if (length == 0) {
/* 709 */       return "{}";
/*     */     }
/* 711 */     StringBuilder sb = new StringBuilder();
/* 712 */     for (int i = 0; i < length; i++) {
/* 713 */       if (i == 0) {
/* 714 */         sb.append("{");
/*     */       }
/*     */       else {
/* 717 */         sb.append(", ");
/*     */       }
/*     */ 
/* 720 */       sb.append(array[i]);
/*     */     }
/* 722 */     sb.append("}");
/* 723 */     return sb.toString();
/*     */   }
/*     */ 
/*     */   public static String nullSafeToString(byte[] array)
/*     */   {
/* 736 */     if (array == null) {
/* 737 */       return "null";
/*     */     }
/* 739 */     int length = array.length;
/* 740 */     if (length == 0) {
/* 741 */       return "{}";
/*     */     }
/* 743 */     StringBuilder sb = new StringBuilder();
/* 744 */     for (int i = 0; i < length; i++) {
/* 745 */       if (i == 0) {
/* 746 */         sb.append("{");
/*     */       }
/*     */       else {
/* 749 */         sb.append(", ");
/*     */       }
/* 751 */       sb.append(array[i]);
/*     */     }
/* 753 */     sb.append("}");
/* 754 */     return sb.toString();
/*     */   }
/*     */ 
/*     */   public static String nullSafeToString(char[] array)
/*     */   {
/* 767 */     if (array == null) {
/* 768 */       return "null";
/*     */     }
/* 770 */     int length = array.length;
/* 771 */     if (length == 0) {
/* 772 */       return "{}";
/*     */     }
/* 774 */     StringBuilder sb = new StringBuilder();
/* 775 */     for (int i = 0; i < length; i++) {
/* 776 */       if (i == 0) {
/* 777 */         sb.append("{");
/*     */       }
/*     */       else {
/* 780 */         sb.append(", ");
/*     */       }
/* 782 */       sb.append("'").append(array[i]).append("'");
/*     */     }
/* 784 */     sb.append("}");
/* 785 */     return sb.toString();
/*     */   }
/*     */ 
/*     */   public static String nullSafeToString(double[] array)
/*     */   {
/* 798 */     if (array == null) {
/* 799 */       return "null";
/*     */     }
/* 801 */     int length = array.length;
/* 802 */     if (length == 0) {
/* 803 */       return "{}";
/*     */     }
/* 805 */     StringBuilder sb = new StringBuilder();
/* 806 */     for (int i = 0; i < length; i++) {
/* 807 */       if (i == 0) {
/* 808 */         sb.append("{");
/*     */       }
/*     */       else {
/* 811 */         sb.append(", ");
/*     */       }
/*     */ 
/* 814 */       sb.append(array[i]);
/*     */     }
/* 816 */     sb.append("}");
/* 817 */     return sb.toString();
/*     */   }
/*     */ 
/*     */   public static String nullSafeToString(float[] array)
/*     */   {
/* 830 */     if (array == null) {
/* 831 */       return "null";
/*     */     }
/* 833 */     int length = array.length;
/* 834 */     if (length == 0) {
/* 835 */       return "{}";
/*     */     }
/* 837 */     StringBuilder sb = new StringBuilder();
/* 838 */     for (int i = 0; i < length; i++) {
/* 839 */       if (i == 0) {
/* 840 */         sb.append("{");
/*     */       }
/*     */       else {
/* 843 */         sb.append(", ");
/*     */       }
/*     */ 
/* 846 */       sb.append(array[i]);
/*     */     }
/* 848 */     sb.append("}");
/* 849 */     return sb.toString();
/*     */   }
/*     */ 
/*     */   public static String nullSafeToString(int[] array)
/*     */   {
/* 862 */     if (array == null) {
/* 863 */       return "null";
/*     */     }
/* 865 */     int length = array.length;
/* 866 */     if (length == 0) {
/* 867 */       return "{}";
/*     */     }
/* 869 */     StringBuilder sb = new StringBuilder();
/* 870 */     for (int i = 0; i < length; i++) {
/* 871 */       if (i == 0) {
/* 872 */         sb.append("{");
/*     */       }
/*     */       else {
/* 875 */         sb.append(", ");
/*     */       }
/* 877 */       sb.append(array[i]);
/*     */     }
/* 879 */     sb.append("}");
/* 880 */     return sb.toString();
/*     */   }
/*     */ 
/*     */   public static String nullSafeToString(long[] array)
/*     */   {
/* 893 */     if (array == null) {
/* 894 */       return "null";
/*     */     }
/* 896 */     int length = array.length;
/* 897 */     if (length == 0) {
/* 898 */       return "{}";
/*     */     }
/* 900 */     StringBuilder sb = new StringBuilder();
/* 901 */     for (int i = 0; i < length; i++) {
/* 902 */       if (i == 0) {
/* 903 */         sb.append("{");
/*     */       }
/*     */       else {
/* 906 */         sb.append(", ");
/*     */       }
/* 908 */       sb.append(array[i]);
/*     */     }
/* 910 */     sb.append("}");
/* 911 */     return sb.toString();
/*     */   }
/*     */ 
/*     */   public static String nullSafeToString(short[] array)
/*     */   {
/* 924 */     if (array == null) {
/* 925 */       return "null";
/*     */     }
/* 927 */     int length = array.length;
/* 928 */     if (length == 0) {
/* 929 */       return "{}";
/*     */     }
/* 931 */     StringBuilder sb = new StringBuilder();
/* 932 */     for (int i = 0; i < length; i++) {
/* 933 */       if (i == 0) {
/* 934 */         sb.append("{");
/*     */       }
/*     */       else {
/* 937 */         sb.append(", ");
/*     */       }
/* 939 */       sb.append(array[i]);
/*     */     }
/* 941 */     sb.append("}");
/* 942 */     return sb.toString();
/*     */   }
/*     */ }

/* Location:           C:\Users\Dell\Desktop\easyexcel-1.1.2-beta5.jar
 * Qualified Name:     com.alibaba.excel.util.ObjectUtils
 * JD-Core Version:    0.6.0
 */