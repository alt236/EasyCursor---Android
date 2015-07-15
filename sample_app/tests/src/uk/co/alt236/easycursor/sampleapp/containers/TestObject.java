package uk.co.alt236.easycursor.sampleapp.containers;

import java.util.Random;
import java.util.UUID;

public class TestObject {
    private final Random random;

    private final String mString;
    private final boolean mBool;
    private final byte[] mByte;
    private final double mDouble;
    private final float mFloat;
    private final int mInt;
    private final long mLong;
    private final short mShort;
    private final Object mNull;

    public TestObject(long seed) {
        random = new Random(seed);

        mString = UUID.randomUUID().toString();
        mByte = mString.getBytes();
        mBool = getRandomBoolean();
        mLong = getRandomLong();
        mInt = getRandomInt();
        mDouble = getRandomDouble();
        mFloat = getRandomFloat();
        mShort = getRandomShort();
        mNull = null;
    }

    public byte[] getByte() {
        return mByte;
    }

    public double getDouble() {
        return mDouble;
    }

    public float getFloat() {
        return mFloat;
    }

    public int getInt() {
        return mInt;
    }

    public long getLong() {
        return mLong;
    }

    public Object getNull() {
        return mNull;
    }

    private boolean getRandomBoolean() {
        return random.nextBoolean();
    }

    private double getRandomDouble() {
        return random.nextDouble();
    }

    private float getRandomFloat() {
        return random.nextFloat();
    }

    private int getRandomInt() {
        return random.nextInt();
    }

    private long getRandomLong() {
        return random.nextLong();
    }

    private short getRandomShort() {
        return (short) random.nextInt(Short.MAX_VALUE + 1);
    }

    public short getShort() {
        return mShort;
    }

    public String getString() {
        return mString;
    }

    public boolean isBool() {
        return mBool;
    }
}
