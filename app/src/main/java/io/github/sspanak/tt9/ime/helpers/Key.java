package io.github.sspanak.tt9.ime.helpers;

import android.view.KeyEvent;

import java.util.HashMap;

import io.github.sspanak.tt9.preferences.settings.SettingsStore;
import io.github.sspanak.tt9.util.Ternary;

public class Key {
	private static final HashMap<Integer, Ternary> handledKeys = new HashMap<>();


	public static void setHandled(int keyCode, Ternary handled) {
		handledKeys.put(keyCode, handled);
	}


	public static boolean setHandled(int keyCode, boolean handled) {
		handledKeys.put(keyCode, handled ? Ternary.TRUE : Ternary.FALSE);
		return handled;
	}


	public static boolean isHandled(int keyCode) {
		return handledKeys.containsKey(keyCode) && handledKeys.get(keyCode) == Ternary.TRUE;
	}


	public static boolean isHandledInSuper(int keyCode) {
		return handledKeys.containsKey(keyCode) && handledKeys.get(keyCode) == Ternary.ALTERNATIVE;
	}


	public static boolean isBackspace(SettingsStore settings, int keyCode) {
		return
			keyCode == KeyEvent.KEYCODE_DEL
			|| keyCode == KeyEvent.KEYCODE_CLEAR
			|| keyCode == settings.getKeyBackspace();
	}


	public static boolean isNumber(int keyCode) {
		return
			(keyCode >= KeyEvent.KEYCODE_0 && keyCode <= KeyEvent.KEYCODE_9)
			|| (keyCode >= KeyEvent.KEYCODE_NUMPAD_0 && keyCode <= KeyEvent.KEYCODE_NUMPAD_9);
	}


	public static boolean isHotkey(SettingsStore settings, int keyCode) {
		return
			keyCode == settings.getKeyAddWord()
			|| keyCode == settings.getKeyBackspace()
			|| keyCode == settings.getKeyCommandPalette()
			|| keyCode == settings.getKeyEditText()
			|| keyCode == settings.getKeyFilterClear()
			|| keyCode == settings.getKeyFilterSuggestions()
			|| keyCode == settings.getKeyPreviousSuggestion()
			|| keyCode == settings.getKeyNextSuggestion()
			|| keyCode == settings.getKeyNextInputMode()
			|| keyCode == settings.getKeyNextLanguage()
			|| keyCode == settings.getKeySelectKeyboard()
			|| keyCode == settings.getKeyShift()
			|| keyCode == settings.getKeyShowSettings()
			|| keyCode == settings.getKeyVoiceInput();
	}


	public static boolean isBack(int keyCode) {
		return keyCode == KeyEvent.KEYCODE_BACK;
	}


	public static boolean isPoundOrStar(int keyCode) {
		return keyCode == KeyEvent.KEYCODE_POUND || keyCode == KeyEvent.KEYCODE_STAR;
	}


	public static boolean isOK(int keyCode) {
		return
			keyCode == KeyEvent.KEYCODE_DPAD_CENTER
			|| keyCode == KeyEvent.KEYCODE_ENTER
			|| keyCode == KeyEvent.KEYCODE_NUMPAD_ENTER;
	}


	public static int codeToNumber(SettingsStore settings, int keyCode) {
		return switch (keyCode) {
			case KeyEvent.KEYCODE_0, KeyEvent.KEYCODE_NUMPAD_0 -> 0;
			case KeyEvent.KEYCODE_1, KeyEvent.KEYCODE_NUMPAD_1 -> settings.getUpsideDownKeys() ? 7 : 1;
			case KeyEvent.KEYCODE_2, KeyEvent.KEYCODE_NUMPAD_2 -> settings.getUpsideDownKeys() ? 8 : 2;
			case KeyEvent.KEYCODE_3, KeyEvent.KEYCODE_NUMPAD_3 -> settings.getUpsideDownKeys() ? 9 : 3;
			case KeyEvent.KEYCODE_4, KeyEvent.KEYCODE_NUMPAD_4 -> 4;
			case KeyEvent.KEYCODE_5, KeyEvent.KEYCODE_NUMPAD_5 -> 5;
			case KeyEvent.KEYCODE_6, KeyEvent.KEYCODE_NUMPAD_6 -> 6;
			case KeyEvent.KEYCODE_7, KeyEvent.KEYCODE_NUMPAD_7 -> settings.getUpsideDownKeys() ? 1 : 7;
			case KeyEvent.KEYCODE_8, KeyEvent.KEYCODE_NUMPAD_8 -> settings.getUpsideDownKeys() ? 2 : 8;
			case KeyEvent.KEYCODE_9, KeyEvent.KEYCODE_NUMPAD_9 -> settings.getUpsideDownKeys() ? 3 : 9;
			default -> -1;
		};
	}

	public static int numberToCode(int number) {
		if (number >= 0 && number <= 9) {
			return KeyEvent.KEYCODE_0 + number;
		} else {
			return -1;
		}
	}
}
