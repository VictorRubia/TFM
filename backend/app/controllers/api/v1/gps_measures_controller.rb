class Api::V1::GpsMeasuresController < Api::V1::BaseController
  before_action :set_gps_measure, only: [:index, :create, :show, :update, :destroy]
  before_action :authorize_gps_measure, only: [:show, :update, :destroy]

  def index
    @gps_measures = @activity.gps_measures
  end

  def show
  end

  def create
    @gps_measure = GpsMeasure.new(gps_measure_params)
    if @gps_measure.save
      render :show, status: :created
    else
      render json: { message: @gps_measure.errors.full_messages }, status: :unprocessable_entity
    end
  end

  def update
    if @gps_measure.update(gps_measure_params)
      render :show, status: :ok
    else
      render json: { message: @gps_measure.errors.full_messages }, status: :unprocessable_entity
    end
  end

  def destroy
    @gps_measure.destroy
    render :show, status: :ok
  end

  private

  def set_gps_measure
    @gps_measure = GpsMeasure.find_by(activity_id: params[:activity_id])
  end

  def authorize_gps_measure
    @activity = Activity.find_by(id: GpsMeasure.find_by(id: params[:id])[:activity_id])
    render json: { message: "No autorizado" }, status: :unauthorized unless @user == @activity.user
  end

  def gps_measure_params
    params.require(:gps_measure).permit(:measurement, :activity_id)
  end
end
