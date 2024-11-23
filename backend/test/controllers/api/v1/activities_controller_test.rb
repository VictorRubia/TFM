require "test_helper"

class Api::V1::ActivityControllerTest < ActionDispatch::IntegrationTest
  setup do
    @user_one = User.create(name: "#{SecureRandom.hex}", email: "#{SecureRandom.hex}@example.com" ,password_digest: "password")
    @user_two = User.create(name: "#{SecureRandom.hex}", email: "#{SecureRandom.hex}@example.com" ,password_digest: "password")
    @user_one_activity= @user_one.reload.activities.create(name: "#{SecureRandom.hex}", start_d: Time.zone.now, end_d: Time.zone.now, viewed: false)
    @user_two_activity = @user_two.reload.activities.create(name: "#{SecureRandom.hex}", start_d: Time.zone.now, end_d: Time.zone.now, viewed: false)
  end

  class Authenticated < Api::V1::ActivityControllerTest

    test "should handle 404" do
      get api_v1_activity_path("does_not_exist"), headers: { "Authorization": "Token token=#{@user_one.private_api_key}" }
      assert_equal "application/json; charset=utf-8", @response.content_type
      assert_response :not_found
    end

    test "should create activity" do
      assert_difference(["Activity.count"], 1) do
        post api_v1_activities_path, headers: { "Authorization": "Token token=#{@user_one.private_api_key}" }, params: { activity: { name: "#{SecureRandom.hex}", start_d: Time.zone.now, end_d: nil, viewed: false }  }
        assert_equal "application/json; charset=utf-8", @response.content_type
        assert_match  "name", @response.body
        assert_response :created
      end
    end

    test "should update activity" do
      put api_v1_activity_path(@user_one_activity), headers: { "Authorization": "Token token=#{@user_one.private_api_key}" }, params: { activity: { end_d: Time.zone.now }  }
      assert_equal "application/json; charset=utf-8", @response.content_type
      assert_match  "updated", @response.body
      assert_response :ok
    end
  end

  class Unauthorized < Api::V1::ActivityControllerTest

    test "should not get activities" do
      get api_v1_activities_path
      assert_response :unauthorized
    end

    test "should not update another's activity" do
      put api_v1_activity_path(@user_two_activity.id), headers: { "Authorization": "Token token=#{@user_one.private_api_key}" } ,params: { activity: { end_d: Time.zone.now }  }
      assert_equal "application/json; charset=utf-8", @response.content_type
      assert_response :unauthorized
    end

  end

end
