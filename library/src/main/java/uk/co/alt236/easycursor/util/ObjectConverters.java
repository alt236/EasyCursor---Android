package uk.co.alt236.easycursor.util;

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;

public class ObjectConverters {
    private static final String UTF_8 = "UTF-8";

    public static boolean toBoolean(Object obj) {
        if (obj instanceof Boolean) {
            return ((Boolean) obj).booleanValue();
        } else if (obj instanceof String) {
            return Boolean.valueOf(String.valueOf(obj)).booleanValue();
        } else {
            throw new ClassCastException("Unable to convert " + obj.getClass().getName() + " to boolean");
        }
    }

    public static byte[] toByteArray(Object obj) {
        if (obj == null) {
            return null;
        }

        if (obj instanceof byte[]) {
            return (byte[]) obj;
        } else if (obj instanceof String) {
            return ((String) obj).getBytes();
        } else if (obj instanceof Integer) {
            return ByteBuffer.allocate(4).putInt((Integer) obj).array();
        } else if (obj instanceof Long) {
            return ByteBuffer.allocate(8).putLong((Long) obj).array();
        } else if (obj instanceof Float) {
            return ByteBuffer.allocate(4).putFloat((Float) obj).array();
        } else if (obj instanceof Double) {
            return ByteBuffer.allocate(8).putDouble((Double) obj).array();
        } else if (obj instanceof Short) {
            return ByteBuffer.allocate(2).putShort((Short) obj).array();
        } else {
            throw new ClassCastException("Unable to convert " + obj.getClass().getName() + " to byte[]");
        }
    }

    public static double toDouble(Object obj) {
        if (obj instanceof Number) {
            return ((Number) obj).doubleValue();
        } else if (obj instanceof String) {
            return Double.valueOf(String.valueOf(obj)).doubleValue();
        } else {
            throw new ClassCastException("Unable to convert " + obj.getClass().getName() + " to double");
        }
    }

    public static float toFloat(Object obj) {
        if (obj instanceof Number) {
            return ((Number) obj).floatValue();
        } else if (obj instanceof String) {
            return Float.valueOf(String.valueOf(obj)).floatValue();
        } else {
            throw new ClassCastException("Unable to convert " + obj.getClass().getName() + " to float");
        }
    }

    public static int toInt(Object obj) {
        if (obj instanceof Number) {
            return ((Number) obj).intValue();
        } else if (obj instanceof String) {
            return Integer.valueOf(String.valueOf(obj)).intValue();
        } else {
            throw new ClassCastException("Unable to convert " + obj.getClass().getName() + " to int");
        }
    }


    public static long toLong(Object obj) {
        if (obj instanceof Number) {
            return ((Number) obj).longValue();
        } else if (obj instanceof String) {
            return Short.valueOf(String.valueOf(obj)).longValue();
        } else {
            throw new ClassCastException("Unable to convert " + obj.getClass().getName() + " to long");
        }
    }

    public static short toShort(Object obj) {
        if (obj instanceof Number) {
            return ((Number) obj).shortValue();
        } else if (obj instanceof String) {
            return Short.valueOf(String.valueOf(obj)).shortValue();
        } else {
            throw new ClassCastException("Unable to convert " + obj.getClass().getName() + " to short");
        }
    }

    public static String toString(Object obj) {
        if (obj instanceof byte[]) {
            try {
                return new String((byte[]) obj, UTF_8);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
                return new String((byte[]) obj);
            }
        } else {
            return String.valueOf(obj);
        }
    }
}
