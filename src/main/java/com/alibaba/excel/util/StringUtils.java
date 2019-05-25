/*      */ package com.alibaba.excel.util;
/*      */ 
/*      */ import java.util.ArrayList;
/*      */ import java.util.Arrays;
/*      */ import java.util.Collection;
/*      */ import java.util.Collections;
/*      */ import java.util.Enumeration;
/*      */ import java.util.Iterator;
/*      */ import java.util.LinkedHashSet;
/*      */ import java.util.LinkedList;
/*      */ import java.util.List;
/*      */ import java.util.Locale;
/*      */ import java.util.Properties;
/*      */ import java.util.Set;
/*      */ import java.util.StringTokenizer;
/*      */ import java.util.TimeZone;
/*      */ 
/*      */ public abstract class StringUtils
/*      */ {
/*      */   private static final String FOLDER_SEPARATOR = "/";
/*      */   private static final String WINDOWS_FOLDER_SEPARATOR = "\\";
/*      */   private static final String TOP_PATH = "..";
/*      */   private static final String CURRENT_PATH = ".";
/*      */   private static final char EXTENSION_SEPARATOR = '.';
/*      */ 
/*      */   public static boolean isEmpty(Object str)
/*      */   {
/*   86 */     return (str == null) || ("".equals(str));
/*      */   }
/*      */ 
/*      */   public static boolean hasLength(CharSequence str)
/*      */   {
/*  105 */     return (str != null) && (str.length() > 0);
/*      */   }
/*      */ 
/*      */   public static boolean hasLength(String str)
/*      */   {
/*  118 */     return (str != null) && (!str.isEmpty());
/*      */   }
/*      */ 
/*      */   public static boolean hasText(CharSequence str)
/*      */   {
/*  139 */     return (hasLength(str)) && (containsText(str));
/*      */   }
/*      */ 
/*      */   public static boolean hasText(String str)
/*      */   {
/*  153 */     return (hasLength(str)) && (containsText(str));
/*      */   }
/*      */ 
/*      */   private static boolean containsText(CharSequence str) {
/*  157 */     int strLen = str.length();
/*  158 */     for (int i = 0; i < strLen; i++) {
/*  159 */       if (!Character.isWhitespace(str.charAt(i))) {
/*  160 */         return true;
/*      */       }
/*      */     }
/*  163 */     return false;
/*      */   }
/*      */ 
/*      */   public static boolean containsWhitespace(CharSequence str)
/*      */   {
/*  174 */     if (!hasLength(str)) {
/*  175 */       return false;
/*      */     }
/*      */ 
/*  178 */     int strLen = str.length();
/*  179 */     for (int i = 0; i < strLen; i++) {
/*  180 */       if (Character.isWhitespace(str.charAt(i))) {
/*  181 */         return true;
/*      */       }
/*      */     }
/*  184 */     return false;
/*      */   }
/*      */ 
/*      */   public static boolean containsWhitespace(String str)
/*      */   {
/*  195 */     return containsWhitespace(str);
/*      */   }
/*      */ 
/*      */   public static String trimWhitespace(String str)
/*      */   {
/*  205 */     if (!hasLength(str)) {
/*  206 */       return str;
/*      */     }
/*      */ 
/*  209 */     StringBuilder sb = new StringBuilder(str);
/*  210 */     while ((sb.length() > 0) && (Character.isWhitespace(sb.charAt(0)))) {
/*  211 */       sb.deleteCharAt(0);
/*      */     }
/*  213 */     while ((sb.length() > 0) && (Character.isWhitespace(sb.charAt(sb.length() - 1)))) {
/*  214 */       sb.deleteCharAt(sb.length() - 1);
/*      */     }
/*  216 */     return sb.toString();
/*      */   }
/*      */ 
/*      */   public static String trimAllWhitespace(String str)
/*      */   {
/*  227 */     if (!hasLength(str)) {
/*  228 */       return str;
/*      */     }
/*      */ 
/*  231 */     int len = str.length();
/*  232 */     StringBuilder sb = new StringBuilder(str.length());
/*  233 */     for (int i = 0; i < len; i++) {
/*  234 */       char c = str.charAt(i);
/*  235 */       if (!Character.isWhitespace(c)) {
/*  236 */         sb.append(c);
/*      */       }
/*      */     }
/*  239 */     return sb.toString();
/*      */   }
/*      */ 
/*      */   public static String trimLeadingWhitespace(String str)
/*      */   {
/*  249 */     if (!hasLength(str)) {
/*  250 */       return str;
/*      */     }
/*      */ 
/*  253 */     StringBuilder sb = new StringBuilder(str);
/*  254 */     while ((sb.length() > 0) && (Character.isWhitespace(sb.charAt(0)))) {
/*  255 */       sb.deleteCharAt(0);
/*      */     }
/*  257 */     return sb.toString();
/*      */   }
/*      */ 
/*      */   public static String trimTrailingWhitespace(String str)
/*      */   {
/*  267 */     if (!hasLength(str)) {
/*  268 */       return str;
/*      */     }
/*      */ 
/*  271 */     StringBuilder sb = new StringBuilder(str);
/*  272 */     while ((sb.length() > 0) && (Character.isWhitespace(sb.charAt(sb.length() - 1)))) {
/*  273 */       sb.deleteCharAt(sb.length() - 1);
/*      */     }
/*  275 */     return sb.toString();
/*      */   }
/*      */ 
/*      */   public static String trimLeadingCharacter(String str, char leadingCharacter)
/*      */   {
/*  285 */     if (!hasLength(str)) {
/*  286 */       return str;
/*      */     }
/*      */ 
/*  289 */     StringBuilder sb = new StringBuilder(str);
/*  290 */     while ((sb.length() > 0) && (sb.charAt(0) == leadingCharacter)) {
/*  291 */       sb.deleteCharAt(0);
/*      */     }
/*  293 */     return sb.toString();
/*      */   }
/*      */ 
/*      */   public static String trimTrailingCharacter(String str, char trailingCharacter)
/*      */   {
/*  303 */     if (!hasLength(str)) {
/*  304 */       return str;
/*      */     }
/*      */ 
/*  307 */     StringBuilder sb = new StringBuilder(str);
/*  308 */     while ((sb.length() > 0) && (sb.charAt(sb.length() - 1) == trailingCharacter)) {
/*  309 */       sb.deleteCharAt(sb.length() - 1);
/*      */     }
/*  311 */     return sb.toString();
/*      */   }
/*      */ 
/*      */   public static boolean startsWithIgnoreCase(String str, String prefix)
/*      */   {
/*  322 */     return (str != null) && (prefix != null) && (str.length() >= prefix.length()) && 
/*  323 */       (str
/*  323 */       .regionMatches(true, 0, prefix, 0, prefix
/*  323 */       .length()));
/*      */   }
/*      */ 
/*      */   public static boolean endsWithIgnoreCase(String str, String suffix)
/*      */   {
/*  334 */     return (str != null) && (suffix != null) && (str.length() >= suffix.length()) && 
/*  335 */       (str
/*  335 */       .regionMatches(true, str
/*  335 */       .length() - suffix.length(), suffix, 0, suffix.length()));
/*      */   }
/*      */ 
/*      */   public static boolean substringMatch(CharSequence str, int index, CharSequence substring)
/*      */   {
/*  346 */     if (index + substring.length() > str.length()) {
/*  347 */       return false;
/*      */     }
/*  349 */     for (int i = 0; i < substring.length(); i++) {
/*  350 */       if (str.charAt(index + i) != substring.charAt(i)) {
/*  351 */         return false;
/*      */       }
/*      */     }
/*  354 */     return true;
/*      */   }
/*      */ 
/*      */   public static int countOccurrencesOf(String str, String sub)
/*      */   {
/*  363 */     if ((!hasLength(str)) || (!hasLength(sub))) {
/*  364 */       return 0;
/*      */     }
/*      */ 
/*  367 */     int count = 0;
/*  368 */     int pos = 0;
/*      */     int idx;
/*  370 */     while ((idx = str.indexOf(sub, pos)) != -1) {
/*  371 */       count++;
/*  372 */       pos = idx + sub.length();
/*      */     }
/*  374 */     return count;
/*      */   }
/*      */ 
/*      */   public static String replace(String inString, String oldPattern, String newPattern)
/*      */   {
/*  385 */     if ((!hasLength(inString)) || (!hasLength(oldPattern)) || (newPattern == null)) {
/*  386 */       return inString;
/*      */     }
/*  388 */     int index = inString.indexOf(oldPattern);
/*  389 */     if (index == -1)
/*      */     {
/*  391 */       return inString;
/*      */     }
/*      */ 
/*  394 */     int capacity = inString.length();
/*  395 */     if (newPattern.length() > oldPattern.length()) {
/*  396 */       capacity += 16;
/*      */     }
/*  398 */     StringBuilder sb = new StringBuilder(capacity);
/*      */ 
/*  400 */     int pos = 0;
/*  401 */     int patLen = oldPattern.length();
/*  402 */     while (index >= 0) {
/*  403 */       sb.append(inString.substring(pos, index));
/*  404 */       sb.append(newPattern);
/*  405 */       pos = index + patLen;
/*  406 */       index = inString.indexOf(oldPattern, pos);
/*      */     }
/*      */ 
/*  410 */     sb.append(inString.substring(pos));
/*  411 */     return sb.toString();
/*      */   }
/*      */ 
/*      */   public static String delete(String inString, String pattern)
/*      */   {
/*  421 */     return replace(inString, pattern, "");
/*      */   }
/*      */ 
/*      */   public static String deleteAny(String inString, String charsToDelete)
/*      */   {
/*  432 */     if ((!hasLength(inString)) || (!hasLength(charsToDelete))) {
/*  433 */       return inString;
/*      */     }
/*      */ 
/*  436 */     StringBuilder sb = new StringBuilder(inString.length());
/*  437 */     for (int i = 0; i < inString.length(); i++) {
/*  438 */       char c = inString.charAt(i);
/*  439 */       if (charsToDelete.indexOf(c) == -1) {
/*  440 */         sb.append(c);
/*      */       }
/*      */     }
/*  443 */     return sb.toString();
/*      */   }
/*      */ 
/*      */   public static String quote(String str)
/*      */   {
/*  458 */     return str != null ? new StringBuilder().append("'").append(str).append("'").toString() : null;
/*      */   }
/*      */ 
/*      */   public static Object quoteIfString(Object obj)
/*      */   {
/*  469 */     return (obj instanceof String) ? quote((String)obj) : obj;
/*      */   }
/*      */ 
/*      */   public static String unqualify(String qualifiedName)
/*      */   {
/*  478 */     return unqualify(qualifiedName, '.');
/*      */   }
/*      */ 
/*      */   public static String unqualify(String qualifiedName, char separator)
/*      */   {
/*  488 */     return qualifiedName.substring(qualifiedName.lastIndexOf(separator) + 1);
/*      */   }
/*      */ 
/*      */   public static String capitalize(String str)
/*      */   {
/*  499 */     return changeFirstCharacterCase(str, true);
/*      */   }
/*      */ 
/*      */   public static String uncapitalize(String str)
/*      */   {
/*  510 */     return changeFirstCharacterCase(str, false);
/*      */   }
/*      */ 
/*      */   private static String changeFirstCharacterCase(String str, boolean capitalize) {
/*  514 */     if (!hasLength(str)) {
/*  515 */       return str;
/*      */     }
/*      */ 
/*  518 */     char baseChar = str.charAt(0);
/*      */     char updatedChar;
/*      */     char updatedChar;
/*  520 */     if (capitalize) {
/*  521 */       updatedChar = Character.toUpperCase(baseChar);
/*      */     }
/*      */     else {
/*  524 */       updatedChar = Character.toLowerCase(baseChar);
/*      */     }
/*  526 */     if (baseChar == updatedChar) {
/*  527 */       return str;
/*      */     }
/*      */ 
/*  530 */     char[] chars = str.toCharArray();
/*  531 */     chars[0] = updatedChar;
/*  532 */     return new String(chars, 0, chars.length);
/*      */   }
/*      */ 
/*      */   public static String getFilename(String path)
/*      */   {
/*  542 */     if (path == null) {
/*  543 */       return null;
/*      */     }
/*      */ 
/*  546 */     int separatorIndex = path.lastIndexOf("/");
/*  547 */     return separatorIndex != -1 ? path.substring(separatorIndex + 1) : path;
/*      */   }
/*      */ 
/*      */   public static String getFilenameExtension(String path)
/*      */   {
/*  557 */     if (path == null) {
/*  558 */       return null;
/*      */     }
/*      */ 
/*  561 */     int extIndex = path.lastIndexOf(46);
/*  562 */     if (extIndex == -1) {
/*  563 */       return null;
/*      */     }
/*      */ 
/*  566 */     int folderIndex = path.lastIndexOf("/");
/*  567 */     if (folderIndex > extIndex) {
/*  568 */       return null;
/*      */     }
/*      */ 
/*  571 */     return path.substring(extIndex + 1);
/*      */   }
/*      */ 
/*      */   public static String stripFilenameExtension(String path)
/*      */   {
/*  581 */     if (path == null) {
/*  582 */       return null;
/*      */     }
/*      */ 
/*  585 */     int extIndex = path.lastIndexOf(46);
/*  586 */     if (extIndex == -1) {
/*  587 */       return path;
/*      */     }
/*      */ 
/*  590 */     int folderIndex = path.lastIndexOf("/");
/*  591 */     if (folderIndex > extIndex) {
/*  592 */       return path;
/*      */     }
/*      */ 
/*  595 */     return path.substring(0, extIndex);
/*      */   }
/*      */ 
/*      */   public static String applyRelativePath(String path, String relativePath)
/*      */   {
/*  607 */     int separatorIndex = path.lastIndexOf("/");
/*  608 */     if (separatorIndex != -1) {
/*  609 */       String newPath = path.substring(0, separatorIndex);
/*  610 */       if (!relativePath.startsWith("/")) {
/*  611 */         newPath = new StringBuilder().append(newPath).append("/").toString();
/*      */       }
/*  613 */       return new StringBuilder().append(newPath).append(relativePath).toString();
/*      */     }
/*      */ 
/*  616 */     return relativePath;
/*      */   }
/*      */ 
/*      */   public static String cleanPath(String path)
/*      */   {
/*  629 */     if (path == null) {
/*  630 */       return null;
/*      */     }
/*  632 */     String pathToUse = replace(path, "\\", "/");
/*      */ 
/*  638 */     int prefixIndex = pathToUse.indexOf(":");
/*  639 */     String prefix = "";
/*  640 */     if (prefixIndex != -1) {
/*  641 */       prefix = pathToUse.substring(0, prefixIndex + 1);
/*  642 */       if (prefix.contains("/")) {
/*  643 */         prefix = "";
/*      */       }
/*      */       else {
/*  646 */         pathToUse = pathToUse.substring(prefixIndex + 1);
/*      */       }
/*      */     }
/*  649 */     if (pathToUse.startsWith("/")) {
/*  650 */       prefix = new StringBuilder().append(prefix).append("/").toString();
/*  651 */       pathToUse = pathToUse.substring(1);
/*      */     }
/*      */ 
/*  654 */     String[] pathArray = delimitedListToStringArray(pathToUse, "/");
/*  655 */     List pathElements = new LinkedList();
/*  656 */     int tops = 0;
/*      */ 
/*  658 */     for (int i = pathArray.length - 1; i >= 0; i--) {
/*  659 */       String element = pathArray[i];
/*  660 */       if (".".equals(element)) {
/*      */         continue;
/*      */       }
/*  663 */       if ("..".equals(element))
/*      */       {
/*  665 */         tops++;
/*      */       }
/*  668 */       else if (tops > 0)
/*      */       {
/*  670 */         tops--;
/*      */       }
/*      */       else
/*      */       {
/*  674 */         pathElements.add(0, element);
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/*  680 */     for (int i = 0; i < tops; i++) {
/*  681 */       pathElements.add(0, "..");
/*      */     }
/*      */ 
/*  684 */     return new StringBuilder().append(prefix).append(collectionToDelimitedString(pathElements, "/")).toString();
/*      */   }
/*      */ 
/*      */   public static boolean pathEquals(String path1, String path2)
/*      */   {
/*  694 */     return cleanPath(path1).equals(cleanPath(path2));
/*      */   }
/*      */ 
/*      */   public static Locale parseLocaleString(String localeString)
/*      */   {
/*  707 */     String[] parts = tokenizeToStringArray(localeString, "_ ", false, false);
/*  708 */     String language = parts.length > 0 ? parts[0] : "";
/*  709 */     String country = parts.length > 1 ? parts[1] : "";
/*      */ 
/*  711 */     validateLocalePart(language);
/*  712 */     validateLocalePart(country);
/*      */ 
/*  714 */     String variant = "";
/*  715 */     if (parts.length > 2)
/*      */     {
/*  718 */       int endIndexOfCountryCode = localeString.indexOf(country, language.length()) + country.length();
/*      */ 
/*  720 */       variant = trimLeadingWhitespace(localeString.substring(endIndexOfCountryCode));
/*  721 */       if (variant.startsWith("_")) {
/*  722 */         variant = trimLeadingCharacter(variant, '_');
/*      */       }
/*      */     }
/*  725 */     return language.length() > 0 ? new Locale(language, country, variant) : null;
/*      */   }
/*      */ 
/*      */   private static void validateLocalePart(String localePart) {
/*  729 */     for (int i = 0; i < localePart.length(); i++) {
/*  730 */       char ch = localePart.charAt(i);
/*  731 */       if ((ch != ' ') && (ch != '_') && (ch != '#') && (!Character.isLetterOrDigit(ch)))
/*  732 */         throw new IllegalArgumentException(new StringBuilder().append("Locale part \"").append(localePart).append("\" contains invalid characters").toString());
/*      */     }
/*      */   }
/*      */ 
/*      */   public static String toLanguageTag(Locale locale)
/*      */   {
/*  745 */     return new StringBuilder().append(locale.getLanguage()).append(hasText(locale.getCountry()) ? new StringBuilder().append("-").append(locale.getCountry()).toString() : "").toString();
/*      */   }
/*      */ 
/*      */   public static TimeZone parseTimeZoneString(String timeZoneString)
/*      */   {
/*  756 */     TimeZone timeZone = TimeZone.getTimeZone(timeZoneString);
/*  757 */     if (("GMT".equals(timeZone.getID())) && (!timeZoneString.startsWith("GMT")))
/*      */     {
/*  759 */       throw new IllegalArgumentException(new StringBuilder().append("Invalid time zone specification '").append(timeZoneString).append("'").toString());
/*      */     }
/*  761 */     return timeZone;
/*      */   }
/*      */ 
/*      */   public static String[] addStringToArray(String[] array, String str)
/*      */   {
/*  778 */     if (ObjectUtils.isEmpty(array)) {
/*  779 */       return new String[] { str };
/*      */     }
/*      */ 
/*  782 */     String[] newArr = new String[array.length + 1];
/*  783 */     System.arraycopy(array, 0, newArr, 0, array.length);
/*  784 */     newArr[array.length] = str;
/*  785 */     return newArr;
/*      */   }
/*      */ 
/*      */   public static String[] concatenateStringArrays(String[] array1, String[] array2)
/*      */   {
/*  797 */     if (ObjectUtils.isEmpty(array1)) {
/*  798 */       return array2;
/*      */     }
/*  800 */     if (ObjectUtils.isEmpty(array2)) {
/*  801 */       return array1;
/*      */     }
/*      */ 
/*  804 */     String[] newArr = new String[array1.length + array2.length];
/*  805 */     System.arraycopy(array1, 0, newArr, 0, array1.length);
/*  806 */     System.arraycopy(array2, 0, newArr, array1.length, array2.length);
/*  807 */     return newArr;
/*      */   }
/*      */ 
/*      */   public static String[] mergeStringArrays(String[] array1, String[] array2)
/*      */   {
/*  821 */     if (ObjectUtils.isEmpty(array1)) {
/*  822 */       return array2;
/*      */     }
/*  824 */     if (ObjectUtils.isEmpty(array2)) {
/*  825 */       return array1;
/*      */     }
/*      */ 
/*  828 */     List result = new ArrayList();
/*  829 */     result.addAll(Arrays.asList(array1));
/*  830 */     for (String str : array2) {
/*  831 */       if (!result.contains(str)) {
/*  832 */         result.add(str);
/*      */       }
/*      */     }
/*  835 */     return toStringArray(result);
/*      */   }
/*      */ 
/*      */   public static String[] sortStringArray(String[] array)
/*      */   {
/*  844 */     if (ObjectUtils.isEmpty(array)) {
/*  845 */       return new String[0];
/*      */     }
/*      */ 
/*  848 */     Arrays.sort(array);
/*  849 */     return array;
/*      */   }
/*      */ 
/*      */   public static String[] toStringArray(Collection<String> collection)
/*      */   {
/*  859 */     if (collection == null) {
/*  860 */       return null;
/*      */     }
/*      */ 
/*  863 */     return (String[])collection.toArray(new String[collection.size()]);
/*      */   }
/*      */ 
/*      */   public static String[] toStringArray(Enumeration<String> enumeration)
/*      */   {
/*  873 */     if (enumeration == null) {
/*  874 */       return null;
/*      */     }
/*      */ 
/*  877 */     List list = Collections.list(enumeration);
/*  878 */     return (String[])list.toArray(new String[list.size()]);
/*      */   }
/*      */ 
/*      */   public static String[] trimArrayElements(String[] array)
/*      */   {
/*  888 */     if (ObjectUtils.isEmpty(array)) {
/*  889 */       return new String[0];
/*      */     }
/*      */ 
/*  892 */     String[] result = new String[array.length];
/*  893 */     for (int i = 0; i < array.length; i++) {
/*  894 */       String element = array[i];
/*  895 */       result[i] = (element != null ? element.trim() : null);
/*      */     }
/*  897 */     return result;
/*      */   }
/*      */ 
/*      */   public static String[] removeDuplicateStrings(String[] array)
/*      */   {
/*  907 */     if (ObjectUtils.isEmpty(array)) {
/*  908 */       return array;
/*      */     }
/*      */ 
/*  911 */     Set set = new LinkedHashSet();
/*  912 */     for (String element : array) {
/*  913 */       set.add(element);
/*      */     }
/*  915 */     return toStringArray(set);
/*      */   }
/*      */ 
/*      */   public static String[] split(String toSplit, String delimiter)
/*      */   {
/*  928 */     if ((!hasLength(toSplit)) || (!hasLength(delimiter))) {
/*  929 */       return null;
/*      */     }
/*  931 */     int offset = toSplit.indexOf(delimiter);
/*  932 */     if (offset < 0) {
/*  933 */       return null;
/*      */     }
/*      */ 
/*  936 */     String beforeDelimiter = toSplit.substring(0, offset);
/*  937 */     String afterDelimiter = toSplit.substring(offset + delimiter.length());
/*  938 */     return new String[] { beforeDelimiter, afterDelimiter };
/*      */   }
/*      */ 
/*      */   public static Properties splitArrayElementsIntoProperties(String[] array, String delimiter)
/*      */   {
/*  953 */     return splitArrayElementsIntoProperties(array, delimiter, null);
/*      */   }
/*      */ 
/*      */   public static Properties splitArrayElementsIntoProperties(String[] array, String delimiter, String charsToDelete)
/*      */   {
/*  973 */     if (ObjectUtils.isEmpty(array)) {
/*  974 */       return null;
/*      */     }
/*      */ 
/*  977 */     Properties result = new Properties();
/*  978 */     for (String element : array) {
/*  979 */       if (charsToDelete != null) {
/*  980 */         element = deleteAny(element, charsToDelete);
/*      */       }
/*  982 */       String[] splittedElement = split(element, delimiter);
/*  983 */       if (splittedElement == null) {
/*      */         continue;
/*      */       }
/*  986 */       result.setProperty(splittedElement[0].trim(), splittedElement[1].trim());
/*      */     }
/*  988 */     return result;
/*      */   }
/*      */ 
/*      */   public static String[] tokenizeToStringArray(String str, String delimiters)
/*      */   {
/* 1008 */     return tokenizeToStringArray(str, delimiters, true, true);
/*      */   }
/*      */ 
/*      */   public static String[] tokenizeToStringArray(String str, String delimiters, boolean trimTokens, boolean ignoreEmptyTokens)
/*      */   {
/* 1033 */     if (str == null) {
/* 1034 */       return null;
/*      */     }
/*      */ 
/* 1037 */     StringTokenizer st = new StringTokenizer(str, delimiters);
/* 1038 */     List tokens = new ArrayList();
/* 1039 */     while (st.hasMoreTokens()) {
/* 1040 */       String token = st.nextToken();
/* 1041 */       if (trimTokens) {
/* 1042 */         token = token.trim();
/*      */       }
/* 1044 */       if ((!ignoreEmptyTokens) || (token.length() > 0)) {
/* 1045 */         tokens.add(token);
/*      */       }
/*      */     }
/* 1048 */     return toStringArray(tokens);
/*      */   }
/*      */ 
/*      */   public static String[] delimitedListToStringArray(String str, String delimiter)
/*      */   {
/* 1065 */     return delimitedListToStringArray(str, delimiter, null);
/*      */   }
/*      */ 
/*      */   public static String[] delimitedListToStringArray(String str, String delimiter, String charsToDelete)
/*      */   {
/* 1084 */     if (str == null) {
/* 1085 */       return new String[0];
/*      */     }
/* 1087 */     if (delimiter == null) {
/* 1088 */       return new String[] { str };
/*      */     }
/*      */ 
/* 1091 */     List result = new ArrayList();
/* 1092 */     if ("".equals(delimiter)) {
/* 1093 */       for (int i = 0; i < str.length(); i++)
/* 1094 */         result.add(deleteAny(str.substring(i, i + 1), charsToDelete));
/*      */     }
/*      */     else
/*      */     {
/* 1098 */       int pos = 0;
/*      */       int delPos;
/* 1100 */       while ((delPos = str.indexOf(delimiter, pos)) != -1) {
/* 1101 */         result.add(deleteAny(str.substring(pos, delPos), charsToDelete));
/* 1102 */         pos = delPos + delimiter.length();
/*      */       }
/* 1104 */       if ((str.length() > 0) && (pos <= str.length()))
/*      */       {
/* 1106 */         result.add(deleteAny(str.substring(pos), charsToDelete));
/*      */       }
/*      */     }
/* 1109 */     return toStringArray(result);
/*      */   }
/*      */ 
/*      */   public static String[] commaDelimitedListToStringArray(String str)
/*      */   {
/* 1119 */     return delimitedListToStringArray(str, ",");
/*      */   }
/*      */ 
/*      */   public static Set<String> commaDelimitedListToSet(String str)
/*      */   {
/* 1131 */     Set set = new LinkedHashSet();
/* 1132 */     String[] tokens = commaDelimitedListToStringArray(str);
/* 1133 */     for (String token : tokens) {
/* 1134 */       set.add(token);
/*      */     }
/* 1136 */     return set;
/*      */   }
/*      */ 
/*      */   public static String collectionToDelimitedString(Collection<?> coll, String delim, String prefix, String suffix)
/*      */   {
/* 1149 */     if (CollectionUtils.isEmpty(coll)) {
/* 1150 */       return "";
/*      */     }
/*      */ 
/* 1153 */     StringBuilder sb = new StringBuilder();
/* 1154 */     Iterator it = coll.iterator();
/* 1155 */     while (it.hasNext()) {
/* 1156 */       sb.append(prefix).append(it.next()).append(suffix);
/* 1157 */       if (it.hasNext()) {
/* 1158 */         sb.append(delim);
/*      */       }
/*      */     }
/* 1161 */     return sb.toString();
/*      */   }
/*      */ 
/*      */   public static String collectionToDelimitedString(Collection<?> coll, String delim)
/*      */   {
/* 1172 */     return collectionToDelimitedString(coll, delim, "", "");
/*      */   }
/*      */ 
/*      */   public static String collectionToCommaDelimitedString(Collection<?> coll)
/*      */   {
/* 1182 */     return collectionToDelimitedString(coll, ",");
/*      */   }
/*      */ 
/*      */   public static String arrayToDelimitedString(Object[] arr, String delim)
/*      */   {
/* 1193 */     if (ObjectUtils.isEmpty(arr)) {
/* 1194 */       return "";
/*      */     }
/* 1196 */     if (arr.length == 1) {
/* 1197 */       return ObjectUtils.nullSafeToString(arr[0]);
/*      */     }
/*      */ 
/* 1200 */     StringBuilder sb = new StringBuilder();
/* 1201 */     for (int i = 0; i < arr.length; i++) {
/* 1202 */       if (i > 0) {
/* 1203 */         sb.append(delim);
/*      */       }
/* 1205 */       sb.append(arr[i]);
/*      */     }
/* 1207 */     return sb.toString();
/*      */   }
/*      */ 
/*      */   public static String arrayToCommaDelimitedString(Object[] arr)
/*      */   {
/* 1218 */     return arrayToDelimitedString(arr, ",");
/*      */   }
/*      */ }

/* Location:           C:\Users\Dell\Desktop\easyexcel-1.1.2-beta5.jar
 * Qualified Name:     com.alibaba.excel.util.StringUtils
 * JD-Core Version:    0.6.0
 */