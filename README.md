# EasyCursor

For those who find statements like `cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAME));`
or `cursor.getColumnIndex(COLUMN)==-1 ? value=FALLBACK : value=cursor.getString(COLUMN);`
a bit too arcane and verbose.

Also, for those that have to maintain both a CursorAdaptor and an ArrayAdaptor for the same data from different sources.

There are 3 types of EasyCursors:

1. EasySqlCursor - A wrapper for normal SqlCursors
2. EasyJsonCursor (experimental) - Converts a JSONArray into a Cursor
3. EasyObjectCursor (experimental) - Converts a List/Array of generic Objects into a Cursor.

**EasyCursors** in general offer the following benefits:

1. A number of code simplification functions such as `cursor.getString(COLUMN_NAME)` to reduce verbosity.
2. A way of accessing optional columns/fields such as `cursor.optLong(COLUMN_NAME)` and `cursor.optLong(COLUMN_NAME, FALLBACK_VALUE)`.
3. (Optionally) A way to store the way the cursor was constructed so that it can be stored for later re-creation.
Currently the library only implements this for the EasySqlCursor but it can be build for the others as well.

**EasySqlCursors** also offer:

1. A way to access booleans directly (i.e. `cursor.getBoolean()`/`cursor.optBoolean()`). The default
implementation of EasySqlCursor is defined in `DefaultBooleanLogic.java` and assumes that `true==1` and
`false!=1` where 1 is a number, but it can be changed by passing a custom BooleanLogic implementation
in the constructor.


## Getting Started Guide

**BREAKING CHANGES** 

As of version 2.0.0, the library package name have been changed from `uk.co.alt236.easycursor.*` to `dev.alt236.easycursor.*`. 
The maven coordinates have also changed (see below).

### Including the Library in Your Project

This project is available as an artifact for use with Gradle. To use that, add the following blocks to your build.gradle file:
```
	dependencies {
		compile 'dev.alt236:easycursor-android:2.0.0'
	}
```
If you *really* need a Jar file, fork the project and execute `./gradlew clean build generateRelease` at the root of the project.
This will create a zip file under `<PROJECT_ROOT>/library/build/` the Jar can be found inside.

### EasySqlCursors
This is the way to convert boring old cursors to an EasyCursor:

```java
final Cursor cursor = builder.query(...);
    
cursor.moveToFirst();
    
final EasyCursor eCursor = new EasySqlCursor(cursor);

```
Notes and Caveats:

