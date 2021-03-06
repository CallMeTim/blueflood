/*
 * Copyright 2014 Rackspace
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package com.rackspacecloud.blueflood.types;

public class DataType {
    private final String type;

    // todo: we need to get rid of this and have a static method that returns the singleton instances below.
    public DataType(String type) {
        this.type = type;
    }

    public final static DataType STRING = new DataType("S");
    public final static DataType INT = new DataType("I");
    public final static DataType LONG = new DataType("L");
    public final static DataType DOUBLE = new DataType("D");
    public final static DataType BOOLEAN = new DataType("B");

    public static DataType getMetricType(Object metricValue) {
        if (metricValue instanceof String) {
            return STRING;
        } else if (metricValue instanceof Integer) {
            return INT;
        } else if (metricValue instanceof Long) {
            return LONG;
        } else if (metricValue instanceof Double) {
            return DOUBLE;
        } else if (metricValue instanceof Boolean) {
            return BOOLEAN;
        } else {
            throw new RuntimeException("Unknown metric value type");
        }
    }

    public static boolean isNumericMetric(Object metricValue) {
        final DataType metricType = getMetricType(metricValue);
        return metricType == DataType.INT || metricType == DataType.LONG || metricType == DataType.DOUBLE;
    }

    public static boolean isStringMetric(Object metricValue) {
        final DataType metricType = getMetricType(metricValue);
        return metricType == DataType.STRING;
    }

    public static boolean isBooleanMetric(Object metricValue) {
        final DataType metricType = getMetricType(metricValue);
        return metricType == DataType.BOOLEAN;
    }

    public static boolean isKnownMetricType(DataType incoming) {
        return incoming.equals(STRING) || incoming.equals(INT) || incoming.equals(LONG) || incoming.equals(DOUBLE)
                || incoming.equals(BOOLEAN);
    }

    @Override
    public String toString() {
        return type;
    }

    public boolean equals(DataType other) {
        return type.equals(other.type);
    }
}
