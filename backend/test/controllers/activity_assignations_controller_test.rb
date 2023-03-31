require "test_helper"

class ActivityAssignationsControllerTest < ActionDispatch::IntegrationTest
  setup do
    @activity_assignation = activity_assignations(:one)
  end

  test "should get index" do
    get activity_assignations_url
    assert_response :success
  end

  test "should get new" do
    get new_activity_assignation_url
    assert_response :success
  end

  test "should create activity_assignation" do
    assert_difference("ActivityAssignation.count") do
      post activity_assignations_url, params: { activity_assignation: { account_id: @activity_assignation.account_id, activities_repository_id: @activity_assignation.activities_repository_id, tags_repository_id: @activity_assignation.tags_repository_id, user_id: @activity_assignation.user_id } }
    end

    assert_redirected_to activity_assignation_url(ActivityAssignation.last)
  end

  test "should show activity_assignation" do
    get activity_assignation_url(@activity_assignation)
    assert_response :success
  end

  test "should get edit" do
    get edit_activity_assignation_url(@activity_assignation)
    assert_response :success
  end

  test "should update activity_assignation" do
    patch activity_assignation_url(@activity_assignation), params: { activity_assignation: { account_id: @activity_assignation.account_id, activities_repository_id: @activity_assignation.activities_repository_id, tags_repository_id: @activity_assignation.tags_repository_id, user_id: @activity_assignation.user_id } }
    assert_redirected_to activity_assignation_url(@activity_assignation)
  end

  test "should destroy activity_assignation" do
    assert_difference("ActivityAssignation.count", -1) do
      delete activity_assignation_url(@activity_assignation)
    end

    assert_redirected_to activity_assignations_url
  end
end
