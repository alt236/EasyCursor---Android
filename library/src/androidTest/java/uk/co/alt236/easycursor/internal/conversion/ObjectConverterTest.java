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
        } catch (final ConversionErrorException e){
            // Expected
        }

        try {
            assertFalse((boolean) mObjectConverter.toType(ObjectType.BOOLEAN, null));
            fail("this should have blown");
        } catch (final ConversionErrorException e){
            // Expected
        }
    }

    public void testToByteArray() throws Exception {
        assertNull(mObjectConverter.toType(ObjectType.BYTE_ARRAY, null));

        final byte[] array = new byte[]{1,2,3, Byte.MAX_VALUE, Byte.MIN_VALUE};
        assertTrue(Arrays.equals(
                array,
                (byte[]) mObjectConverter.toType(ObjectType.BYTE_ARRAY, array)));

        assertTrue(Arrays.equals(
                new byte[]{116, 101, 115, 116},
                (byte[]) mObjectConverter.toType(ObjectType.BYTE_ARRAY, "test")));

        try {
            mObjectConverter.toType(ObjectType.BYTE_ARRAY, new TestObject(1));
            fail("this should have blown");
        } catch (final ConversionErrorException e){
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
        } catch (final ConversionErrorException e){
            // Expected
        }

        try {
            mObjectConverter.toType(ObjectType.DOUBLE, null);
            fail("this should have blown");
        } catch (final ConversionErrorException e){
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
        } catch (final ConversionErrorException e){
            // Expected
        }

        try {
            mObjectConverter.toType(ObjectType.FLOAT, null);
            fail("this should have blown");
        } catch (final ConversionErrorException e){
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
        } catch (final ConversionErrorException e){
            // Expected
        }

        try {
            mObjectConverter.toType(ObjectType.INTEGER, null);
            fail("this should have blown");
        } catch (final ConversionErrorException e){
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
        } catch (final ConversionErrorException e){
            // Expected
        }

        try {
            mObjectConverter.toType(ObjectType.LONG, null);
            fail("this should have blown");
        } catch (final ConversionErrorException e){
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
        } catch (final ConversionErrorException e){
            // Expected
        }

        try {
            mObjectConverter.toType(ObjectType.SHORT, null);
            fail("this should have blown");
        } catch (final ConversionErrorException e){
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