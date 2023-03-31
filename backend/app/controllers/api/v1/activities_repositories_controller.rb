class Api::V1::ActivitiesRepositoriesController < Api::V1::BaseController
  before_action :set_repository, only: [:index]

  def index
  end

  def get_activities_from_user
    @activity_assignations = ActivityAssignation.where(user_id: @user.id).includes(activities_repository: { activity_tags_assignations: :tags_repository })
  end

  private

  def set_repository
    @activities_repositories = ActivitiesRepository.where(account_id: @user.account_id)
  end

end