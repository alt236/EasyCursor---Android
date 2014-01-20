#EasyCursor

For those who find statements like `cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAME));` or `cursor.getColumnIndex(COLUMN)==-1 ? value=FALLBACK : value=cursor.getString(COLUMN);`a little bit too verbose.

This library provides an different way to run queries and use the resulting cursor.

It offers:

1. A number of code simplification functions such as `cursor.getString(COLUMN_NAME)` to reduce verbosity.
2. A way of accessing optional columns such os `cursor.optLong(COLUMN_NAME)` and `cursor.optLONG(COLUMN_NAME, FALLBACK_VALUE)`.
3. A way to automatically get a JSON representation of a query for persistance.
4. A way to easily convert any existing Cursor into an EasyCursor - but you will lose the JSON representation :).
5. A way to access booleans directly (i.e. `cursor.getBoolean()`/`cursor.optBoolean()`. The default implementation of EasySqlCursor assumes that `true==1` and `false!=1` where 1 is a number, but it can be overriden by subclassing.
6. The SqlCursor's implementation of EasyCursor improves the performance of `getColumnIndex()` and `getColumnIndexOrThrow()` using a built-in cache.

The way this happens via an Interface, EasyCursor, which extends the bog standard Cursor.

At the moment the only implementation of EasyCursor is EasySqlCursor.

##Installation
To install:

Download a copy of the EasyCursor library and reference it in your project. 
Alternatively you can produce a JAR file and use that (see Jarification below).

##Jarification
Type `ant jar` at the root of the Library Project to produce a Jar file.

##Generating an EasyCursor for SQL
###1. Using an EasyQueryModel

This is the "native" way of using EasyCursor.

```
final EasySqlQueryModel model = new EasySqlQueryModel();
model.setTables(QueryConstants.DEFAULT_TABLES);
model.setDistinct(true);
model.setQueryParams(
		QueryConstants.DEFAULT_SELECT,
		QueryConstants.DEFAULT_WHERE,
		QueryConstants.DEFAULT_PARAMS,
		QueryConstants.DEFAULT_ORDER_BY);
model.setComment("Default easy query");
return model.execute(getReadableDatabase());
```
###2. Using an QueryBuilder Interface
Define a class implementing either the `SqlSelectBuilder` or `SqlRawQueryBuilder` interfaces and do the following:

```
final LousyQueryBuilder builder = new LousyQueryBuilder();
final EasySqlQueryModel model = builder.setSelect(QueryConstants.DEFAULT_SELECT)
		.setWhere(QueryConstants.DEFAULT_WHERE)
		.setWhereArgs(QueryConstants.DEFAULT_PARAMS)
		.setOrderBy(QueryConstants.DEFAULT_ORDER_BY)
		.build();
model.setComment("Builder query");
return model.execute(getReadableDatabase());
```
###3. The Backwards Compatible way

You can convert an existing Cursor to an EasyCursor by wrapping like this:

`final EasyCursor eCursor = new EasySqlCursor(boringOldCursor);`

As you did not use an SqlQueryModel, you will not be able to access the JSON representation of the query.

##Things you can do with an EasyCursor
An EasyCursor offers (apart from the usual Cursor methods) the following:

* `easycursor.getXXX(String columnName)` : Which is shorthand for `easycursor.getString(easycursor.getColumnIndexOrThrow(COLUMN_NAME))`.
* `easycursor.optXXX(String columnName)` : Which will return an implementation specific fallback value if the column is missing (see the Javadoc for details).
* `easycursor.optXXX(String columnName, XXX fallback)`: Which will return the provided fallback value if the column is missing.
* `easycursor.optXXXAsWrapperType(String columnName)`: Which will return null if the column is missing (if anyone can think of a better name let me know :)).

Where XXX is the usual data types (long, int, String, etc.). 

Not all functions are available for all datatypes though, as some are meaningless (`getStringAsWrapperType()` for example).


##Accessing an EasyCursor's EasyQueryModel
If the cursor has been generated via an EasyQueryModel, then you can access the model like this: `easycursor.getQueryModel()`.

The model can then be converted to a JSON string via its `model.toJson()` function.

To recreate the model from its JSON representation, use its constructor: `final EasySqlQueryModel model = new EasySqlQueryModel(json);`


##Keeping track of an EasyQueryModel
In order to keep track of an EasyQueryModel file and help in maintaining compatibility of the model to the underlying DB (if your schema can change), you can use the following functions.

* `setModelComment(String comment)/String getModelComment()`
* `setModelTag(String tag)/ String getModelTag()`
* `setModelVersion(int version)/ int getModelVersion()`

There is no business logic behind these functions -- it is up to each user to keep a compatibility matrix.


##Changelog
* v0.1.0 First public release

##Permission Explanation
* No permissions required
	
##Sample App Screenshots
*TODO*

##Links
* Github: [https://github.com/alt236/Reflective-Drawable-Loader---Android]()

##Credits
Author: [Alexandros Schillings](https://github.com/alt236).

The code in this project is licensed under the Apache Software License 2.0.

Copyright (c) 2013 Alexandros Schillings.

