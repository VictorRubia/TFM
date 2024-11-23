require "test_helper"

class TagsRepositoriesControllerTest < ActionDispatch::IntegrationTest
  setup do
    @tags_repository = tags_repositories(:one)
  end

  test "should get index" do
    get tags_repositories_url
    assert_response :success
  end

  test "should get new" do
    get new_tags_repository_url
    assert_response :success
  end

  test "should create tags_repository" do
    assert_difference("TagsRepository.count") do
      post tags_repositories_url, params: { tags_repository: { name: @tags_repository.name, name_wearos: @tags_repository.name_wearos } }
    end

    assert_redirected_to tags_repository_url(TagsRepository.last)
  end

  test "should show tags_repository" do
    get tags_repository_url(@tags_repository)
    assert_response :success
  end

  test "should get edit" do
    get edit_tags_repository_url(@tags_repository)
    assert_response :success
  end

  test "should update tags_repository" do
    patch tags_repository_url(@tags_repository), params: { tags_repository: { name: @tags_repository.name, name_wearos: @tags_repository.name_wearos } }
    assert_redirected_to tags_repository_url(@tags_repository)
  end

  test "should destroy tags_repository" do
    assert_difference("TagsRepository.count", -1) do
      delete tags_repository_url(@tags_repository)
    end

    assert_redirected_to tags_repositories_url
  end
end
