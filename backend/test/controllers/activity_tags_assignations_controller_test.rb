require "test_helper"

class ActivityTagsAssignationsControllerTest < ActionDispatch::IntegrationTest
  setup do
    @activity_tags_assignation = activity_tags_assignations(:one)
  end

  test "should get index" do
    get activity_tags_assignations_url
    assert_response :success
  end

  test "should get new" do
    get new_activity_tags_assignation_url
    assert_response :success
  end

  test "should create activity_tags_assignation" do
    assert_difference("ActivityTagsAssignation.count") do
      post activity_tags_assignations_url, params: { activity_tags_assignation: { account_id: @activity_tags_assignation.account_id, activities_repository_id: @activity_tags_assignation.activities_repository_id, tags_repository_id: @activity_tags_assignation.tags_repository_id } }
    end

    assert_redirected_to activity_tags_assignation_url(ActivityTagsAssignation.last)
  end

  test "should show activity_tags_assignation" do
    get activity_tags_assignation_url(@activity_tags_assignation)
    assert_response :success
  end

  test "should get edit" do
    get edit_activity_tags_assignation_url(@activity_tags_assignation)
    assert_response :success
  end

  test "should update activity_tags_assignation" do
    patch activity_tags_assignation_url(@activity_tags_assignation), params: { activity_tags_assignation: { account_id: @activity_tags_assignation.account_id, activities_repository_id: @activity_tags_assignation.activities_repository_id, tags_repository_id: @activity_tags_assignation.tags_repository_id } }
    assert_redirected_to activity_tags_assignation_url(@activity_tags_assignation)
  end

  test "should destroy activity_tags_assignation" do
    assert_difference("ActivityTagsAssignation.count", -1) do
      delete activity_tags_assignation_url(@activity_tags_assignation)
    end

    assert_redirected_to activity_tags_assignations_url
  end
end
