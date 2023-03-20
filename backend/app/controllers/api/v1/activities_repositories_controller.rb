class Api::V1::ActivitiesRepositoriesController < Api::V1::BaseController
  before_action :set_repository, only: [:index]
  before_action :activities_from_user, only: [:get_activities_from_user]

  def index
  end

  def get_activities_from_user

  end

  private

  def set_repository
    @activities_repositories = ActivitiesRepository.where(account_id: @user.account_id)
  end

  def activities_from_user
    @activitie
  end

end