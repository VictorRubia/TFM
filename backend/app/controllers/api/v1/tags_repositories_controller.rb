class Api::V1::TagsRepositoriesController < Api::V1::BaseController
  before_action :set_repository, only: [:index]

  def index
  end

  private

  def set_repository
    @tags_repositories = TagsRepository.where(account_id: @user.account_id)
  end

end