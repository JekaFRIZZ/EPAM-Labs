package com.epam.esm.util;

public class DBUtils {

    private static final String ORDER_BY = " ORDER BY ";
    private static final String ASC = " ASC";
    private static final String DESC = " DESC";

    public static String constructQueryForSorting(String query, String fieldName, boolean isASC) {
        StringBuilder stringBuilder = new StringBuilder(query).
                append(ORDER_BY).
                append(fieldName);

        return isASC ? stringBuilder.append(ASC).toString() : stringBuilder.append(DESC).toString();
    }
}
