class Api::V1::ActivitiesRepositoriesController < Api::V1::BaseController
  before_action :set_repository, only: [:index]

  def index
  end

  private

  def set_repository
    @activities_repositories = ActivitiesRepository.where(account_id: @user.account_id)
  end

end