* Look [here](#easysqlcursor_full) for a more in-depth explanation on how EasySqlCursors work.

### EasyJsonCursors
Any JSON array can be converted to a cursor like this:

```java
  final JSONArray jArray = ... // Any Json Array.

  // If the JSONObjects in the JSONArray do not have an android-valid "_id" field,
  // you can setup an alias.
  final String _idAlias = "id"; 
  final EasyCursor cursor = new EasyJsonCursor(jArray, _idAlias);
```
Notes and Caveats:

* The "columns" array for the "getColumnIndexOrThrow() / getColumnIndex()" methods is calculated
based on the JSON fields of the first array item. This means that if the other items have more,
you will not be able to access them using any of the getXXX methods - you will get an IllegalArgumentException.
To access those fields you will need to use the optXXX methods.

* EasyJsonCursor is internally using org.json. Normally, org.json getXXX methods throw checked
JsonExceptions but in order to keep the EasyCursor API consistent these exceptions are caught
(when thrown from getXXX methods) and re-thrown as runtime IllegalArgumentExceptions or ConversionErrorExceptions.

* getBytes()/optBytes() is not implemented and will throw an UnsupportedOperationException when called.

* In addition to the usual EasyCursor methods, an EasyJsonCursor also has the following:
  1. `getJsonObject(String name)`
  2. `getJsonArray(String name)`
  3. `optJsonObject(String name)`
  4. `optJsonArray(String name)`
       
### EasyObjectCursors

```java
  final List<WhateverObject> data = ... // somehow get an object list

  // If the Objects in the list do not have "get_Id()" getter method, 
  // you can setup an alias. The alias below will match a "getId()" method.
  final String _idAlias = "id"; 
  final EasyCursor cursor = new EasyObjectCursor<WhateverObject>(WhateverObject.class, data, _idAlias);
```
Notes and Caveats:

* The "columns" array for the "getColumnIndexOrThrow() / getColumnIndex()" methods is calculated as follows:
  * Take the class type you passed as a parameter in the constructor
  * Reflectively get all public methods with zero parameters which are non-void and their name is more than 3 characters long.
  * Check to see if they start with "get" or "is".
  * If they do, chop the "get" / "is" prefix off, lowercase them add add them to the array. 
  * This means that if you call `cursor.getBytes("mymethod") you will internally call the `getMyMethod()` getter of the current object.

* In addition to the usual EasyCursor methods, an EasyObjectCursor also has the following:
  1.  `getItem()` - Which returns the current object.
  2.  `getObject(int fieldNo)` - Which return the result of the getter execution as an object
  3.  `getObject(String name)` - Which return the result of the getter execution as an object
  4.  `optObject(int fieldNo)` - Which return the result of the getter execution as an object
  5.  `optObject(String name)` - Which return the result of the getter execution as an object

* An EasyObjectCursor will not catch any exceptions that calling an object's method will throw.

* When calling a `get[Number] or /opt[Number]` method the following actions will take place:
  1. Check to see if what comes out of the getter is an instance of the Number class. If so, return the correct type
  2. If not, check if the object is a String and try to parse it as the requested Number type
  3. Throw a ConversionErrorException
    
* The getBlob() / getBlob() methods is only valid for the types listed below. Any other will throw a ConversionErrorException.
  1. Strings
  2. ByteArrays
  3. Integers
  4. Long
  5. Float
  6. Double
  7. Short

&nbsp;

## <a name="easysqlcursor_full"></a>EasySqlCursors In Depth
### Creation
#### 1. Using one of the included Builders EasyQueryModel

This is the "native" way of using EasyCursor.

For a normal Select Query:
```java
  final SqlQueryModel model = new SqlQueryModel.SelectQueryBuilder()
    .setDistinct(true)
    .setProjectionIn("track.trackId AS _id", "artist.name AS artist", "album.title AS album", "mediatype.name AS media")
    .setTables("track LEFT OUTER JOIN album ON track.albumId = album.albumid")
    .setSelection("media=?")
    .setSelectionArgs("MPEG audio file")
    .setSortOrder("artist, album, track, composer")
    .setModelComment("Example query")
    .build();
    
  final EasyCursor eCursor = model.execute(db.getReadableDatabase());
```

For a raw SQL query:
```java
  private static final String RAW_QUERY =
    "SELECT track.trackId AS _id, artist.name AS artist, album.title AS album, track.name AS track, mediatype.name AS media, track.composer AS composer, (ifnull(track.composer, 0)>0) AS hascomposer, SUM(track.Milliseconds) AS meaninglessSum, SUM(track.Milliseconds)/3.33 AS meaninglessDiv"
    + " FROM track"
    + " LEFT OUTER JOIN album ON track.albumId = album.albumid"
    + " LEFT OUTER JOIN artist ON artist.artistId = album.artistid"
    + " LEFT OUTER JOIN mediatype ON track.mediatypeid = mediatype.mediatypeid"
    + " WHERE media=?"
    + " GROUP BY album"
    + " HAVING SUM(track.Milliseconds) > 1000"
    + " ORDER BY artist, album, track, composer"
    + " LIMIT 10000";
              
  final SqlQueryModel model = new SqlQueryModel.RawQueryBuilder()
    .setRawSql(RAW_QUERY)
    .setSelectionArgs("MPEG audio file")
    .setModelComment("Raw query")
    .build();

  final EasyCursor eCursor = model.execute(db.getReadableDatabase());
```
#### 2. Using an QueryBuilder Interface

You can define a class implementing either the `SqlSelectBuilder` or `SqlRawQueryBuilder` interfaces and do the following:
This way you can write, or re-use, your own builders. The following example reads a little bit more like a "standard" SQL statement:
```java
  final LousyQueryBuilder builder = new LousyQueryBuilder();

  final SqlQueryModel model = builder
      .setSelect("track.trackId AS _id", "artist.name AS artist", "album.title AS album", "mediatype.name AS media")
      .setFrom("track LEFT OUTER JOIN album ON track.albumId = album.albumid")
      .setWhere("media=?")
      .setWhereArgs("MPEG audio file")
      .setOrderBy("artist, album, track, composer")
      .build();
  		
  model.setComment("Builder query");
      
  final EasyCursor eCursor = model.execute(db.getReadableDatabase());
```
#### 3. The Backwards Compatible way

You can convert an existing Cursor to an EasyCursor by wrapping it like this:

```java
  // Android build-in query builder.
  final SQLiteQueryBuilder builder = new SQLiteQueryBuilder();

  builder.setTables("track LEFT OUTER JOIN album ON track.albumId = album.albumid");
  builder.setDistinct(true);

  final Cursor cursor = builder.query(
    getReadableDatabase(),
    "track.trackId AS _id", "artist.name AS artist", "album.title AS album", "mediatype.name AS media",
    "media=?",
    "MPEG audio file",
    null,
    null,
    "artist, album, track, composer");

  cursor.moveToFirst();

  final EasyCursor eCursor = new EasySqlCursor(cursor);
```

But, as you did not use an SqlQueryModel, you will not be able to access the JSON representation of the query.

#### 4. The "Compatibility" way
By compatibility I mean that the API is similar to a SQLiteQueryBuilder.

```java
  final EasyCompatSqlModelBuilder builder = new EasyCompatSqlModelBuilder();

  builder.setTables(QueryConstants.DEFAULT_TABLES);
  builder.setDistinct(true);
  builder.setQueryParams(
    "track.trackId AS _id", "artist.name AS artist", "album.title AS album", "mediatype.name AS media",
    "media=?",
    "MPEG audio file",
    null,
    null,
    "artist, album, track, composer");

  final SqlQueryModel model = builder.build();

  model.setModelComment("Default compat query");

  final EasyCursor eCursor = model.execute(getReadableDatabase());
```

The `setQueryParams()` method has an identical signature to `SQLiteQueryBuilder.query()` minus the database instance parameter.

### Things you can do with an EasyCursor
An EasyCursor offers (apart from the usual Cursor methods) the following:

* `easycursor.getXXX(String columnName)` : Which is shorthand for `easycursor.getXXX(easycursor.getColumnIndexOrThrow(COLUMN_NAME))`.
* `easycursor.optXXX(String columnName)` : Which will return an implementation specific fallback value if the column is missing (see the Javadoc for details).
* `easycursor.optXXX(String columnName, XXX fallback)`: Which will return the provided fallback value if the column is missing.
* `easycursor.optXXXAsWrapperType(String columnName)`: Which will return null if the column is missing (_if anyone can think of a better name let me know :)_).

Where XXX is the usual data types (long, int, String, etc.). 

Not all functions are available for all datatypes though, as some are meaningless (`getStringAsWrapperType()` for example).

### Booleans
In addition you get the following functions for booleans, which work the same as the ones above:

* `easycursor.getBoolean(String columnName)`
* `easycursor.optBoolean(String columnName)`
* `easycursor.optBoolean(String columnName, boolean fallback)`
* `easycursor.optBooleanAsWrapperType(String columnName)`

The logic behind a boolean is as follows: `true==1` and `false!=1` is defined in `DefaultBooleanLogic.java`.

### Accessing an EasyCursor's EasyQueryModel, saving it and replaying
If the cursor has been generated via an EasyQueryModel, then you can access the model like this: 

```java
  final SqlQueryModel model = easycursor.getQueryModel()`
```

The model can then be converted to a JSON string via its `model.toJson()` function.

To re-create the model, you can do the following:

```java
  final SqlQueryModel model = SqlJsonModelConverter.convert(jsonString);
```

The following snippet will get the Model JSON of a cursor, save it in local prefs, read it back and re-query:
```java
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
  final SqlQueryModel model = SqlJsonModelConverter.convert(jsonString);
  final EasyCursor eCursor = model.execute(getReadableDatabase());
```  		
### Keeping track of an EasyQueryModel
In order to keep track of an EasyQueryModel file and help in maintaining compatibility of the model
to the underlying DB (if your schema can change), you can use the following functions.

* `setModelComment(String comment)/String getModelComment()`
* `setModelTag(String tag)/ String getModelTag()`
* `setModelVersion(int version)/ int getModelVersion()`

There is no business logic behind these functions -- it is up to each user to keep a compatibility matrix.

## Extending the implementation of EasySqlCursor
If you need to extend the base implementation of EasySqlCursor, for example because you treat
booleans differently, you can use the following syntax:

```java
final EasyCursor cursor = model.execute(getReadableDatabase(), MyExtendedEasyCursor.class);
```

## Things to remember
1. An EasyCursor directly extends the default Cursor interface, so you can pass it around as a standard Cursor.
2. When using an SqlQueryModel, the resulting Cursor is automatically moved to the first position.
3. When you call execute() on an SqlQueryModel it still internally uses a standard Android SQLiteQueryBuilder, which will throw any usual exceptions.
4. When you call execute() on an SqlQueryModel the execution happens on the same thread it was called in.

## Changelog
* v0.1.0 First public release
* v0.1.1 Fixed a crash caused by not properly handing null arrays in JsonPayloadHelper 
* v1.0.0 API tidy, proper release.

## Links
* Github: [https://github.com/alt236/EasyCursor---Android]()

## Credits
Author: [Alexandros Schillings](https://github.com/alt236).

The code in this project is licensed under the Apache Software License 2.0.

Copyright (c) 2017 Alexandros Schillings.
