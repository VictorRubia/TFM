require "application_system_test_case"

class AccelerometerMeasuresTest < ApplicationSystemTestCase
  setup do
    @accelerometer_measure = accelerometer_measures(:one)
  end

  test "visiting the index" do
    visit accelerometer_measures_url
    assert_selector "h1", text: "Accelerometer measures"
  end

  test "should create accelerometer measure" do
    visit accelerometer_measures_url
    click_on "New accelerometer measure"

    fill_in "Activity", with: @accelerometer_measure.activity_id
    fill_in "Measurement", with: @accelerometer_measure.measurement
    click_on "Create Accelerometer measure"

    assert_text "Accelerometer measure was successfully created"
    click_on "Back"
  end

  test "should update Accelerometer measure" do
    visit accelerometer_measure_url(@accelerometer_measure)
    click_on "Edit this accelerometer measure", match: :first

    fill_in "Activity", with: @accelerometer_measure.activity_id
    fill_in "Measurement", with: @accelerometer_measure.measurement
    click_on "Update Accelerometer measure"

    assert_text "Accelerometer measure was successfully updated"
    click_on "Back"
  end

  test "should destroy Accelerometer measure" do
    visit accelerometer_measure_url(@accelerometer_measure)
    click_on "Destroy this accelerometer measure", match: :first

    assert_text "Accelerometer measure was successfully destroyed"
  end
end
