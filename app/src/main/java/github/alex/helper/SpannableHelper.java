package github.alex.helper;

import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;

/**
 * Created by hasee on 2016/6/25.
 */
public class SpannableHelper {
    private SpannableString spannableString;
    private ForegroundColorSpan foregroundColorSpan;
    private String text;

    public SpannableHelper(String text) {
        this.text = text;
        spannableString = new SpannableString(text);
    }

    public SpannableHelper foregroundColor(int foregroundColor) {
        if (foregroundColorSpan == null) {
            foregroundColorSpan = new ForegroundColorSpan(foregroundColor);
        }

        return this;
    }
}
