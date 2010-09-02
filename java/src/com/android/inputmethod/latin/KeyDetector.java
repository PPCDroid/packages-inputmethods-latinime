/*
 * Copyright (C) 2010 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

package com.android.inputmethod.latin;

import android.inputmethodservice.Keyboard;
import android.inputmethodservice.Keyboard.Key;

import java.util.Arrays;
import java.util.List;

abstract class KeyDetector {
    protected Keyboard mKeyboard;
    private Key[] mKeys;

    protected int mCorrectionX;
    protected int mCorrectionY;
    protected boolean mProximityCorrectOn;
    protected int mProximityThresholdSquare;

    public Key[] setKeyboard(Keyboard keyboard, float correctionX, float correctionY) {
        if (keyboard == null)
            throw new NullPointerException();
        mCorrectionX = (int)correctionX;
        mCorrectionY = (int)correctionY;
        mKeyboard = keyboard;
        List<Key> keys = mKeyboard.getKeys();
        Key[] array = keys.toArray(new Key[keys.size()]);
        mKeys = array;
        return array;
    }

    protected int getTouchX(int x) {
        return x + mCorrectionX;
    }

    protected int getTouchY(int y) {
        return y + mCorrectionY;
    }

    protected Key[] getKeys() {
        if (mKeys == null)
            throw new IllegalStateException("keyboard isn't set");
        // mKeyboard is guaranteed not null at setKeybaord() method
        return mKeys;
    }

    public void setProximityCorrectionEnabled(boolean enabled) {
        mProximityCorrectOn = enabled;
    }

    public boolean isProximityCorrectionEnabled() {
        return mProximityCorrectOn;
    }

    public void setProximityThreshold(int threshold) {
        mProximityThresholdSquare = threshold * threshold;
    }

    public int[] newCodeArray() {
        int[] codes = new int[getMaxNearbyKeys()];
        Arrays.fill(codes, LatinKeyboardBaseView.NOT_A_KEY);
        return codes;
    }

    abstract protected int getMaxNearbyKeys();

    abstract public int getKeyIndexAndNearbyCodes(int x, int y, int[] allKeys);
}