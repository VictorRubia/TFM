require "application_system_test_case"

class ActivityAssignationsTest < ApplicationSystemTestCase
  setup do
    @activity_assignation = activity_assignations(:one)
  end

  test "visiting the index" do
    visit activity_assignations_url
    assert_selector "h1", text: "Activity assignations"
  end

  test "should create activity assignation" do
    visit activity_assignations_url
    click_on "New activity assignation"

    fill_in "Account", with: @activity_assignation.account_id
    fill_in "Activities repository", with: @activity_assignation.activities_repository_id
    fill_in "Tags repository", with: @activity_assignation.tags_repository_id
    fill_in "User", with: @activity_assignation.user_id
    click_on "Create Activity assignation"

    assert_text "Activity assignation was successfully created"
    click_on "Back"
  end

  test "should update Activity assignation" do
    visit activity_assignation_url(@activity_assignation)
    click_on "Edit this activity assignation", match: :first

    fill_in "Account", with: @activity_assignation.account_id
    fill_in "Activities repository", with: @activity_assignation.activities_repository_id
    fill_in "Tags repository", with: @activity_assignation.tags_repository_id
    fill_in "User", with: @activity_assignation.user_id
    click_on "Update Activity assignation"

    assert_text "Activity assignation was successfully updated"
    click_on "Back"
  end

  test "should destroy Activity assignation" do
    visit activity_assignation_url(@activity_assignation)
    click_on "Destroy this activity assignation", match: :first

    assert_text "Activity assignation was successfully destroyed"
  end
end
