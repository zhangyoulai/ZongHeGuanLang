package app.android.mingjiang.com.androidbootstrap.api.view;

import android.support.annotation.Nullable;

import app.android.mingjiang.com.androidbootstrap.BootstrapText;

/**
 * Views which implement this interface can set their text using BootstrapText
 */
public interface BootstrapTextView {

    String KEY = "app.android.mingjiang.com.androidbootstrap.BootstrapText";

    /**
     * Sets the view to display the given BootstrapText
     *
     * @param bootstrapText the BootstrapText
     */
    void setBootstrapText(@Nullable BootstrapText bootstrapText);

    /**
     * @return the current BootstrapText, or null if none exists
     */
    @Nullable BootstrapText getBootstrapText();

    /**
     * Sets the view to display the given markdown text, by constructing a BootstrapText. e.g.
     * "This is a {fa-stop} button"
     *
     * @param text the markdown text
     */
    void setMarkdownText(@Nullable String text);

}
