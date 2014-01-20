package uk.co.alt236.easycursor.sampleapp.database;

class QueryConstants {
	static final String RAW_QUERY = "SELECT track.trackId AS _id, artist.name AS artist, album.title AS album, track.name AS track, mediatype.name AS media, track.composer AS composer, (ifnull(track.composer, 0)>0) AS hascomposer"
			+ " FROM track"
			+ " LEFT OUTER JOIN album ON track.albumId = album.albumid"
			+ " LEFT OUTER JOIN artist ON artist.artistId = album.artistid "
			+ " LEFT OUTER JOIN mediatype ON track.mediatypeid = mediatype.mediatypeid "
			+ " WHERE media=?"
			+ " ORDER BY artist, album, track, composer;";
	static final String[] RAW_SQL_PARAMS = { "MPEG audio file" };

	static final String[] DEFAULT_SELECT = {
		"track.trackId AS _id",
		"artist.name AS artist",
		"album.title AS album",
		"track.name AS track",
		"mediatype.name AS media",
		"track.composer AS composer",
		"(ifnull(track.composer, 0)>0) AS hascomposer"
	};

	static final String DEFAULT_ORDER_BY = "artist, album, track, composer";
	static final String DEFAULT_WHERE = "media=?";
	static final String DEFAULT_TABLES = "track"
			+ " LEFT OUTER JOIN album ON track.albumId = album.albumid"
			+ " LEFT OUTER JOIN artist ON artist.artistId = album.artistid "
			+ " LEFT OUTER JOIN mediatype ON track.mediatypeid = mediatype.mediatypeid";
}
