package io.tracee.contextlogger.profile;

import io.tracee.contextlogger.contextprovider.api.Profile;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.io.IOException;
import java.util.Properties;

/**
 * Test class for {@link io.tracee.contextlogger.contextprovider.api.Profile}.
 * Created by Tobias Gindler, holisticon AG on 14.03.14.
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest(Profile.class)
public class ProfileTest {

	public static final String PROFILE_SELECTOR_PROPERTIES = "ProfileSelector.properties";

	public static final String TEST_PROPERTY_FILENAME = "/test.property";
	public static final String TEST_PROPERTY_PROP1_KEY = "test_key";
	public static final String TEST_PROPERTY_PROP1_VALUE = "test_value";

	// CUSTOM PROPERTY FILES
	public static final String TEST_PROPERTY_INVALID_CUSTOM_PROPERTIES = "/invalid_ProfileSelector.properties";
	public static final String TEST_PROPERTY_VALID_CUSTOM_PROPERTIES = "/valid_ProfileSelector.properties";

	@Test
	public void openProperties_should_open_properties() throws IOException {

		Properties properties = ProfileLookup.openProperties(TEST_PROPERTY_FILENAME);

		MatcherAssert.assertThat(properties, Matchers.notNullValue());
		MatcherAssert.assertThat((String) properties.get(TEST_PROPERTY_PROP1_KEY), Matchers.equalTo(TEST_PROPERTY_PROP1_VALUE));

	}

	@Test
	public void openProperties_should_return_null_if_properties_cant_be_found() throws IOException {

		Properties properties = ProfileLookup.openProperties("NON_EXISTENT_FILE");
		MatcherAssert.assertThat(properties, Matchers.nullValue());

	}

	@Test
	public void openProperties_should_return_null_if_properties_is_set_to_null() throws IOException {

		Properties properties = ProfileLookup.openProperties(null);
		MatcherAssert.assertThat(properties, Matchers.nullValue());

	}

	@Test
	public void getProfileFromSystemProperties_should_return_null_for_missing_system_property() {
		System.getProperties().remove(ProfilePropertyNames.PROFILE_SET_GLOBALLY_VIA_SYSTEM_PROPERTIES);
		Profile profile = ProfileLookup.getProfileFromSystemProperties();

		MatcherAssert.assertThat(profile, Matchers.nullValue());
	}

	@Test
	public void getProfileFromSystemProperties_should_return_null_for_invalid_system_property() {
		System.getProperties().setProperty(ProfilePropertyNames.PROFILE_SET_GLOBALLY_VIA_SYSTEM_PROPERTIES, "INVALID");
		Profile profile = ProfileLookup.getProfileFromSystemProperties();

		MatcherAssert.assertThat(profile, Matchers.nullValue());
	}

	@Test
	public void getProfileFromSystemProperties_should_return_basic_profile_for_system_property() {
		System.getProperties().setProperty(ProfilePropertyNames.PROFILE_SET_GLOBALLY_VIA_SYSTEM_PROPERTIES, Profile.BASIC.name());
		Profile profile = ProfileLookup.getProfileFromSystemProperties();

		MatcherAssert.assertThat(profile, Matchers.equalTo(Profile.BASIC));

	}

	@Test
	public void getProfileFromSystemProperties_should_return_enhanced_profile_for_system_property() {
		System.getProperties().setProperty(ProfilePropertyNames.PROFILE_SET_GLOBALLY_VIA_SYSTEM_PROPERTIES, Profile.ENHANCED.name());
		Profile profile = ProfileLookup.getProfileFromSystemProperties();

		MatcherAssert.assertThat(profile, Matchers.equalTo(Profile.ENHANCED));

	}

	@Test
	public void getProfileFromSystemProperties_should_return_full_profile_for_system_property() {
		System.getProperties().setProperty(ProfilePropertyNames.PROFILE_SET_GLOBALLY_VIA_SYSTEM_PROPERTIES, Profile.FULL.name());
		Profile profile = ProfileLookup.getProfileFromSystemProperties();

		MatcherAssert.assertThat(profile, Matchers.equalTo(Profile.FULL));

	}

	@Test
	public void getProfileFromFileInClasspath_should_return_null_for_nonexisting_property_file() {

		Profile profile = ProfileLookup.getProfileFromFileInClasspath("NON_EXISTING_FILE");
		MatcherAssert.assertThat(profile, Matchers.nullValue());

	}

	@Test
	public void getProfileFromFileInClasspath_should_return_null_for_existing_and_invalid_property_file() {

		Profile profile = ProfileLookup.getProfileFromFileInClasspath(TEST_PROPERTY_INVALID_CUSTOM_PROPERTIES);
		MatcherAssert.assertThat(profile, Matchers.nullValue());

	}

	@Test
	public void getProfileFromFileInClasspath_should_return_profile_for_existing_and_valid_property_file() {

		Profile profile = ProfileLookup.getProfileFromFileInClasspath(TEST_PROPERTY_VALID_CUSTOM_PROPERTIES);
		MatcherAssert.assertThat(profile, Matchers.notNullValue());

	}

	@Test
	public void getCurrentProfile_should_return_default_profile_if_nothing_is_defined() {
		System.getProperties().remove(ProfilePropertyNames.PROFILE_SET_GLOBALLY_VIA_SYSTEM_PROPERTIES);
		Profile profile = ProfileLookup.getCurrentProfile();

		MatcherAssert.assertThat(profile, Matchers.notNullValue());
		MatcherAssert.assertThat(profile, Matchers.equalTo(ProfilePropertyNames.DEFAULT_PROFILE));
	}

	@Test
	public void getCurrentProfile_should_return_enhanced_profile_if_defined_via_system_properties() {
		System.getProperties().put(ProfilePropertyNames.PROFILE_SET_GLOBALLY_VIA_SYSTEM_PROPERTIES, Profile.ENHANCED.name());
		Profile profile = ProfileLookup.getCurrentProfile();

		MatcherAssert.assertThat(profile, Matchers.notNullValue());
		MatcherAssert.assertThat(profile, Matchers.equalTo(Profile.ENHANCED));
	}


}
