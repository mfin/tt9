package io.github.sspanak.tt9.ui.main.keys;

import android.content.Context;
import android.util.AttributeSet;
import android.view.KeyEvent;

import io.github.sspanak.tt9.R;
import io.github.sspanak.tt9.languages.LanguageKind;
import io.github.sspanak.tt9.preferences.settings.SettingsStore;
import io.github.sspanak.tt9.ui.Vibration;

public class SoftKeyBackspace extends SwipeableKey {
	private int repeat = 0;

	public SoftKeyBackspace(Context context) {
		super(context);
	}

	public SoftKeyBackspace(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public SoftKeyBackspace(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
	}


	private boolean isFastDeleteOn() {
		return tt9 != null && tt9.getSettings().getBackspaceAcceleration();
	}


	/**
	 * When fast-delete is on, decrease the hold duration threshold for smoother operation.
	 */
	@Override
	protected float getHoldDurationThreshold() {
		return isFastDeleteOn() ? SettingsStore.SOFT_KEY_REPEAT_DELAY * 3 : super.getHoldDurationThreshold();
	}


	/**
	 * When fast-delete is on, prevent swapping that does nothing. It may feel frustrating if the user
	 * moves their finger slightly and the key does not delete anything.
	 */
	@Override
	protected float getSwipeXThreshold(Context context) {
		return isFastDeleteOn() ? super.getSwipeXThreshold(context) : Integer.MAX_VALUE;
	}


	/**
	 * Disable vertical swiping for backspace key.
	 */
	@Override
	protected float getSwipeYThreshold(Context context) {
		return Integer.MAX_VALUE;
	}


	@Override
	final protected boolean handlePress() {
		super.handlePress();
		return deleteText();
	}


	@Override
	final protected void handleHold() {
		repeat++;
		deleteText();
	}


	@Override
	final protected boolean handleRelease() {
		vibrate(repeat > 0 ? Vibration.getReleaseVibration() : Vibration.getNoVibration());
		repeat = 0;
		return true;
	}


	@Override
	protected void handleEndSwipeX(float position, float delta) {
		if (validateTT9Handler()) {
			tt9.onBackspace(SettingsStore.BACKSPACE_ACCELERATION_REPEAT_DEBOUNCE);
		}
	}


	private boolean deleteText() {
		if (validateTT9Handler() && !tt9.onBackspace(repeat)) {
			// Limited or special numeric field (e.g. formatted money or dates) cannot always return
			// the text length, therefore onBackspace() seems them as empty and does nothing. This results
			// in fallback to the default hardware key action. Here we simulate the hardware BACKSPACE.
			tt9.sendDownUpKeyEvents(KeyEvent.KEYCODE_DEL);
			return true;
		}

		return false;
	}


	@Override
	protected int getNoEmojiTitle() {
		return R.string.virtual_key_del;
	}


	@Override
	protected String getTitle() {
		return LanguageKind.isRTL(tt9 != null ? tt9.getLanguage() : null) ? "⌦" : "⌫";
	}


	@Override
	public void render() {
		super.render();
		if (tt9 != null) {
			setEnabled(!tt9.isVoiceInputActive());
		}
	}
}
