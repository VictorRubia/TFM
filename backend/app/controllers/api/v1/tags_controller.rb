class Api::V1::TagsController < Api::V1::BaseController
  # before_action :set_tag, only: [:index, :create, :update, :destroy]
  before_action :authorize_tag, only: [:show, :update, :destroy]
  before_action :set_tag, only: [:show, :update, :destroy]

  def index
    @tags = Tag.all
  end

  def show
  end

  def create
    @tag = Tag.new(tag_params)
    if @tag.save
      render :show, status: :created
    else
      render json: { message: @tag.errors.full_messages }, status: :unprocessable_entity
    end
  end

  def update
    if @tag.update(tag_params)
      render :show, status: :ok
    else
      render json: { message: @tag.errors.full_messages }, status: :unprocessable_entity
    end
  end

  def destroy
    @tag.destroy
    render :show, status: :ok
  end

  private

  def set_tag
    # @tag = Tag.find_by(activity_id: params[:activity_id])
    @tag = Tag.find(params[:id])
  end

  def authorize_tag
    @activity = Activity.find_by(id: Tag.find_by(id: params[:id])[:activity_id])
    render json: { message: "No autorizado" }, status: :unauthorized unless @user == @activity.user
  end

  def tag_params
    params.require(:tag).permit(:tag, :datetime, :activity_id)
  end
end
