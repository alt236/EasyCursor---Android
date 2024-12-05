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

package dev.alt236.easycursor.objectcursor;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import dev.alt236.easycursor.internal.FieldAccessor;

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

    public ObjectFieldAccessor(final Class<T> clazz) {
        mFieldToMethodMap = Collections.synchronizedMap(new HashMap<String, Method>());
        mMethodList = new ArrayList<>();
        mFieldToIndexMap = new HashMap<>();
        mFieldNameList = new ArrayList<>();
        populateMethodList(clazz);
    }

    @Override
    public int getFieldIndexByName(final String name) {
        if (mFieldToIndexMap.containsKey(name)) {
            return mFieldToIndexMap.get(name);
        } else {
            return -1;
        }
    }

    @Override
    public String getFieldNameByIndex(final int index) {
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

    public Method getMethod(final int index) {
        return mMethodList.get(index);
    }

    public List<Method> getMethodList() {
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
