require "application_system_test_case"

class TagsRepositoriesTest < ApplicationSystemTestCase
  setup do
    @tags_repository = tags_repositories(:one)
  end

  test "visiting the index" do
    visit tags_repositories_url
    assert_selector "h1", text: "Tags repositories"
  end

  test "should create tags repository" do
    visit tags_repositories_url
    click_on "New tags repository"

    fill_in "Image data", with: @tags_repository.image_data
    fill_in "Name", with: @tags_repository.name
    fill_in "Name wearos", with: @tags_repository.name_wearos
    click_on "Create Tags repository"

    assert_text "Tags repository was successfully created"
    click_on "Back"
  end

  test "should update Tags repository" do
    visit tags_repository_url(@tags_repository)
    click_on "Edit this tags repository", match: :first

    fill_in "Image data", with: @tags_repository.image_data
    fill_in "Name", with: @tags_repository.name
    fill_in "Name wearos", with: @tags_repository.name_wearos
    click_on "Update Tags repository"

    assert_text "Tags repository was successfully updated"
    click_on "Back"
  end

  test "should destroy Tags repository" do
    visit tags_repository_url(@tags_repository)
    click_on "Destroy this tags repository", match: :first

    assert_text "Tags repository was successfully destroyed"
  end
end
