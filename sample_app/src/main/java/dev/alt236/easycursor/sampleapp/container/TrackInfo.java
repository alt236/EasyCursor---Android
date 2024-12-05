/*
 * ***************************************************************************
 * Copyright 2024 Alexandros Schillings
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

package dev.alt236.easycursor.sampleapp.container;

import dev.alt236.easycursor.EasyCursor;

public class TrackInfo {
    private final long m_id;
    private final String mArtist;
    private final String mAlbum;
    private final String mTrack;
    private final String mMedia;
    private final String mComposer;
    private final boolean mHasComposer;
    private final long mMeaninglessSum;
    private final double mMeaninglessDiv;


    public TrackInfo(final EasyCursor c) {
        m_id = c.getLong("_id");
        mArtist = c.getString("artist");
        mAlbum = c.getString("album");
        mTrack = c.getString("track");
        mMedia = c.getString("media");
        mComposer = c.getString("composer");
        mHasComposer = c.getBoolean("hascomposer");
        mMeaninglessSum = c.getLong("meaninglessSum");
        mMeaninglessDiv = c.getDouble("meaninglessDiv");
    }

    public String getAlbum() {
        return mAlbum;
    }

    public String getArtist() {
        return mArtist;
    }

    public String getComposer() {
        return mComposer;
    }

    public double getMeaninglessDiv() {
        return mMeaninglessDiv;
    }

    public long getMeaninglessSum() {
        return mMeaninglessSum;
    }

    public String getMedia() {
        return mMedia;
    }

    public String getTrack() {
        return mTrack;
    }

    public long get_id() {
        return m_id;
    }

    public boolean isHasComposer() {
        return mHasComposer;
    }
}
