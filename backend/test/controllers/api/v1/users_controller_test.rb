require "test_helper"

class Api::V1::UsersControllerTest < ActionDispatch::IntegrationTest
  setup do
    @user_one = User.create(name: "#{SecureRandom.hex}", email: "#{SecureRandom.hex}@example.com" ,password_digest: "password")
    @user_two = User.create(name: "#{SecureRandom.hex}", email: "#{SecureRandom.hex}@example.com" ,password_digest: "password")
  end

  class Authenticated < Api::V1::UsersControllerTest

    test "should get user" do
      get api_v1_users_path, headers: { "Authorization": "Token token=#{@user_one.private_api_key}" }
      assert_equal "application/json; charset=utf-8", @response.content_type
      assert_match  "email", @response.body
      assert_response :ok
    end

    test "should get user's apiKey" do
      get api_v1_users_get_api_key_path, params: { email: @user_one.email, password_digest: @user_one.password_digest }
      assert_equal "application/json; charset=utf-8", @response.content_type
      assert_match  "email", @response.body
      assert_response :ok
    end

  end

  class Unauthorized < Api::V1::UsersControllerTest

    test "should not get user" do
      get api_v1_users_path
      assert_response :unauthorized
    end
  end

end
