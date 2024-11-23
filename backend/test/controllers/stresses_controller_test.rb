require "test_helper"

class StressesControllerTest < ActionDispatch::IntegrationTest
  setup do
    login
    @stress = stresses(:one)
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

  test "should create stress" do
    patch stresses(@stress), params: { stress: { activity_id: @stress.activity_id, datetime: @stress.datetime, level: @stress.level } }
    assert_redirected_to stress_url(@stress)
  end

  test "should destroy stress" do
    assert_difference("Stress.count", -1) do
      delete @stress
    end

    assert_redirected_to stresses_url
  end
end
