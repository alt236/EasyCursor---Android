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

package uk.co.alt236.easycursor.internal.conversion;

import junit.framework.TestCase;

import java.nio.charset.Charset;
import java.util.Arrays;

import uk.co.alt236.easycursor.exceptions.ConversionErrorException;
import uk.co.alt236.easycursor.objectcursor.TestObject;

/**
 *
 */
public class ObjectConverterTest extends TestCase {
    private final ObjectConverter mObjectConverter = new ObjectConverter();

    public void testDoubleByteArrayConversion() throws Exception {
        final double expectedValue1 = Double.MAX_VALUE;
        final double expectedValue2 = Double.MIN_VALUE;
        final double expectedValue3 = Double.NaN;
        final double expectedValue4 = Double.NEGATIVE_INFINITY;
        final double expectedValue5 = Double.POSITIVE_INFINITY;

        final byte[] byteArray1 = (byte[]) mObjectConverter.toType(ObjectType.BYTE_ARRAY, expectedValue1);
        final byte[] byteArray2 = (byte[]) mObjectConverter.toType(ObjectType.BYTE_ARRAY, expectedValue2);
        final byte[] byteArray3 = (byte[]) mObjectConverter.toType(ObjectType.BYTE_ARRAY, expectedValue3);
        final byte[] byteArray4 = (byte[]) mObjectConverter.toType(ObjectType.BYTE_ARRAY, expectedValue4);
        final byte[] byteArray5 = (byte[]) mObjectConverter.toType(ObjectType.BYTE_ARRAY, expectedValue5);

        final double returnValue1 = (double) mObjectConverter.toType(ObjectType.DOUBLE, byteArray1);
        final double returnValue2 = (double) mObjectConverter.toType(ObjectType.DOUBLE, byteArray2);
        final double returnValue3 = (double) mObjectConverter.toType(ObjectType.DOUBLE, byteArray3);
        final double returnValue4 = (double) mObjectConverter.toType(ObjectType.DOUBLE, byteArray4);
        final double returnValue5 = (double) mObjectConverter.toType(ObjectType.DOUBLE, byteArray5);

        assertEquals(expectedValue1, returnValue1);
        assertEquals(expectedValue2, returnValue2);
        assertEquals(expectedValue3, returnValue3);
        assertEquals(expectedValue4, returnValue4);
        assertEquals(expectedValue5, returnValue5);
    }

    public void testFloatByteArrayConversion() throws Exception {
        final float expectedValue1 = Float.MAX_VALUE;
        final float expectedValue2 = Float.MIN_VALUE;
        final float expectedValue3 = Float.NaN;
        final float expectedValue4 = Float.NEGATIVE_INFINITY;
        final float expectedValue5 = Float.POSITIVE_INFINITY;

        final byte[] byteArray1 = (byte[]) mObjectConverter.toType(ObjectType.BYTE_ARRAY, expectedValue1);
        final byte[] byteArray2 = (byte[]) mObjectConverter.toType(ObjectType.BYTE_ARRAY, expectedValue2);
        final byte[] byteArray3 = (byte[]) mObjectConverter.toType(ObjectType.BYTE_ARRAY, expectedValue3);
        final byte[] byteArray4 = (byte[]) mObjectConverter.toType(ObjectType.BYTE_ARRAY, expectedValue4);
        final byte[] byteArray5 = (byte[]) mObjectConverter.toType(ObjectType.BYTE_ARRAY, expectedValue5);

        final float returnValue1 = (float) mObjectConverter.toType(ObjectType.FLOAT, byteArray1);
        final float returnValue2 = (float) mObjectConverter.toType(ObjectType.FLOAT, byteArray2);
        final float returnValue3 = (float) mObjectConverter.toType(ObjectType.FLOAT, byteArray3);
        final float returnValue4 = (float) mObjectConverter.toType(ObjectType.FLOAT, byteArray4);
        final float returnValue5 = (float) mObjectConverter.toType(ObjectType.FLOAT, byteArray5);

        assertEquals(expectedValue1, returnValue1);
        assertEquals(expectedValue2, returnValue2);
        assertEquals(expectedValue3, returnValue3);
        assertEquals(expectedValue4, returnValue4);
        assertEquals(expectedValue5, returnValue5);
    }

