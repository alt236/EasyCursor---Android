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

package dev.alt236.easycursor.internal.conversion;

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import dev.alt236.easycursor.exceptions.ConversionErrorException;

public final class ObjectConverter {
    private static final int INT_BYTE_SIZE = 4;
    private static final int LONG_BYTE_SIZE = 8;
    private static final int FLOAT_BYTE_SIZE = 4;
    private static final int DOUBLE_BYTE_SIZE = 8;
    private static final int SHORT_BYTE_SIZE = 2;

    private static final String UTF_8 = "UTF-8";

    private final String mEncoding;
    private final ByteOrder mByteOrder;

    public ObjectConverter() {
        this(UTF_8, ByteOrder.BIG_ENDIAN);
    }

    public ObjectConverter(final String encoding, final ByteOrder byteOrder) {
        mEncoding = encoding;
        mByteOrder = byteOrder;
    }

    private ByteBuffer getByteBuffer(final int size) {
        return ByteBuffer.allocate(size).order(mByteOrder);
    }

    private byte[] getBytes(final String string) {
        try {
            return string.getBytes(mEncoding);
        } catch (final UnsupportedEncodingException e) {
            throw new ConversionErrorException(e);
        }
    }

    private String getString(final byte[] array) {
        try {
            return new String(array, mEncoding);
        } catch (final UnsupportedEncodingException e) {
            throw new ConversionErrorException(e);
        }
    }

    private boolean toBoolean(final Object obj) {
        if (obj instanceof Boolean) {
            return (Boolean) obj;
        } else if (obj instanceof String) {
            return Boolean.valueOf(String.valueOf(obj));
        } else {
            throw new ConversionErrorException("Unable to convert '" + getClassName(obj) + "' to boolean");
        }
    }

    private byte[] toByteArray(final Object obj) {
        if (obj == null) {
            return null;
        }

        if (obj instanceof byte[]) {
            return (byte[]) obj;
        } else if (obj instanceof String) {
            return getBytes((String) obj);
        } else if (obj instanceof Integer) {
            return getByteBuffer(INT_BYTE_SIZE).putInt((Integer) obj).array();
        } else if (obj instanceof Long) {
            return getByteBuffer(LONG_BYTE_SIZE).putLong((Long) obj).array();
        } else if (obj instanceof Float) {
            return getByteBuffer(FLOAT_BYTE_SIZE).putFloat((Float) obj).array();
        } else if (obj instanceof Double) {
            return getByteBuffer(DOUBLE_BYTE_SIZE).putDouble((Double) obj).array();
        } else if (obj instanceof Short) {
            return getByteBuffer(SHORT_BYTE_SIZE).putShort((Short) obj).array();
        } else {
            throw new ConversionErrorException("Unable to convert '" + getClassName(obj) + "' to byte[]");
        }
    }

    private double toDouble(final Object obj) {
        if (obj instanceof Number) {
            return ((Number) obj).doubleValue();
        } else if (obj instanceof String) {
            try {
                return Double.valueOf(String.valueOf(obj));
            } catch (final NumberFormatException e) {
                throw new ConversionErrorException(e);
            }
        } else if (obj instanceof byte[] && ((byte[]) obj).length == DOUBLE_BYTE_SIZE) {
            final ByteBuffer buffer = getByteBuffer(DOUBLE_BYTE_SIZE).put((byte[]) obj);
            buffer.flip();
            return buffer.getDouble();
        } else {
            throw new ConversionErrorException("Unable to convert '" + getClassName(obj) + "' to double");
        }
    }

    private float toFloat(final Object obj) {
        if (obj instanceof Number) {
            return ((Number) obj).floatValue();
        } else if (obj instanceof String) {
            try {
                return Float.valueOf(String.valueOf(obj));
            } catch (final NumberFormatException e) {
                throw new ConversionErrorException(e);
            }
        } else if (obj instanceof byte[] && ((byte[]) obj).length == FLOAT_BYTE_SIZE) {
            final ByteBuffer buffer = getByteBuffer(FLOAT_BYTE_SIZE).put((byte[]) obj);
            buffer.flip();
            return buffer.getFloat();
        } else {
            throw new ConversionErrorException("Unable to convert '" + getClassName(obj) + "' to float");
        }
    }

    private int toInt(final Object obj) {
        if (obj instanceof Number) {
            return ((Number) obj).intValue();
        } else if (obj instanceof String) {
            try {
                return Integer.valueOf(String.valueOf(obj));
            } catch (final NumberFormatException e) {
                throw new ConversionErrorException(e);
            }
        } else if (obj instanceof byte[] && ((byte[]) obj).length == INT_BYTE_SIZE) {
            final ByteBuffer buffer = getByteBuffer(INT_BYTE_SIZE).put((byte[]) obj);
            buffer.flip();
            return buffer.getInt();
        } else {
            throw new ConversionErrorException("Unable to convert '" + getClassName(obj) + "' to int");
        }
    }

    private long toLong(final Object obj) {
        if (obj instanceof Number) {
            return ((Number) obj).longValue();
        } else if (obj instanceof String) {
            try {
                return Long.valueOf(String.valueOf(obj));
            } catch (final NumberFormatException e) {
                throw new ConversionErrorException(e);
            }
        } else if (obj instanceof byte[] && ((byte[]) obj).length == LONG_BYTE_SIZE) {
            final ByteBuffer buffer = getByteBuffer(LONG_BYTE_SIZE).put((byte[]) obj);
            buffer.flip();
            return buffer.getLong();
        } else {
            throw new ConversionErrorException("Unable to convert '" + getClassName(obj) + "' to long");
        }
    }

    private short toShort(final Object obj) {
        if (obj instanceof Number) {
            return ((Number) obj).shortValue();
        } else if (obj instanceof String) {
            try {
                return Short.valueOf(String.valueOf(obj));
            } catch (final NumberFormatException e) {
                throw new ConversionErrorException(e);
            }
        } else if (obj instanceof byte[] && ((byte[]) obj).length == SHORT_BYTE_SIZE) {
            final ByteBuffer buffer = getByteBuffer(SHORT_BYTE_SIZE).put((byte[]) obj);
            buffer.flip();
            return buffer.getShort();
        } else {
            throw new ConversionErrorException("Unable to convert '" + getClassName(obj) + "' to short");
        }
    }

    private String toString(final Object obj) {
        if (obj == null) {
            return null;
        }

        if (obj instanceof String) {
            return (String) obj;
        } else if (obj instanceof byte[]) {
            return getString((byte[]) obj);
        } else {
            return String.valueOf(obj);
        }
    }

    public Object toType(final ObjectType type, final Object candidate) {
        switch (type) {
            case BOOLEAN:
                return toBoolean(candidate);
            case BYTE_ARRAY:
                return toByteArray(candidate);
            case DOUBLE:
                return toDouble(candidate);
            case FLOAT:
                return toFloat(candidate);
            case INTEGER:
                return toInt(candidate);
            case LONG:
                return toLong(candidate);
            case SHORT:
                return toShort(candidate);
            case STRING:
                return toString(candidate);
            default:
                throw new IllegalStateException("Unknown ObjectType: " + type);
        }
    }

    private static String getClassName(final Object obj) {
        if (obj == null) {
            return "<null>";
        } else {
            return obj.getClass().getName();
        }
    }
}
