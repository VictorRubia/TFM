require "test_helper"

class ActivitiesControllerTest < ActionDispatch::IntegrationTest
  setup do
    @activity = activities(:one)
  end

  test "should update activity" do
    patch activity_url(@activity), params: { activity: { end_d: @activity.end_d, name: @activity.name, user_id: @activity.user_id, start_d: @activity.start_d } }
    assert_redirected_to activity_url(@activity)
  end

  test "should destroy activity" do
    assert_difference("Activity.count", -1) do
      delete activity_url(@activity)
    end

    assert_redirected_to dashboard_view_activity_url
  end
end