    public void testIntByteArrayConversion() throws Exception {
        final int expectedValue1 = Integer.MAX_VALUE;
        final int expectedValue2 = Integer.MIN_VALUE;

        final byte[] byteArray1 = (byte[]) mObjectConverter.toType(ObjectType.BYTE_ARRAY, expectedValue1);
        final byte[] byteArray2 = (byte[]) mObjectConverter.toType(ObjectType.BYTE_ARRAY, expectedValue2);

        final int returnValue1 = (int) mObjectConverter.toType(ObjectType.INTEGER, byteArray1);
        final int returnValue2 = (int) mObjectConverter.toType(ObjectType.INTEGER, byteArray2);

        assertEquals(expectedValue1, returnValue1);
        assertEquals(expectedValue2, returnValue2);
    }

    public void testLongByteArrayConversion() throws Exception {
        final long expectedValue1 = Long.MAX_VALUE;
        final long expectedValue2 = Long.MIN_VALUE;

        final byte[] byteArray1 = (byte[]) mObjectConverter.toType(ObjectType.BYTE_ARRAY, expectedValue1);
        final byte[] byteArray2 = (byte[]) mObjectConverter.toType(ObjectType.BYTE_ARRAY, expectedValue2);

        final long returnValue1 = (long) mObjectConverter.toType(ObjectType.LONG, byteArray1);
        final long returnValue2 = (long) mObjectConverter.toType(ObjectType.LONG, byteArray2);

        assertEquals(expectedValue1, returnValue1);
        assertEquals(expectedValue2, returnValue2);
    }

    public void testShortByteArrayConversion() throws Exception {
        final short expectedValue1 = Short.MAX_VALUE;
        final short expectedValue2 = Short.MIN_VALUE;

        final byte[] byteArray1 = (byte[]) mObjectConverter.toType(ObjectType.BYTE_ARRAY, expectedValue1);
        final byte[] byteArray2 = (byte[]) mObjectConverter.toType(ObjectType.BYTE_ARRAY, expectedValue2);

        final short returnValue1 = (short) mObjectConverter.toType(ObjectType.SHORT, byteArray1);
        final short returnValue2 = (short) mObjectConverter.toType(ObjectType.SHORT, byteArray2);

        assertEquals(expectedValue1, returnValue1);
        assertEquals(expectedValue2, returnValue2);
    }

    public void testToBoolean() throws Exception {
        assertTrue((boolean) mObjectConverter.toType(ObjectType.BOOLEAN, true));
        assertTrue((boolean) mObjectConverter.toType(ObjectType.BOOLEAN, "true"));
        assertTrue((boolean) mObjectConverter.toType(ObjectType.BOOLEAN, "TruE"));
        assertFalse((boolean) mObjectConverter.toType(ObjectType.BOOLEAN, false));
        assertFalse((boolean) mObjectConverter.toType(ObjectType.BOOLEAN, "false"));
        assertFalse((boolean) mObjectConverter.toType(ObjectType.BOOLEAN, "FalsE"));

        try {
            assertFalse((boolean) mObjectConverter.toType(ObjectType.BOOLEAN, 1));
            fail("this should have blown");
        } catch (final ConversionErrorException e) {
            // Expected
        }

        try {
            assertFalse((boolean) mObjectConverter.toType(ObjectType.BOOLEAN, null));
            fail("this should have blown");
        } catch (final ConversionErrorException e) {
            // Expected
        }
    }

    public void testToByteArray() throws Exception {
        assertNull(mObjectConverter.toType(ObjectType.BYTE_ARRAY, null));

        final byte[] array = new byte[]{1, 2, 3, Byte.MAX_VALUE, Byte.MIN_VALUE};
        assertTrue(Arrays.equals(
                array,
                (byte[]) mObjectConverter.toType(ObjectType.BYTE_ARRAY, array)));

        assertTrue(Arrays.equals(
                new byte[]{116, 101, 115, 116},
                (byte[]) mObjectConverter.toType(ObjectType.BYTE_ARRAY, "test")));

        try {
            mObjectConverter.toType(ObjectType.BYTE_ARRAY, new TestObject(1));
            fail("this should have blown");
        } catch (final ConversionErrorException e) {
            // Expected
        }
    }

    public void testToDouble() throws Exception {
        assertEquals(1.0D, mObjectConverter.toType(ObjectType.DOUBLE, 1.0F));
        assertEquals(1.0D, mObjectConverter.toType(ObjectType.DOUBLE, 1.0D));
        assertEquals(1.0D, mObjectConverter.toType(ObjectType.DOUBLE, 1L));
        assertEquals(1.0D, mObjectConverter.toType(ObjectType.DOUBLE, 1));
        assertEquals(1.0D, mObjectConverter.toType(ObjectType.DOUBLE, "1"));
        assertEquals(1.0D, mObjectConverter.toType(ObjectType.DOUBLE, "1.0"));

        try {
            mObjectConverter.toType(ObjectType.DOUBLE, false);
            fail("this should have blown");
        } catch (final ConversionErrorException e) {
            // Expected
        }

        try {
            mObjectConverter.toType(ObjectType.DOUBLE, null);
            fail("this should have blown");
        } catch (final ConversionErrorException e) {
            // Expected
        }
    }

