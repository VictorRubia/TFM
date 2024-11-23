class Api::V1::SignificantMovMeasuresController < Api::V1::BaseController
  before_action :set_significant_mov_measure, only: [:index, :create, :show, :update, :destroy]
  before_action :authorize_significant_mov_measure, only: [:show, :update, :destroy]

  def index
    @significant_mov_measures = @activity.significant_mov_measures
  end

  def show
  end

  def create
    @significant_mov_measure = SignificantMovMeasure.new(significant_mov_measure_params)
    if @significant_mov_measure.save
      render :show, status: :created
    else
      render json: { message: @significant_mov_measure.errors.full_messages }, status: :unprocessable_entity
    end
  end

  def update
    if @significant_mov_measure.update(significant_mov_measure_params)
      render :show, status: :ok
    else
      render json: { message: @significant_mov_measure.errors.full_messages }, status: :unprocessable_entity
    end
  end

  def destroy
    @significant_mov_measure.destroy
    render :show, status: :ok
  end

  private

  def set_significant_mov_measure
    @significant_mov_measure = SignificantMovMeasure.find_by(activity_id: params[:activity_id])
  end

  def authorize_significant_mov_measure
    @activity = Activity.find_by(id: SignificantMovMeasure.find_by(id: params[:id])[:activity_id])
    render json: { message: "No autorizado" }, status: :unauthorized unless @user == @activity.user
  end

  def significant_mov_measure_params
    params.require(:significant_mov_measure).permit(:measurement, :activity_id)
  end
end
