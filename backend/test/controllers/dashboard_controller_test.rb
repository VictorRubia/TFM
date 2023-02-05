require "test_helper"

class DashboardControllerTest < ActionDispatch::IntegrationTest
  setup do
    @user = users(:one)
    @activity = activities(:one)
  end

  private

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

  def logout
    post "/logout"
  end

  test "required authentication" do
    get dashboard_index_url

    assert_response 302
    assert_redirected_to "/login"
    assert_equal "Please login to continue", flash[:alert]

    login

    get dashboard_index_url
    assert_response 200

    logout

    get dashboard_index_url
    assert_response 302
    assert_equal "Please login to continue", flash[:alert]
  end

  test "should get index" do
    login
    get dashboard_index_url
    assert_response :success
  end

  test "should get users" do
    login
    get dashboard_create_user_url
    assert_response :success
  end

  test "should get activities" do
    login
    get dashboard_search_user_activity_url
    assert_response :success
  end

  test "should get view_activities" do
    login
    get dashboard_view_activity_url(@user.id)
    assert_response :success
  end

  test "should get activity_details" do
    login
    get dashboard_activity_details_url(id: @user.id, id_activity: @activity.id)
    assert_response :success
  end

end
