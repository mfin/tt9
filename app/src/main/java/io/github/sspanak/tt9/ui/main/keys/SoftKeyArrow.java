package io.github.sspanak.tt9.ui.main.keys;

import android.content.Context;
import android.util.AttributeSet;

import io.github.sspanak.tt9.R;

public class SoftKeyArrow extends SoftKey {
	public SoftKeyArrow(Context context) { super(context); }
	public SoftKeyArrow(Context context, AttributeSet attrs) { super(context, attrs); }
	public SoftKeyArrow(Context context, AttributeSet attrs, int defStyleAttr) { super(context, attrs, defStyleAttr); }

	@Override
	protected boolean handleRelease() {
		return handleHold();
	}

	@Override
	protected boolean handleHold() {
		if (!validateTT9Handler()) {
			return false;
		}

		int keyId = getId();
		if (keyId == R.id.soft_key_left_arrow) return onLeft();
		if (keyId == R.id.soft_key_right_arrow) return onRight();

		return false;
	}

	private boolean onLeft() {
		return tt9.onKeyScrollSuggestion(false, true) || tt9.onKeyMoveCursor(true);
	}

	private boolean onRight() {
		return tt9.onKeyScrollSuggestion(false, false) || tt9.onKeyMoveCursor(false);
	}
}