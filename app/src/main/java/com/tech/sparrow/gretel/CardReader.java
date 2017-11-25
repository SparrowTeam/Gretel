/*
 * Copyright (C) 2013 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.tech.sparrow.gretel;

import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.util.Log;


/**
 * Callback class, invoked when an NFC card is scanned while the device is running in reader mode.
 *
 * Reader mode can be invoked by calling NfcAdapter
 */
public class CardReader implements NfcAdapter.ReaderCallback {
    private static final String TAG = "CardReader";
    private UserActivity parentActivity;

    public CardReader(UserActivity parentActivity) {
        this.parentActivity = parentActivity;
    }

    /**
     * Callback when a new tag is discovered by the system.
     *
     * <p>Communication with the card should take place here.
     *
     * @param tag Discovered tag
     */
    @Override
    public void onTagDiscovered(Tag tag) {
        Log.i(TAG, "New tag discovered");
        Log.i(TAG, "Tag info: " + tag.toString());
        byte[] tagId = tag.getId();
        String tagIdStr = String.format("%02X %02X %02X %02X",
            tagId[0],
            tagId[1],
            tagId[2],
            tagId[3]
        );
        Log.i(TAG, "ID: " + tagIdStr);
        parentActivity.handleTagId(tagIdStr);
    }
}
