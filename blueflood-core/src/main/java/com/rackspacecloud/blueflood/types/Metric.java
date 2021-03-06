/*
 * Copyright 2013 Rackspace
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


import com.rackspacecloud.blueflood.utils.TimeValue;

public class Metric implements IMetric {
    private final Locator locator;
    private final Object metricValue;
    private final long collectionTime;
    private int ttlInSeconds;
    private final DataType dataType;
    private final String unit;

    public Metric(Locator locator, Object metricValue, long collectionTime, TimeValue ttl, String unit) {
        this.locator = locator;
        this.metricValue = metricValue;
        this.collectionTime = collectionTime;
        this.dataType = DataType.getMetricType(metricValue);
        this.unit = unit;

        setTtl(ttl);
    }

    public Locator getLocator() {
        return locator;
    }

    public Object getMetricValue() {
        return metricValue;
    }

    public DataType getDataType() {
        return dataType;
    }

    public int getTtlInSeconds() {
        return ttlInSeconds;
    }

    public long getCollectionTime() {
        return collectionTime;
    }

    public String getUnit() {
        return unit;
    }

    public boolean isNumeric() {
        return DataType.isNumericMetric(metricValue);
    }

    public boolean isString() {
        return DataType.isStringMetric(metricValue);
    }

    public boolean isBoolean() {
        return DataType.isBooleanMetric(metricValue);
    }

    public void setTtl(TimeValue ttl) {
        if (!isValidTTL(ttl.toSeconds())) {
            throw new RuntimeException("TTL supplied for metric is invalid. Required: 0 < ttl < " + Integer.MAX_VALUE +
                    ", provided: " + ttl.toSeconds());
        }

        ttlInSeconds = (int) ttl.toSeconds();
    }

    public void setTtlInSeconds(int ttlInSeconds) {
        if (!isValidTTL(ttlInSeconds)) {
            throw new RuntimeException("TTL supplied for metric is invalid. Required: 0 < ttl < " + Integer.MAX_VALUE +
                    ", provided: " + ttlInSeconds);
        }

        this.ttlInSeconds = ttlInSeconds;
    }
    
    public RollupType getRollupType() {
        return RollupType.BF_BASIC;
    }

    @Override
    public String toString() {
        return String.format("%s:%s:%s:%s:%s", locator.toString(), metricValue, dataType, ttlInSeconds, unit == null ? "" : unit.toString());
    }

    private boolean isValidTTL(long ttlInSeconds) {
        return (ttlInSeconds < Integer.MAX_VALUE && ttlInSeconds > 0);
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Metric)) {
            return false;
        }
        Metric other = (Metric) o;
        if (locator.equals(other.getLocator()) &&
                collectionTime == other.getCollectionTime() &&
                ttlInSeconds == other.getTtlInSeconds() &&
                dataType.equals(other.getDataType()) &&
                unit.equals(other.getUnit())) {
            return true;
        }
        return false;
    }
}
