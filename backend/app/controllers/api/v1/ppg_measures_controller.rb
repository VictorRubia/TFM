class Api::V1::PpgMeasuresController < Api::V1::BaseController
  before_action :set_ppg_measure, only: [:index, :create, :show, :update, :destroy]
  before_action :authorize_ppg_measure, only: [:show, :update, :destroy]

  def index
    @ppg_measures = @activity.ppg_measures
  end

  def show
  end

  def create
    @ppg_measure = PpgMeasure.new(ppg_measure_params)
    if @ppg_measure.save
      render :show, status: :created
    else
      render json: { message: @ppg_measure.errors.full_messages }, status: :unprocessable_entity
    end
  end

  def update
    if @ppg_measure.update(ppg_measure_params)
      render :show, status: :ok
    else
      render json: { message: @ppg_measure.errors.full_messages }, status: :unprocessable_entity
    end
  end

  def destroy
    @ppg_measure.destroy
    render :show, status: :ok
  end

  private

  def set_ppg_measure
    @ppg_measure = PpgMeasure.find_by(activity_id: params[:activity_id])
  end

  def authorize_ppg_measure
    @activity = Activity.find_by(id: PpgMeasure.find_by(id: params[:id])[:activity_id])
    render json: { message: "No autorizado" }, status: :unauthorized unless @user == @activity.user
  end

  def ppg_measure_params
    params.require(:ppg_measure).permit(:measurement, :activity_id)
  end
end
