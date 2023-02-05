require "test_helper"

class Api::V1::TagControllerTest < ActionDispatch::IntegrationTest
  setup do
    @user_one = User.create(name: "#{SecureRandom.hex}", email: "#{SecureRandom.hex}@example.com" ,password_digest: "password")
    @user_one_activity= @user_one.reload.activities.create(name: "#{SecureRandom.hex}", start_d: Time.zone.now, end_d: Time.zone.now, viewed: false)
    @user_one_tag = tags(:one)
  end

  class Authenticated < Api::V1::TagControllerTest

    test "should create tag" do
      assert_difference(["Tag.count"], 1) do
        post api_v1_tags_path, headers: { "Authorization": "Token token=#{@user_one.private_api_key}" }, params: { tag: { tag: @user_one_tag.tag, datetime: @user_one_tag.datetime, activity_id: @user_one_activity.id}  }
        assert_equal "application/json; charset=utf-8", @response.content_type
        assert_match  "tag", @response.body
        assert_response :created
      end
    end

  end

end
