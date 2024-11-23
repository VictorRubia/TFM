require "test_helper"

class UsersControllerTest < ActionDispatch::IntegrationTest
  setup do
    @user = users(:one)
    login
  end

  # Manually modify the session into what Rodauth expects.
  def login(login: "user@example.com", password: "secret")
    post "/create-account", params: {
      "login"            => login,
      "password"         => password,
      "password-confirm" => password,
    }

    post "/login", params: {
      "floatingInput"    => login,
      "floatingPassword" => password,
    }
  end

  test "should create user" do
    assert_difference("User.count") do
      post dashboard_create_user_path, params: { user: { email: 'prueba@gmail.com', name: 'pruebaNombre', password_digest: '1234' } }
    end

    assert_redirected_to dashboard_create_user_url
  end


  test "should update user" do
    patch user_url(@user), params: { user: { email: @user.email, name: @user.name, password_digest: @user.password_digest } }
    assert_redirected_to dashboard_create_user_url
  end

  test "should destroy user" do
    assert_difference("User.count", -1) do
      delete user_url(@user)
    end

    assert_redirected_to dashboard_create_user_url
  end
end
