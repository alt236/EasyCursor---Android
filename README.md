EasyCursor
-----------
For those who find statements like `cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAME))` a little bit too verbose.

This library provides an different way to run queries and use the resulting cursor.

It offers:
1) A number of code simplification functions such as `cursor.getString(COLUMN_NAME)` to reduce verbosity
2) A way of accessing optional columns such os `cursor.optLong(COLUMN_NAME)` and `cursor.optLONG(COLUMN_NAME, FALLBACK_VALUE)`
3) The SqlCursor's implementation of EasyCursor improves the performance of `getColumnIndex()` and `getColumnIndexOrThrow()` using a built-in cache.
4) A way to automatically get a JSON representation of a query for persistance.
5) A way to easily convert any existing Cursor into an EasyCursor - but you will lose the JSON representation :)
6) A way to access booleans directly (i.e. cursor.getBoolean()/cursor.optBoolean(). The default implementation of EasySqlCursor assumes that `true==1` and `false!=1` where 1 is a number, but it can be overriden by subclassing.

The way this happens via an Interface, EasyCursor, which extends the bog standard Cursor.

At the moment the only implementation of EasyCursor is EasySqlCursor.

Installation
-----------
To install:

Download a copy of the EasyCursor library and reference it in your project. 
Alternatively you can produce a JAR file and use that.

Generating an EasyCursor for SQL
-----------
<b>1. The Easy way</b>

You can convert an existing Cursor to an EasyCursor by wrapping like this:
`EasyCursor eCursor = new EasySqlCursor(boringOldCursor);`

<b>2. Using an EasyQueryModel</b>

This is the "native" way of using EasyCursor.
*TODO*

<b>3. Using an QueryBuilder Interface</b>
*TODO*

Jarification
-----------
Type `ant jar` at the root of the Library Project to produce a Jar file.


Changelog
-----------
* v0.1.0 First public release

Permission Explanation
-----------
* No permissions required
	
Sample App Screenshots
-----------
*TODO*

Links
-----------
* Github: [https://github.com/alt236/Reflective-Drawable-Loader---Android]()

Credits
-----------
Author: [Alexandros Schillings](https://github.com/alt236).

The code in this project is licensed under the Apache Software License 2.0.

Copyright (c) 2013 Alexandros Schillings.

