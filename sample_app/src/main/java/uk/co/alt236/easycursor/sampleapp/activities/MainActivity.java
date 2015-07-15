/**
 * ****************************************************************************
 * Copyright 2013 Alexandros Schillings
 * <p/>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p/>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p/>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * ****************************************************************************
 */
package uk.co.alt236.easycursor.sampleapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;

import uk.co.alt236.easycursor.sampleapp.R;

public class MainActivity extends FragmentActivity {


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onEasyJsonCursorExampleClick(View v) {
        startActivity(new Intent(this, EasyJsonCursorExampleActivity.class));
    }

    public void onEasyObjectCursorExampleClick(View v) {
        startActivity(new Intent(this, EasyObjectCursorExampleActivity.class));
    }

    public void onEasySqlCursorExampleClick(View v) {
        startActivity(new Intent(this, EasySqlCursorExampleActivity.class));
    }
}
