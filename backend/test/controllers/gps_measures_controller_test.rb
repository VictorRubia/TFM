require "test_helper"

class GpsMeasuresControllerTest < ActionDispatch::IntegrationTest
  setup do
    @gps_measure = gps_measures(:one)
  end

  test "should get index" do
    get gps_measures_url
    assert_response :success
  end

  test "should get new" do
    get new_gps_measure_url
    assert_response :success
  end

  test "should create gps_measure" do
    assert_difference("GpsMeasure.count") do
      post gps_measures_url, params: { gps_measure: { activity_id: @gps_measure.activity_id, measurement: @gps_measure.measurement } }
    end

    assert_redirected_to gps_measure_url(GpsMeasure.last)
  end

  test "should show gps_measure" do
    get gps_measure_url(@gps_measure)
    assert_response :success
  end

  test "should get edit" do
    get edit_gps_measure_url(@gps_measure)
    assert_response :success
  end

  test "should update gps_measure" do
    patch gps_measure_url(@gps_measure), params: { gps_measure: { activity_id: @gps_measure.activity_id, measurement: @gps_measure.measurement } }
    assert_redirected_to gps_measure_url(@gps_measure)
  end

  test "should destroy gps_measure" do
    assert_difference("GpsMeasure.count", -1) do
      delete gps_measure_url(@gps_measure)
    end

    assert_redirected_to gps_measures_url
  end
end
