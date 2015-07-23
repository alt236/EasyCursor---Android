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

package uk.co.alt236.easycursor.sampleapp.database;

public final class QueryConstants {

    //
    // RAW QUERY
    //
    public static final String RAW_QUERY =
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
    public static final String[] RAW_SQL_PARAMS = {"MPEG audio file"};

    //
    // SELECT QUERY
    //
    public static final String[] DEFAULT_SELECT = {
            "track.trackId AS _id",
            "artist.name AS artist",
            "album.title AS album",
            "track.name AS track",
            "mediatype.name AS media",
            "track.composer AS composer",
            "(ifnull(track.composer, 0)>0) AS hascomposer",
            "SUM(track.Milliseconds) AS meaninglessSum",
            "SUM(track.Milliseconds)/3.33 AS meaninglessDiv"
    };

    public static final String DEFAULT_WHERE = "media=?";
    public static final String DEFAULT_SELECT_GROUP_BY = "album";
    public static final String DEFAULT_SELECT_HAVING = "SUM(track.Milliseconds) > 1000";
    public static final String DEFAULT_SELECT_LIMIT = "10000";
    public static final String DEFAULT_ORDER_BY = "artist, album, track, composer";
    public static final String[] DEFAULT_SELECT_WHERE_PARAMS = RAW_SQL_PARAMS;
    public static final String DEFAULT_TABLES = "track"
            + " LEFT OUTER JOIN album ON track.albumId = album.albumid"
            + " LEFT OUTER JOIN artist ON artist.artistId = album.artistid "
            + " LEFT OUTER JOIN mediatype ON track.mediatypeid = mediatype.mediatypeid";

    private QueryConstants() {
    }
}
