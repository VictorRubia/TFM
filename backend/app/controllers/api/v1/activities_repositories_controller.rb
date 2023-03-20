class Api::V1::ActivitiesRepositoriesController < Api::V1::BaseController
  before_action :set_repository, only: [:index]
  before_action :activities_from_user, only: [:get_activities_from_user]

  def index
  end

  def get_activities_from_user
    @activity_assignations = ActivityAssignation.where(user_id: params[:user_id]).includes(activities_repository: { activity_tags_assignations: :tags_repository })
  end

  private

  def set_repository
    @activities_repositories = ActivitiesRepository.where(account_id: @user.account_id)
  end

  def activities_from_user
    @activities_assigned_to_user = ActivityAssignation.where(user_id: params[:user_id])
    @activities = []
    @activities_assigned_to_user.each do |activity|
      @activities << ActivitiesRepository.find(activity.activities_repository_id)
    end
  end


end