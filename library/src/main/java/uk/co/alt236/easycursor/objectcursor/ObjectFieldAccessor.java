package uk.co.alt236.easycursor.objectcursor;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import uk.co.alt236.easycursor.internal.FieldAccessor;

/**
 *
 */
class ObjectFieldAccessor<T> implements FieldAccessor {
    private static final String IS = "is";
    private static final String GET = "get";
    private final Map<String, Integer> mFieldToIndexMap;
    private final Map<String, Method> mFieldToMethodMap;
    private final List<Method> mMethodList;
    private final List<String> mFieldNameList;

    public ObjectFieldAccessor(final Class<T> clazz){
        mFieldToMethodMap = Collections.synchronizedMap(new HashMap<String, Method>());
        mMethodList = new ArrayList<>();
        mFieldToIndexMap = new HashMap<>();
        mFieldNameList = new ArrayList<>();
        populateMethodList(clazz);
    }

    @Override
    public int getFieldIndexByName(final String name){
        if (mFieldToIndexMap.containsKey(name)) {
            return mFieldToIndexMap.get(name);
        } else {
            return -1;
        }
    }

    @Override
    public String getFieldNameByIndex(final int index){
        return mFieldNameList.get(index);
    }

    @Override
    public String[] getFieldNames() {
        return mFieldNameList.toArray(new String[mFieldNameList.size()]);
    }

    public synchronized Method getGetterForField(final String field) {
        if (mFieldToMethodMap.containsKey(field)) {
            return mFieldToMethodMap.get(field);
        } else {
            final String booleanField = IS + field.toLowerCase(Locale.US);
            final String otherField = GET + field.toLowerCase(Locale.US);

            Method methodResult = null;
            for (final Method method : mMethodList) {
                if (method.getName().toLowerCase(Locale.US).equals(booleanField)) {
                    methodResult = method;
                    break;
                } else if (method.getName().toLowerCase(Locale.US).equals(otherField)) {
                    methodResult = method;
                    break;
                }
            }

            mFieldToMethodMap.put(field, methodResult);
            return methodResult;
        }
    }

    public Method getMethod(final int index){
        return mMethodList.get(index);
    }

    public List<Method> getMethodList(){
        return mMethodList;
    }

    private void populateMethodList(final Class<T> clazz) {
        Method candidate;
        String canditateCleanName;

        int count = 0;
        for (final Method method : clazz.getMethods()) {
            candidate = null;
            canditateCleanName = null;

            if (Modifier.isPublic(method.getModifiers())) {

                // Just in case there is a get()/is() function
                if (method.getName().length() > 3) {
                    if (method.getParameterTypes().length == 0) {
                        if (!method.getReturnType().equals(Void.TYPE)) {
                            if (method.getName().startsWith(GET)) {
                                candidate = method;
                                canditateCleanName =
                                        method.getName().substring(GET.length()).toLowerCase(Locale.US);

                            } else if (method.getName().startsWith(IS)) {
                                candidate = method;
                                canditateCleanName =
                                        method.getName().substring(IS.length()).toLowerCase(Locale.US);
                            }

                            if (candidate != null) {
                                mMethodList.add(candidate);
                                mFieldToIndexMap.put(canditateCleanName, count);
                                mFieldNameList.add(canditateCleanName);
                                count++;
                            }
                        }
                    }
                }
            }
        }
    }
}
