require "test_helper"

class Api::V1::PpgMeasureControllerTest < ActionDispatch::IntegrationTest
  setup do
    @user_one = User.create(name: "#{SecureRandom.hex}", email: "#{SecureRandom.hex}@example.com" ,password_digest: "password")
    @user_one_activity= @user_one.reload.activities.create(name: "#{SecureRandom.hex}", start_d: Time.zone.now, end_d: Time.zone.now, viewed: false)
    @user_one_ppg_measure = ppg_measures(:one)
  end

  class Authenticated < Api::V1::PpgMeasureControllerTest

    test "should create ppg measure" do
      assert_difference(["PpgMeasure.count"], 1) do
        post api_v1_ppg_measures_path, headers: { "Authorization": "Token token=#{@user_one.private_api_key}" }, params: { ppg_measure: { measurement: @user_one_ppg_measure.measurement, activity_id: @user_one_activity.id}  }
        assert_equal "application/json; charset=utf-8", @response.content_type
        assert_match  "measurement", @response.body
        assert_response :created
      end
    end

  end

end
