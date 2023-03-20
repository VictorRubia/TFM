require "application_system_test_case"

class ActivitiesRepositoriesTest < ApplicationSystemTestCase
  setup do
    @activities_repository = activities_repositories(:one)
  end

  test "visiting the index" do
    visit activities_repositories_url
    assert_selector "h1", text: "Activities repositories"
  end

  test "should create activities repository" do
    visit activities_repositories_url
    click_on "New activities repository"

    fill_in "Account", with: @activities_repository.account_id
    fill_in "Name", with: @activities_repository.name
    fill_in "Name wearos", with: @activities_repository.name_wearos
    click_on "Create Activities repository"

    assert_text "Activities repository was successfully created"
    click_on "Back"
  end

  test "should update Activities repository" do
    visit activities_repository_url(@activities_repository)
    click_on "Edit this activities repository", match: :first

    fill_in "Account", with: @activities_repository.account_id
    fill_in "Name", with: @activities_repository.name
    fill_in "Name wearos", with: @activities_repository.name_wearos
    click_on "Update Activities repository"

    assert_text "Activities repository was successfully updated"
    click_on "Back"
  end

  test "should destroy Activities repository" do
    visit activities_repository_url(@activities_repository)
    click_on "Destroy this activities repository", match: :first

    assert_text "Activities repository was successfully destroyed"
  end
end
