require "application_system_test_case"

class GpsMeasuresTest < ApplicationSystemTestCase
  setup do
    @gps_measure = gps_measures(:one)
  end

  test "visiting the index" do
    visit gps_measures_url
    assert_selector "h1", text: "Gps measures"
  end

  test "should create gps measure" do
    visit gps_measures_url
    click_on "New gps measure"

    fill_in "Activity", with: @gps_measure.activity_id
    fill_in "Measurement", with: @gps_measure.measurement
    click_on "Create Gps measure"

    assert_text "Gps measure was successfully created"
    click_on "Back"
  end

  test "should update Gps measure" do
    visit gps_measure_url(@gps_measure)
    click_on "Edit this gps measure", match: :first

    fill_in "Activity", with: @gps_measure.activity_id
    fill_in "Measurement", with: @gps_measure.measurement
    click_on "Update Gps measure"

    assert_text "Gps measure was successfully updated"
    click_on "Back"
  end

  test "should destroy Gps measure" do
    visit gps_measure_url(@gps_measure)
    click_on "Destroy this gps measure", match: :first

    assert_text "Gps measure was successfully destroyed"
  end
end
