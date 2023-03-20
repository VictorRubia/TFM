require "test_helper"

class ActivitiesRepositoriesControllerTest < ActionDispatch::IntegrationTest
  setup do
    @activities_repository = activities_repositories(:one)
  end

  test "should get index" do
    get activities_repositories_url
    assert_response :success
  end

  test "should get new" do
    get new_activities_repository_url
    assert_response :success
  end

  test "should create activities_repository" do
    assert_difference("ActivitiesRepository.count") do
      post activities_repositories_url, params: { activities_repository: { account_id: @activities_repository.account_id, name: @activities_repository.name, name_wearos: @activities_repository.name_wearos } }
    end

    assert_redirected_to activities_repository_url(ActivitiesRepository.last)
  end

  test "should show activities_repository" do
    get activities_repository_url(@activities_repository)
    assert_response :success
  end

  test "should get edit" do
    get edit_activities_repository_url(@activities_repository)
    assert_response :success
  end

  test "should update activities_repository" do
    patch activities_repository_url(@activities_repository), params: { activities_repository: { account_id: @activities_repository.account_id, name: @activities_repository.name, name_wearos: @activities_repository.name_wearos } }
    assert_redirected_to activities_repository_url(@activities_repository)
  end

  test "should destroy activities_repository" do
    assert_difference("ActivitiesRepository.count", -1) do
      delete activities_repository_url(@activities_repository)
    end

    assert_redirected_to activities_repositories_url
  end
end
