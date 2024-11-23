require "application_system_test_case"

class ActivityTagsAssignationsTest < ApplicationSystemTestCase
  setup do
    @activity_tags_assignation = activity_tags_assignations(:one)
  end

  test "visiting the index" do
    visit activity_tags_assignations_url
    assert_selector "h1", text: "Activity tags assignations"
  end

  test "should create activity tags assignation" do
    visit activity_tags_assignations_url
    click_on "New activity tags assignation"

    fill_in "Account", with: @activity_tags_assignation.account_id
    fill_in "Activities repository", with: @activity_tags_assignation.activities_repository_id
    fill_in "Tags repository", with: @activity_tags_assignation.tags_repository_id
    click_on "Create Activity tags assignation"

    assert_text "Activity tags assignation was successfully created"
    click_on "Back"
  end

  test "should update Activity tags assignation" do
    visit activity_tags_assignation_url(@activity_tags_assignation)
    click_on "Edit this activity tags assignation", match: :first

    fill_in "Account", with: @activity_tags_assignation.account_id
    fill_in "Activities repository", with: @activity_tags_assignation.activities_repository_id
    fill_in "Tags repository", with: @activity_tags_assignation.tags_repository_id
    click_on "Update Activity tags assignation"

    assert_text "Activity tags assignation was successfully updated"
    click_on "Back"
  end

  test "should destroy Activity tags assignation" do
    visit activity_tags_assignation_url(@activity_tags_assignation)
    click_on "Destroy this activity tags assignation", match: :first

    assert_text "Activity tags assignation was successfully destroyed"
  end
end