    public void testToFloat() throws Exception {
        assertEquals(1.0F, mObjectConverter.toType(ObjectType.FLOAT, 1.0D));
        assertEquals(1.0F, mObjectConverter.toType(ObjectType.FLOAT, 1.0F));
        assertEquals(1.0F, mObjectConverter.toType(ObjectType.FLOAT, 1L));
        assertEquals(1.0F, mObjectConverter.toType(ObjectType.FLOAT, 1));
        assertEquals(1.0F, mObjectConverter.toType(ObjectType.FLOAT, "1"));
        assertEquals(1.0F, mObjectConverter.toType(ObjectType.FLOAT, "1.0"));

        try {
            mObjectConverter.toType(ObjectType.FLOAT, false);
            fail("this should have blown");
        } catch (final ConversionErrorException e) {
            // Expected
        }

        try {
            mObjectConverter.toType(ObjectType.FLOAT, null);
            fail("this should have blown");
        } catch (final ConversionErrorException e) {
            // Expected
        }
    }

    public void testToInt() throws Exception {
        assertEquals(1, mObjectConverter.toType(ObjectType.INTEGER, 1.0D));
        assertEquals(1, mObjectConverter.toType(ObjectType.INTEGER, 1.0F));
        assertEquals(1, mObjectConverter.toType(ObjectType.INTEGER, 1L));
        assertEquals(1, mObjectConverter.toType(ObjectType.INTEGER, 1));
        assertEquals(1, mObjectConverter.toType(ObjectType.INTEGER, "1"));

        try {
            mObjectConverter.toType(ObjectType.INTEGER, false);
            fail("this should have blown");
        } catch (final ConversionErrorException e) {
            // Expected
        }

        try {
            mObjectConverter.toType(ObjectType.INTEGER, null);
            fail("this should have blown");
        } catch (final ConversionErrorException e) {
            // Expected
        }
    }

    public void testToLong() throws Exception {
        assertEquals(1L, mObjectConverter.toType(ObjectType.LONG, 1.0D));
        assertEquals(1L, mObjectConverter.toType(ObjectType.LONG, 1.0F));
        assertEquals(1L, mObjectConverter.toType(ObjectType.LONG, 1L));
        assertEquals(1L, mObjectConverter.toType(ObjectType.LONG, 1));
        assertEquals(1L, mObjectConverter.toType(ObjectType.LONG, "1"));

        try {
            mObjectConverter.toType(ObjectType.LONG, false);
            fail("this should have blown");
        } catch (final ConversionErrorException e) {
            // Expected
        }

        try {
            mObjectConverter.toType(ObjectType.LONG, null);
            fail("this should have blown");
        } catch (final ConversionErrorException e) {
            // Expected
        }
    }

    public void testToShort() throws Exception {
        assertEquals((short) 1, mObjectConverter.toType(ObjectType.SHORT, 1.0D));
        assertEquals((short) 1, mObjectConverter.toType(ObjectType.SHORT, 1.0F));
        assertEquals((short) 1, mObjectConverter.toType(ObjectType.SHORT, 1L));
        assertEquals((short) 1, mObjectConverter.toType(ObjectType.SHORT, 1));
        assertEquals((short) 1, mObjectConverter.toType(ObjectType.SHORT, "1"));

        try {
            mObjectConverter.toType(ObjectType.SHORT, false);
            fail("this should have blown");
        } catch (final ConversionErrorException e) {
            // Expected
        }

        try {
            mObjectConverter.toType(ObjectType.SHORT, null);
            fail("this should have blown");
        } catch (final ConversionErrorException e) {
            // Expected
        }
    }

    public void testToString() throws Exception {
        assertEquals("foo", mObjectConverter.toType(ObjectType.STRING, "foo"));
        assertEquals("foo", mObjectConverter.toType(ObjectType.STRING, "foo".getBytes(Charset.forName("UTF-8"))));
        assertEquals(null, mObjectConverter.toType(ObjectType.STRING, null));
        assertEquals("1", mObjectConverter.toType(ObjectType.STRING, 1));
        assertEquals("1.0", mObjectConverter.toType(ObjectType.STRING, 1.0));
        assertEquals("true", mObjectConverter.toType(ObjectType.STRING, true));
        assertEquals("false", mObjectConverter.toType(ObjectType.STRING, false));
    }
}