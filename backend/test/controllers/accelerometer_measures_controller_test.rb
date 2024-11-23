require "test_helper"

class AccelerometerMeasuresControllerTest < ActionDispatch::IntegrationTest
  setup do
    @accelerometer_measure = accelerometer_measures(:one)
  end

  test "should get index" do
    get accelerometer_measures_url
    assert_response :success
  end

  test "should get new" do
    get new_accelerometer_measure_url
    assert_response :success
  end

  test "should create accelerometer_measure" do
    assert_difference("AccelerometerMeasure.count") do
      post accelerometer_measures_url, params: { accelerometer_measure: { activity_id: @accelerometer_measure.activity_id, measurement: @accelerometer_measure.measurement } }
    end

    assert_redirected_to accelerometer_measure_url(AccelerometerMeasure.last)
  end

  test "should show accelerometer_measure" do
    get accelerometer_measure_url(@accelerometer_measure)
    assert_response :success
  end

  test "should get edit" do
    get edit_accelerometer_measure_url(@accelerometer_measure)
    assert_response :success
  end

  test "should update accelerometer_measure" do
    patch accelerometer_measure_url(@accelerometer_measure), params: { accelerometer_measure: { activity_id: @accelerometer_measure.activity_id, measurement: @accelerometer_measure.measurement } }
    assert_redirected_to accelerometer_measure_url(@accelerometer_measure)
  end

  test "should destroy accelerometer_measure" do
    assert_difference("AccelerometerMeasure.count", -1) do
      delete accelerometer_measure_url(@accelerometer_measure)
    end

    assert_redirected_to accelerometer_measures_url
  end
end
