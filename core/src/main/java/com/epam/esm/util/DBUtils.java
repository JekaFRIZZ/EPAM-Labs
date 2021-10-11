package com.epam.esm.util;

import java.util.Map;

public class DBUtils {

    private static final String ORDER_BY = " ORDER BY ";
    private static final String ASC = " ASC";
    private static final String DESC = " DESC";
    private static final String EQUEL = " = ";
    private static final String SINGLE_QUOT = "'";
    private static final String COMMA = ",";
    private static final String WHERE = " WHERE ";
    private static final String ID = "id";

    public static String constructQueryForSorting(String query, String fieldName, boolean isASC) {
        StringBuilder stringBuilder = new StringBuilder(query).
                append(ORDER_BY).
                append(fieldName);

        return isASC ? stringBuilder.append(ASC).toString() : stringBuilder.append(DESC).toString();
    }

    public static String constructQueryForUpdate(String query, Integer id, Map<String, String> fieldForUpdate) {
        StringBuilder builder = new StringBuilder(query);

        for(Map.Entry<String, String> entry : fieldForUpdate.entrySet()) {
            String fieldName = entry.getKey();
            String fieldValue = entry.getValue();

            if(fieldName != null) {
                builder
                        .append(fieldName)
                        .append(EQUEL)
                        .append(SINGLE_QUOT)
                        .append(fieldValue)
                        .append(SINGLE_QUOT)
                        .append(COMMA);
            }
        }

        int lastCommaIndex = builder.length() - 1;
        builder.deleteCharAt(lastCommaIndex);

        builder.append(WHERE).append(ID).append(EQUEL).append(id);

        return builder.toString();
    }
}
