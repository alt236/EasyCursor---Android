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

package dev.alt236.easycursor.sampleapp.adapters;

import android.content.Context;
import android.support.v4.widget.SimpleCursorAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import dev.alt236.easycursor.EasyCursor;
import dev.alt236.easycursor.sampleapp.R;

public class MusicExampleAdapter extends SimpleCursorAdapter {
    private static final String NO_SUCH_COLUMN = "<no column>";
    private static final String HAS_COMPOSER = "Has composer";
    private static final String NO_COMPOSER = "No composer";

    @SuppressWarnings("deprecation")
    public MusicExampleAdapter(final Context context, final int layout, final EasyCursor c, final String[] from, final int[] to) {
        super(context, layout, c, from, to);
    }


    @Override
    public EasyCursor getCursor() {
        return (EasyCursor) super.getCursor();
    }

    @Override
    public View getView(final int position, final View convertView, final ViewGroup parent) {
        final View view = super.getView(position, convertView, parent);
        final TextView composer = (TextView) view.findViewById(R.id.hasComposer);
        final TextView media = (TextView) view.findViewById(R.id.medium);
        final TextView track = (TextView) view.findViewById(R.id.track);

        media.setText(getCursor().optString("media", NO_SUCH_COLUMN));
        track.setText(getCursor().optString("track", NO_SUCH_COLUMN));


        final boolean composerPresent = getCursor().optBoolean("hascomposer");

        if (composerPresent) {
            composer.setText(HAS_COMPOSER);
        } else {
            composer.setText(NO_COMPOSER);
        }

        return view;
    }
}
