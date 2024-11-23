require "test_helper"

class PpgMeasuresControllerTest < ActionDispatch::IntegrationTest
  setup do
    login
    @ppg_measure = ppg_measures(:one)
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

  def logout
    post "/logout"
  end

  test "should update ppg_measure" do
    patch ppg_measure_url(@ppg_measure), params: { ppg_measure: { activity_id: @ppg_measure.activity_id, measurement: @ppg_measure.measurement } }
  end

  test "should destroy ppg_measure" do
    login
    assert_difference("PpgMeasure.count", -1) do
      delete ppg_measure_path(@ppg_measure)
      assert_redirected_to ppg_measures_url
    end
  end
end
