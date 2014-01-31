#EasyCursor

For those who find statements like `cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAME));` or `cursor.getColumnIndex(COLUMN)==-1 ? value=FALLBACK : value=cursor.getString(COLUMN);`a bit too arcane and verbose.

This library provides an different way to run queries and use the resulting cursor.

It offers:

1. A number of code simplification functions such as `cursor.getString(COLUMN_NAME)` to reduce verbosity.
2. A way of accessing optional columns such as `cursor.optLong(COLUMN_NAME)` and `cursor.optLong(COLUMN_NAME, FALLBACK_VALUE)`.
3. A way to automatically get a JSON representation of a query for persistence.
4. A way to easily convert any existing Cursor into an EasyCursor - but you will not get the JSON representation :).
5. A way to access booleans directly (i.e. `cursor.getBoolean()`/`cursor.optBoolean()`. The default implementation of EasySqlCursor assumes that `true==1` and `false!=1` where 1 is a number, but it can be overridden by subclassing.

The way this happens via an Interface, EasyCursor, which extends the bog standard Cursor.

At the moment the only implementation of EasyCursor is EasySqlCursor.

##Installation
To install:

Download a copy of the EasyCursor library and reference it in your project. 
Alternatively you can produce a JAR file and use that (see Jarification below).

##Jarification
Type `ant` at the root of the Library Project to produce a Jar file. This will also produce the Javadoc Jar.

##Generating an EasyCursor for SQL
###1. Using one of the inlcuded Builders EasyQueryModel

This is the "native" way of using EasyCursor.

For a normal Select Query:
```
    final EasySqlQueryModel model = new EasySqlQueryModel.SelectQueryBuilder()
	.setDistict(true)
	.setStrict(true)
	.setSelect(QueryConstants.DEFAULT_SELECT)
	.setFrom(QueryConstants.DEFAULT_TABLES)
	.setWhere(QueryConstants.DEFAULT_WHERE)
	.setWhereArgs(QueryConstants.DEFAULT_SELECT_WHERE_PARAMS)
	.setGroupBy(QueryConstants.DEFAULT_SELECT_GROUP_BY)
	.setHaving(QueryConstants.DEFAULT_SELECT_HAVING)
	.setSortOrder(QueryConstants.DEFAULT_ORDER_BY)
	.setLimit(QueryConstants.DEFAULT_SELECT_LIMIT)
    .setModelComment("Default easy query")
    .build();
    
    final EasyCursor eCursor = model.execute(getReadableDatabase());
```

For a raw SQL query:
```
    final EasySqlQueryModel model = new EasySqlQueryModel.RawQueryBuilder()
    .setRawSql(QueryConstants.RAW_QUERY)
    .setSelectionArgs(QueryConstants.RAW_SQL_PARAMS)
    .setModelComment("Raw query")
    .build();
    
    final EasyCursor eCursor = model.execute(getReadableDatabase());
```
###2. Using an QueryBuilder Interface

You can define a class implementing either the `SqlSelectBuilder` or `SqlRawQueryBuilder` interfaces and do the following:
This way you can write, or re-use, your own builders.

```
    final LousyQueryBuilder builder = new LousyQueryBuilder();
    
    final EasySqlQueryModel model = builder.setSelect(QueryConstants.DEFAULT_SELECT)
    		.setWhere(QueryConstants.DEFAULT_WHERE)
    		.setWhereArgs(QueryConstants.DEFAULT_PARAMS)
    		.setOrderBy(QueryConstants.DEFAULT_ORDER_BY)
    		.build();
    		
    model.setComment("Builder query");
    
    final EasyCursor eCursor = model.execute(getReadableDatabase());
```
###3. The Backwards Compatible way

You can convert an existing Cursor to an EasyCursor by wrapping it like this:

```
    final SQLiteQueryBuilder builder = new SQLiteQueryBuilder();
    
    builder.setTables(QueryConstants.DEFAULT_TABLES);
    builder.setDistinct(true);
    
    final Cursor cursor = builder.query(
    		getReadableDatabase(),
    		QueryConstants.DEFAULT_SELECT,
    		QueryConstants.DEFAULT_WHERE,
    		QueryConstants.RAW_SQL_PARAMS,
    		null,
    		null,
    		QueryConstants.DEFAULT_ORDER_BY);
    
    cursor.moveToFirst();
    
    final EasyCursor eCursor = new EasySqlCursor(cursor);
```

But, as you did not use an SqlQueryModel, you will not be able to access the JSON representation of the query.

###4. The "Compatibility" way
By compatibility I mean that the API is similar to a SQLiteQueryBuilder.

```
		final EasyCompatSqlModelBuilder builder = new EasyCompatSqlModelBuilder();
		
		builder.setTables(QueryConstants.DEFAULT_TABLES);
		builder.setDistinct(true);
		builder.setQueryParams(
				QueryConstants.DEFAULT_SELECT,
				QueryConstants.DEFAULT_WHERE,
				QueryConstants.DEFAULT_SELECT_WHERE_PARAMS,
				QueryConstants.DEFAULT_SELECT_GROUP_BY,
				QueryConstants.DEFAULT_SELECT_HAVING,
				QueryConstants.DEFAULT_ORDER_BY,
				QueryConstants.DEFAULT_SELECT_LIMIT);

		final EasySqlQueryModel model = builder.build();
		
		model.setModelComment("Default compat query");
		
		final EasyCursor eCursor = model.execute(getReadableDatabase());
```

The `setQueryParams()` method has an identical signature to `SQLiteQueryBuilder.query()` minus the database instance parameter.

##Things you can do with an EasyCursor
An EasyCursor offers (apart from the usual Cursor methods) the following:

* `easycursor.getXXX(String columnName)` : Which is shorthand for `easycursor.getXXX(easycursor.getColumnIndexOrThrow(COLUMN_NAME))`.
* `easycursor.optXXX(String columnName)` : Which will return an implementation specific fallback value if the column is missing (see the Javadoc for details).
* `easycursor.optXXX(String columnName, XXX fallback)`: Which will return the provided fallback value if the column is missing.
* `easycursor.optXXXAsWrapperType(String columnName)`: Which will return null if the column is missing (_if anyone can think of a better name let me know :)_).

