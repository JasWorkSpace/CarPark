package com.greenorange.gooutdoor.framework.widget.calligraphy;

import android.os.Build;
import android.text.TextUtils;

import com.greenorange.outdoorhelper.R;

/**
 * Created by chris on 20/12/2013
 * Project: Calligraphy
 */
public class CalligraphyConfig {

    private static CalligraphyConfig sInstance;

    /**
     * Set the default Calligraphy Config
     *
     * @param calligraphyConfig the config build using the builder.
     */
    public static void initDefault(CalligraphyConfig calligraphyConfig) {
        sInstance = calligraphyConfig;
    }

    /**
     * The current Calligraphy Config.
     * If not set it will create a default config.
     */
    public static CalligraphyConfig get() {
        if (sInstance == null)
            sInstance = new CalligraphyConfig(new Builder());
        return sInstance;
    }

    /**
     * Is a default font set?
     */
    private final boolean mIsFontSet;
    /**
     * The default Font Path if nothing else is setup.
     */
    private final String mFontPath;
    /**
     * Default Font Path Attr Id to lookup
     */
    private final int mAttrId;
    /**
     * Use Reflection to inject the private factory.
     */
    private final boolean mReflection;
    /**
     * Use Reflection to intercept CustomView inflation with the correct Context.
     */
    private final boolean mCustomViewCreation;

    protected CalligraphyConfig(Builder builder) {
        mIsFontSet = builder.isFontSet;
        mFontPath = builder.fontAssetPath;
        mAttrId = builder.attrId;
        mReflection = builder.reflection;
        mCustomViewCreation = builder.customViewCreation;
    }

    /**
     * @return mFontPath for text views might be null
     */
    public String getFontPath() {
        return mFontPath;
    }

    /**
     * @return true if set, false if null|empty
     */
    boolean isFontSet() {
        return mIsFontSet;
    }

    public boolean isReflection() {
        return mReflection;
    }

    public boolean isCustomViewCreation() {
        return mCustomViewCreation;
    }

    /**
     * @return the custom attrId to look for, -1 if not set.
     */
    public int getAttrId() {
        return mAttrId;
    }

    public static class Builder {
        /**
         * Default AttrID if not set.
         */
        public static final int INVALID_ATTR_ID = -1;
        /**
         * Use Reflection to inject the private factory. Doesn't exist pre HC. so defaults to false.
         */
        private boolean reflection = Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB;
        /**
         * Use Reflection to intercept CustomView inflation with the correct Context.
         */
        private boolean customViewCreation = true;
        /**
         * The fontAttrId to look up the font path from.
         */
        private int attrId = R.attr.fontPath;
        /**
         * Has the user set the default font path.
         */
        private boolean isFontSet = false;
        /**
         * The default fontPath
         */
        private String fontAssetPath = null;

        public Builder setFontAttrId(int fontAssetAttrId) {
            this.attrId = fontAssetAttrId != INVALID_ATTR_ID ? fontAssetAttrId : INVALID_ATTR_ID;
            return this;
        }

        public Builder setDefaultFontPath(String defaultFontAssetPath) {
            this.isFontSet = !TextUtils.isEmpty(defaultFontAssetPath);
            this.fontAssetPath = defaultFontAssetPath;
            return this;
        }


        public Builder disablePrivateFactoryInjection() {
            this.reflection = false;
            return this;
        }
        public Builder disableCustomViewInflation() {
            this.customViewCreation = false;
            return this;
        }

        public CalligraphyConfig build() {
            this.isFontSet = !TextUtils.isEmpty(fontAssetPath);
            return new CalligraphyConfig(this);
        }
    }
}
