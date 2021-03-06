package io.tracee.contextlogger.profile;

import io.tracee.contextlogger.contextprovider.api.Profile;
import io.tracee.contextlogger.contextprovider.core.java.JavaThrowableContextProvider;
import io.tracee.contextlogger.testdata.ProfileSettingsBuilder;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Ignore;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * Test class for {@link io.tracee.contextlogger.profile.ProfileSettings} Created by Tobias Gindler, holisticon AG on 25.03.14.
 */
public class ProfileSettingsTest {

	// TODO TG must be fixed in maven build
	@Ignore
	@Test
	public void should_return_true_for_throwable_message_in_basic_profile() {

		ProfileSettings profileSettings = new ProfileSettings(Profile.BASIC);
		boolean shouldBeFalse = profileSettings.getPropertyValue(JavaThrowableContextProvider.class.getCanonicalName() + ".message");

		MatcherAssert.assertThat(shouldBeFalse, Matchers.equalTo(true));

	}

	@Test
	public void should_return_false_for_unknown_property_key_in_basic_profile() {

		ProfileSettings profileSettings = new ProfileSettings(Profile.BASIC);
		Boolean shouldBeFalse = profileSettings.getPropertyValue("UNKNOWN");

		MatcherAssert.assertThat(shouldBeFalse, Matchers.nullValue());

	}

	@Test
	public void should_return_null_for_null_valued_property_key_in_basic_profile() {

		ProfileSettings profileSettings = new ProfileSettings(Profile.BASIC);
		Boolean shouldBeNull = profileSettings.getPropertyValue(null);

		MatcherAssert.assertThat(shouldBeNull, Matchers.nullValue());

	}

	public void should_return_true_for_manual_override() {
		final String KEY = "KEY_UNKNOWN_IN_PROFILE";
		final boolean VALUE = true;
		final Map<String, Boolean> overrideMap = new HashMap<String, Boolean>();
		overrideMap.put(KEY, VALUE);

		ProfileSettings profileSettings = ProfileSettingsBuilder.create(Profile.NONE, overrideMap);

		boolean result = profileSettings.getPropertyValue(KEY);

		MatcherAssert.assertThat(result, Matchers.equalTo(true));

	}

	public void should_return_false_for_manual_override() {
		final String KEY = "KEY_UNKNOWN_IN_PROFILE";
		final boolean VALUE = false;
		final Map<String, Boolean> overrideMap = new HashMap<String, Boolean>();
		overrideMap.put(KEY, VALUE);

		ProfileSettings profileSettings = ProfileSettingsBuilder.create(Profile.NONE, overrideMap);
		boolean result = profileSettings.getPropertyValue(KEY);

		MatcherAssert.assertThat(result, Matchers.equalTo(false));

	}

}