Where XXX is the usual data types (long, int, String, etc.). 

Not all functions are available for all datatypes though, as some are meaningless (`getStringAsWrapperType()` for example).

##Booleans
In addition you get the following functions for booleans, which work the same as the ones above:

* `easycursor.getBoolean(String columnName)`
* `easycursor.optBoolean(String columnName)`
* `easycursor.optBoolean(String columnName, boolean fallback)`
* `easycursor.optBooleanAsWrapperType(String columnName)`

The logic behind a boolean is as follows: `true==1` and `false!=1` where 1 is a number.

##Accessing an EasyCursor's EasyQueryModel, saving it and replaying
If the cursor has been generated via an EasyQueryModel, then you can access the model like this: 

```
    final EasyQueryModel model = easycursor.getQueryModel()`
```

The model can then be converted to a JSON string via its `model.toJson()` function.

To re-create the model, you can do the following:
```
    final EasySqlQueryModel model = new EasySqlQueryModel(json);
```

The following snippet will get the Model JSON of a cursor, save it in local prefs, read it back and re-query:
```
    // Get the JSON of a cursor model
	final String jsonOut = oldCursor.getQueryModel().toJson();
	
	// Store it in local prefs
	final SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(this);
	final SharedPreferences.Editor editor = settings.edit();
	editor.putString(Constants.PREFS_SAVED_QUERY, jsonOut);
	editor.commit();
	
	// Read it back
	final String jsonIn = settings.getString(Constants.PREFS_SAVED_QUERY, null);
	
	// Recreate the model and execute the query
	final EasySqlQueryModel model = new EasySqlQueryModel(json);
	final EasyCursor eCursor = model.execute(getReadableDatabase());
```  		
##Keeping track of an EasyQueryModel
In order to keep track of an EasyQueryModel file and help in maintaining compatibility of the model to the underlying DB (if your schema can change), you can use the following functions.

* `setModelComment(String comment)/String getModelComment()`
* `setModelTag(String tag)/ String getModelTag()`
* `setModelVersion(int version)/ int getModelVersion()`

There is no business logic behind these functions -- it is up to each user to keep a compatibility matrix.

##Extending the implementation of EasySqlCursor
If you need to extend the base implementation of EasySqlCursor, for example because you treat booleans differently, you can use the following syntax:

```
    final EasyCursor cursor = model.execute(getReadableDatabase(), MyExtendedEasyCursor.class);
```

##Things to remember
1. An EasyCursor directly extends the default Cursor interface.
2. When using an EasySqlQueryModel, the resulting Cursor is automatically moved to the first position.
3. When you call execute() on an EasySqlQueryModel it still internally uses a standard Android SQLiteQueryBuilder, which will throw any usual exceptions. 

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

