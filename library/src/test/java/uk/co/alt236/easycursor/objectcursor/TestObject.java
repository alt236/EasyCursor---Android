/*
 * ***************************************************************************
 * Copyright 2015 Alexandros Schillings
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * ***************************************************************************
 *
 */

package uk.co.alt236.easycursor.objectcursor;

import java.util.Random;
import java.util.UUID;

public class TestObject {
    private final String mString;
    private final Boolean mBool;
    private final byte[] mByte;
    private final Double mDouble;
    private final Float mFloat;
    private final Integer mInt;
    private final Long mLong;
    private final Short mShort;
    private final Object mNull = null;

    public TestObject(final long seed) {
        final Random random = new Random(seed);

        mString = UUID.randomUUID().toString();
        mByte = mString.getBytes();
        mBool = random.nextBoolean();
        mLong = random.nextLong();
        mInt = random.nextInt();
        mDouble = random.nextDouble();
        mFloat = random.nextFloat();
        mShort = (short) random.nextInt(Short.MAX_VALUE + 1);
    }

    public TestObject(final String mString,
                      final Boolean mBool,
                      final byte[] mByte,
                      final Double mDouble,
                      final Float mFloat,
                      final Integer mInt,
                      final Long mLong,
                      final Short mShort) {

        this.mString = mString;
        this.mBool = mBool;
        this.mByte = mByte;
        this.mDouble = mDouble;
        this.mFloat = mFloat;
        this.mInt = mInt;
        this.mLong = mLong;
        this.mShort = mShort;
    }

    public byte[] getByte() {
        return mByte;
    }

    public Double getDouble() {
        return mDouble;
    }

    public Float getFloat() {
        return mFloat;
    }

    public Integer getInt() {
        return mInt;
    }

    public Long getLong() {
        return mLong;
    }

    public Object getNull() {
        return mNull;
    }

    public Short getShort() {
        return mShort;
    }

    public String getString() {
        return mString;
    }

    public Boolean isBool() {
        return mBool;
    }

    public static class Builder {
        private String mString;
        private Boolean mBool;
        private byte[] mByte;
        private Double mDouble;
        private Float mFloat;
        private Integer mInt;
        private Long mLong;
        private Short mShort;

        public Builder() {
        }

        public TestObject build() {
            return new TestObject(mString, mBool, mByte, mDouble, mFloat, mInt, mLong, mShort);
        }

        public Builder withBool(final Boolean mBool) {
            this.mBool = mBool;
            return this;
        }

        public Builder withByte(final byte[] mByte) {
            this.mByte = mByte;
            return this;
        }

        public Builder withDouble(final Double mDouble) {
            this.mDouble = mDouble;
            return this;
        }

        public Builder withFloat(final Float mFloat) {
            this.mFloat = mFloat;
            return this;
        }

        public Builder withInt(final Integer mInt) {
            this.mInt = mInt;
            return this;
        }

        public Builder withLong(final Long mLong) {
            this.mLong = mLong;
            return this;
        }

        public Builder withShort(final Short mShort) {
            this.mShort = mShort;
            return this;
        }

        public Builder withString(final String mString) {
            this.mString = mString;
            return this;
        }
    }
}
