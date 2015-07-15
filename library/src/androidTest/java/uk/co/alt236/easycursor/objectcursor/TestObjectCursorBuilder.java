package uk.co.alt236.easycursor.objectcursor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import uk.co.alt236.easycursor.EasyCursor;

/**
 *
 */
public final class TestObjectCursorBuilder {

    private TestObjectCursorBuilder() {
    }

    public static EasyCursor getCursor() {
        return getCursor(null);
    }

    public static EasyCursor getCursor(final String alias) {
        return new EasyObjectCursor<>(TestObject.class, getList(), alias);
    }

    private static List<TestObject> getList() {
        final List<TestObject> list = new ArrayList<>();

        final TestObject object1 = new TestObject.Builder()
                .withBool(false)
                .withString("foo")
                .withByte(toByteArray("foo"))
                .withDouble(Double.MIN_VALUE)
                .withFloat(Float.MIN_VALUE)
                .withInt(Integer.MIN_VALUE)
                .withLong(Long.MIN_VALUE)
                .withShort(Short.MIN_VALUE)
                .build();

        final TestObject object2 = new TestObject.Builder()
                .withBool(true)
                .withString("bar")
                .withByte(toByteArray("bar"))
                .withDouble(Double.MAX_VALUE)
                .withFloat(Float.MAX_VALUE)
                .withInt(Integer.MAX_VALUE)
                .withLong(Long.MAX_VALUE)
                .withShort(Short.MAX_VALUE)
                .build();

        final TestObject object3 = new TestObject.Builder()
                .withBool(null)
                .withString(null)
                .withByte(null)
                .withDouble(null)
                .withFloat(null)
                .withInt(null)
                .withLong(null)
                .withShort(null)
                .build();

        list.add(object1);
        list.add(object2);
        list.add(object3);

        return Collections.unmodifiableList(list);
    }

    private static byte[] toByteArray(final String string){
        try {
            return string.getBytes("UTF-8");
        } catch (final Exception e) {
            // will never be thrown
            throw new IllegalStateException(e);
        }
    }
}
