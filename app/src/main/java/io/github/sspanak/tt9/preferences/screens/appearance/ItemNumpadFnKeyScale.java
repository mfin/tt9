package io.github.sspanak.tt9.preferences.screens.appearance;

import androidx.preference.DropDownPreference;

import java.util.LinkedHashMap;

import io.github.sspanak.tt9.preferences.items.ItemDropDown;
import io.github.sspanak.tt9.preferences.settings.SettingsStore;

public class ItemNumpadFnKeyScale extends ItemDropDown implements ItemLayoutChangeReactive {
	public static final String NAME = "pref_numpad_fn_key_width";

	private final SettingsStore settings;

	ItemNumpadFnKeyScale(DropDownPreference item, SettingsStore settings) {
		super(item);
		this.settings = settings;
	}

	@Override
	public ItemDropDown populate() {
		if (item == null) {
			return this;
		}

		LinkedHashMap<String, String> options = new LinkedHashMap<>();
		options.put("1", "100 ％");
		options.put("0.85", "115 ％");
		options.put("0.75", "125 ％");
		options.put("0.675", "135 ％");
		options.put("0.576", "145 ％");
		options.put("0.477", "155 ％"); // whatever...

		super.populate(options);
		super.setValue(getClosestOption(settings.getNumpadFnKeyScale(), options));
		onLayoutChange(settings.getMainViewLayout());

		return this;
	}

	private String getClosestOption(float value, LinkedHashMap<String, String> options) {
		float minDiff = Float.MAX_VALUE;
		String closest = null;

		for (String key : options.keySet()) {
			float fKey = Float.parseFloat(key);
			float diff = Math.abs(value - fKey);

			if (diff < minDiff) {
				minDiff = diff;
				closest = key;
			}
		}

		return closest;
	}

	public void onLayoutChange(int mainViewLayout) {
		if (item != null) {
			item.setVisible(mainViewLayout == SettingsStore.LAYOUT_NUMPAD);
			item.setIconSpaceReserved(false);
		}
	}
}
